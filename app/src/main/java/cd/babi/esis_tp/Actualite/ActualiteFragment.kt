package cd.babi.esis_tp.Actualite

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cd.babi.esis_tp.R
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class ActualiteFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_actualite, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = ArticleAdapter()
        recyclerView.adapter = adapter
        progressBar = view.findViewById(R.id.progressBar)

        ScrapeTask().execute()
        return view
    }

    private inner class ScrapeTask : AsyncTask<Void, Void, List<Article>>() {
        override fun doInBackground(vararg params: Void?): List<Article> {
            return scrapeArticles()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
            //progressBar.startAnimation(AnimationUtils.loadAnimation(requireActivity(), android.R.anim.fade_in))

        }
        override fun onPostExecute(result: List<Article>?) {
            super.onPostExecute(result)
            if (result != null) {
                progressBar.visibility = View.GONE
               // progressBar.startAnimation(AnimationUtils.loadAnimation(requireActivity(), android.R.anim.fade_out))
                adapter.submitList(result)
            }
        }
    }


    fun scrapeArticles(): List<Article> {
        val articles = mutableListOf<Article>()

        try {
            val url = "https://www.esisalama.com/index.php?module=actualite/"
            val doc = Jsoup.connect(url).get()
            val data: Elements = doc.select("div.liste-actualites")
            val size = data.size
            // Toast.makeText(this, "$size", Toast.LENGTH_SHORT).show()
            for (i in 0 until size) {
                var imgUrl = data.select("div.liste-actualites")
                    .select("img.img-thumbnail")
                    .eq(i)
                    .attr("src")
                //faire un test pour savoir si l'image est vide
                if (imgUrl == "") {
                    //log pour savoir si l'image est vide
                    Log.d("imgUrl", "imgUrl: $imgUrl")
                } else {
                    //log pour savoir si l'image n'est pas vide ajouter ce https://www.esisalama.com/ pour avoir l'url complet
                    imgUrl = "https://www.esisalama.com/$imgUrl"
                    Log.d("imgUrl", "imgUrl: $imgUrl")
                }
                val title = data.select("h3.text-center").select("a").eq(i).text()
                val detailUrl = data.select("h3.text-center")
                    .select("a")
                    .eq(i)
                    .attr("href")
                val article = Article(title, imgUrl, detailUrl)
                articles.add(article)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }

        return articles
    }


}
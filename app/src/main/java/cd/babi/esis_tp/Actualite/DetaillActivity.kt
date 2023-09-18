package cd.babi.esis_tp.Actualite

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cd.babi.esis_tp.R
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import java.io.IOException

class DetaillActivity : AppCompatActivity() {

    private var detailString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detaill)
        val titre = intent.getStringExtra("titre")
        val image = intent.getStringExtra("image")
        val image_detail = findViewById<ImageView>(R.id.image_detail)
        Picasso.get().load(image).into(image_detail)
        supportActionBar?.title = titre
        findViewById<TextView>(R.id.titre).setText(titre)
        ScrapeTask().execute()


    }

    private inner class ScrapeTask : AsyncTask<Void, Void,Void>() {
        override fun doInBackground(vararg params: Void?):Void? {
            try {
                val baseUrl = "https://www.esisalama.com/index.php"
                //https://www.esisalama.com/index.php?module=actualite&id_actu=90
                val detailUrl = intent.getStringExtra("detailUrl")
                val url = baseUrl + detailUrl
                //faire un log pour voir si l'url est bien formé
                // Log.d("LienActualite", url);
                Log.d("LienActualite", "LienActualite: $url")
                val doc = Jsoup.connect(url).get()
                val data = doc.select("p#contenu-actualite")
                Log.d("contenue", "contenue: $data")
                detailString = data.select("p#contenu-actualite")
                    .text()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
          //  progressBar.visibility = View.VISIBLE
            //progressBar.startAnimation(AnimationUtils.loadAnimation(requireActivity(), android.R.anim.fade_in))

        }
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val detail = findViewById<TextView>(R.id.descrption_detail)
            detail.setText(detailString)
            if (result != null) {
                val detail = findViewById<TextView>(R.id.descrption_detail)
                detail.setText(detailString)
             //   progressBar.visibility = View.GONE
                // progressBar.startAnimation(AnimationUtils.loadAnimation(requireActivity(), android.R.anim.fade_out))
                //adapter.submitList(result)
            }
        }
    }

}
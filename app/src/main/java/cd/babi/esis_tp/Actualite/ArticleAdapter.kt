package cd.babi.esis_tp.Actualite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cd.babi.esis_tp.R
import com.squareup.picasso.Picasso

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.actualite_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        //la ligne ci-dessous est pour le click sur un item de la liste
        holder.itemView.setOnClickListener {
            //Toast.makeText(holder.itemView.context, "Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, DetaillActivity::class.java)
            intent.putExtra("titre", article.title)
            intent.putExtra("detailUrl", article.detailurl)
            intent.putExtra("image", article.image)
            holder.itemView.context.startActivity(intent)
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textView)
        private val imageview: ImageView = itemView.findViewById(R.id.imageView)
      //  private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)

        fun bind(article: Article) {
            titleTextView.text = article.title
            Picasso.get().load(article.image).into(imageview)

        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}
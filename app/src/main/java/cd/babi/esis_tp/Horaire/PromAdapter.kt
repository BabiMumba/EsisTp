package cd.babi.esis_tp.Horaire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cd.babi.esis_tp.R

class PromAdapter(private val promList: List<PromModel>) : RecyclerView.Adapter<PromAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filiere, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prom = promList[position]
        holder.bind(prom)
    }

    override fun getItemCount(): Int {
        return promList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val filiereNameTextView: TextView = itemView.findViewById(R.id.filiereNameTextView)
        private val positionTextView: TextView = itemView.findViewById(R.id.positionTextView)

        fun bind(prom: PromModel) {
            filiereNameTextView.text = prom.promotion
            positionTextView.text = (adapterPosition + 1).toString()
        }
    }
}
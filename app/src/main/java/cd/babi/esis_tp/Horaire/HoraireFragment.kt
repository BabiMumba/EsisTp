package cd.babi.esis_tp.Horaire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cd.babi.esis_tp.R

class HoraireFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PromAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_horaire, container, false)

        // Création de la liste de filières
        val filieres = createFiliereList()

        adapter = PromAdapter(filieres)
        recyclerView.adapter = adapter

        return view
    }

    private fun createFiliereList(): List<PromModel> {
        val filieres = mutableListOf<PromModel>()

        // Ajout de 10 filières à la liste
        for (i in 1..10) {
            val filiere = PromModel("Promotion $i", "Abbr$i")
            filieres.add(filiere)
        }

        return filieres
    }
}
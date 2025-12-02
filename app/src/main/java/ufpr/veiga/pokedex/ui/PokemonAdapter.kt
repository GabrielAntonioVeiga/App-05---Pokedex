package ufpr.veiga.pokedex.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ufpr.veiga.pokedex.R
import ufpr.veiga.pokedex.dto.PokemonRequest

class PokemonAdapter(
    private var lista: List<PokemonRequest>,
    private val onItemClick: (PokemonRequest) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nome: TextView = v.findViewById(R.id.txtNomePokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = lista[position]
        holder.nome.text = pokemon.nome
        holder.itemView.setOnClickListener {
            onItemClick(pokemon)
        }
    }

    fun update(newList: List<PokemonRequest>) {
        lista = newList
        notifyDataSetChanged()
    }
}

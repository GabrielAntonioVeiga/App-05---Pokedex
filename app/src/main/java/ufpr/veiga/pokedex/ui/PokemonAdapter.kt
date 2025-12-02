package ufpr.veiga.pokedex.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ufpr.veiga.pokedex.databinding.ItemPokemonBinding
import ufpr.veiga.pokedex.dto.PokemonRequest

class PokemonAdapter(
    private val onItemClick: (PokemonRequest) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var pokemons: List<PokemonRequest> = emptyList()

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: PokemonRequest) {
            binding.tvPokemonNome.text = pokemon.nome
            binding.root.setOnClickListener {
                onItemClick(pokemon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemons[position])
    }

    override fun getItemCount(): Int = pokemons.size

    fun atualizarLista(novaLista: List<PokemonRequest>) {
        pokemons = novaLista
        notifyDataSetChanged()
    }
}

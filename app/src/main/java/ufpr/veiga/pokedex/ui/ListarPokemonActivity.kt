package ufpr.veiga.pokedex.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ufpr.veiga.pokedex.databinding.ActivityListarPokemonBinding
import ufpr.veiga.pokedex.dto.PokemonRequest
import ufpr.veiga.pokedex.network.RetrofitClient

class ListarPokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListarPokemonBinding
    private lateinit var adapter: PokemonAdapter

    private val pokemonService = RetrofitClient.pokemonApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListarPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarRecyclerView()
        carregarPokemons()
    }

    private fun configurarRecyclerView() {
        adapter = PokemonAdapter(emptyList()) { pokemon ->
            abrirDetalhes(pokemon)
        }

        binding.recyclerViewPokemons.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPokemons.adapter = adapter
    }

    private fun carregarPokemons() {
        lifecycleScope.launch {
            try {
                val pokemons = pokemonService.listarTodos()
                adapter.update(pokemons)
            } catch (e: Exception) {
                mostrarMensagemErro()
            }
        }
    }

    private fun abrirDetalhes(pokemon: PokemonRequest) {
        val intent = Intent(this, DetalhesPokemonActivity::class.java)
        intent.putExtra("pokemon", pokemon)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        carregarPokemons()
    }

    private fun mostrarMensagemErro() {
        AlertDialog.Builder(this)
            .setTitle("Erro ao carregar pokémons")
            .setMessage("Ocorreu um erro ao carregar a lista de pokémons. Tente novamente mais tarde.")
            .setPositiveButton("OK", null)
            .show()
    }
}
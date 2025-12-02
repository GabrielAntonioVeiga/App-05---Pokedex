package ufpr.veiga.pokedex.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ufpr.veiga.pokedex.databinding.ActivityPesquisarTipoBinding
import ufpr.veiga.pokedex.network.RetrofitClient

class PesquisarTipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPesquisarTipoBinding

    private val pokemonService = RetrofitClient.pokemonApi

    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPesquisarTipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // agora com callback
        adapter = PokemonAdapter(listOf()) { pokemon ->
            mostrarMensagem("Você clicou em: ${pokemon.nome}")
        }

        binding.recyclerPokemons.layoutManager = LinearLayoutManager(this)
        binding.recyclerPokemons.adapter = adapter

        binding.btnPesquisarTipo.setOnClickListener {
            val tipo = binding.etTipo.text.toString().trim()

            if (tipo.isEmpty()) {
                Toast.makeText(this, "Digite um tipo!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            pesquisar(tipo)
        }
    }

    private fun pesquisar(tipo: String) {
        lifecycleScope.launch {
            try {
                val resultado = pokemonService.listarPorTipo(tipo)

                if (resultado.isEmpty()) {
                    mostrarMensagem("Nenhum Pokémon encontrado para esse tipo.")
                    adapter.update(emptyList())
                    return@launch
                }

                adapter.update(resultado)

            } catch (e: Exception) {
                mostrarMensagem("Erro ao pesquisar. Tente novamente.")
            }
        }
    }

    private fun mostrarMensagem(mensagem: String) {
        AlertDialog.Builder(this)
            .setTitle("Aviso")
            .setMessage(mensagem)
            .setPositiveButton("OK", null)
            .show()
    }
}

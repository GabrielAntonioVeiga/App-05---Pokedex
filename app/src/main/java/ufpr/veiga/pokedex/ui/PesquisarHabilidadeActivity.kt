package ufpr.veiga.pokedex.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ufpr.veiga.pokedex.databinding.ActivityPesquisarHabilidadeBinding
import ufpr.veiga.pokedex.network.RetrofitClient

class PesquisarHabilidadeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPesquisarHabilidadeBinding

    private val pokemonService = RetrofitClient.pokemonApi

    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPesquisarHabilidadeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PokemonAdapter(listOf())
        binding.recyclerPokemons.layoutManager = LinearLayoutManager(this)
        binding.recyclerPokemons.adapter = adapter

        binding.btnPesquisar.setOnClickListener {
            val habilidade = binding.etHabilidade.text.toString().trim()

            if (habilidade.isEmpty()) {
                Toast.makeText(this, "Digite uma habilidade!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            pesquisar(habilidade)
        }
    }

    private fun pesquisar(habilidade: String) {
        lifecycleScope.launch {
            try {
                val resultado = pokemonService.listarPorHabilidade(habilidade)

                if (resultado.isEmpty()) {
                    mostrarMensagem("Nenhum Pokémon encontrado para essa habilidade.")
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

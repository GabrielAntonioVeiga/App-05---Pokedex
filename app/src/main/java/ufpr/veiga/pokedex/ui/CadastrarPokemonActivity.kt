package ufpr.veiga.pokedex.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ufpr.veiga.pokedex.databinding.ActivityCadastrarPokemonBinding
import ufpr.veiga.pokedex.dto.PokemonRequest
import ufpr.veiga.pokedex.network.RetrofitClient

class CadastrarPokemonActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCadastrarPokemonBinding

    private val pokemonService = RetrofitClient.pokemonApi



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userEmail = intent.getStringExtra("email")

        binding = ActivityCadastrarPokemonBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnUploadPokemon.setOnClickListener {
            val nome = binding.etPokemonName.text.toString()
            val tipo = binding.spinnerPokemonType.selectedItem?.toString()
            val habilidades = listOf(
                binding.etAbility1.text.toString(),
                binding.etAbility2.text.toString(),
                binding.etAbility3.text.toString()
            )

            if (nome.isEmpty() || tipo.isNullOrEmpty() || habilidades.isEmpty()) {
                Toast.makeText(this, "Preencha o nome, o tipo e pelo menos 1 habilidade.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val habilidadesString = habilidades.joinToString(separator = ";")
            cadastrar(PokemonRequest(nome, tipo, habilidadesString, userEmail, null))
        }
    }

    private fun cadastrar(pokemon: PokemonRequest) {
        lifecycleScope.launch {
            try {
                val response = pokemonService.cadastrarPokemon(pokemon)

                if (response.mensagemErro != null) {
                    mostrarMensagemErro(response.mensagemErro)
                    return@launch
                }

                mostrarMensagemSucesso()
            } catch (e: Exception) {
                mostrarMensagemErro(null)
            }
        }
    }

    private fun mostrarMensagemSucesso() {
        AlertDialog.Builder(this).setTitle("Pokemon cadastrado com sucesso").show()
    }

    private fun mostrarMensagemErro(mensagem: String?) {
        // Usa o operador Elvis (?:) do Kotlin.
        // Se 'mensagem' for nula ou vazia, define a mensagem padrão.
        val mensagemParaExibir = if (mensagem.isNullOrBlank()) {
            "Ocorreu um erro desconhecido. Tente novamente mais tarde."
        } else {
            mensagem
        }

        AlertDialog.Builder(this)
            .setTitle("Erro ao cadastrar Pokémon")
            .setMessage(mensagemParaExibir)
            .setPositiveButton("OK", null)
            .show()
    }
}
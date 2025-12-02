package ufpr.veiga.pokedex.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ufpr.veiga.pokedex.R
import ufpr.veiga.pokedex.databinding.ActivityDetalhesPokemonBinding
import ufpr.veiga.pokedex.dto.PokemonRequest
import ufpr.veiga.pokedex.network.RetrofitClient

class DetalhesPokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesPokemonBinding
    private lateinit var pokemon: PokemonRequest
    private var emailUsuarioOriginal: String? = null

    private val pokemonService = RetrofitClient.pokemonApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalhesPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemon = intent.getSerializableExtra("pokemon") as PokemonRequest
        emailUsuarioOriginal = pokemon.emailusuario

        preencherCampos()
        configurarBotoes()
    }

    private fun preencherCampos() {
        binding.etPokemonName.setText(pokemon.nome)

        val tipos = resources.getStringArray(R.array.pokemon_types_array)
        val tipoIndex = tipos.indexOf(pokemon.tipo)
        if (tipoIndex >= 0) {
            binding.spinnerPokemonType.setSelection(tipoIndex)
        }

        val habilidades = pokemon.habilidades
        if (habilidades.isNotEmpty()) {
            binding.etAbility1.setText(habilidades[0])
        }
        if (habilidades.size > 1) {
            binding.etAbility2.setText(habilidades[1])
        }
        if (habilidades.size > 2) {
            binding.etAbility3.setText(habilidades[2])
        }

        binding.tvCadastrador.text = "Cadastrado por: ${pokemon.emailusuario}"
    }

    private fun configurarBotoes() {
        binding.btnSalvar.setOnClickListener {
            salvarAlteracoes()
        }

        binding.btnExcluir.setOnClickListener {
            confirmarExclusao()
        }
    }

    private fun salvarAlteracoes() {
        val nome = binding.etPokemonName.text.toString()
        val tipo = binding.spinnerPokemonType.selectedItem?.toString()
        val habilidades = listOf(
            binding.etAbility1.text.toString(),
            binding.etAbility2.text.toString(),
            binding.etAbility3.text.toString()
        ).filter { it.isNotEmpty() }

        if (nome.isEmpty() || tipo.isNullOrEmpty() || habilidades.isEmpty()) {
            Toast.makeText(this, "Preencha o nome, o tipo e pelo menos 1 habilidade.", Toast.LENGTH_LONG).show()
            return
        }

        val habilidadesString = habilidades

        val pokemonAtualizado = PokemonRequest(
            id = pokemon.id,
            nome = nome,
            tipo = tipo,
            habilidades = habilidadesString,
            emailusuario = emailUsuarioOriginal,
            mensagemErro = null
        )

        lifecycleScope.launch {
            try {
                val response = pokemonService.editarPokemon(pokemon.id!!, pokemonAtualizado)

                if (response.mensagemErro != null) {
                    mostrarMensagemErro(response.mensagemErro)
                    return@launch
                }

                mostrarMensagemSucesso("Pokémon atualizado com sucesso")
                finish()
            } catch (e: Exception) {
                mostrarMensagemErro(null)
            }
        }
    }

    private fun confirmarExclusao() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar exclusão")
            .setMessage("Deseja realmente excluir ${pokemon.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                excluirPokemon()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun excluirPokemon() {
        lifecycleScope.launch {
            try {
                pokemonService.deletarPokemon(pokemon.id!!)
                mostrarMensagemSucesso("Pokémon excluído com sucesso")
                finish()
            } catch (e: Exception) {
                mostrarMensagemErro("Erro ao excluir pokémon")
            }
        }
    }

    private fun mostrarMensagemSucesso(mensagem: String) {
        AlertDialog.Builder(this)
            .setTitle("Sucesso")
            .setMessage(mensagem)
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .show()
    }

    private fun mostrarMensagemErro(mensagem: String?) {
        val mensagemParaExibir = if (mensagem.isNullOrBlank()) {
            "Ocorreu um erro desconhecido. Tente novamente mais tarde."
        } else {
            mensagem
        }

        AlertDialog.Builder(this)
            .setTitle("Erro")
            .setMessage(mensagemParaExibir)
            .setPositiveButton("OK", null)
            .show()
    }
}

package ufpr.veiga.pokedex.dto

import java.io.Serializable

data class PokemonRequest(
    val id: Long?,
    val nome: String,
    val tipo: String,
    val habilidades: List<String>,
    val emailusuario: String?,
    val mensagemErro: String?
) : Serializable

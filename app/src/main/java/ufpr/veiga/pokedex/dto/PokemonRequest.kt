package ufpr.veiga.pokedex.dto

data class PokemonRequest(
    val nome: String,
    val tipo: String,
    val habilidades: String,
    val emailusuario: String?,
    val mensagemErro: String?
)

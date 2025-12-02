package ufpr.veiga.pokedex.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ufpr.veiga.pokedex.dto.PokemonRequest

interface PokemonService {

    @POST("pokemon")
    suspend fun cadastrarPokemon(@Body pokemon: PokemonRequest): PokemonRequest

    @GET("pokemon/by-habilidade")
    suspend fun listarPorHabilidade(@Query("habilidade") habilidade: String): List<String>

    @GET("pokemon/by-tipo")
    suspend fun listarPorTipo(@Query("tipo") tipo: String): List<String>
}
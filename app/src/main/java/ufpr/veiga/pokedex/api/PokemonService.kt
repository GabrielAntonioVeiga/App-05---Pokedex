package ufpr.veiga.pokedex.api

import retrofit2.Response
import retrofit2.http.*
import ufpr.veiga.pokedex.dto.PokemonRequest

interface PokemonService {

    @POST("pokemon")
    suspend fun cadastrarPokemon(@Body pokemon: PokemonRequest): PokemonRequest

    @GET("pokemon")
    suspend fun listarTodos(): List<PokemonRequest>

    @PUT("pokemon/{id}")
    suspend fun editarPokemon(@Path("id") id: Long, @Body pokemon: PokemonRequest): PokemonRequest

    @DELETE("pokemon/{id}")
    suspend fun deletarPokemon(@Path("id") id: Long): Response<Void>

    @GET("pokemon/by-habilidade")
    suspend fun listarPorHabilidade(@Query("habilidade") habilidade: String): List<String>

    @GET("pokemon/by-tipo")
    suspend fun listarPorTipo(@Query("tipo") tipo: String): List<String>
}

package ufpr.veiga.pokedex.api

import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ufpr.veiga.pokedex.dto.PokemonRequest
import ufpr.veiga.pokedex.dto.TipoContagem

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

    @GET("pokemon/count")
    suspend fun contarPokemons(): Int

    @GET("pokemon/top/tipos")
    suspend fun topTipos(): List<TipoContagem>

    @GET("pokemon/top/habilidades")
    suspend fun topHabilidades(): List<TipoContagem>

}

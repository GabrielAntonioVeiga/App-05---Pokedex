package ufpr.veiga.pokedex.api

import retrofit2.http.Body
import retrofit2.http.POST
import ufpr.veiga.pokedex.dto.PokemonRequest

interface PokemonService {

    @POST("pokemon")
    suspend fun cadastrarPokemon(@Body pokemon: PokemonRequest): PokemonRequest

}
package ufpr.veiga.pokedex.network

import ufpr.veiga.pokedex.api.AuthService
import ufpr.veiga.pokedex.api.PokemonService

object RetrofitClient {
    private val BASE_URL = "http://192.168.0.17:8080/"

    private val client = okhttp3.OkHttpClient.Builder()
        .build()


    val authApi: AuthService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    val pokemonApi: PokemonService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }
}
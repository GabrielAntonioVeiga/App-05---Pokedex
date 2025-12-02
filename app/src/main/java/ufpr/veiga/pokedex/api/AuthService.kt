package ufpr.veiga.pokedex.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ufpr.veiga.pokedex.dto.AutenticacaoRequest

interface AuthService {

    @POST("auth/login")
    suspend fun autenticar(@Body request: AutenticacaoRequest): Boolean

}
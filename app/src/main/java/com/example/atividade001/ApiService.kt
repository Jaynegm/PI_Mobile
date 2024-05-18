package com.example.atividade001

import retrofit2.Call
import retrofit2.http.GET
import Produto

interface ApiService {
    @GET("/")
    fun getProdutos(): Call<List<Produto>>
}
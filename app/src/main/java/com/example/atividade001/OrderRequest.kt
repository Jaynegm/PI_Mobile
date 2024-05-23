package com.example.atividade001

data class OrderRequest(
        val userId: Int,
        val total: Double,
        val products: List<Produto>
    )


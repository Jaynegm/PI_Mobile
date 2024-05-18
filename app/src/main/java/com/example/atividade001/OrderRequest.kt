package com.example.atividade001

import Produto

data class OrderRequest(
        val userId: Int,
        val total: Double,
        val products: List<Produto>
    )


package com.example.atividade001

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProdutoDetalhesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_detalhes)

        val nomeProduto = intent.getStringExtra("NOME_PRODUTO")
        val descricaoProduto = intent.getStringExtra("DESCRICAO_PRODUTO")

        findViewById<TextView>(R.id.txtNomeProduto).text = nomeProduto
        findViewById<TextView>(R.id.txtDescricaoProduto).text = descricaoProduto

        findViewById<Button>(R.id.btnAdicionarAoCarrinho).setOnClickListener {
            // TODO ADICIONAR AO CARRINHO
        }
    }
}
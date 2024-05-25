package com.example.atividade001

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class ProdutosDetalhesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos_detalhes)

        val nomeProduto = intent.getStringExtra("NOME_PRODUTO") ?: "Nome nÃƒÂ£o disponÃƒÂ­vel"
        val imagemProduto = intent.getStringExtra("IMAGEM_URL") ?: "Imagem indisponível"
        val descricaoProduto = intent.getStringExtra("DESCRICAO_PRODUTO") ?: "DescriÃƒÂ§ÃƒÂ£o nÃƒÂ£o disponÃƒÂ­vel"
        val produtoId = intent.getIntExtra("ID_PRODUTO", 0)
        val quantidadeDisponivel = intent.getIntExtra("QUANTIDADE_DISPONIVEL", 0)


        Glide.with(this)
            .load(imagemProduto)
            .placeholder(R.drawable.ic_launcher_background) // placeholder
            .error(R.drawable.ic_launcher_background) // indica erro
            .into(findViewById(R.id.imagem_produto))


        findViewById<TextView>(R.id.txtNomeProduto).text = nomeProduto
        findViewById<TextView>(R.id.txtDescricaoProduto).text = descricaoProduto
        findViewById<TextView>(R.id.txtQuantidadeDisponivel).text = quantidadeDisponivel.toString()

        val editTextQuantidade = findViewById<EditText>(R.id.editQuantidadeDesejada)
        val btnAdicionarCarrinho = findViewById<Button>(R.id.btnAdicionarAoCarrinho)
        val btnCarrinho = findViewById<Button>(R.id.btnCarrinho)

        val sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", 0)

        var intent2 = Intent(this@ProdutosDetalhesActivity, CartActivity::class.java)
        intent2.putExtra("userId", userId)
        btnCarrinho.setOnClickListener {
            startActivity(intent2)
        }

        btnAdicionarCarrinho.setOnClickListener {
            val quantidadeDesejada = editTextQuantidade.text.toString().toIntOrNull() ?: 0
            adicionarAoCarrinho(userId, produtoId, quantidadeDesejada)
        }
    }

    private fun adicionarAoCarrinho(userId: Int, produtoId: Int, quantidade: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://12746cbd-77e6-4f72-9c25-47a219be274e-00-274zo2e79g7dj.riker.replit.dev/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        api.adicionarAoCarrinho(userId, produtoId, quantidade).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProdutosDetalhesActivity, response.body() ?: "Sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ProdutosDetalhesActivity, "Resposta nÃƒÂ£o bem-sucedida", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ProdutosDetalhesActivity, "Erro na API: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    interface ApiService {
        @FormUrlEncoded
        @POST("/")
        fun adicionarAoCarrinho(
            @Field("userId") userId: Int,
            @Field("produtoId") produtoId: Int,
            @Field("quantidade") quantidade: Int
        ): Call<String>
    }
}
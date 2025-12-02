package ufpr.veiga.pokedex.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ufpr.veiga.pokedex.databinding.ActivityAutenticacaoBinding
import ufpr.veiga.pokedex.dto.AutenticacaoRequest
import ufpr.veiga.pokedex.network.RetrofitClient

class AutenticacaoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAutenticacaoBinding

    private val authService = RetrofitClient.authApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAutenticacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val senha = binding.password.text.toString()

            autenticar(email, senha)
        }
    }

    private fun autenticar(email: String, senha: String) {
        lifecycleScope.launch {
            try {
                val response = authService.autenticar(AutenticacaoRequest(email, senha))

                if (response) {
                    Toast.makeText(this@AutenticacaoActivity, "Autenticado com sucesso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AutenticacaoActivity, DashboardActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                } else {
                    mostrarMensagemErro()
                }
            } catch (e: Exception) {
                mostrarMensagemErro()
            }
        }
    }

    private fun mostrarMensagemErro() {
        AlertDialog.Builder(this).setTitle("Erro de autenticação")
            .setMessage("Login ou Senha incorretos")
            .setPositiveButton("OK", null)
            .show()
    }
}
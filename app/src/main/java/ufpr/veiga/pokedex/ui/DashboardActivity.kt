package ufpr.veiga.pokedex.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ufpr.veiga.pokedex.R
import ufpr.veiga.pokedex.network.RetrofitClient

class DashboardActivity : AppCompatActivity() {

    private var loggedInUserEmail: String? = null

    private val pokemonService = RetrofitClient.pokemonApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        loggedInUserEmail = intent.getStringExtra("email")

        loadDashboardData()
    }

    private fun loadDashboardData() {
        lifecycleScope.launch {
            try {

            val totalPokemons = pokemonService.contarPokemons()

            val topTiposPokemon = pokemonService.topTipos()
            val topTipos: List<Pair<String, Int>> = topTiposPokemon.map {
                it.tipo to it.quantidade
            }

            val topHabilidadesPokemon = pokemonService.topHabilidades()
            val topHabilidades: List<Pair<String, Int>> = topHabilidadesPokemon.map {
                it.tipo to it.quantidade
            }

            findViewById<TextView>(R.id.tvTotalPokemons).text = totalPokemons.toString()

            fillList(findViewById(R.id.containerTopTipos), topTipos)
            fillList(findViewById(R.id.containerTopHabilidades), topHabilidades)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    private fun fillList(container: LinearLayout, items: List<Pair<String, Int>>) {
        container.removeAllViews()

        for ((nome, count) in items) {
            val tv = TextView(this).apply {
                text = "$nome — $count"
                textSize = 14f
                setTextColor(Color.WHITE)
                setPadding(8, 8, 8, 8)
            }
            container.addView(tv)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val emailToPass = loggedInUserEmail

        when (item.itemId) {
            R.id.menuCadastrar -> {
                val intent = Intent(this, CadastrarPokemonActivity::class.java)
                intent.putExtra("email", emailToPass)
                startActivity(intent)
            }
            R.id.menuListar -> {
                val intent = Intent(this, ListarPokemonActivity::class.java)
                intent.putExtra("email", emailToPass)
                startActivity(intent)
            }
            R.id.menuPesquisarTipo -> {
                val intent = Intent(this, PesquisarTipoActivity::class.java)
                intent.putExtra("email", emailToPass)
                startActivity(intent)
            }
            R.id.menuPesquisarHabilidade -> {
                val intent = Intent(this, PesquisarHabilidadeActivity::class.java)
                intent.putExtra("email", emailToPass)
                startActivity(intent)
            }
            R.id.menuSair -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.example.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializa el adapter sin personajes al principio
        adapter = ItemAdapter(emptyList(), RetrofitInstance.apiService)
        recyclerView.adapter = adapter

        // Carga los datos
        fetchData()
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            var currentPage = 1
            val allCharacters = mutableListOf<Character>()

            do {
                val characterResponse = RetrofitInstance.apiService.getCharacters(currentPage)
                allCharacters.addAll(characterResponse.results)
                currentPage++
            } while (currentPage <= characterResponse.info.pages)

            // Ahora obtenemos los episodios
            val episodeResponse = RetrofitInstance.apiService.getEpisodes()
            val episodes = episodeResponse.results.associateBy { it.url } // Mapea la URL a la información del episodio

            // Asigna el nombre del primer episodio a cada personaje
            allCharacters.forEach { character ->
                if (character.episode.isNotEmpty()) {
                    val episodeUrl = character.episode.first()
                    character.firstEpisodeName = episodes[episodeUrl]?.name ?: "Unknown"
                }
            }

            withContext(Dispatchers.Main) {
                adapter.updateCharacters(allCharacters)
            }
        }
    }

}


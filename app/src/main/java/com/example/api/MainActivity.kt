package com.example.api

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter // Asegúrate de tener un adaptador para el RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cambia recyclerView a recycler_view
        recyclerView = findViewById(R.id.recycler_view) // Asegúrate de que el ID coincida con el XML
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PhotoAdapter(listOf()) // Inicializa con una lista vacía
        recyclerView.adapter = adapter

        fetchPhotos()
    }

    private fun fetchPhotos() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.getPhotos()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { photos ->
                        adapter.updatePhotos(photos) // Asume que tienes un método para actualizar la lista en tu adaptador
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

package com.example.api

data class ApiResponse<T>(
    val info: Info,
    val results: List<T>
)

// Información de paginación
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

// Modelo de datos para los personajes
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: LocationInfo,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
    var firstEpisodeName: String? = null

)

// Modelo de datos para la ubicación
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String,
    val image: String?
)

// Modelo de datos para el episodio
data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

// Modelo de datos para el origen del personaje
data class Origin(
    val name: String,
    val url: String
)

// Modelo de datos para la ubicación del personaje
data class LocationInfo(
    val name: String,
    val url: String
)

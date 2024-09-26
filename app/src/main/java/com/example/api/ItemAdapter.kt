package com.example.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemAdapter(private var characters: List<Character>, private val apiService: ApiService) : RecyclerView.Adapter<ItemAdapter.CharacterViewHolder>() {

    fun updateCharacters(newCharacters: List<Character>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characters.size

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val characterImage: ImageView = itemView.findViewById(R.id.character_image)
        private val characterName: TextView = itemView.findViewById(R.id.character_name)
        private val characterStatusSpecies: TextView = itemView.findViewById(R.id.character_status_species)
        private val characterLocation: TextView = itemView.findViewById(R.id.character_location)
        private val characterEpisode: TextView = itemView.findViewById(R.id.character_episode)

        fun bind(character: Character) {
            Glide.with(itemView)
                .load(character.image)
                .into(characterImage)

            characterName.text = character.name
            characterStatusSpecies.text = "${character.status} - ${character.species}"
            characterLocation.text = "Last known location:\n${character.location.name}"

            // Obtener el primer episodio
            if (character.episode.isNotEmpty()) {
                val episodeUrl = character.episode.first()
                apiService.getEpisodeByUrl(episodeUrl).enqueue(object : Callback<Episode> {
                    override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                        if (response.isSuccessful) {
                            val episode = response.body()
                            characterEpisode.text = "First seen in:\n${episode?.name ?: "N/A"}"
                        } else {
                            characterEpisode.text = "First seen in:\nN/A"
                        }
                    }

                    override fun onFailure(call: Call<Episode>, t: Throwable) {
                        characterEpisode.text = "First seen in:\nN/A"
                    }
                })
            } else {
                characterEpisode.text = "First seen in:\nN/A"
            }
        }
    }
}



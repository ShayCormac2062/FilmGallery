package com.example.filmgallery.presentation.adapter.actors

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmgallery.R
import com.example.filmgallery.databinding.ViewActorBinding
import com.example.filmgallery.domain.model.Actor

class ActorsViewHolder(
    private val binding: ViewActorBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    private var element: Actor? = null
    fun bind(item: Actor) {
        this.element = item
        updateTitle(item.name)
        updatePoster(item.imageUrl)
    }

    fun updateFields(bundle: Bundle) {
        bundle.run {
            getString("NAME")?.also {
                updateTitle(it)
            }
            getString("POSTERPATH")?.also {
                updatePoster(it)
            }
        }
    }

    private fun updateTitle(title: String) {
        binding.tvName.text = title
    }

    private fun updatePoster(url: String) {
        binding.ivPoster.load(
            String.format(context.getString(R.string.image_url), url)
        )
    }

    companion object {

        fun create(
            parent: ViewGroup,
            context: Context
        ) = ActorsViewHolder(
            ViewActorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )

    }
}

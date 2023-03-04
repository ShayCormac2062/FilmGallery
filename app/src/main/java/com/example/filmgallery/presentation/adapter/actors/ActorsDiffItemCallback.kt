package com.example.filmgallery.presentation.adapter.actors

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.filmgallery.domain.model.Actor
import com.example.filmgallery.domain.model.BaseElement

class ActorsDiffItemCallback  : DiffUtil.ItemCallback<Actor>() {

    override fun areItemsTheSame(
        oldItem: Actor,
        newItem: Actor
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: Actor,
        newItem: Actor
    ): Boolean = (oldItem.imageUrl == newItem.imageUrl)

    override fun getChangePayload(oldItem: Actor, newItem: Actor): Any? {
        val bundle = Bundle()
        if (oldItem.name != newItem.name) {
            bundle.putString("NAME", newItem.name)
        }
        if (oldItem.imageUrl != newItem.imageUrl) {
            bundle.putString("POSTERPATH", newItem.imageUrl)
        }
        if (bundle.isEmpty) return null
        return bundle
    }

}

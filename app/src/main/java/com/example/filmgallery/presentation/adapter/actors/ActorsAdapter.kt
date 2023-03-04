package com.example.filmgallery.presentation.adapter.actors

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.filmgallery.domain.model.Actor

class ActorsAdapter(
    private val context: Context
) : ListAdapter<Actor, ActorsViewHolder>(ActorsDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorsViewHolder = ActorsViewHolder.create(parent, context)

    override fun onBindViewHolder(
        holder: ActorsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ActorsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf { it is Bundle }?.let {
                holder.updateFields(it as Bundle)
            }
        }
    }

    override fun submitList(list: MutableList<Actor>?) {
        super.submitList(list)
    }

}

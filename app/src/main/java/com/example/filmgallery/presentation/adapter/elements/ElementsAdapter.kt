package com.example.filmgallery.presentation.adapter.elements

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.filmgallery.domain.model.BaseElement

class ElementsAdapter (
    private val context: Context,
    private val action: (Int, String) -> Unit
) : ListAdapter<BaseElement, ElementViewHolder>(ElementsDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ElementViewHolder = ElementViewHolder.create(parent, action, context)

    override fun onBindViewHolder(
        holder: ElementViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ElementViewHolder,
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

    override fun submitList(list: MutableList<BaseElement>?) {
        super.submitList(list)
    }

}

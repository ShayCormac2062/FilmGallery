package com.example.filmgallery.presentation.adapter.elements

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.filmgallery.domain.model.BaseElement

class ElementsDiffItemCallback  : DiffUtil.ItemCallback<BaseElement>() {

    override fun areItemsTheSame(
        oldItem: BaseElement,
        newItem: BaseElement
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: BaseElement,
        newItem: BaseElement
    ): Boolean = (oldItem.posterPath == newItem.posterPath)

    override fun getChangePayload(oldItem: BaseElement, newItem: BaseElement): Any? {
        val bundle = Bundle()
        if (oldItem.name != newItem.name) {
            bundle.putString("NAME", newItem.name)
        }
        if (oldItem.posterPath != newItem.posterPath) {
            bundle.putString("POSTERPATH", newItem.posterPath)
        }
        if (oldItem.voteAverage != newItem.voteAverage) {
            bundle.putDouble("RATING", newItem.voteAverage)
        }
        if (bundle.isEmpty) return null
        return bundle
    }
}

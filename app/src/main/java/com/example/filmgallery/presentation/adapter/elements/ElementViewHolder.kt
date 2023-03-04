package com.example.filmgallery.presentation.adapter.elements

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmgallery.R
import com.example.filmgallery.databinding.ViewBaseElementBinding
import com.example.filmgallery.domain.model.BaseElement

class ElementViewHolder(
    private val binding: ViewBaseElementBinding,
    private val action: (Int, String) -> Unit,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    private var book: BaseElement? = null
    private var color: Int? = null

    init {
        itemView.setOnClickListener {
            book?.id?.let {
                action(it, book?.label.toString())
            }
        }
    }

    fun bind(item: BaseElement) {
        this.book = item
        this.color = if (item.label == "Serial") R.color.green_700 else R.color.red_700
        with(binding) {
            updateTitle(item.name)
            updatePoster(item.posterPath)
            updateRating(item.voteAverage, color)
            updateLabel(item.label, color)
            cardviewElement.setOnClickListener { action(item.id, item.label) }
        }

    }

    fun updateFields(bundle: Bundle) {
        bundle.run {
            getString("NAME")?.also {
                updateTitle(it)
            }
            getString("POSTERPATH")?.also {
                updateLabel(it, color)
            }
            getString("RATING")?.also {
                updateLabel(it, color)
            }
        }
    }

    private fun updateTitle(title: String) {
        binding.tvName.text = title
    }

    private fun updateLabel(title: String, color: Int?) {
        with(binding.tvLabel) {
            text = title
            setBackgroundColor(color ?: R.color.black)
        }
    }

    private fun updatePoster(url: String) {
        binding.ivPoster.load(
            String.format(context.getString(R.string.image_url), url)
        )
    }

    private fun updateRating(title: Double, color: Int?) {
        with(binding.tvRating) {
            text = title.toString()
            setBackgroundColor(color ?: R.color.black)
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action: (Int, String) -> Unit,
            context: Context
        ) = ElementViewHolder(
            ViewBaseElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action, context
        )

    }
}

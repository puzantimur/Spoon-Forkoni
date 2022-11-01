package com.example.homew3.cleanArch.presentation.ui.info

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homew3.databinding.ExtendedIngredientsBinding
import com.example.homew3.domain.model.ExtendedIngredient


class ExtendedIngredientsAdapter(
    context: Context
) : ListAdapter<ExtendedIngredient, InfoViewHolder>(DiffUtil) {


    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        return InfoViewHolder(
            binding = ExtendedIngredientsBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {

        private val DiffUtil = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
            override fun areItemsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

class InfoViewHolder(
    private val binding: ExtendedIngredientsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ExtendedIngredient) {
        with(binding) {
            ingredientView.load(item.image)
            ingredientName.text = item.nameClean
        }
    }
}
package com.example.homew3.cleanArch.presentation.ui.ingredients

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homew3.databinding.IngredientBinding
import com.example.homew3.domain.model.Ingredient

class IngredientsAdapter(
    context: Context
) : ListAdapter<Ingredient, IngredientsViewHolder>(DiffUtil) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            binding = IngredientBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        val DiffUtil = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(
                oldItem: Ingredient,
                newItem: Ingredient
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Ingredient,
                newItem: Ingredient
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class IngredientsViewHolder(
    private val binding: IngredientBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Ingredient) {
        with(binding) {
            ingredientView.load("https://spoonacular.com/cdn/ingredients_100x100/${item.image}")
            ingredientName.text = item.localizedName
        }
    }
}

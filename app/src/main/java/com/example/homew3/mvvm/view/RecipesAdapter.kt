package com.example.homew3.mvvm.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.homew3.databinding.RecipesBinding
import com.example.homew3.mvvm.model.Recipe


class RecipesAdapter(
    context: Context,
    private val onRecipeClicked: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipesViewHolder>(DiffUtil) {


    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            binding = RecipesBinding.inflate(layoutInflater, parent, false),
            onRecipeClicked = onRecipeClicked
        )
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val DiffUtil = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(
                oldItem: Recipe,
                newItem: Recipe
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Recipe,
                newItem: Recipe
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

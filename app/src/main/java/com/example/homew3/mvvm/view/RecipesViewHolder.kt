package com.example.homew3.mvvm.view

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homew3.databinding.RecipesBinding
import com.example.homew3.mvvm.model.Recipe

class RecipesViewHolder(
    private val binding: RecipesBinding,
    private val onRecipeClicked: (Recipe) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Recipe) {
        with(binding) {
            recipesView.load(item.image)
            recipeName.text = item.title

            root.setOnClickListener {
                onRecipeClicked(item)
            }
        }
    }
}

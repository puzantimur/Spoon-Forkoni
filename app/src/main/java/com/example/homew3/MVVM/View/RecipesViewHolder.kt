package com.example.homew3.MVVM.View

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homew3.MVVM.Model.Recipe
import com.example.homew3.databinding.RecipesBinding

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

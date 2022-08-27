package com.example.homew3

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homew3.databinding.RecipeLoadingBinding
import com.example.homew3.databinding.RecipesBinding


class RecipesAdapter(
    private val context: Context,
    private val onRecipeClicked: (Recipe) -> Unit
): ListAdapter<PaggingRecipes<Recipe>, RecyclerView.ViewHolder>(DiffUtil) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PaggingRecipes.Item -> TYPE_RECIPE
            PaggingRecipes.Loading -> TYPE_LOADING
        }
    }

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_RECIPE -> {
                RecipesViewHolder(
                    binding = RecipesBinding.inflate(layoutInflater, parent, false),
                    onRecipeClicked = onRecipeClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = RecipeLoadingBinding.inflate(layoutInflater, parent,false)
                )
            }
            else -> error("Causes $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item  = getItem(position)) {
            is PaggingRecipes.Item -> {
                checkNotNull(holder as RecipesViewHolder) {"Causes incorret VH $item"}
                holder.bind(item.data)
            }
            PaggingRecipes.Loading -> {
            }
        }
    }

    companion object {

        private const val TYPE_RECIPE = 0
        private const val TYPE_LOADING = 1

        private val DiffUtil = object : DiffUtil.ItemCallback<PaggingRecipes<Recipe>>() {
            override fun areItemsTheSame(
                oldItem: PaggingRecipes<Recipe>,
                newItem: PaggingRecipes<Recipe>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PaggingRecipes<Recipe>,
                newItem: PaggingRecipes<Recipe>
            ): Boolean {
                return oldItem == newItem
            }


        }
    }
}
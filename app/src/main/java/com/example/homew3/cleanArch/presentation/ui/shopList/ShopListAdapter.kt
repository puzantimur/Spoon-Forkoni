package com.example.homew3.cleanArch.presentation.ui.shopList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homew3.databinding.ExtendedIngredientsBinding
import com.example.homew3.databinding.ShopListIngredientBinding
import com.example.homew3.domain.model.ExtendedIngredient

class ShopListAdapter(
    context: Context,
    private val onIngredientClicked: (ExtendedIngredient) -> Unit
) : ListAdapter<ExtendedIngredient, ShopListViewHolder>(DiffUtil) {


    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        return ShopListViewHolder(
            binding = ShopListIngredientBinding.inflate(layoutInflater, parent, false),
            onIngredientClicked = onIngredientClicked
        )
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
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

class ShopListViewHolder(
    private val binding: ShopListIngredientBinding,
    private val onIngredientClicked: (ExtendedIngredient) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ExtendedIngredient) {
        with(binding) {
            ingredientView.load(item.image)
            ingredientName.text = item.nameClean
            ingredientAmount.text = item.amount.toString()
            ingredientUnit.text = item.unit

        }
    }
}
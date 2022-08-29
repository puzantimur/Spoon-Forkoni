package com.example.homew3

sealed class PaggingRecipes<out T> {
    data class Item<T>(val data: T) : PaggingRecipes<T>()

    object Loading : PaggingRecipes<Nothing>()


}

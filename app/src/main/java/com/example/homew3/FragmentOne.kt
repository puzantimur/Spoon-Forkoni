package com.example.homew3

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.homew3.databinding.FragmentOneBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class FragmentOne : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy { RecipesAdapter(
        context = requireContext(),
        onRecipeClicked = {

        }) }

    private val currentRecipes = mutableListOf<Recipe>()

    private var currentRequest: Call<List<Recipe>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentOneBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.bottom = SomeData.getData
                    }
                }
            )

//            toolbar
//                .menu
//                .findItem(R.id.action_search)
//                .actionView
//                .let { it as SearchView }
//                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                    override fun onQueryTextSubmit(query: String): Boolean {
//                        return false
//                    }
//
//                    override fun onQueryTextChange(query: String): Boolean {
//
//                        val items = currentRecipes.map {
//                            PaggingRecipes.Item(it)
//                        } + PaggingRecipes.Loading
//                        adapter.submitList(items.filter {
//                            it.contains(query, true)
//                        })
//                        return true
//                    }
//                })

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val recipesApi = retrofit.create<RecipesApi>()

            currentRequest = recipesApi
                .getPlayers(
                    "26fdb210da4142409671f31104a8ef9f",
                    "egg",
                    60
                )
                .apply {
                    enqueue(object : Callback<List<Recipe>> {
                        override fun onResponse(
                            call: Call<List<Recipe>>,
                            response: Response<List<Recipe>>
                        ) {
                            if (response.isSuccessful) {
                                val recipes = response.body() ?: return
                                currentRecipes.addAll(recipes)
                                val items = recipes.map {
                                    PaggingRecipes.Item(it)
                                } + PaggingRecipes.Loading
                                adapter.submitList(items)
                            } else {
                                handleException(HttpException(response))
                            }
                        }

                        override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                            println()
                        }

                    })
                }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentRequest?.cancel()
        _binding = null
    }

    private fun handleException(e: Throwable) {
        Toast.makeText(requireContext(), "Dumb", Toast.LENGTH_SHORT).show()
    }
}

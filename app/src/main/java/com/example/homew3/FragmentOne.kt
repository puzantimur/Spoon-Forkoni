package com.example.homew3

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.homew3.databinding.FragmentOneBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class FragmentOne : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        RecipesAdapter(
            context = requireContext(),
            onRecipeClicked = {
                findNavController().navigate(
                    FragmentOneDirections.toSecondFragment(
                        it.id,
                        it.title,
                        it.image
                    )
                )
            }
        )
    }

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


            swipeRefresh.setOnRefreshListener {
                executeRequest {
                    swipeRefresh.isRefreshing = false
                }
            }

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

        }
        executeRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentRequest?.cancel()
        _binding = null
    }

    private fun handleException(e: Throwable) {
        Toast.makeText(requireContext(), "Dumb", Toast.LENGTH_SHORT).show()
    }

    private fun executeRequest(
        onRequestFinished: () -> Unit = {}
    ) {

        val finishRequest = {
            onRequestFinished()
            currentRequest = null
        }

        currentRequest?.cancel()
        currentRequest = SomeData.api
            .getRecipes(
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
                        finishRequest()
                    }

                    override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                        if (!call.isCanceled) {
                            handleException(t)
                        }
                        finishRequest()
                    }
                })
            }

    }
}

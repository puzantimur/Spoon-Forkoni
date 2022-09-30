package com.example.homew3.mvvm.view

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.homew3.R
import com.example.homew3.databinding.FragmentRecipesBinding
import com.example.homew3.mvvm.model.appDataBase
import com.example.homew3.mvvm.viewModel.RecipesViewModel
import com.example.homew3.mvvm.viewModel.ServiceLocator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        RecipesAdapter(
            context = requireContext(),
            onRecipeClicked = {
                findNavController().navigate(
                    RecipesFragmentDirections.toMoreInfoFragment(
                        it.id,
                        it.title,
                        it.image
                    )
                )
            }
        )
    }

    private val recipesDao by lazy {
        requireContext().appDataBase.recipesDao
    }

    private val viewModel: RecipesViewModel by viewModels(
        factoryProducer = {
            viewModelFactory {
                initializer {
                    RecipesViewModel(ServiceLocator.provideRecipes(), recipesDao)
                }
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRecipesBinding.inflate(inflater, container, false)
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
                        outRect.bottom = 50   //R.dimen.default_padding если делаю так, то только один объект отображется =(
                    }
                }
            )

            swipeRefresh.setOnRefreshListener {
                adapter.submitList(emptyList())
                viewModel.onRefreshedRecipes()
            }


            toolbar.menu
                .findItem(R.id.action_search)
                .actionView
                .let { it as SearchView }
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(query: String): Boolean {
                        viewModel.onQueryChanged(query)
                        return true
                    }
                })


            viewModel
                .recipeFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach { swipeRefresh.isRefreshing = false }
                .onEach { adapter.submitList(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

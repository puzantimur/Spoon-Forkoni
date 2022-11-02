package com.example.homew3.cleanArch.presentation.ui.recipes

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.homew3.R
import com.example.homew3.databinding.FragmentRecipesBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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
                    )
                )
            }
        )
    }

    private val args by navArgs<RecipesFragmentArgs>()


    private val viewModel by viewModel<RecipesViewModel> {
        parametersOf(args.ingredientsStart)
    }

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
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            ViewCompat.setOnApplyWindowInsetsListener(recyclerView) { _, insets ->
                val recyclerViewSystemBarInsets =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars())
                recyclerView.updatePadding(
                    left = recyclerViewSystemBarInsets.left,
                    right = recyclerViewSystemBarInsets.right
                )
                insets
            }

            ViewCompat.setOnApplyWindowInsetsListener(toolbar) { _, insets ->
                val toolbarSystemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                toolbar.updatePadding(
                    top = toolbarSystemBarInsets.top,
                    left = toolbarSystemBarInsets.left,
                    right = toolbarSystemBarInsets.right,

                )
                insets
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
                        outRect.bottom =
                            requireContext().resources.getDimension(R.dimen.default_padding)
                                .toInt()
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
                .onEach(adapter::submitList)
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

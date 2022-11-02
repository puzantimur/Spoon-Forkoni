package com.example.homew3.cleanArch.presentation.ui.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.homew3.R
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.ui.recipes.RecipesAdapter
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.addItemDecorationBottom
import com.example.homew3.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = requireNotNull(_binding)


    private val favoriteRecipesViewModel by viewModel<FavoriteViewModel>{
        parametersOf(prefsManager.userId)
    }

    private val adapter by lazy {
        RecipesAdapter(
            context = requireContext(),
            onRecipeClicked = {
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete this recipe?")
                    .setPositiveButton(android.R.string.ok){_,_->
                        favoriteRecipesViewModel.onClickedOk(it)
                    }
                    .setNegativeButton(android.R.string.cancel){_,_->
                    }
                    .show()

            }
        )
    }

    private val prefsManager by lazy {
        SharedPreferencesManager(requireContext())
    }

    private val outRectBottom by lazy {
        requireContext().resources.getDimension(R.dimen.default_padding)
            .toInt()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            recyclerView.adapter = adapter



            if(prefsManager.isLogined){
                favoriteRecipesViewModel
                    .favoriteFlow
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                    .onEach { lce ->
                        when(lce){
                            is Lce.Content -> {
                                progress.isVisible = false
                                if (lce.data.isEmpty()){
                                    textViewAttention.isVisible = true
                                } else {
                                    recyclerView.isVisible = true
                                    adapter.submitList(lce.data)
                                }
                            }
                            is Lce.Error -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Something went wrong, try later",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                progress.isVisible = false
                            }

                            Lce.Loading -> progress.isVisible = true
                        }
                    }
                    .launchIn(viewLifecycleOwner.lifecycleScope)



                recyclerView.addItemDecorationBottom(outRectBottom)
            } else {
                textViewAttention.isVisible = true
                textViewAttention.text = "You have to login to be able to add favorite recipes"
            }



        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
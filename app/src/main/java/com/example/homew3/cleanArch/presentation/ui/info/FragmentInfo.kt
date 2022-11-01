package com.example.homew3.cleanArch.presentation.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homew3.R
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.databinding.FragmetRecipeInfoBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FragmentInfo : Fragment() {

    private var _binding: FragmetRecipeInfoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        ExtendedIngredientsAdapter(requireContext())
    }

    private val args by navArgs<FragmentInfoArgs>()

    private val infoViewModel by viewModel<InfoViewModel> {
        parametersOf(requireContext(),args.id.toString())
    }

    private val prefsManager by lazy {
        SharedPreferencesManager(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmetRecipeInfoBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefsManager.recipeId = args.id.toString()

        with(binding) {
            recyclerViewInfo.adapter = adapter

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }


            infoViewModel.infoFlow
                .onEach { lce ->
                    when (lce) {
                        is Lce.Content -> {
                            progress.isVisible = false
                            if (!lce.data.title.isEmpty()) {
                                recipeInfoName.isVisible = true
                                recipeInfoName.text = lce.data.title
                            }
                            linearVegetarian.isVisible = true
                            checkboxVegetarian.isChecked = lce.data.vegetarian
                            linearCheap.isVisible = true
                            checkboxCheap.isChecked = lce.data.cheap
                            linearLikes.isVisible = true
                            textAmountLikes.text = lce.data.aggregateLikes.toString()
                            linearPrice.isVisible = true
                            textPricePerServAmount.text = lce.data.pricePerServing.toString()
                            recyclerViewInfo.isVisible = true
                            actiobToWeb.isVisible = true
                            actiobToWeb.setOnClickListener {
                                val webIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(lce.data.spoonacularSourceUrl)
                                )
                                startActivity(webIntent)
                            }
                            adapter.submitList(lce.data.extendedIngredients)
                        }
                        is Lce.Error ->
                            Toast.makeText(
                                requireContext(),
                                "There is no info for this recipe",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        Lce.Loading -> progress.isVisible = true
                    }

                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            infoViewModel
                .shopFlow
                .onEach {lce->
                    when(lce){
                        is Lce.Content -> Toast.makeText(
                            requireContext(),
                            "Succes",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        is Lce.Error -> Toast.makeText(
                            requireContext(),
                            lce.throwable.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Lce.Loading -> null
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            toolbar.setOnMenuItemClickListener {
                    infoViewModel.onButtonClicked()
                true
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
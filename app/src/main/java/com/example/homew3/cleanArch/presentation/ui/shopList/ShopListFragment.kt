package com.example.homew3.cleanArch.presentation.ui.shopList

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.homew3.R
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.ui.info.ExtendedIngredientsAdapter
import com.example.homew3.cleanArch.presentation.ui.info.InfoViewModel
import com.example.homew3.cleanArch.presentation.ui.stepByStep.StepByStepFragmentArgs
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.addItemDecorationBottom
import com.example.homew3.databinding.FragmentIngredientShopListBinding
import com.example.homew3.databinding.FragmetRecipeInfoBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ShopListFragment : Fragment() {

    private var _binding: FragmentIngredientShopListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        ShopListAdapter(
            requireContext(),
        onIngredientClicked = {
//            AlertDialog.Builder(requireContext())
//                .setTitle("Delete this recipe?")
//                .setPositiveButton(android.R.string.ok){_,_->
//                    .onClickedOk(it)
//                }
//                .setNegativeButton(android.R.string.cancel){_,_->
//                }
//                .show()
            TODO()
        })

    }

    private val outRectBottom by lazy {
        requireContext().resources.getDimension(R.dimen.default_padding)
            .toInt()
    }

    private val prefsManager by lazy {
        SharedPreferencesManager(requireContext())
    }




    private val shopListViewModel by viewModel<ShopListViewModel> {
        parametersOf(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentIngredientShopListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            recyclerView.adapter = adapter

            recyclerView.addItemDecorationBottom(outRectBottom)

            if(prefsManager.isLogined){
                shopListViewModel
                    .shopListFlow
                    .onEach {lce->
                        when(lce){
                            is Lce.Content -> {
                                progress.isVisible = false
                                recyclerView.isVisible = true
                                adapter.submitList(lce.data)
                            }
                            is Lce.Error -> Toast.makeText(requireContext(), lce.throwable.message, Toast.LENGTH_LONG).show()
                            Lce.Loading -> progress.isVisible = true
                        }

                    }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                textViewShop.isVisible = true
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
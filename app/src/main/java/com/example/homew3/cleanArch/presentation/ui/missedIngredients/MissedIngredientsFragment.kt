package com.example.homew3.cleanArch.presentation.ui.missedIngredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homew3.R
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.addItemDecorationBottom
import com.example.homew3.databinding.FragmentMissedIngredientsBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MissedIngredientsFragment : Fragment() {

    private var _binding: FragmentMissedIngredientsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<MissedIngredientsFragmentArgs>()

    private val adapter by lazy {
        MissedIngredientsAdapter(
            context = requireContext()
        )
    }

    private val missedIngredientViewModel by viewModel<MissedIngredientViewModel> {
        parametersOf(args.id.toString())
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
        return FragmentMissedIngredientsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            recyclerView.addItemDecorationBottom(outRectBottom)

            ViewCompat.setOnApplyWindowInsetsListener(recyclerView) { _, insets ->
                val recyclerViewSystemBarInsets =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars())
                recyclerView.updatePadding(
                    bottom = recyclerViewSystemBarInsets.bottom,
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
                    bottom = toolbarSystemBarInsets.bottom

                )
                insets
            }

            missedIngredientViewModel
                .missedFlow
                .onEach { lce ->
                    when(lce){
                        is Lce.Content -> {
                            progress.isVisible = false
                            textViewMissedIngredients.isVisible = true
                            recyclerView.isVisible = true
                            adapter.submitList(lce.data)
                        }
                        is Lce.Error -> Toast.makeText(
                            requireContext(),
                            lce.throwable.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        Lce.Loading -> progress.isVisible = true
                    }

                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

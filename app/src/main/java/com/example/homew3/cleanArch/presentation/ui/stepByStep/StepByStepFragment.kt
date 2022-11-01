package com.example.homew3.cleanArch.presentation.ui.stepByStep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homew3.R
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.addItemDecorationBottom
import com.example.homew3.databinding.FragmentStepByStepBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StepByStepFragment : Fragment() {

    private var _binding: FragmentStepByStepBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        StepsAdapter(requireContext())
    }

    private val args by navArgs<StepByStepFragmentArgs>()

    private val stepsViewModel by viewModel<StepsViewModel> {
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
        return FragmentStepByStepBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            recyclerView.adapter = adapter

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

            stepsViewModel
                .stepFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach {
                    adapter.submitList(it)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
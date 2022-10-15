package com.example.homew3.cleanArch.presentation.ui.missedIngredients

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.homew3.R
import com.example.homew3.databinding.FragmentMissedIngredientsBinding

class MissedIngredientsFragment : Fragment() {

    private var _binding: FragmentMissedIngredientsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<MissedIngredientsFragmentArgs>()

    private val adapter by lazy {
        MissedIngredientsAdapter(
            context = requireContext()
        )
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


            adapter.submitList(args.missedIngredients.toList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

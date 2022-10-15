package com.example.homew3.cleanArch.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homew3.databinding.FragmentMoreInfoBinding

class MoreInfoFragment : Fragment() {

    private var _binding: FragmentMoreInfoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<MoreInfoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMoreInfoBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            buttonStepByStep.insets()
            buttonMissedIngredients.insets()
            buttonSetAlarm.insets()
            toolbar.insets()


            buttonMissedIngredients.setOnClickListener {
                findNavController()
                    .navigate(
                        MoreInfoFragmentDirections
                            .toMissedIngredientsFragment(args.missedIngredients)
                    )
            }

            buttonStepByStep.setOnClickListener {
                findNavController()
                    .navigate(
                        MoreInfoFragmentDirections.toStepsFragment(args.id)
                    )

                buttonSetAlarm.setOnClickListener {
                    //заготовка под будущий таймер, пока не успел разобраться и сделать
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun View.insets() {
            ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
                val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                this.updatePadding(
                    top = systemBarInsets.top,
                    left = systemBarInsets.left,
                    right = systemBarInsets.right,
                    bottom = systemBarInsets.bottom
                )
                insets
            }
        }
    }
}

package com.example.homew3.cleanArch.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homew3.databinding.FragmentStartAppBinding
import com.google.android.material.textfield.TextInputLayout

class StartFragment : Fragment() {

    private var _binding: FragmentStartAppBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentStartAppBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStart.setOnClickListener {
            val ingredients =
                binding.validationIngredients.getTextOrSetError() ?: return@setOnClickListener
            findNavController()
                .navigate(
                    StartFragmentDirections
                        .toRecipeFragment(ingredients)
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun TextInputLayout.getTextOrSetError(): String? {
            return editText?.text?.toString()
                ?.ifBlank {
                    error = "Enter something"
                    null
                }
        }
    }
}

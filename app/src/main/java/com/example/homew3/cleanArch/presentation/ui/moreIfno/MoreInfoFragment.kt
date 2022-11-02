package com.example.homew3.cleanArch.presentation.ui.moreIfno

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.insets
import com.example.homew3.databinding.FragmentMoreInfoBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoreInfoFragment : Fragment() {

    private var _binding: FragmentMoreInfoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<MoreInfoFragmentArgs>()

    private val prefsManager by lazy {
        SharedPreferencesManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMoreInfoBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    private val infoViewModel by viewModel<MoreInfoViewModel> {
        parametersOf(args.id.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                prefsManager.recipeId = ""
                findNavController().popBackStack()
            }
            buttonStepByStep.insets()
            buttonMissedIngredients.insets()
            buttonSetAlarm.insets()
            buttonRecipeInfo.insets()


            buttonMissedIngredients.setOnClickListener {
                findNavController()
                    .navigate(
                        MoreInfoFragmentDirections
                            .toMissedIngredientsFragment(args.id)
                    )
            }

            buttonStepByStep.setOnClickListener {
                findNavController()
                    .navigate(
                        MoreInfoFragmentDirections.toStepsFragment(args.id)
                    )

            }

            buttonRecipeInfo.setOnClickListener {
                findNavController()
                    .navigate(
                        MoreInfoFragmentDirections.toInfoFragment(args.id)
                    )
            }

            buttonSetAlarm.setOnClickListener {
                findNavController()
                    .navigate(
                        MoreInfoFragmentDirections.toTimerFragment()
                    )
            }

            binding.toolbar.setOnMenuItemClickListener {
                infoViewModel.onButtonClicked(args.id, prefsManager.userId)
                true
            }


            infoViewModel
                .favoriteFlow
                .onEach {lce ->
                    when(lce) {
                        is Lce.Content -> Toast.makeText(requireContext(), "Added to favorite", Toast.LENGTH_SHORT).show()
                        is Lce.Error -> Toast.makeText(requireContext(), lce.throwable.message, Toast.LENGTH_LONG).show()
                        Lce.Loading -> null
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        prefsManager.recipeId = ""
        _binding = null
    }
}

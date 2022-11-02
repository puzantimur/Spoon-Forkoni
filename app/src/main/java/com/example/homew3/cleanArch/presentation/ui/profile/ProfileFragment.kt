package com.example.homew3.cleanArch.presentation.ui.profile

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
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.ui.profile.registryLogin.LoginFragmentDirections
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.getPasswordOrSetError
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.getTextOrSetError
import com.example.homew3.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val prefsManager by lazy {
        SharedPreferencesManager(requireContext())
    }


    private val profileViewModel by viewModel<ProfileViewModel>{
        parametersOf(prefsManager.userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentProfileBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        with(binding){

            profileViewModel
                .loginFlow
                .onEach {lce->
                    when(lce) {
                        is Lce.Content -> {
                            progress.isVisible = false
                            imageAvatar.isVisible = true
                            nickname.isVisible = true
                            textViewRang.isVisible = true
                            amountFavorite.text = lce.data.favorite.size.toString()
                            textViewFavorite.isVisible = true
                            amountRang.isVisible = true
                            amountFavorite.isVisible = true
                            buttonDeleteMe.isVisible = true
                            buttonLogOut.isVisible = true
                            nickname.text = lce.data.login
                        }
                        is Lce.Error -> Toast
                            .makeText(
                                requireContext(),
                                lce.throwable.message,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        Lce.Loading -> progress.isVisible = true
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            buttonLogOut.setOnClickListener {
                prefsManager.isLogined = false
                prefsManager.userId = ""
                findNavController().navigate(ProfileFragmentDirections.toRegistry())
            }

            buttonDeleteMe.setOnClickListener {
                profileViewModel.onButtonClicked(prefsManager.userId)
                prefsManager.isLogined = false
                prefsManager.userId = ""
                findNavController().navigate(ProfileFragmentDirections.toRegistry())
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.homew3.cleanArch.presentation.ui.profile.registryLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.ui.profile.ProfileViewModel
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.getPasswordOrSetError
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.getTextOrSetError
import com.example.homew3.databinding.FragmentProfileBinding
import com.example.homew3.databinding.FragmentRegistryBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class RegistryFragment : Fragment() {

    private var _binding: FragmentRegistryBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val registryViewModel by viewModel<RegistryViewModel>{
        parametersOf(requireContext())
    }

    private val prefsManager by lazy {
        SharedPreferencesManager(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRegistryBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(prefsManager.isLogined) findNavController().navigate(RegistryFragmentDirections.toProfile())

        registryViewModel
            .registryFlow
            .onEach { lce ->
                when (lce) {
                    is Lce.Content -> {
                        Toast
                            .makeText(requireContext(), "Success registry", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(RegistryFragmentDirections.toProfile())
                    }
                    is Lce.Error -> Toast
                        .makeText(
                            requireContext(),
                            "Email or login already used",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    Lce.Loading -> null
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        with(binding) {
            buttonEnter.setOnClickListener {
                val login = validationLogin.getTextOrSetError()
                val password = validationPassword.getPasswordOrSetError()
                val email = validationEmail.getTextOrSetError()
                if (login == null || password == null || email == null) {
                    return@setOnClickListener
                }
                registryViewModel.onButtonClicked(login, password, email)
            }

            signIn.setOnClickListener {
                findNavController().navigate(RegistryFragmentDirections.toLoginProfile())
            }
        }



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
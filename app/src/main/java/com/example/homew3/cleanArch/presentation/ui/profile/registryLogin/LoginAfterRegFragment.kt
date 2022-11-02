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
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.getTextOrSetError
import com.example.homew3.databinding.FragmentLogAfterRegBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginAfterRegFragment : Fragment() {

    private var _binding: FragmentLogAfterRegBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val loginViewModel by viewModel<LoginViewModel> {
        parametersOf(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLogAfterRegBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            buttonEnter.setOnClickListener {
                val login = validationLogin.getTextOrSetError()
                val password = validationPassword.getTextOrSetError()
                if (login == null || password == null)
                    return@setOnClickListener
                loginViewModel.onButtonClicked(login, password)
            }
        }

        loginViewModel
            .loginFlow
            .onEach { lce ->
                when (lce) {
                    is Lce.Content -> {
                        Toast
                            .makeText(requireContext(), "Success login", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(LoginAfterRegFragmentDirections.toProfile())
                    }
                    is Lce.Error -> Toast
                        .makeText(
                            requireContext(),
                            "There is no user like this",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    Lce.Loading -> null
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.homew3.cleanArch.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.databinding.FragmentSpoonforkoniBinding

class FragmentGetStarted : Fragment() {

    private var _binding: FragmentSpoonforkoniBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val prefsManager by lazy {
        SharedPreferencesManager(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSpoonforkoniBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            if(prefsManager.isLogined) findNavController().navigate(FragmentGetStartedDirections.toStartFragment())

            buttonGetStarted.setOnClickListener {
                findNavController()
                    .navigate(
                        FragmentGetStartedDirections.toStartFragment()
                    )
            }

            signIn.setOnClickListener {
                findNavController()
                    .navigate(
                        FragmentGetStartedDirections.toLoginFragment()
                    )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


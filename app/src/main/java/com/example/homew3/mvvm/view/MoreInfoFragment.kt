package com.example.homew3.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
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
            textViewId.text = args.id.toString()
            textViewTitle.text = args.title
            thisRecipeView.load(args.image)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

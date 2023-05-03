package com.example.silencio_app.soundlevels

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentCreatePassswordBinding
import com.example.silencio_app.databinding.FragmentExampleReferencesBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ExampleReferencesFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentExampleReferencesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExampleReferencesBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        binding.cancel.setOnClickListener {

            findNavController().navigateUp()

        }


        return binding.root

    }



}
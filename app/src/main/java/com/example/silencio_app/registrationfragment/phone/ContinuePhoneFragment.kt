package com.example.silencio_app.registrationfragment.phone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.util.TableInfo.Index
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentCodeBinding
import com.example.silencio_app.databinding.FragmentContinuePhoneBinding
import com.example.silencio_app.registrationfragment.invitecode.CodeFragmentDirections
import com.example.silencio_app.registrationfragment.model.RegisterUserRequestModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContinuePhoneFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentContinuePhoneBinding? = null
    private val binding get() = _binding!!
    private var isCodevalid = false

   private val ages by navArgs<ContinuePhoneFragmentArgs>()
   // private val ags by navArgs<ContinuePhoneFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentContinuePhoneBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED




        setOnClick()
        return  binding.root

    }

    private fun setOnClick(){
        binding.editContinue.setOnClickListener {
            findNavController().navigate(

                ContinuePhoneFragmentDirections.actionContinuePhoneFragmentToRegisterPhoneNumberFragment(ages.userX)

            )

        }
        binding.cancel.setOnClickListener {

            findNavController().navigateUp()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
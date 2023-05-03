package com.example.silencio_app.registrationfragment.phone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentCodeBinding
import com.example.silencio_app.databinding.FragmentContinuePhoneBinding
import com.example.silencio_app.databinding.FragmentRegisterPhoneNumberBinding
import com.example.silencio_app.registrationfragment.invitecode.CodeFragmentDirections
import com.example.silencio_app.registrationfragment.invitecode.CodeViewModel
import com.example.silencio_app.registrationfragment.model.RegisterUserRequestModel
import com.example.silencio_app.util.NetworkResult
import com.example.silencio_app.util.snackBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterPhoneNumberFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentRegisterPhoneNumberBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegiterViewModel by viewModels()
    private var isPhonevalid = false
    private val args by navArgs<RegisterPhoneNumberFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterPhoneNumberBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED


        binding.viewmodel = viewModel
        binding.lifecycleOwner= viewLifecycleOwner
        setObservetion()
        setOnClick()
        return binding.root
    }
    private fun setOnClick(){
        binding.btncode.setOnClickListener {
            Toast.makeText(requireContext(),"phone valid", Toast.LENGTH_LONG).show()
            if(isPhonevalid){
                viewModel.verifynumber(binding.editCountryCode.selectedCountryCodeWithPlus)
            }

        }
        binding.cancel.setOnClickListener {

            findNavController().navigateUp()

        }

    }

    private fun setObservetion(){

        lifecycleScope.launch {

            viewModel.isphonevail.collect(){
                isPhonevalid = it
                binding.btncode.isEnabled = it

            }

        }
        viewModel.registerWithPhoneResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {


                }
                is NetworkResult.Success -> {

                        findNavController().navigate(

                            RegisterPhoneNumberFragmentDirections.actionRegisterPhoneNumberFragmentToCreateAccountFragment(args.regiterUser.copy(
                                countryCode = binding.editCountryCode.selectedCountryCodeWithPlus,
                                phone = viewModel.phonenumber.value
                            ))

                        )


                }
                else -> {}
            }
        })

    }

}
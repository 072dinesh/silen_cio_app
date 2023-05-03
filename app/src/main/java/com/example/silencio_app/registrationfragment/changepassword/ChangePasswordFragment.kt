package com.example.silencio_app.registrationfragment.changepassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentChangePasswordBinding
import com.example.silencio_app.databinding.FragmentCreatePassswordBinding
import com.example.silencio_app.login.LoginFragmentDirections
import com.example.silencio_app.registrationfragment.password.PasswordViewModel
import com.example.silencio_app.util.NetworkResult
import com.example.silencio_app.util.PrefManagers
import com.example.silencio_app.util.snackBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private var isvalidpass = false
    private val viewModela: ChangePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        binding.cancel.setOnClickListener {

            findNavController().navigateUp()

        }


//        setOnClick()
//        setObservetion()

        setObservetion()
        setOnclick()
        binding.viewmodel = viewModela
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root

    }
    private fun setOnclick(){

        binding.btnOnBoardingContinue.setOnClickListener {
            viewModela.changePassword()
        }

    }


    private fun setObservetion(){

//        lifecycleScope.launch {
//
//            viewModela.isValid.collect(){
//                ispasswordvalid = it
//                //binding.btncode.isEnabled = it
//
//            }
//
//        }
        viewModela.changePasswordResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {

                    response.data?.data?.let {
                        lifecycleScope.launch {

                            with(Dispatchers.IO) {
                                viewModela.savadatauser(it.copy(uuid = 1))
                                PrefManagers.setBoolean(PrefManagers.IS_LOGIN, true)
                                PrefManagers.setString(PrefManagers.ACCESS_TOKEN, it.authToken)
                            }

                        }



                    }
                }
                else -> {}
            }
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
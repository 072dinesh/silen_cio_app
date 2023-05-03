package com.example.silencio_app.login

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
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentCreateAccountBinding
import com.example.silencio_app.databinding.FragmentLoginBinding
import com.example.silencio_app.registrationfragment.account.AccountViewModel
import com.example.silencio_app.registrationfragment.account.CreateAccountFragmentDirections
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
class LoginFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var ispasswordvalid = false
    private val viewModela: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED


        binding.viewmodel = viewModela
        binding.lifecycleOwner = viewLifecycleOwner

        setOnClick()
        setObservetion()
        return binding.root
    }

    private fun setOnClick(){
        binding.btncode.setOnClickListener {
            Toast.makeText(requireContext(),"code valid", Toast.LENGTH_LONG).show()
            if(ispasswordvalid){
                //add country code
                //use dependency add
                viewModela.checkphonenumber(binding.editCountryCodes.selectedCountryCodeWithPlus)
            }

        }
        binding.cancel.setOnClickListener {
            //fragment exit
            findNavController().navigateUp()

        }

    }

    private fun setObservetion(){

        //maping data
        lifecycleScope.launch {

            viewModela.isValid.collect(){
                ispasswordvalid = it
                //binding.btncode.isEnabled = it

            }

        }
        viewModela.checkPhoneNumberResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {


                }
                is NetworkResult.Success -> {

                    //store authToken
                    response.data?.data?.let {
                        lifecycleScope.launch {

                            with(Dispatchers.IO) {
                                viewModela.savadatauser(it.copy(uuid = 1))
                                PrefManagers.setBoolean(PrefManagers.IS_LOGIN, true)
                                PrefManagers.setString(PrefManagers.ACCESS_TOKEN, it.authToken)
                            }
                            findNavController().navigate(

                                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            )
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
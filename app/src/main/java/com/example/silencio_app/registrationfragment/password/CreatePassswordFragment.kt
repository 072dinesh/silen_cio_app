package com.example.silencio_app.registrationfragment.password

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
import com.example.silencio_app.databinding.FragmentCreateAccountBinding
import com.example.silencio_app.databinding.FragmentCreatePassswordBinding
import com.example.silencio_app.registrationfragment.account.AccountViewModel
import com.example.silencio_app.registrationfragment.account.CreateAccountFragmentArgs
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
class CreatePassswordFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCreatePassswordBinding? = null
    private val binding get() = _binding!!

    private var isvalidpass = false
    private val viewModela: PasswordViewModel by viewModels()

    private val ages by navArgs<CreatePassswordFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePassswordBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        binding.cancel.setOnClickListener {

            findNavController().navigateUp()

        }


       setOnClick()
        setObservetion()

        binding.viewmodelpassword = viewModela
        binding.lifecycleOwner = this

        return binding.root


    }

    private fun setObservetion() {
        lifecycleScope.launch{

            viewModela.ispasswordValid.collect(){

                isvalidpass=it
            }

        }
    }

    private fun setOnClick(){
        binding.btncode.setOnClickListener {
            Toast.makeText(requireContext(),"code valid", Toast.LENGTH_LONG).show()
            if(isvalidpass){
                viewModela.verifySignupPhone(ages.regiteruser)
            }

        }

        viewModela.registerSignupPhoneResponse.observe(viewLifecycleOwner, Observer { response ->
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
                            findNavController().navigate(

                                CreatePassswordFragmentDirections.actionCreatePassswordFragmentToHomeFragment()
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
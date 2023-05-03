package com.example.silencio_app.registrationfragment.invitecode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.silencio_app.databinding.FragmentCodeBinding

import com.example.silencio_app.registrationfragment.model.RegisterUserRequestModel
import com.example.silencio_app.util.NetworkResult
import com.example.silencio_app.util.snackBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CodeFragment : BottomSheetDialogFragment() {
    private var _binding:FragmentCodeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CodeViewModel by viewModels()
    private var isCodevalid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        binding.cancel.setOnClickListener {

            findNavController().navigateUp()

        }


        setOnClick()
        setObservetion()

        binding.viewcode = viewModel
        binding.lifecycleOwner=this
        return binding.root
    }

    private fun setOnClick(){
        binding.btncodeBottom.setOnClickListener {
            Toast.makeText(requireContext(),"code valid",Toast.LENGTH_LONG).show()
            if(isCodevalid){
                viewModel.getPostdatainvaidcode()
            }

        }
        binding.textCon.setOnClickListener {

        }

        binding.textAlreadyLogin.setOnClickListener {
            findNavController().navigate(

                    CodeFragmentDirections.actionCodeFragmentToLoginFragment5()

            )
        }

    }


    private fun setObservetion(){

        lifecycleScope.launch {

            viewModel.iscodevail.collect(){
                isCodevalid = it
                binding.btncodeBottom.isEnabled = it
                binding.btncodeBottom.isClickable = it
            }

        }
        viewModel.allUsers.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {


                }
                is NetworkResult.Success -> {

                    response.data?.data?.id?.let {

                        findNavController().navigate(

                            CodeFragmentDirections.actionRegistrationFragmentToContinuePhoneFragment(
                                RegisterUserRequestModel(referreId = it))

                        )

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
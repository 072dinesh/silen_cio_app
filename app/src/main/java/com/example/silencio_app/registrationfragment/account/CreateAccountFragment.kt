package com.example.silencio_app.registrationfragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.silencio_app.databinding.FragmentCreateAccountBinding
import com.example.silencio_app.registrationfragment.phone.RegisterPhoneNumberFragmentDirections
import com.example.silencio_app.util.NetworkResult
import com.example.silencio_app.util.snackBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateAccountFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private var isnickvalid = false
    private val viewModela: AccountViewModel by viewModels()

    private val ages by navArgs<CreateAccountFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        binding.cancel.setOnClickListener {

            findNavController().navigateUp()

        }


        setOnClick()
        setObservetion()

        binding.viewmodelaccount = viewModela
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
    private fun setOnClick(){
        binding.btncode.setOnClickListener {
            Toast.makeText(requireContext(),"code valid", Toast.LENGTH_LONG).show()
            if(isnickvalid){
                viewModela.verifyNickName()
            }

        }
        binding.cancel.setOnClickListener {

            findNavController().navigateUp()

        }

    }

    private fun setObservetion(){

        lifecycleScope.launch {

            viewModela.isNameValid.collect(){
                isnickvalid = it
                binding.btncode.isEnabled = it

            }

        }
        viewModela.registerWithNickNameResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {


                }
                is NetworkResult.Success -> {

                    findNavController().navigate(

                        CreateAccountFragmentDirections.actionCreateAccountFragmentToCreatePassswordFragment(ages.regiteruser.copy(
                           firstName = viewModela.firstName.value,
                            lastName = viewModela.lastName.value,
                            nickName = viewModela.nickName.value

                        ))

                    )


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
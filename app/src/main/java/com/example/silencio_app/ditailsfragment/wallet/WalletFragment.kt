package com.example.silencio_app.ditailsfragment.wallet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentCodeBinding
import com.example.silencio_app.databinding.FragmentWalletBinding
import com.example.silencio_app.registrationfragment.invitecode.CodeViewModel
import com.example.silencio_app.util.NetworkResult
import com.example.silencio_app.util.PrefManagers
import com.example.silencio_app.util.Utils
import com.example.silencio_app.util.snackBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletFragment : Fragment() {
    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WalleModelView by viewModels()
   // lateinit var fragmentManager: FragmentManager
    private lateinit var adapter: WalletAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)

        setupUi()
        setObservers()
        setOnclick()
       // onBackPressed()
        adapter = WalletAdapter()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerview.adapter = adapter

        return binding.root
    }
//    private fun onBackPressed(){
//
//        fragmentManager = activity?.supportFragmentManager!!
//
//        fragmentManager.popBackStack()
//
//    }
    private fun setupUi() {
        viewModel.getPostdatainvaidcode()
        viewModel.getfrindlist()

    }
    private fun setOnclick(){
        binding.textView8.setOnClickListener {
            viewModel.getPingdata()
        }

        binding.materialTextButton.setOnClickListener {

            viewModel.users.value?.getOrNull(0)?.let {
                Utils.share(requireContext().getString(R.string.invite_string_formatter,it.nickName),
                    requireContext()
                    )
            }
        }

    }
    private fun setObservers() {
        viewModel.WalletData.observe(viewLifecycleOwner, Observer{ response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {

                    response.data?.data?.let {
                        Log.e("DATA",it.toString())

                        //binding.viewModelWallet = response.data
                        binding.textView3.text = String.format("%,.2f",it.totalWalletAmount)
                        binding.textView5.text = getString(R.string.wallet_forment,it?.totalCoinsFromSamples)
                        binding.textfrind.text = getString(R.string.wallet_friend,it?.totalCoinsFromReferal)

                    }
                }
            }
        })


        viewModel.FrindList.observe(viewLifecycleOwner, Observer{ response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {

                    response.data?.data?.let {data->
                    adapter.setData(data ?.filterNotNull()?: emptyList())


                    }
                }
            }
        })

        viewModel.PingtData.observe(viewLifecycleOwner, Observer{ response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {

                    response.data?.message?.snackBar(requireView(),requireContext())

                    viewModel.getPingdata()

                    }
                }
        })

        viewModel.users.observe(viewLifecycleOwner){}

    }

}
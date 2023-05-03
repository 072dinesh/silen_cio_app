package com.example.silencio_app.ditailsfragment.shop

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentHomeBinding
import com.example.silencio_app.databinding.FragmentShopBinding
import com.example.silencio_app.databinding.FragmentWalletBinding
import com.example.silencio_app.ditailsfragment.wallet.WalleModelView
import com.example.silencio_app.util.Constants
import com.example.silencio_app.util.NetworkResult
import com.example.silencio_app.util.PrefManagers
import com.example.silencio_app.util.snackBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    //lateinit var fragmentManager: FragmentManager
    private val viewModel: ShopViewModel by viewModels()
    var sideMenuBroadcaster: LocalBroadcastManager? = null
    var bottomNavBroadcaster: LocalBroadcastManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(layoutInflater)

        setOnClicks()
        setUpUI()
        setObservers()
        setupUi()
       // onBackPressed()

        return binding.root
    }

    private fun setUpUI() {
        //binding.guidelineTop.setGuidelineBegin(getStatusBarHeight())

        sideMenuBroadcaster = LocalBroadcastManager.getInstance(requireContext())
        bottomNavBroadcaster = LocalBroadcastManager.getInstance(requireContext())
    }

    private fun setOnClicks() {
        binding.toolbar.setOnClickListener {
            sideMenuBroadcaster?.sendBroadcast(
                Intent(Constants.BRODCAST_RECIVER.SIDE_MENU)
            )

        }
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
    }
    private fun setObservers() {
        viewModel.WalletShopData.observe(viewLifecycleOwner, Observer{ response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {

                    response.data?.data?.let {

                        binding.shopCoin.text = String.format("%,.2f",it.totalWalletAmount)

                    }
                }
            }
        })



    }

}
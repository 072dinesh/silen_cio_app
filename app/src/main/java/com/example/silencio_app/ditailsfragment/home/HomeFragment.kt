package com.example.silencio_app.ditailsfragment.home

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentHomeBinding
import com.example.silencio_app.ditailsfragment.wallet.WalleModelView
import com.example.silencio_app.util.*
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class  HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var sideMenuBroadcaster: LocalBroadcastManager? = null
    var bottomNavBroadcaster: LocalBroadcastManager? = null
   private val viewModel: HomeViewModel by viewModels()
    lateinit var toggle: ActionBarDrawerToggle

    private var isBackPressdOnce = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setDarkStatusBar()
        setOnClicks()
        setUpUI()
        setObservers()
        setupUi()
       viewModel.getPostdatainvaidcode()

        Timber.e("Token",PrefManagers.getString(PrefManagers.ACCESS_TOKEN))

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


        private fun setupUi() {
            viewModel.getPostdatainvaidcode()
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

                            binding.homeCoin.text = String.format("%,.2f",it.totalWalletAmount)

                        }
                    }
                }
            })



        }


//        binding.cardHomeNoiseCoin.setOnClickListener {
//            val intent = Intent(Constant.BROADCAST_RECEIVER.BOTTOM_NAV)
//            intent.putExtra(
//                Constant.BOTTOM_NAV_BROADCAST_NAME,
//                "wallet"
//            )
//            bottomNavBroadcaster?.sendBroadcast(intent)
//        }
//        binding.askSilencioBtn.setOnClickListener {
//            findNavController().navigate(
//                HomeFragmentDirections.actionHomeFragmentToAskSilencioBottomSheet()
//            )
//        }

}
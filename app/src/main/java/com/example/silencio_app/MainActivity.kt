package com.example.silencio_app

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract.Root
import android.provider.SyncStateContract.Constants
import android.view.View
import android.widget.Toast
import android.window.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.silencio_app.databinding.DrawarlayoutBinding
import com.example.silencio_app.databinding.FragmentHomeBinding
import com.example.silencio_app.databinding.FragmentOnboardingBinding
import com.example.silencio_app.util.PrefManagers
import com.example.silencio_app.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private var _binding: DrawarlayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

   /// private lateinit var splashScreen: SplashScreen
    private var showSplashScreen = true
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
    var splashScreen = installSplashScreen()
            //splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = DrawarlayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Timber.e("Token",PrefManagers.getString(PrefManagers.ACCESS_TOKEN))
        splashScreen.setKeepOnScreenCondition{
            false
        }



        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment

        navController = navHostFragment.navController

        var nabGaph = navController.navInflater.inflate(R.navigation.my_nav)
        setonclicksaidbar()



        val isLoggedIn = PrefManagers.getBoolean(
            com.example.silencio_app.util.Constants.IS_LOGIN,
            false
        )
        val isIntroComplete = PrefManagers.getBoolean(
            com.example.silencio_app.util.Constants.IS_INTRO_COMPLETE,
            false
        )
        if (isLoggedIn && isIntroComplete) {
            nabGaph.setStartDestination(R.id.homeFragment)
        }

        if (!isLoggedIn) {
            nabGaph.setStartDestination(R.id.onboardingFragment)
        }

        if (!isIntroComplete) {
            nabGaph.setStartDestination(R.id.onboardingFragment)
        }


        showSplashScreen=false
        navController.graph = nabGaph
        binding.activityMain.customBottomNavWithoutFabBottomNav.setupWithNavController(navController)

        navController = navHostFragment.navController
        binding.navview.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
               R.id.homeFragment,
                R.id.walletFragment,
                R.id.mapFragment,
                R.id.shopFragment,
            )
        )

        setCurrentDestinationListener()
        setOnclick()
    }




    private fun setCurrentDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            deselectAllTabs()
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.activityMain.customNavHome.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,R.drawable.vectorhome,0,0
                    )

                }
                R.id.walletFragment -> {
                    binding.activityMain.wallet.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,R.drawable.vector__22_w,0,0
                    )

                }
                R.id.mapFragment -> {

                    binding.activityMain.map.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,R.drawable.vector_tem,0,0
                    )

                }
                R.id.shopFragment -> {
                    binding.activityMain.shop.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,R.drawable.vector__23_shop,0,0
                    )

                }

                else ->Unit

            }

            when (destination.id) {
                R.id.homeFragment,
                R.id.walletFragment,
                R.id.mapFragment,
                R.id.shopFragment ->
                 {
                    binding.activityMain.customLinearLayout.isVisible = true
                    binding.activityMain.customNavFloatBtn.isVisible = true
                }
                else -> {
                    binding.activityMain.customLinearLayout.isVisible = false
                    binding.activityMain.customNavFloatBtn.isVisible = false
                }
            }
        }
    }



    private fun setOnclick(){


        binding.navHeader.llLogout.setOnClickListener{

            Utils.logout(this)
        }

        binding.activityMain.customNavHome.setOnClickListener{

            binding.activityMain.customBottomNavWithoutFabBottomNav.selectedItemId = R.id.homeFragment
        }
        binding.activityMain.wallet.setOnClickListener {

            binding.activityMain.customBottomNavWithoutFabBottomNav.selectedItemId = R.id.walletFragment
        }
        binding.activityMain.map.setOnClickListener {

            binding.activityMain.customBottomNavWithoutFabBottomNav.selectedItemId = R.id.mapFragment
        }
        binding.activityMain.shop.setOnClickListener {

            binding.activityMain.customBottomNavWithoutFabBottomNav.selectedItemId = R.id.shopFragment
        }
        binding.activityMain.customNavFloatBtn.setOnClickListener {


            if(PrefManagers.getBoolean(com.example.silencio_app.util.Constants.DO_NOT_SHOW_AGIN.HOW_TO_AGIN,false)){
                navController.navigate(R.id.measureFragment)

            }
            else
            {
                //congratulation check
                navController.navigate(R.id.measureFragment)
            }
        }




    }

    private fun openProfileFragment(){
        navController.navigate(R.id.profileFragment)
        binding.drawerLayout.close()
    }


    private fun setonclicksaidbar(){

        binding.navHeader.ivUserImg.setOnClickListener {
            openProfileFragment()
        }

    }

    private fun deselectAllTabs() {
        binding.activityMain.customNavHome.setCompoundDrawablesRelativeWithIntrinsicBounds(
            0,R.drawable.vector__21_,0,0
        )
        binding.activityMain.wallet.setCompoundDrawablesRelativeWithIntrinsicBounds(
            0,R.drawable.vectores,0,0
        )
        binding.activityMain.map.setCompoundDrawablesRelativeWithIntrinsicBounds(
            0,R.drawable.vectornew,0,0
        )
        binding.activityMain.shop.setCompoundDrawablesRelativeWithIntrinsicBounds(
            0,R.drawable.vector_23,0,0
        )

    }
    private val openSidMenuDrawar : BroadcastReceiver = object :BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?) {
            binding.drawerLayout.open()
        }
    }

    private val bottomNavBroadcast: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when(intent.getStringExtra(com.example.silencio_app.util.Constants.IS_LOGIN)){
                    "wallet" -> {
                        binding.activityMain.customBottomNavWithoutFabBottomNav.selectedItemId = R.id.walletFragment
                    }
                    else -> Unit
                }
            }
        }


    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            (openSidMenuDrawar),
            IntentFilter(com.example.silencio_app.util.Constants.BRODCAST_RECIVER.SIDE_MENU)
        )
        LocalBroadcastManager.getInstance(this).registerReceiver(
            (bottomNavBroadcast),
            IntentFilter(com.example.silencio_app.util.Constants.BRODCAST_RECIVER.BUTTOM_NEV)
        )

    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    override fun onDestroy() {
        super.onDestroy()
    _binding = null
    }
}
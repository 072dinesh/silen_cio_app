package com.example.silencio_app.onboardingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2


import com.example.silencio_app.databinding.FragmentOnboardingBinding
import com.example.silencio_app.util.Constants
import com.example.silencio_app.util.PrefManagers
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment() {

    private var _binding:FragmentOnboardingBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        setUpUi()
        setOnClicks()
        return binding.root
    }

    private fun setUpUi() {
        //binding.guidelineTop.setGuidelineBegin(getStatusBarHeight())

        var adapter = OnboardingAdapter(requireActivity())
        binding.viewTransition.adapter = adapter

        TabLayoutMediator(binding.TircleIndicator3, binding.viewTransition){ _, _ ->

        }.attach()

        binding.viewTransition.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.btnOnBoardingContinue.isEnabled = position == 3
            }
        })
    }
    private fun setOnClicks() {
        binding.btnOnBoardingContinue.setOnClickListener {
            PrefManagers.setBoolean(Constants.IS_INTRO_COMPLETE, true)


            findNavController().navigate(

                OnboardingFragmentDirections.actionOnboardingFragmentToCodeFragment()


            )

        }
    }


}
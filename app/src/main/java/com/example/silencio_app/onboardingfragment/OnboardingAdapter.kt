package com.example.silencio_app.onboardingfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.silencio_app.R


class OnboardingAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    var NUM_PAGES = 4
    override fun getItemCount(): Int = NUM_PAGES


    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            OnboardingViewFragment(

                R.drawable.volume_down,
                "Welcome to Silencio!",
                " The first community to fight noise pollution Run silencio once a day to help us build a global community driven noise pollution map."
            )
        } else if (position == 1){
            OnboardingViewFragment(

                R.drawable.x34__1,
                "What is noise pollution?",
                "Get notified when your surrounding gets too loud and avoid loud areas with our map."
            )
        } else if (position == 2){
            OnboardingViewFragment(

                R.drawable.x35,
                "Help us measure noise levels around your daily activities",
                "We respect your privacy and never record any audio on your phone, only loudness."
            )
        } else{
            OnboardingViewFragment(

                R.drawable.x36,
                "Earn tokens while adding value to the community",
            "Run Silencio once a day to help us build a global community driven noise pollution map."
            )
        }
    }
}
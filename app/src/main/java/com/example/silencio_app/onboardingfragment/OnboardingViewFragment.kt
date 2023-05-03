package com.example.silencio_app.onboardingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentOnboardingViewBinding


class OnboardingViewFragment(@DrawableRes private val imageRes: Int,private val community:String,private val description:String ) : Fragment() {
    private var _binding: FragmentOnboardingViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingViewBinding.inflate(inflater, container, false)

        setUpUi()

        return binding.root
    }
    private fun setUpUi() {
        //binding.textwelcome.text = title
        binding.textcomunity.text = community
        binding.textDescription.text = description
        //binding.shapeableImageViews.imageAlpha = imageRes


        binding.image.load(imageRes){
            crossfade(false)

        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
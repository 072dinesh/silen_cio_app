package com.example.silencio_app.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation

import com.example.silencio_app.R
import com.example.silencio_app.databinding.FragmentProfileBinding
import com.example.silencio_app.util.*

import com.lassi.data.media.MiMedia
import com.lassi.domain.media.LassiOption

import com.lassi.presentation.builder.Lassi
import com.lassi.presentation.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var lassiResult: ActivityResultLauncher<Intent>
    private val viewModel: ProfileModelView by viewModels()
    private val editprofileirl: File? = null
    var sideMenuBroadcaster : LocalBroadcastManager?=null
    var editProfileUrl :File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        ViewCompat.setOnApplyWindowInsetsListener(requireActivity().window.decorView) { v, insets ->
            val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())

            val height = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

            if(_binding != null){
                if(isKeyboardVisible){
                    binding.clMain.setPadding(
                        0,0,0, height
                    )
                } else {
                    binding.clMain.setPadding(
                        0,0,0, 30
                    )
                }
            }

            return@setOnApplyWindowInsetsListener insets
        }

       setobserup()
        setObservetion()
        setOnlick()
        setUpUi()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root

    }


    private fun setOnlick(){

        binding.btnProfilContinue.setOnClickListener {

            viewModel.verifyProfileName(
                editprofileirl != null,
                editprofileirl
            )
        }
    }

    private fun setobserup(){

        viewModel.user.observe(viewLifecycleOwner){users->
            users.firstOrNull()?.let {
                binding.user = it
                binding.editPhoneNumber.setText("${it.countryCode} ${it.phone}")
                viewModel.firstName.value = it.firstName ?: ""
                viewModel.lastName.value = it.lastName ?: ""
            }
        }

        binding.cardView3.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.textChangePassword.setOnClickListener {

            findNavController().navigate(

                ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            )
        }



        binding.textDelete.setOnClickListener {

                    viewModel.deleteAccount()
        }

        binding.btnEditProfileImg.setOnClickListener {
            lassiResult.launch(
                Lassi(requireContext())
                    .with(LassiOption.CAMERA_AND_GALLERY)
                    .setMaxCount(1)
                    .setMediaType(com.lassi.domain.media.MediaType.IMAGE)
                    .setCropType(CropImageView.CropShape.RECTANGLE)
                    .setStatusBarColor(com.lassi.R.color.design_default_color_primary)
                    .setToolbarColor(R.color.bluish_color)
                    .setProgressBarColor(R.color.black)
                    .setCropAspectRatio(1, 1)
                    .enableFlip()
                    .enableRotate()
                    .build()
            )
        }

    }
    private fun setUpUi() {
       // binding.guidelineTop.setGuidelineBegin(getStatusBarHeight())

        sideMenuBroadcaster = LocalBroadcastManager.getInstance(requireContext())

        lassiResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedMedia =
                    it.data?.getSerializableExtra(KeyUtils.SELECTED_MEDIA) as ArrayList<MiMedia>
                if (!selectedMedia.isNullOrEmpty()) {
                    selectedMedia.forEachIndexed { index, miMedia ->
                        val file = File(miMedia.path)
                        if(file.exists()){
                            binding.imageView10.load(file){
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            }
                            editProfileUrl = file
//                            val imageBean = ImageBean()
//                            imageBean.imageUrl = file.toURI().toString()
//                            if(reviewImages.size <= 1){
//                                reviewImages.add(imageBean)
//                            }
//                            showReviewImages()
                        }
                    }
                }
            }
        }
    }









    private fun setObservetion(){


        viewModel.profileUsers.observe(viewLifecycleOwner, Observer { response ->
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
                                viewModel.savadatauser(it.copy(uuid = 1))
                                PrefManagers.setBoolean(PrefManagers.IS_LOGIN, true)
                                PrefManagers.setString(PrefManagers.ACCESS_TOKEN, it.authToken)
                            }
                            findNavController().navigateUp()
                        }



                    }
                }
                else -> {}
            }
        })

        viewModel.deleteUsers.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Error -> {
                    response.message?.snackBar(requireView(), requireContext())
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {

                   // Utils.logout(requireContext())
                    Utils.logout(requireContext())
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
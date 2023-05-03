package com.example.silencio_app.util

import android.view.View
import androidx.fragment.app.Fragment



    fun Fragment.setDarkStatusBar() {
        activity?.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

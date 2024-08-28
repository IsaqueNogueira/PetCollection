package com.isaquesoft.petcollection.presentation.util

import com.airbnb.lottie.LottieAnimationView
import com.isaquesoft.petcollection.R

/**
 * Created by Isaque Nogueira on 27/08/2024
 */
fun LottieAnimationView.setRawFromString(rawName: String) {
    try {
        val res: Class<*> = R.raw::class.java
        val field = res.getField(rawName)
        val rawId = field.getInt(null)
        setAnimation(rawId)
    } catch
    (e: Exception) {
        e.printStackTrace()
    }
}

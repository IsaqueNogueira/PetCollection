package com.isaquesoft.petcollection.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safelyNavigate(direction: NavDirections) =
    run {
        try {
            navigate(direction)
        } catch (e: Exception) {
            null
        }
    }

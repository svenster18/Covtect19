package com.gachateam.covtect_19

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var userId: String?,
    val age: Int,
    val gender: String,
    val height: Int,
    val weight: Int,
) : Parcelable
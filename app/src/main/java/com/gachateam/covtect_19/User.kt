package com.gachateam.covtect_19

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class User (
    var userId: String?,
    val age: Int,
    val gender: String,
    val height: Int,
    val weight: Int,
    var coughRecord: File?
) : Parcelable
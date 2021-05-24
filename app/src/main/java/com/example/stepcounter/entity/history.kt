package com.example.stepcounter.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class history(
        var id: Int=0,
        var target: Int=0,
        var date: String? = null
) : Parcelable

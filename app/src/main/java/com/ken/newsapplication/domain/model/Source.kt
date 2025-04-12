package com.ken.newsapplication.domain.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Source(
    val id: String,
    val name: String,
) : Parcelable
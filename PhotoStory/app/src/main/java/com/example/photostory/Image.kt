package com.example.photostory

import androidx.annotation.DrawableRes

data class Image(
    @DrawableRes
    val imageurl : Int,
    val title : String
)
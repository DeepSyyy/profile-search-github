package com.example.profilegithubsearcher.util

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.profilegithubsearcher.R
import com.google.android.material.imageview.ShapeableImageView

class Utils {
    companion object{
        fun GlideBinding(context: Context, url: String, imageView: ShapeableImageView) {
            Glide.with(context).load(url).apply(RequestOptions.centerCropTransform()).into(imageView)
        }

        const val TOKEN = "ini token kamu"
    }
}
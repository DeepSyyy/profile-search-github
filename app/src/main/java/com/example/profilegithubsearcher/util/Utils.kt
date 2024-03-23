package com.example.profilegithubsearcher.util

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView

class Utils {
    companion object{
        fun glideBinding(context: Context, url: String, imageView: ShapeableImageView) {
            Glide.with(context).load(url).apply(RequestOptions.centerCropTransform()).into(imageView)
        }

        const val TOKEN = "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"
    }
}
package com.codecool.lolapp.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codecool.lolapp.R

class Util {
    companion object {
        fun loadListImage(name: String?, view: View, image: ImageView) {
            val url = BASE_URL_LIST_IMAGE + name + BASE_URL_LIST_TYPE
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.character_placeholder)
                .error(R.mipmap.ic_launcher_round)
            Glide.with(view)
                .load(url)
                .apply(options)
                .into(image)
        }

        fun loadDetailsImage(name: String?, activity: Activity, image: ImageView) {
            val url = BASE_URL_DETAILS_IMAGE + name + BASE_URL_DETAILS_TYPE
            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.character_placeholder)
                .error(R.mipmap.ic_launcher_round)
            Glide.with(activity)
                .load(url)
                .apply(options)
                .into(image)
        }
    }
}
package com.codecool.lolapp.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codecool.lolapp.R

class Util {

    companion object {

        fun loadListImage(name: String?, view: View, image: ImageView) {
            val url = BASE_URL_LIST_IMAGE + name + BASE_URL_LIST_TYPE

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.character_placeholder)
            Glide.with(view)
                .load(url)
                .apply(options)
                .into(image)
        }

        fun loadDetailsImage(name: String?, activity: Activity, image: ImageView) {
            val url = BASE_URL_DETAILS_IMAGE + name + BASE_URL_DETAILS_TYPE

            val circularProgressDrawable = CircularProgressDrawable(activity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            val options: RequestOptions = RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.character_placeholder)
            Glide.with(activity)
                .load(url)
                .apply(options)
                .into(image)
        }
    }
}
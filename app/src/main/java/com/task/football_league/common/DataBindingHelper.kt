package com.task.football_league.common

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.task.football_league.R

object DataBindingHelper {
    @BindingAdapter("loadImage")
    fun loadImage(imageView: ImageView?, url: String?) {

        if (url == null) return
        Picasso.get()
            .load(url)
            .error(R.drawable.logo)
            .fit()
            .into(imageView)
    }
}
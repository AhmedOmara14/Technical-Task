package com.task.football_league.common

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.pixplicity.sharp.Sharp
import com.task.football_league.R
import okhttp3.*
import java.io.IOException

object HelperClass {
    private var httpClient: OkHttpClient? = null

    fun showToast(context: Context?, message: String?) {
        val toast: Toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun toastNoInternetConnection(context: Context) {
        val toast: Toast = Toast.makeText(
            context,
            context.getString(R.string.no_internet_connection),
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
    fun fetchSvg(context: Context, url: String?, target: ImageView) {
        if (httpClient == null) {
            httpClient = OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, 5 * 1024 * 1014))
                .build()
        }
        Log.d("TAG", "fetchSvg: "+url)
        val request: Request? = url?.let { Request.Builder().url(it).build() }
        request?.let {
            httpClient?.newCall(it)?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    target.setImageResource(R.drawable.logo)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    // sharp is a library which will load stream which we generated
                    // from url in our target imageview.
                    val stream = response.body!!.byteStream()
                    Sharp.loadInputStream(stream).into(target)
                    stream.close()
                }
            })
        }
    }

}
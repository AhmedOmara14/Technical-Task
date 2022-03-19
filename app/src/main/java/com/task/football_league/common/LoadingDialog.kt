package com.task.football_league.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.task.football_league.R

class LoadingDialog : DialogFragment() {
    private lateinit var  dialog: ACProgressFlower
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initComponent();
        return inflater.inflate(R.layout.loading_dialog, container, false)
    }

    private fun initComponent() {
        dialog = ACProgressFlower.Builder(context)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(Color.WHITE)
            .fadeColor(Color.DKGRAY).build()
        dialog.show()
    }

    fun cancelDialog() {
        dialog.dismiss()
    }
}
package com.db.newly.view.custom

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.db.newly.R
import kotlinx.android.synthetic.main.custom_toast.*
import kotlinx.android.synthetic.main.custom_toast.view.*

fun Toast.toast(context: Context, message: String, position: Int, duration: Int) {
    val inflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val layout = inflater.inflate(
        R.layout.custom_toast,
        (context as Activity).custom_toast_container
    )
    layout.text.text = message
    setGravity(position, 0, 0)
    setDuration(duration)
    view = layout
    show()
}

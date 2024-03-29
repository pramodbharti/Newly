package com.db.newly.extensions

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.db.newly.view.custom.toast

fun Context.toastShort(message: String) = Toast(this)
    .toast(this, message, Gravity.CENTER, Toast.LENGTH_SHORT)

fun Context.toastLong(message: String) = Toast(this)
    .toast(this, message, Gravity.CENTER, Toast.LENGTH_LONG)




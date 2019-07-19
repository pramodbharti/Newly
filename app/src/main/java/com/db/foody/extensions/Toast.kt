package com.db.foody.extensions

import android.content.Context
import android.widget.Toast

fun Context.toastShort(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toastShort(textRes: Int) =
    Toast.makeText(this, textRes, Toast.LENGTH_SHORT).show()

fun Context.toastLong(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastLong(textRes: Int) =
    Toast.makeText(this, textRes, Toast.LENGTH_LONG).show()
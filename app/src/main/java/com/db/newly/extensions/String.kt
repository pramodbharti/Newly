package com.db.newly.extensions

import android.webkit.URLUtil

fun String?.isValidURL(): Boolean {
    if (this == null) return false
    return this.isNotEmpty() && URLUtil.isValidUrl(this)
}

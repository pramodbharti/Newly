package com.db.foody.extensions

import io.reactivex.*

fun <T> maybe(value: T?): Maybe<T> {
    return if(value == null) Maybe.empty()
    else Maybe.just(value)
}


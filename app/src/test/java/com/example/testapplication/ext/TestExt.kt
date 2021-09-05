package com.example.testapplication.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
): T {

    var data: T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(result: T) {
            data = result
            latch.countDown()
            removeObserver(this)
        }
    }

    observeForever(observer)

    if (!latch.await(time, TimeUnit.SECONDS)) {
        throw TimeoutException("Timed out waiting for Live Data")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
package org.example

import io.reactivex.rxjava3.core.Flowable
import retrofit2.Retrofit

class RxUtil {
    init {

        Flowable.just("Hello world").subscribe(System.out::println)
        val build = Retrofit.Builder().build()
    }
}
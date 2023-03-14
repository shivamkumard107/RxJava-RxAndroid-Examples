package com.codingwithmitch.todolist.lec7

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class IntervalAndTimerOperator {
    init {
//        createFromInterval();
        createFromTimer();
    }
    private fun createFromInterval() {
        Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .takeWhile { t -> t <= 5; }
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Long) {
                    Log.d(TAG, "onNext: $t");
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }

            })
    }
    
    private fun createFromTimer() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long>{
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: ")
                }

                override fun onNext(t: Long) {
                    Log.d(TAG, "onNext: $t")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }

            })
    }
    companion object {
        private const val TAG = "IntervalAndTimerOp"
    }
}
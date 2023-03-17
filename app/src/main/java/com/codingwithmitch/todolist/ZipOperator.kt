package com.codingwithmitch.todolist

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class ZipOperator {
    private val networkService = NetworkService();

    init {
        zipObservable();
    }

    private fun getCricketFansObservable(): Observable<List<User>> {
        return networkService.getCricketFansObservable()
    }

    private fun getFootballFansObservable(): Observable<List<User>> {
        return networkService.getFootballFansObservable()
    }

    private fun getObserver(): Observer<List<User>> {
        return object : Observer<List<User>> {
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }

            override fun onNext(userList: List<User>) {
                println("onNext : $userList")
            }

            override fun onError(e: Throwable) {
                println("onError : ${e.message}")
            }

            override fun onComplete() {
                println("onComplete")
            }
        }
    }

    private fun filterUserWhoLovesBoth(
        cricketFans: List<User>,
        footballFans: List<User>
    ): List<User> {
        val userWhoLovesBoth = ArrayList<User>()
        for (footballFan in footballFans) {
            if (cricketFans.contains(footballFan)) {
                userWhoLovesBoth.add(footballFan)
            }
        }
        return userWhoLovesBoth
    };

    private fun zipObservable() {
        Observable.zip(getCricketFansObservable(), getFootballFansObservable(),
            BiFunction<List<User>, List<User>, List<User>> { cricket, football ->
                return@BiFunction filterUserWhoLovesBoth(cricket, football);
            }
        )
            .observeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(getObserver());
    }

    class NetworkService {

        fun getCricketFansObservable(): Observable<List<User>> {
            return Observable.create<List<User>> { emitter ->
                if (!emitter.isDisposed) {
                    // fetch data from network
                    val data = fetchUserListFromNetwork()
                    emitter.onNext(data)
                    emitter.onComplete()
                }
            }.subscribeOn(Schedulers.io())
        }

        fun getFootballFansObservable(): Observable<List<User>> {
            return Observable.create<List<User>> { emitter ->
                if (!emitter.isDisposed) {
                    // fetch data from network
                    val data = fetchUserListFromNetwork()
                    emitter.onNext(data)
                    emitter.onComplete()
                }
            }.subscribeOn(Schedulers.io())
        }

        private fun fetchUserListFromNetwork(): List<User> {
            return listOf()
        }

    }
}
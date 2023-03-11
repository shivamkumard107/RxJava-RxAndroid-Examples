package com.codingwithmitch.todolist.lec6

import android.util.Log
import com.codingwithmitch.todolist.lec3.DataSource
import com.codingwithmitch.todolist.lec3.models.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class CreateOperator() {
    init {
        createObsFromObj()
    }

    private fun createObsFromObj() {
        val task = Task("Practice Guitar", false, 1);
        val singleTaskObservable = Observable
            .create<Task> { emitter ->
                if (!emitter.isDisposed) {
                    emitter.onNext(task)
                    emitter.onComplete()
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        // Subscribe to the Observable and get the emitted object
        singleTaskObservable.subscribe(object : Observer<Task?> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(task: Task) {
                Log.d(TAG, "onNext: single task: " + task.description)
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })
    }

    private fun createObsFromList() {
        val listObs = Observable
            .create<Task> { emitter -> {
                for (task in DataSource.createTasksList()) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(task!!)
                    }
                }
                emitter.onComplete();
            }
        }

        listObs
            .subscribe(object : Observer<Task> {
                override fun onSubscribe(d: Disposable) {
                    TODO("Not yet implemented")
                }

                override fun onNext(t: Task) {
                    TODO("Not yet implemented")
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onComplete() {
                    TODO("Not yet implemented")
                }

            })

    }

    companion object {
        private const val TAG = "CreateOperator"
    }
}
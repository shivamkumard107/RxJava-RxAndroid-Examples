package com.codingwithmitch.todolist.lec4;

import android.util.Log;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class Flowable {
    private static final String TAG = "Flowable";

    public Flowable() {
//        flowableExample1();
//        flowableToObsConv();
    }

    private void flowableToObsConv() {
        Observable<Integer> observable = Observable
                .just(1, 2, 3, 4, 5);

        io.reactivex.Flowable<Integer> flowable = observable.toFlowable(BackpressureStrategy.BUFFER);

        Observable<Integer> backToObservable = flowable.toObservable();
    }

    private void flowableExample1() {

        io.reactivex.Flowable.range(1, 1_000_000)
                .onBackpressureBuffer()
                .observeOn(Schedulers.computation())
                .subscribe(new DisposableSubscriber<Integer>() {
                    @Override
                    public void onStart() {
                        request(1);
                    }

                    public void onNext(Integer v) {
                        Log.d(TAG, "onNext: ");
                        request(1);
                    }

                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done!");
                    }
                });
    }
}

package com.codingwithmitch.todolist;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codingwithmitch.todolist.models.Task;
import com.codingwithmitch.todolist.util.DataSource;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //ui
    private TextView text;

    // vars
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        rxObservableExample3();

    }

    /*
    Not defining subscribeOn and observerOn will automatically use the main thread.
    If one of the above is defined, the thread defined in that will be used for both subscribeOn and observeOn.
     */
    private void rxObservableExample3() {
        Observable.fromIterable(DataSource.createTasksList()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    private void rxObservableExample2() {
         /*
        Sleeping the background thread will not freeze the ui
         */
        Observable.fromIterable(DataSource.createTasksList()).subscribeOn(Schedulers.io()).filter(new Predicate<Task>() {
            @Override
            public boolean test(Task task) throws Exception {
                // Thread.sleep will sleep the background io thread. We use the filter operator here
                Log.d(TAG, "test: " + Thread.currentThread().getName()); // prints a background thread RxCachedThreadScheduler-1
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return task.isComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Task task) {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }


    /*
    What thread to do work on: subscribeOn

    What thread to observe the response on: observeOn

    Do the work on Schedulers.io() thread and return the result on AndroidSchedulers.mainThread();
   */
    private void rxObservableExample1() {

        Observable<Task> observable = Observable.fromIterable(DataSource.createTasksList()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(d -> {
            Log.d(TAG, "doOnSubscribe: " + Thread.currentThread().getName());
        });

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + task.getDescription());
//                        Thread.sleep will sleep the main thread and ui will freeze
//                        try {
//                            Thread.sleep(1000);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        disposables.clear();
    }

}
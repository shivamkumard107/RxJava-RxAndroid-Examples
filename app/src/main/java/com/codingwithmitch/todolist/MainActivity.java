package com.codingwithmitch.todolist;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codingwithmitch.todolist.R;
import com.codingwithmitch.todolist.lec3.models.Task;
import com.codingwithmitch.todolist.lec6.CreateOperator;
import com.codingwithmitch.todolist.lec7.IntervalAndTimerOperator;

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
        IntervalAndTimerOperator operator = new IntervalAndTimerOperator();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
//        disposables.clear();
    }

}
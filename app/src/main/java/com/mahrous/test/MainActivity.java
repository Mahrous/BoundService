package com.mahrous.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mResultTextView;
    private boolean isBound = false;
    private MyBoundService mMyBoundService;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, MyBoundService.class);
        intent.putExtra("FIRST_NUMBER", 10);
        intent.putExtra("SECOND_NUMBER", 20);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;

            MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) service;
            mMyBoundService = myBinder.getService();
            mResultTextView = findViewById(R.id.tv);
            mResultTextView.setText(mMyBoundService.getResult() + " ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

}
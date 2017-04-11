package com.aclass.shishir.handler_looper_example;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    Button StartSimpleThread, StartSimpleThread1, StartSimpleThread2;
    SeekBar seekBarUpdate;
    final int RESULT_SUCCESS = 1,RESULT_FAIL = -1;
    final int firstUpdate = 10,secondUpdate = 20, thirdUpdate = 30;
    HandlerBackground handlerBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartSimpleThread = (Button)findViewById(R.id.StartSimpleThread);
        StartSimpleThread1 = (Button)findViewById(R.id.StartSimpleThread1);
        StartSimpleThread2 = (Button)findViewById(R.id.StartSimpleThread2);
        seekBarUpdate = (SeekBar) findViewById(R.id.seekBarUpdate);

        StartSimpleThread1.setVisibility(View.INVISIBLE);
        StartSimpleThread2.setVisibility(View.INVISIBLE);

        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();

        HandlerMain handlerMain = new HandlerMain();

        handlerBackground = new HandlerBackground(handlerThread.getLooper(),handlerMain);

        StartSimpleThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message1 = handlerBackground.obtainMessage(firstUpdate,10);
                handlerBackground.sendMessage(message1);

            }
        });

        StartSimpleThread1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message2 = handlerBackground.obtainMessage(secondUpdate,20);
                handlerBackground.sendMessage(message2);
            }
        });

        StartSimpleThread2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message3 = handlerBackground.obtainMessage(thirdUpdate,30);
                handlerBackground.sendMessage(message3);
            }
        });
    }

    class HandlerBackground extends Handler{

        private HandlerMain handlerMain;

        public HandlerBackground(Looper looper, HandlerMain handlerMain) {
            super(looper);
            this.handlerMain = handlerMain;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case firstUpdate:
                    Message message1;
                    message1 = handlerMain.obtainMessage(RESULT_SUCCESS,"15");
                    handlerMain.sendMessage(message1);

                    break;

                case secondUpdate:
                    Message message2;
                    message2 = handlerMain.obtainMessage(RESULT_SUCCESS,"35");
                    handlerMain.sendMessage(message2);

                    break;

                case thirdUpdate:
                    Message message3;
                    message3 = handlerMain.obtainMessage(RESULT_SUCCESS,"50");
                    handlerMain.sendMessage(message3);
                    break;
            }
        }
    }

    class HandlerMain extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case RESULT_SUCCESS:

                    seekBarUpdate.setProgress(Integer.parseInt(msg.obj.toString()));

                    if(msg.obj.toString().equals("15")){

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        StartSimpleThread1.performClick();

                    }
                    if(msg.obj.toString().equals("35")){

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        StartSimpleThread2.performClick();

                    }

                    if(msg.obj.toString().equals("50")){

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        seekBarUpdate.setProgress(Integer.parseInt(msg.obj.toString())+20);

                    }

                    break;
                case RESULT_FAIL:


                    break;
            }

        }
    }

}

package com.aclass.shishir.handler_looper_example;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class Handler_MessageQueue extends AppCompatActivity {

    SeekBar seekBarUpdate;
    Thread thread;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler__message_queue);

        seekBarUpdate = (SeekBar) findViewById(R.id.seekBarUpdate);

        thread = new Thread(new MyThread());
        thread.start();

        handler = new Handler(){
            @Override    // handle message in main thread
            public void handleMessage(Message msg) {// handle the message coming from other thread
                seekBarUpdate.setProgress(msg.arg1);
            }
        };   // created in the Main Thread

    }

    class MyThread implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<100;i++){
                Message message = Message.obtain();
                message.arg1 = i;
                handler.sendMessage(message);   // send message from other thread  // send the message here
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.example.user.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button btn;
    protected SeekBar rabbit_seekBar,turtle_seekBar;
    int rabbit_count =0,turtle_count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.btn);
        rabbit_seekBar= (SeekBar)findViewById(R.id.rabbit_seekBar);
        turtle_seekBar = (SeekBar)findViewById(R.id.turtle_seekBar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"開始",Toast.LENGTH_LONG).show();
                runThread();
                runAsyncTask();
                }

        });
    }
    private void  runThread(){
        rabbit_count = 0;
        new Thread() {
            public void run(){
            do

            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rabbit_count += (int) (Math.random() * 3);
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);

            }while(rabbit_count <=100);
        }
        }.start();
    }
    private Handler mHandler = new Handler(){
        public  void handleMessage(Message msg){
            switch(msg.what) {
                case 1:
                    rabbit_seekBar.setProgress(rabbit_count);
                    break;
            }
            if(rabbit_count>=100)
                if(turtle_count<100)
                    Toast.makeText(MainActivity.this,"兔子完成",Toast.LENGTH_LONG).show();
            }

    };
    private  void runAsyncTask(){
        new AsyncTask<Void, Integer, Boolean>(){
            protected  void onPreExecute(){
                super.onPreExecute();
                turtle_count=0;
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                do

                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    turtle_count += (int) (Math.random() * 3);
                    publishProgress(turtle_count);

                }while(turtle_count <=100);
                return true;
            }
            protected void onProgressUpdate(Integer... values){
                super.onProgressUpdate(values);
                turtle_seekBar.setProgress(values[0]);
            }
            protected void onPostExecute(Boolean status){
                if(rabbit_count<100)
                    Toast.makeText(MainActivity.this,"烏龜獲勝",Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}


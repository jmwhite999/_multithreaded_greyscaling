package com.jmwhite.andriod.sample.multithread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jmwhite.andriod.sample.multithread.asynctask.AsyncTaskActivity;
import com.jmwhite.andriod.sample.multithread.futuretask.FutureTaskActivity;
import com.jmwhite.andriod.sample.multithread.threadpool.ThreadPoolActivity;

public class MultiThreadSampleActivity extends Activity {
	Button threadPoolButt;
	Button futureTaskButt;
	Button asyncTaskButt;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        threadPoolButt = (Button) findViewById(R.id.button2);
        futureTaskButt = (Button) findViewById(R.id.button3);
        asyncTaskButt = (Button) findViewById(R.id.button4);
        
        threadPoolButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiThreadSampleActivity.this, ThreadPoolActivity.class);
				startActivity(intent);
			}
		});
        
        futureTaskButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiThreadSampleActivity.this, FutureTaskActivity.class);
				startActivity(intent);
			}
		});
        
        asyncTaskButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiThreadSampleActivity.this, AsyncTaskActivity.class);
				startActivity(intent);
			}
		});
    }
}
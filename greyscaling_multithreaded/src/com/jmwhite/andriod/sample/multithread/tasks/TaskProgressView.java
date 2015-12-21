package com.jmwhite.andriod.sample.multithread.tasks;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TaskProgressView extends LinearLayout{
	private ProgressBar progressBar;
	private TextView taskName;
	private SimpleTask taskWorker;
	private Button pauseButt;
	private boolean pauseButtState=false;
	
	public SimpleTask getTaskWorker() {
		return taskWorker;
	}

	public void setTaskWorker(SimpleTask taskWorker) {
		this.taskWorker = taskWorker;
	}

	public TaskProgressView(Context context) {
		super(context);

		this.setOrientation(LinearLayout.VERTICAL);
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		LinearLayout l = new LinearLayout(context);
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(l);
		
		progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setMax(100);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		l.addView(progressBar);
		
		pauseButt = new Button(context);
		pauseButt.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0));
		pauseButt.setText("Pause");
		l.addView(pauseButt);
		pauseButt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(pauseButtState==false){
					taskWorker.pauseAndResume();
					pauseButtState=true;
					pauseButt.setText("Resume");
				}else{
					taskWorker.pauseAndResume();
					pauseButtState=false;
					pauseButt.setText("Pause");
				}
				
			}
		});
		
		taskName = new TextView(context);
		taskName.setText("Task Name");
		taskName.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(taskName);
	}
	
	public void setTaskName(String name){
		this.taskName.setText(name);
	}
	
	public void setProgress(int progress){
		this.progressBar.setProgress(progress);
	}

}

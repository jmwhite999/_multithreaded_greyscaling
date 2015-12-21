package com.jmwhite.andriod.sample.multithread.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jmwhite.andriod.sample.multithread.R;
import com.jmwhite.andriod.sample.multithread.tasks.GrayscaleConverter;

/**
 * This sample is about how to use FutureTask.
 * First, you need to create a callable
 * Second, create thread to process the callable object
 * Third, call get() method to get your result back, remember don't use the main/UI thread
 * to call get(), because the method will block your thread. 
 * 
 * @author Marcus
 *
 */
public class AsyncTaskActivity extends Activity {
	/** Called when the activity is first created. */
	Button convertButt;
	private Bitmap imageBitmap;
	private ImageView iv;
	private ProgressBar pb;
	private boolean isGray = false;

	/**
	 * Use future task to perform HTTP get request to download image
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynctasklayout);
		convertButt = (Button) findViewById(R.id.grayscaleImageButt);
		iv = (ImageView) findViewById(R.id.grayImageView);
		pb = (ProgressBar) findViewById(R.id.grayImageProgressBar);
		pb.setVisibility(View.GONE);
		
		imageBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ken_barbie);
		
		convertButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isGray==false){
					pb.setVisibility(View.VISIBLE);
					convertButt.setEnabled(false);
					new GrayscaleConverter(pb, iv, convertButt).execute(imageBitmap);
					isGray=true;
				}else{
					iv.setImageBitmap(imageBitmap);
					isGray=false;
					convertButt.setText(R.string.convert_grayscale_butt);
				}
			}
		});
	}
	
}
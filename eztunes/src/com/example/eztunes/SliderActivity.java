package com.example.eztunes;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;


/*        ----- Got this code from Moodle source ----- */

public class SliderActivity extends Activity {

	Thread t;
	Thread initStartThread;
	int sr = 44100;
	boolean isRunning = true;
	SeekBar fSlider;
	double sliderval;
	final String TAG = "States";

	int buffsize = AudioTrack.getMinBufferSize(sr,
			AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
	// create an audiotrack object
	AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
			sr, AudioFormat.CHANNEL_OUT_MONO,
			AudioFormat.ENCODING_PCM_16BIT, buffsize,
			AudioTrack.MODE_STREAM);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eztuner);

		Toast.makeText(getApplicationContext(), "AudioTrack from Android", Toast.LENGTH_LONG).show();

		fSlider = (SeekBar) findViewById(R.id.freq);
		Button startButton = (Button) findViewById(R.id.startbutton1);
		Button stopButton = (Button) findViewById(R.id.stopbutton1);

		// create a listener for the slider bar;
		OnSeekBarChangeListener listener = new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) { }
			public void onStartTrackingTouch(SeekBar seekBar) { }
			public void onProgressChanged(SeekBar seekBar, 
					int progress,
					boolean fromUser) {
				if(fromUser) sliderval = progress / (double)seekBar.getMax();
			}
		};

		// set the listener on the slider
		fSlider.setOnSeekBarChangeListener(listener);


		startButton.setOnClickListener(    //the button is a view - setOnClickListener(View.OnClickListener l)
				//Register a callback to be invoked when this view is clicked
				new View.OnClickListener() {  //Interface definition for a callback to be invoked when a view is clicked.
					@Override
					public void onClick(View v) { //the onClick() callback method
						// Called when a view has been clicked
						//Thread 
						initStartThread = new Thread(new Runnable() {
							public void run() {
								isRunning = true;
								play_music();
							}
						});
						//t.start();
						//ensure thread is killed when main thread killed
						initStartThread.setDaemon(true);
						initStartThread.start();
					}
				});

		stopButton.setOnClickListener(    //the button is a view - setOnClickListener(View.OnClickListener l)
				//Register a callback to be invoked when this view is clicked
				new View.OnClickListener() {  //Interface definition for a callback to be invoked when a view is clicked.
					@Override
					public void onClick(View v) { //the onClick() callback method
						// Called when a view has been clicked
						isRunning = false;

					}
				});

	}  //end onCreate

	private void play_music() {

		short samples[] = new short[buffsize];
		int amp = 10000;
		double twopi = 8.*Math.atan(1.);
		double fr = 440.f;
		double ph = 0.0;

		// start audio
		audioTrack.play();

		// synthesis loop
		while(isRunning){
			// gets a weighting value from the slider on the GUI
			fr =  440 + 440*sliderval;
			for(int i=0; i < buffsize; i++){
				samples[i] = (short) (amp*Math.sin(ph));
				ph += twopi*fr/sr;
			} //end for
			audioTrack.write(samples, 0, buffsize);
		} //end while
		audioTrack.stop();
		//   audioTrack.release();
	}

	/*	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.slider, menu);
			return true;
		} */


	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "MainActivity: onStart()");
		isRunning = true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "MainActivity: onResume()");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//	audioTrack.pause();
		//			isRunning = false;
		Log.d(TAG, "MainActivity: onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "MainActivity: onStop()");
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d(TAG, "MainActivity: onDesstroy()");
		isRunning = false;
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t = null;
	}
}

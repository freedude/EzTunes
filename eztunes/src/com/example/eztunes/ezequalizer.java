package com.example.eztunes;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ezequalizer extends Activity {

	//Creates the display at runtime
	private static final float VISUALIZER_HEIGHT_DIP = 50f;

	private MediaPlayer mMediaPlayer;
	private Visualizer mVisualizer;
	private Equalizer mEqualizer;

	private LinearLayout mLinearLayout;
	private VisualizerView mVisualizerView;

	private ImageButton playButton,pauseButton;
	private double startTime = 0;
	private double finalTime = 0;
	private int forwardTime = 5000; 
	private int backwardTime = 5000;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ezequalizer);

		// Set up media control buttons
		playButton = (ImageButton)findViewById(R.id.playbtn);
		pauseButton = (ImageButton)findViewById(R.id.pausebtn);

		// Set device's volume control to control the audio stream we'll be playing
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// Which song to play depending on what you picked in tracks screen
		int choice;
		// Get info to decide song
		choice = getIntent().getExtras().getInt("choice");

		if(choice==1)
		{
			mMediaPlayer = MediaPlayer.create(this, R.raw.freeman);
		}
		if(choice==2)
		{
			mMediaPlayer = MediaPlayer.create(this, R.raw.statefunk);
		}
		if(choice==3)
		{
			mMediaPlayer = MediaPlayer.create(this, R.raw.swampstomp);
		}
		if(choice==4)
		{
			mMediaPlayer = MediaPlayer.create(this, R.raw.kannonsklaim);
		}
		if (choice==5)
		{
			mMediaPlayer = MediaPlayer.create(this, R.raw.dominator);
		}

		//Mediaplayer loop
		mMediaPlayer.setLooping(true);

		//Create the equalizer with default priority of 0 & attach to our media player
		mEqualizer = new Equalizer(0, mMediaPlayer.getAudioSessionId());
		mEqualizer.setEnabled(true);

		//Set up visualizer and equalizer bars
		setupVisualizerFxAndUI();
		setupEqualizerFxAndUI();

		//Enable the visualizer
		mVisualizer.setEnabled(true);

		//Listen for when the music stream ends playing
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			public void onCompletion(MediaPlayer mediaPlayer) {
				//                disable the visualizer as it's no longer needed
				mVisualizer.setEnabled(false);
			}
		});
	}


	/* Shows spinner with list of equalizer presets to choose from
    	- updates the seekBar progress and gain levels according
    	to those of the selected preset*/

	private void equalizeSound() {
		//Set up the spinner
		ArrayList<String> equalizerPresetNames = new ArrayList<String>();
		ArrayAdapter<String> equalizerPresetSpinnerAdapter= new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,equalizerPresetNames);
		equalizerPresetSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner equalizerPresetSpinner = (Spinner) findViewById(R.id.eqspinner);

		//Get list of the device's equalizer presets
		for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++) {
			equalizerPresetNames.add(mEqualizer.getPresetName(i));
		}

		equalizerPresetSpinner.setAdapter(equalizerPresetSpinnerAdapter);

		//Handle the spinner item selections
		equalizerPresetSpinner.setOnItemSelectedListener(new AdapterView
				.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				//First list item selected by default and sets the preset accordingly
				mEqualizer.usePreset((short) position);
				//Get the lower gain setting for this equalizer band
				final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];

				//Set seekBar indicators according to selected preset
				for (short i = 0; i <5; i++) {
					short equalizerBandIndex = i;
					SeekBar seekBar = (SeekBar) findViewById(equalizerBandIndex);
					// Get current gain setting for this equalizer band
					// Set the progress indicator of this seekBar to indicate the current gain value
					seekBar.setProgress(mEqualizer
							.getBandLevel(equalizerBandIndex) - lowerEqualizerBandLevel);
				}
			}
			@Override

			public void onNothingSelected(AdapterView<?> parent) {
				//Not used
			}
		});
	}

	/* Displays the SeekBar sliders for the supported equalizer frequency bands
    user can move sliders to change the frequency of the bands*/

	private void setupEqualizerFxAndUI() {

		//Get reference to linear layout for the seekBars
		mLinearLayout = (LinearLayout) findViewById(R.id.sliderlay);

		// Equalizer heading
		TextView equalizerHeading = new TextView(this);
		equalizerHeading.setText("Equalizer");
		equalizerHeading.setTextSize(20);
		equalizerHeading.setGravity(Gravity.CENTER_HORIZONTAL);
		mLinearLayout.addView(equalizerHeading);

		// Get number frequency bands supported by the equalizer engine
		short numberFrequencyBands = mEqualizer.getNumberOfBands();

		// Get the level ranges to be used in setting the band level
		// Get lower limit of the range in milliBels
		final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];
		//Get the upper limit of the range in millibels
		final short upperEqualizerBandLevel = mEqualizer.getBandLevelRange()[1];

		//Loop through all the equalizer bands to display the band headings, lower
		//& upper levels and the seek bars
		for (short i = 0; i < numberFrequencyBands; i++) {
			final short equalizerBandIndex = i;

			//Frequency header for each seekBar
			TextView frequencyHeaderTextview = new TextView(this);
			frequencyHeaderTextview.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			frequencyHeaderTextview.setGravity(Gravity.CENTER_HORIZONTAL);
			frequencyHeaderTextview
			.setText((mEqualizer.getCenterFreq(equalizerBandIndex) / 1000) + " Hz");
			mLinearLayout.addView(frequencyHeaderTextview);

			//Set up linear layout to contain each seekBar
			LinearLayout seekBarRowLayout = new LinearLayout(this);
			seekBarRowLayout.setOrientation(LinearLayout.HORIZONTAL);

			//Set up lower level textview for this seekBar
			TextView lowerEqualizerBandLevelTextview = new TextView(this);
			lowerEqualizerBandLevelTextview.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			lowerEqualizerBandLevelTextview.setText((lowerEqualizerBandLevel / 100) + " dB");

			//Set up upper level textview for this seekBar
			TextView upperEqualizerBandLevelTextview = new TextView(this);
			upperEqualizerBandLevelTextview.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			upperEqualizerBandLevelTextview.setText((upperEqualizerBandLevel / 100) + " dB");

			//            **********  the seekBar  **************
			//Set the layout parameters for the seekbar
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.weight = 1;

			//Create a new seekBar
			SeekBar seekBar = new SeekBar(this);
			//Give the seekBar an ID
			seekBar.setId(i);

			seekBar.setLayoutParams(layoutParams);
			seekBar.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
			//Set the progress for this seekBar
			seekBar.setProgress(mEqualizer.getBandLevel(equalizerBandIndex));


			//Change progress as its changed by moving the sliders
			seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					mEqualizer.setBandLevel(equalizerBandIndex,
							(short) (progress + lowerEqualizerBandLevel));
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
					//Not used
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
					//Not used
				}
			});

			//Add the lower and upper band level textviews and the seekBar to the row layout
			seekBarRowLayout.addView(lowerEqualizerBandLevelTextview);
			seekBarRowLayout.addView(seekBar);
			seekBarRowLayout.addView(upperEqualizerBandLevelTextview);

			mLinearLayout.addView(seekBarRowLayout);

			//Show the spinner
			equalizeSound();
		}
	}
	//Displays the audio waveform
	private void setupVisualizerFxAndUI() {

		mLinearLayout = (LinearLayout) findViewById(R.id.vislay);
		// Create a VisualizerView to display the audio waveform for the current settings
		mVisualizerView = new VisualizerView(this);
		mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				(int) (VISUALIZER_HEIGHT_DIP * getResources().getDisplayMetrics().density)));
		mLinearLayout.addView(mVisualizerView);

		// Create the Visualizer object and attach it to our media player.
		mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
		mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

		mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
			public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
					int samplingRate) {
				mVisualizerView.updateVisualizer(bytes);
			}

			public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
			}
		}, Visualizer.getMaxCaptureRate() / 2, true, false);
	}

	// Pause button functionality
	public void pause(View view){

		mMediaPlayer.pause();
		pauseButton.setEnabled(false);
		playButton.setEnabled(true);

		if (isFinishing() && mMediaPlayer != null) {
			mVisualizer.release();
			mEqualizer.release();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	//Play button functionality
	public void play(View view){
		mMediaPlayer.start();
		finalTime = mMediaPlayer.getDuration();
		startTime = mMediaPlayer.getCurrentPosition();

		pauseButton.setEnabled(true);
		playButton.setEnabled(false);
	}

	// Fast forward button functionality
	public void forward(View view) {
		int temp = (int)startTime;
		if((temp+forwardTime)<=finalTime){
			startTime = startTime + forwardTime;
			mMediaPlayer.seekTo((int) startTime);
		}
		else{
			Toast.makeText(getApplicationContext(), 
					"Cannot jump forward 5 seconds", 
					Toast.LENGTH_SHORT).show();
		}
	}

	// Rewind button functionality
	public void rewind(View view){
		int temp = (int)startTime;
		if((temp-backwardTime)>0){
			startTime = startTime - backwardTime;
			mMediaPlayer.seekTo((int) startTime);
		}
		else{
			Toast.makeText(getApplicationContext(), 
					"Cannot jump backward 5 seconds",
					Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onBackPressed(){
		mMediaPlayer.stop();
//		Intent newintent = new Intent(ezequalizer.this,eztracks.class);
//		startActivity(newintent);
		
		int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
	}
}
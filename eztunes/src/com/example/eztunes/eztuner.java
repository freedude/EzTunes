package com.example.eztunes;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class eztuner extends Activity{

	//Tone Duration in seconds
	private final int duration = 3; 
	private final int sampleRate = 8000;
	private final int numSamples = duration * sampleRate;
	private final double sample[] = new double[numSamples];
	public double freqOfTone,freqOfTone1,freqOfTone2,freqOfTone3,freqOfTone4,freqOfTone5; // hz

	//Arrays for generating tone frequency
	private final byte generatedSnd[] = new byte[2 * numSamples];
	private final byte generatedSnd1[] = new byte[2 * numSamples];
	private final byte generatedSnd2[] = new byte[2 * numSamples];
	private final byte generatedSnd3[] = new byte[2 * numSamples];
	private final byte generatedSnd4[] = new byte[2 * numSamples];
	private final byte generatedSnd5[] = new byte[2 * numSamples];
	private final byte generatedSnd6[] = new byte[2 * numSamples];

	//Handler for some reason
	Handler handler = new Handler();
	
	//Spinner for list of notes
	private String[] noteSpinner;
	//Spinner for preset note sequences
	private String[] presetSpinner;
	
	//Buttons to play tones, and guitar notes
	private ImageButton toneplay1,toneplay2,toneplay3,toneplay4,toneplay5,toneplay6,
	guitarplay1,guitarplay2,guitarplay3,guitarplay4,guitarplay5,guitarplay6;
	
	//Mediaplayers to play guitar notes
		private MediaPlayer mediaPlayerC,mediaPlayerCsharp,mediaPlayerD,mediaPlayerDsharp,mediaPlayerE,mediaPlayerF,
		mediaPlayerFsharp,mediaPlayerG,mediaPlayerGsharp,mediaPlayerA, mediaPlayerAsharp,mediaPlayerB,mediaPlayerhighE;

	//Spinners for each of the 6 guitar strings, starting at 0
	Spinner s,s1,s2,s3,s4,s5;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eztuner); 

		//Setup mediaplayers to play their seperate notes
		 mediaPlayerC = MediaPlayer.create(this, R.raw.c);
		 mediaPlayerCsharp = MediaPlayer.create(this, R.raw.csharp);
		 mediaPlayerD = MediaPlayer.create(this, R.raw.d);
		 mediaPlayerDsharp = MediaPlayer.create(this, R.raw.dsharp);
		 mediaPlayerE = MediaPlayer.create(this, R.raw.e);
		 mediaPlayerF = MediaPlayer.create(this, R.raw.f);
		 mediaPlayerFsharp = MediaPlayer.create(this, R.raw.fsharp);
		 mediaPlayerG = MediaPlayer.create(this, R.raw.g);
		 mediaPlayerGsharp = MediaPlayer.create(this, R.raw.gsharp);
		 mediaPlayerA = MediaPlayer.create(this, R.raw.a);
		 mediaPlayerAsharp = MediaPlayer.create(this, R.raw.asharp);
		 mediaPlayerB = MediaPlayer.create(this, R.raw.b);
		 mediaPlayerhighE = MediaPlayer.create(this, R.raw.highe);
		 
		 //Set tone play image buttons to play the tone chosen
		toneplay1 = (ImageButton)findViewById(R.id.play1);
		toneplay1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			
				playSound();
			}
		});
		
		toneplay2 = (ImageButton)findViewById(R.id.play2);
		toneplay2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				playSound1();
			}
		});
		toneplay3 = (ImageButton)findViewById(R.id.play3);
		toneplay3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				playSound2();
			}
		});
		toneplay4 = (ImageButton)findViewById(R.id.play4);
		toneplay4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				playSound3();
			}
		});
		toneplay5 = (ImageButton)findViewById(R.id.play5);
		toneplay5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				playSound4();
			}
		});
		toneplay6 = (ImageButton)findViewById(R.id.play6);
		toneplay6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				playSound5();
			}
		});
		
	
		
				this.presetSpinner = new String[]{
						"Standard", "Drop D", "Drop C"
				};

				Spinner pres = (Spinner) findViewById(R.id.presets);
				ArrayAdapter <String> presadapter = new ArrayAdapter <String>(this,
						android.R.layout.simple_spinner_dropdown_item, presetSpinner);
				pres.setAdapter(presadapter);
				
				pres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> pres, View view, int pos, long id) {
						
						if(pos==0){
							
							s.setSelection(4);
							s1.setSelection(9);
							s2.setSelection(2);
							s3.setSelection(7);
							s4.setSelection(11);
							s5.setSelection(12);
						}
						if(pos==1){
							
							s.setSelection(2);
							s1.setSelection(9);
							s2.setSelection(2);
							s3.setSelection(7);
							s4.setSelection(11);
							s5.setSelection(12);
						}
						if (pos==2){
							
							s.setSelection(0);
							s1.setSelection(8);
							s2.setSelection(0);
							s3.setSelection(5);
							s4.setSelection(9);
							s5.setSelection(2);						
						}
						
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// Auto-generated method stub
						
					}
				});
		
		// Set array values to notes on the scale
		this.noteSpinner = new String[] {
				"C", "C#", "D", "D#", "E", "F","F#","G","G#","A","A#","B", "High E"
		};

		// Set conditions for each string's spinner, plays particular note depending on users choice
		s = (Spinner) findViewById(R.id.note1);
		ArrayAdapter <String> adapter = new ArrayAdapter <String>(this,
				android.R.layout.simple_spinner_dropdown_item, noteSpinner);
		s.setAdapter(adapter);

		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> s, View view, int pos, long id) {
				//C note
				if(pos == 0)
				{
					//set tone frequency and generate
					freqOfTone = 262;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						//play guitar note when button is pressed
						@Override
						public void onClick(View v) {
							
							playC();
						
						}
					});
				}
				if(pos == 1)
				{
					freqOfTone = 277;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playCsharp();
							
						}
					});
				} if(pos == 2)
				{
					freqOfTone = 294;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playD();
							
						}
					});

				} if(pos == 3)
				{
					freqOfTone = 311;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playDsharp();
							
						}
					});

				} if(pos == 4)
				{
					freqOfTone = 330;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playE();
							
						}
					});
					
					
					

				} if(pos == 5)
				{
					freqOfTone = 349;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playF();
							

						}
					});

				} if(pos == 6)
				{
					freqOfTone = 370;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playFsharp();
							

						}
					});
				} if(pos == 7)
				{
					freqOfTone = 392;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playG();
							
						}
					});
				} if(pos == 8)
				{
					freqOfTone = 415;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playGsharp();
							
						}
					});
				} if(pos == 9)
				{
					freqOfTone = 440;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playA();
							
						}
					});
				} if(pos == 10)
				{
					freqOfTone = 466;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playAsharp();
							
						}
					});
				} if(pos == 11)
				{
					freqOfTone = 494;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playB();
							
						}
					});
						
				}
				
				if(pos == 12)
				{
					freqOfTone = 660;
					genTone();
					guitarplay1 = (ImageButton)findViewById(R.id.gp1);
					guitarplay1.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playhighE();
							
						}
					});
					
					
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});


		s1 = (Spinner) findViewById(R.id.note2);
		ArrayAdapter <String> adapter1 = new ArrayAdapter <String>(this,
				android.R.layout.simple_spinner_dropdown_item, noteSpinner);
		s1.setAdapter(adapter1);

		s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> s1, View view, int pos, long id) {

				if(pos == 0)
				{
					freqOfTone1 = 262;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playC();
							
							// TODO Auto-generated method stub
						}
					});
				}
				if(pos == 1)
				{
					freqOfTone1 = 277;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playCsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 2)
				{
					freqOfTone1 = 294;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playD();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 3)
				{
					freqOfTone1 = 311;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playDsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 4)
				{
					freqOfTone1 = 330;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playE();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 5)
				{
					freqOfTone1 = 349;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playF();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 6)
				{
					freqOfTone1 = 370;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playFsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 7)
				{
					freqOfTone1 = 392;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playG();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 8)
				{
					freqOfTone1 = 415;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playGsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 9)
				{
					freqOfTone1 = 440;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playA();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 10)
				{
					freqOfTone1 = 466;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playAsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 11)
				{
					freqOfTone1 = 494;
					genTone1();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playB();
							
							// TODO Auto-generated method stub
						}
					});
				}
				if(pos == 12)
				{
					freqOfTone = 660;
					genTone();
					guitarplay2 = (ImageButton)findViewById(R.id.gp2);
					guitarplay2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playhighE();
							
							
						}
					});
					
					
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		s2 = (Spinner) findViewById(R.id.note3);
		ArrayAdapter <String> adapter2 = new ArrayAdapter <String>(this,
				android.R.layout.simple_spinner_dropdown_item, noteSpinner);
		s2.setAdapter(adapter2);

		s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> s2, View view, int pos, long id) {


				if(pos == 0)
				{
					freqOfTone2 = 262;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playC();
							
							// TODO Auto-generated method stub
						}
					});

				}
				if(pos == 1)
				{
					freqOfTone2 = 277;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playCsharp();
							
							// TODO Auto-generated method stub
						}
					});


				} if(pos == 2)
				{
					freqOfTone2 = 294;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playD();
							
							// TODO Auto-generated method stub
						}
					});


				} if(pos == 3)
				{
					freqOfTone2 = 311;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playDsharp();
							
							// TODO Auto-generated method stub
						}
					});


				} if(pos == 4)
				{
					freqOfTone2 = 330;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playE();
							
							// TODO Auto-generated method stub
						}
					});


				} if(pos == 5)
				{
					freqOfTone2 = 349;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playF();
							
							// TODO Auto-generated method stub
						}
					});


				} if(pos == 6)
				{
					freqOfTone2 = 370;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playFsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 7)
				{
					freqOfTone2 = 392;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playG();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 8)
				{
					freqOfTone2 = 415;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playGsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 9)
				{
					freqOfTone2 = 440;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playA();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 10)
				{
					freqOfTone2 = 466;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playAsharp();
							
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 11)
				{
					freqOfTone2 = 494;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playB();
							
							// TODO Auto-generated method stub
						}
					});

				}
				
				if(pos == 12)
				{
					freqOfTone = 660;
					genTone2();
					guitarplay3 = (ImageButton)findViewById(R.id.gp3);
					guitarplay3.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playhighE();
							
							// TODO Auto-generated method stub
						}
					});
					
					
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		s3 = (Spinner) findViewById(R.id.note4);
		ArrayAdapter <String >adapter3 = new ArrayAdapter <String>(this,
				android.R.layout.simple_spinner_dropdown_item, noteSpinner);
		s3.setAdapter(adapter3);

		s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> s3, View view, int pos, long id) {


				if(pos == 0)
				{
					freqOfTone3 = 262;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playC();
							
							// TODO Auto-generated method stub
						}
					});


				}
				if(pos == 1)
				{
					freqOfTone3 = 277;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playCsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 2)
				{
					freqOfTone3 = 294;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playD();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 3)
				{
					freqOfTone3 = 311;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playDsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 4)
				{
					freqOfTone3 = 330;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playE();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 5)
				{
					freqOfTone3 = 349;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playF();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 6)
				{
					freqOfTone3 = 370;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playFsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 7)
				{
					freqOfTone3 = 392;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playG();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 8)
				{
					freqOfTone3 = 415;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playGsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 9)
				{
					freqOfTone3 = 440;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playA();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 10)
				{
					freqOfTone3 = 466;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playAsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 11)
				{
					freqOfTone3 = 494;
					genTone3();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playB();
							
							// TODO Auto-generated method stub
						}
					});
				}
				if(pos == 12)
				{
					freqOfTone = 660;
					genTone();
					guitarplay4 = (ImageButton)findViewById(R.id.gp4);
					guitarplay4.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playhighE();
							
							// TODO Auto-generated method stub
						}
					});
					
					
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		s4 = (Spinner) findViewById(R.id.note5);
		ArrayAdapter <String >adapter4 = new ArrayAdapter <String>(this,
				android.R.layout.simple_spinner_dropdown_item, noteSpinner);
		s4.setAdapter(adapter4);

		s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> s4, View view, int pos, long id) {


				if(pos == 0)
				{
					freqOfTone4 = 262;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playC();
							
							// TODO Auto-generated method stub
						}
					});

				}
				if(pos == 1)
				{
					freqOfTone4 = 277;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playCsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 2)
				{
					freqOfTone4 = 294;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playD();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 3)
				{
					freqOfTone4 = 311;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playDsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 4)
				{
					freqOfTone4 = 330;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playE();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 5)
				{
					freqOfTone4 = 349;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playF();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 6)
				{
					freqOfTone4 = 370;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playFsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 7)
				{
					freqOfTone4 = 392;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playG();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 8)
				{
					freqOfTone4 = 415;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playGsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 9)
				{
					freqOfTone4 = 440;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playA();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 10)
				{
					freqOfTone4 = 466;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playAsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 11)
				{
					freqOfTone4 = 494;
					genTone4();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playB();
							
							// TODO Auto-generated method stub
						}
					});
				}
				
				if(pos == 12)
				{
					freqOfTone = 660;
					genTone();
					guitarplay5 = (ImageButton)findViewById(R.id.gp5);
					guitarplay5.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playhighE();
							
							// TODO Auto-generated method stub
						}
					});
					
					
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		s5 = (Spinner) findViewById(R.id.note6);
		ArrayAdapter <String> adapter5 = new ArrayAdapter <String>(this,
				android.R.layout.simple_spinner_dropdown_item, noteSpinner);
		s5.setAdapter(adapter5);

		s5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> s5, View view, int pos, long id) {

				if(pos == 0)
				{
					freqOfTone5 = 262;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playC();
							
							// TODO Auto-generated method stub
						}
					});
				}
				if(pos == 1)
				{
					freqOfTone5 = 277;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playCsharp();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 2)
				{
					freqOfTone5 = 294;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playD();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 3)
				{
					freqOfTone5 = 311;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playDsharp();
							
							
						}
					});

				} if(pos == 4)
				{
					freqOfTone5 = 330;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playE();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 5)
				{
					freqOfTone5 = 349;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playF();
							
							// TODO Auto-generated method stub
						}
					});

				} if(pos == 6)
				{
					freqOfTone5 = 370;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playFsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 7)
				{
					freqOfTone5 = 392;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playG();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 8)
				{
					freqOfTone5 = 415;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playGsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 9)
				{
					freqOfTone5 = 440;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playA();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 10)
				{
					freqOfTone5 = 466;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playAsharp();
							
							// TODO Auto-generated method stub
						}
					});
				} if(pos == 11)
				{
					freqOfTone5 = 494;
					genTone5();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playB();
							
							// TODO Auto-generated method stub
						}
					});
				}
				if(pos == 12)
				{
					freqOfTone = 660;
					genTone6();
					guitarplay6 = (ImageButton)findViewById(R.id.gp6);
					guitarplay6.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							playhighE();
							
							// TODO Auto-generated method stub
						}
					});
					
					
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		
		
	

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Use a new tread as this can take a while
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				genTone();
				handler.post(new Runnable() {

					public void run() {
						playSound();
					}
				});
			}
		});
		thread.start();
	}
	public void genTone(){
		// Fill out the array
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
		}
	// Convert to 16 bit pcm sound array
	// Assumes the sample buffer is normalised.
	int idx = 0;
		for (final double dVal : sample) {
			// Scale to maximum amplitude
			final short val = (short) ((dVal * 32767));
			// In 16 bit wav PCM, first byte is the low order byte
			generatedSnd[idx++] = (byte) (val & 0x00ff);
			generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

		}
	}
	public void genTone1(){
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone1));
		}
	
	int idx = 0;
		for (final double dVal : sample) {
			final short val = (short) ((dVal * 32767));
			generatedSnd1[idx++] = (byte) (val & 0x00ff);
			generatedSnd1[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}
	public void genTone2(){
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone2));
		}
	int idx = 0;
		for (final double dVal : sample) {
			final short val = (short) ((dVal * 32767));
			generatedSnd2[idx++] = (byte) (val & 0x00ff);
			generatedSnd2[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}
	public void genTone3(){
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone3));
		}
	int idx = 0;
		for (final double dVal : sample) {
			final short val = (short) ((dVal * 32767));
			generatedSnd3[idx++] = (byte) (val & 0x00ff);
			generatedSnd3[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}
	public void genTone4(){
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone4));
		}
	int idx = 0;
		for (final double dVal : sample) {
			final short val = (short) ((dVal * 32767));
			generatedSnd4[idx++] = (byte) (val & 0x00ff);
			generatedSnd4[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}
	public void genTone5(){
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone5));
		}
	int idx = 0;
		for (final double dVal : sample) {
			final short val = (short) ((dVal * 32767));
			generatedSnd5[idx++] = (byte) (val & 0x00ff);
			generatedSnd5[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}
	
	public void genTone6(){
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone5));
		}
	int idx = 0;
		for (final double dVal : sample) {
			final short val = (short) ((dVal * 32767));
			generatedSnd6[idx++] = (byte) (val & 0x00ff);
			generatedSnd6[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}
	
	//Start media-players to play notes
	public void playC(){
			      mediaPlayerC.start();	      
	}
	public void playCsharp(){
		      mediaPlayerCsharp.start();	      
	}
	public void playD(){
		      mediaPlayerD.start();	      
	}
	public void playDsharp(){
		      mediaPlayerDsharp.start();	      
	}
	public void playE(){
		      mediaPlayerE.start();	      
	}
	public void playF(){
		      mediaPlayerF.start();	      
	}
	public void playFsharp(){
		      mediaPlayerFsharp.start();	      
	}
	public void playG(){
		      mediaPlayerG.start();	      
	}
	public void playGsharp(){
		      mediaPlayerGsharp.start();	      
	}
	public void playA(){
		      mediaPlayerA.start();	      
	}
	public void playAsharp(){	
		      mediaPlayerAsharp.start();	      
	}
	public void playB(){
		      mediaPlayerB.start();	      
	}
	public void playhighE(){
	      mediaPlayerhighE.start();	      
}
	
	//Generate tones
	public void playSound(){
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
				AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSnd, 0, generatedSnd.length);
		audioTrack.play();
	}
	public void playSound1(){
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, generatedSnd1.length,
				AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSnd1, 0, generatedSnd1.length);
		audioTrack.play();
	}
	public void playSound2(){
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, generatedSnd2.length,
				AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSnd2, 0, generatedSnd2.length);
		audioTrack.play();
	}
	public void playSound3(){
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, generatedSnd3.length,
				AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSnd3, 0, generatedSnd3.length);
		audioTrack.play();
	}
	public void playSound4(){
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, generatedSnd4.length,
				AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSnd4, 0, generatedSnd4.length);
		audioTrack.play();
	}
	public void playSound5(){
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, generatedSnd5.length,
				AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSnd5, 0, generatedSnd5.length);
		audioTrack.play();
	}
	public void playSound6(){
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, generatedSnd6.length,
				AudioTrack.MODE_STATIC);
		audioTrack.write(generatedSnd6, 0, generatedSnd6.length);
		audioTrack.play();
	}
}
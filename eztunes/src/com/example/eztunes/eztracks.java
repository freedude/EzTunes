package com.example.eztunes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Displays screen to let you decide what songs to pick, based on a choice of 5
public class eztracks extends Activity{

	Button trck1btn;
	Button trck2btn;
	Button trck3btn;
	Button trck4btn;
	Button trck5btn;
	int choice;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eztracks);
	}
	
	// If user choses first song, put extra data to equalizer class to load your choice into the mediaplayer
	public void a(View v){
		Intent track1intent = new Intent(eztracks.this,ezequalizer.class);
		choice =1;
		track1intent.putExtra("choice", choice);
		startActivity(track1intent);
	}
	public void b(View v){
		Intent track1intent = new Intent(eztracks.this,ezequalizer.class);
		choice =2;
		track1intent.putExtra("choice", choice);
		startActivity(track1intent);
	}
	public void c(View v){
		Intent track1intent = new Intent(eztracks.this,ezequalizer.class);
		choice =3;
		track1intent.putExtra("choice", choice);
		startActivity(track1intent);
	}
	public void d(View v){
		Intent track1intent = new Intent(eztracks.this,ezequalizer.class);
		choice =4;
		track1intent.putExtra("choice", choice);
		startActivity(track1intent);
	}
	public void e(View v){
		Intent track1intent = new Intent(eztracks.this,ezequalizer.class);
		choice =5;
		track1intent.putExtra("choice", choice);
		startActivity(track1intent);
	}
	
	@Override
	public void onBackPressed(){
//		Intent newintent = new Intent(eztracks.this,ezmenu.class);
//		startActivity(newintent);
		
		int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
	}
}
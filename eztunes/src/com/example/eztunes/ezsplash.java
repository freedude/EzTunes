package com.example.eztunes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

// Creates 2 second Splash Screen when app is first opened
public class ezsplash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ezsplash);
        
        getActionBar().hide();
        
        Thread logoTimer = new Thread() {
        	public void run(){
        		try{
        			int logoTimer = 0;
        			while(logoTimer < 2000){
        				sleep(100);
        				logoTimer = logoTimer +100;
        			};
        			startActivity(new Intent("com.eztunes.EZMENU"));
        		}
        		catch (InterruptedException e) {
        			e.printStackTrace();
        			}
        		finally{
        			finish();
        		}
        	}
        	};
        	logoTimer.start();
        }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ezmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

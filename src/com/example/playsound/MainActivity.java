package com.example.playsound;

import java.io.IOException;

import android.R.drawable;
import android.R.layout;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Identity;
import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SoundPool soundPool;
	private int violinID,guitarraID,flautaID,campanasID,ID,vozID;
	private boolean loaded;
	
	private Spinner spinner;
	private ArrayAdapter<CharSequence> adapter;
	
	private AssetManager assetManager;
	
	private MediaPlayer mpMusic;
	
	private Button baseButton;
	private Button vozButton;
	private boolean reproduciendo;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		spinner = (Spinner) findViewById(R.id.spinner);
		adapter = ArrayAdapter.createFromResource(this, R.array.bases, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		baseButton = (Button) findViewById(R.id.c);
		
		assetManager = getAssets();
	}
	
	@Override
	protected void onStart() 
	{
		super.onStart();
	    this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	    
	    
		mpMusic = new MediaPlayer();
		try {
			Toast toast2 = Toast.makeText(getApplicationContext(), "Cambiar base por defecto\nseleccionando en el desplegable", Toast.LENGTH_LONG);
			toast2.show();
			mpMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mpMusic.setDataSource(getString(getResources().getIdentifier("Base", "string", "com.example.playsound")));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mpMusic.prepareAsync();
		
		mpMusic.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) 
			{
//				Toast toast = Toast.makeText(getApplicationContext(), "Reproduciendo base\n     :)", Toast.LENGTH_LONG);
//				toast.show();
				mpMusic.setLooping(true);
				mpMusic.start();
				baseButton.setBackgroundResource(R.drawable.pause);
				reproduciendo = true;
			}
		});


	    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
	      @Override
	      public void onLoadComplete(SoundPool soundPool, int sampleId,
	          int status) {
	        loaded = true;
	      }
	    });
	    try {
			violinID = soundPool.load(assetManager.openFd("sounds/violin.mp3"), 1);
		    guitarraID = soundPool.load(assetManager.openFd("sounds/guitar.mp3"), 1);
		    flautaID = soundPool.load(assetManager.openFd("sounds/flute.mp3"), 1);
		    campanasID = soundPool.load(assetManager.openFd("sounds/bell.mp3"), 1);
		    vozID = soundPool.load(assetManager.openFd("sounds/voz.mp3"),1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.d("Asset","Mp3 not found");
		}


	    spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) 
			{
					mpMusic.stop();
					mpMusic.reset();
								
					String base = getString(getResources().getIdentifier(parent.getItemAtPosition(pos).toString(), "string", "com.example.playsound"));
					
					try {
//						Toast toast2 = Toast.makeText(getApplicationContext(), "Cargando base ..", Toast.LENGTH_LONG);
//						toast2.show();
						mpMusic.setDataSource(base);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mpMusic.prepareAsync();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) 
			{
				
			}
	    	
		});
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
		
	@Override
	protected void onStop() {
		super.onStop();
		soundPool.release();
		mpMusic.stop();
		mpMusic.release();
	}

//	public void play(View v)
//	{
////		String sButton = (String) v.getTag();
////		Log.d("Button", sButton);
////		Log.d("Resource", ""+estadioID);
////		Log.d("Resource",""+getResources().getIdentifier(sButton, "raw", "com.example.playsound"));
////		ID = soundPool.load(this, getResources().getIdentifier(sButton, "raw", "com.example.playsound"), 1);
//		
//		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//		float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//	    float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//	    float volume = actualVolume / maxVolume;
//	    
//	    
//	    if (loaded) {
//	    	switch (v.getId()) {
//			case R.id.btnBell:
//				soundPool.play(campanasID, volume, volume, 1, 0, 1f);
//				break;
//			case R.id.btnFlauta:
//				soundPool.play(flautaID, volume, volume, 1, 0, 1f);
//				break;
//			case R.id.btnGuitar:
//				soundPool.play(guitarraID, volume, volume, 1, 0, 1f);
//				break;
//			case R.id.btnViolin:
//				soundPool.play(violinID, volume, volume, 1, 0, 1f);
//				break;
//			case R.id.btnVoz:
//				soundPool.play(vozID, volume, volume, 1, 0, 1f);
//				break;
//			default:
//				break;
//			}
//	    }
//	}
	
	public void playViolin(View v)
	{
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    float volume = actualVolume/ maxVolume;
	          
	    if (loaded) {
	    	soundPool.play(violinID, volume, volume, 1, 0, 1f);
//	        Log.e("Test", "Played sound");
	        }
	}
		
	public void playGuitarra(View v)
	{
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    float volume = actualVolume/ maxVolume;
	          
	    if (loaded) {
	    	soundPool.play(guitarraID, volume, volume, 1, 0, 1f);
//	        Log.e("Test", "Played sound");
	        }
	}
	
	public void playFlauta(View v)
	{
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    float volume = actualVolume/ maxVolume;
	          
	    if (loaded) {
	    	soundPool.play(flautaID, volume, volume, 1, 0, 1f);
//	        Log.e("Test", "Played sound");
	        }
	}
	
	public void playCampanas(View v)
	{
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    float volume = actualVolume/ maxVolume;
	          
	    if (loaded) {
	    	soundPool.play(campanasID, volume, volume, 1, 0, 1f);
//	        Log.e("Test", "Played sound");
	        }
	}
	
	public void playVoz (View v)
	{
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    float volume =actualVolume/ maxVolume;
	          
	    if (loaded) {
	    	soundPool.play(vozID, volume, volume, 1, 0, 1f);
//	        Log.e("Test", "Played sound");
	        }
	}
	
	public void cambio (View v)
	{
		if (reproduciendo) 
		{
			mpMusic.pause();
			baseButton.setBackgroundResource(R.drawable.play);
			
		}
		else
		{
			mpMusic.start();
			baseButton.setBackgroundResource(R.drawable.pause);
		}
		reproduciendo=!reproduciendo;
	}
}

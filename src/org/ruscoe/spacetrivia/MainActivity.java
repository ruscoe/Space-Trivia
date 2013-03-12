package org.ruscoe.spacetrivia;

import org.ruscoe.spacetrivia.R;
import org.ruscoe.spacetrivia.dao.UserPrefsData;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

/**
 * The main application activity. Displays the title menu and
 * allows the user to start a trivia round.
 * 
 * @author Dan Ruscoe
 */
public class MainActivity extends Activity
{
	private UserPrefsData userPrefsData;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		userPrefsData = new UserPrefsData(this);
		
		Sound.loadSound(this);
		Sound.setSoundEnabled(userPrefsData.isSoundEnabled());
		
		// Allow the user to control the media volume of their device.
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		Sound.release();
		
		if (userPrefsData != null)
		{
			userPrefsData.close();
		}
	}
	
	/**
	 * Displays the trivia categories list.
	 * 
	 * @param View view - The current view.
	 */
	public void showCategories(View view)
	{
		Sound.playButtonClick();
		
		Intent i = new Intent(view.getContext(), CategoriesActivity.class);
		startActivity(i);
	}
	
	/**
	 * Displays the trivia scores list.
	 * 
	 * @param View view - The current view.
	 */
	public void showScores(View view)
	{
		Sound.playButtonClick();
		
		Intent i = new Intent(view.getContext(), ScoresActivity.class);
		startActivity(i);
	}
	
	/**
	 * Displays the options menu.
	 * 
	 * @param View view - The current view.
	 */
	public void showOptions(View view)
	{
		Sound.playButtonClick();
		
		Intent i = new Intent(view.getContext(), OptionsActivity.class);
		startActivity(i);
	}
}
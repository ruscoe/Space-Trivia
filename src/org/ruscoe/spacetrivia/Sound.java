package org.ruscoe.spacetrivia;

import org.ruscoe.spacetrivia.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound
{
	private static SoundPool mSounds;
	private static int mButtonClick;
	private static int mButtonClickSuccess;
	private static int mButtonClickFailure;
	
	private static boolean mSoundEnabled = true;
	
	public static void loadSound(Context context)
	{
		mSounds = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		mButtonClick = mSounds.load(context, R.raw.button_click, 1);
		mButtonClickSuccess = mSounds.load(context, R.raw.button_click_success, 1);
		mButtonClickFailure = mSounds.load(context, R.raw.button_click_failure, 1);
	}
	
	public static void playButtonClick()
	{
		if (mSoundEnabled)
		{
			mSounds.play(mButtonClick, 1, 1, 1, 0, 1);
		}
	}
	
	public static void playButtonClickSuccess()
	{
		if (mSoundEnabled)
		{
			mSounds.play(mButtonClickSuccess, 1, 1, 1, 0, 1);
		}
	}
	
	public static void playButtonClickFailure()
	{
		if (mSoundEnabled)
		{
			mSounds.play(mButtonClickFailure, 1, 1, 1, 0, 1);
		}
	}
	
	/**
	 * Should be called in onDestroy method of Activity.
	 */
	public static final void release()
	{
		if (mSoundEnabled)
		{
			mSounds.release();
		}
	}

	public static final void setSoundEnabled(boolean enabled)
	{
		mSoundEnabled = enabled;
	}
}

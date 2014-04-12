package org.ruscoe.spacetrivia;

import java.util.List;
import java.util.Map;

import org.ruscoe.spacetrivia.R;

import org.ruscoe.spacetrivia.adapters.ScoreAdapter;
import org.ruscoe.spacetrivia.constants.Constants;
import org.ruscoe.spacetrivia.dao.CategoryData;
import org.ruscoe.spacetrivia.dao.ScoreData;
import org.ruscoe.spacetrivia.models.Category;
import org.ruscoe.spacetrivia.models.Score;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * Displays a list of trivia categories mapped
 * to the user's highest score in each.
 * 
 * @author Ruscoe
 */
public class ScoresActivity extends Activity
{
	private ListView mScoresList;
	private ScoreAdapter mScoreAdapter;
	private CategoryData mCategoryData;
	private ScoreData mScoreData;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scores);
		
		mScoresList = (ListView) findViewById(R.id.scoresList);
		
		mCategoryData = new CategoryData(this);
		List<Category> categories = mCategoryData.getCategories();
		
		mScoreData = new ScoreData(this);
		
		Map<Integer, Score> scores = mScoreData.getScoresByCategoryId();
		
		Category currentCategory = null;
		Score currentScore = null;
		
		for (int i = 0; i < categories.size(); i++)
		{
			currentCategory = categories.get(i);
			
			currentScore = scores.get(currentCategory.getCategoryId());
			
			if (currentScore != null)
			{
				Log.i(Constants.APP_LOG_NAME, "Found score for category ID " + currentCategory.getCategoryId());
				
				currentScore.setCategoryName(currentCategory.getName());
			}
			else
			{
				Log.i(Constants.APP_LOG_NAME, "No score found for category ID " + currentCategory.getCategoryId());
				
				currentScore = new Score();
				currentScore.setCategoryId(currentCategory.getCategoryId());
				currentScore.setCategoryName(currentCategory.getName());
				currentScore.setQuestionsAnswered(0);
				currentScore.setCorrectAnswers(0);
				
				scores.put(currentScore.getCategoryId(), currentScore);
			}
		}
		
		mScoreAdapter = new ScoreAdapter(this, scores);
    	mScoresList.setAdapter(mScoreAdapter);
    	
    	// Allow the user to control the media volume of their device.
    	setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		if (mCategoryData != null)
		{
			mCategoryData.close();
		}
		
		if (mScoreData != null)
		{
			mScoreData.close();
		}
	}
}
package org.ruscoe.spacetrivia;

import java.util.Map;

import org.ruscoe.spacetrivia.constants.Constants;
import org.ruscoe.spacetrivia.dao.CategoryData;
import org.ruscoe.spacetrivia.dao.ScoreData;
import org.ruscoe.spacetrivia.models.Category;
import org.ruscoe.spacetrivia.models.Score;
import org.ruscoe.spacetrivia.R;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Displays a summary of a trivia round. Provides the user with their
 * total correct answers and a percentage score for the round.
 * 
 * @author Dan Ruscoe
 */
public class SummaryActivity extends Activity
{
	private int mCategoryId = 0;
	private int mQuestionsAnswered = 0;
	private int mCorrectAnswers = 0;
	
	private TextView mSummaryText;
	private TextView mScoreText;
	
	private ScoreData mScoreData;
	private CategoryData mCategoryData;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
		
        mCategoryId = getIntent().getIntExtra("categoryId", 0);
        mQuestionsAnswered = getIntent().getIntExtra("questionsAnswered", 0);
        mCorrectAnswers = getIntent().getIntExtra("correctAnswers", 0);
        
        mSummaryText = (TextView)findViewById(R.id.summaryText);
        mScoreText = (TextView)findViewById(R.id.scoreText);
        
        displaySummary();
        
        updateScore();

        // Allow the user to control the media volume of their device.
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	
    	if (mScoreData != null)
    	{
    		mScoreData.close();
    	}
    	
    	if (mCategoryData != null)
    	{
    		mCategoryData.close();
    	}
    }
    
    /**
     * Displays the trivia round summary.
     * Shows total questions, correct questions and score.
     */
    private void displaySummary()
    {
    	String summary = getString(R.string.answered)
    			+ " " + mCorrectAnswers
    			+ " " + getString(R.string.of)
    			+ " " + mQuestionsAnswered
    			+ " " + getString(R.string.questions_correctly);
    	
    	mSummaryText.setText(summary);
    	
    	int score = (int)(((double)mCorrectAnswers / (double)mQuestionsAnswered) * 100);
    	
    	mScoreText.setText(score + "%");
    }
    
    /**
     * Updates the user's score for this trivia category in the database.
     */
    private void updateScore()
    {
    	mScoreData = new ScoreData(this);
    	
    	Score score = mScoreData.getScoreByCategoryId(mCategoryId);
    	
    	if (score == null)
    	{
    		score = new Score();
    		score.setCategoryId(mCategoryId);
    	}
    	
    	score.setQuestionsAnswered(mQuestionsAnswered);
    	score.setCorrectAnswers(mCorrectAnswers);
    	
    	mScoreData.saveScore(score);
    }
    
    /**
     * Click handler for the new round button.
     * Displays the trivia category selection menu.
     * 
     * @param View view - The current view.
     */
    public void newRoundButtonClickHandler(View view)
    {
    	Sound.playButtonClick();
    	
	  	Intent i = new Intent(view.getContext(), CategoriesActivity.class);
	  	startActivity(i);
    }
    
    /**
     * Click handler for the main menu button.
     * Displays the application's main menu.
     * 
     * @param View view - The current view.
     */
    public void mainMenuButtonClickHandler(View view)
    {
    	Sound.playButtonClick();
    	
	  	Intent i = new Intent(view.getContext(), MainActivity.class);
	  	startActivity(i);
    }
    
    /**
     * Click handler for share score button.
     * Allows the user to share their trivia round score
     * via Android's Send Intent.
     * 
     * @param View view - The current view.
     */
    public void shareScoreButtonClickHandler(View view)
    {
    	Sound.playButtonClick();
    	
    	Intent i = new Intent(android.content.Intent.ACTION_SEND);
    	
    	i.setType("text/plain");
    	
    	mCategoryData = new CategoryData(this);
    	
    	Map<Integer, Category> categories = mCategoryData.getCategoriesById();
    	Category currentCategory = categories.get(mCategoryId);
    	
    	String shareText = getString(R.string.share_score_part_1)
    			+ " " + mScoreText.getText()
    			+ " " + getString(R.string.share_score_part_2)
    			+ " \"" + currentCategory.getName() + "\""
    			+ " " + getString(R.string.share_score_part_3)
    			+ " " + Constants.APP_PAGE_URL;
    	
    	Log.i(Constants.APP_LOG_NAME, "Sharing: " + shareText);
    	
    	i.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
    	i.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
    	
    	startActivity(Intent.createChooser(i, getString(R.string.share_via)));
    }
}
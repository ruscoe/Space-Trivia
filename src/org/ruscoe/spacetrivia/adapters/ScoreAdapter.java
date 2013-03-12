package org.ruscoe.spacetrivia.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ruscoe.spacetrivia.constants.Constants;
import org.ruscoe.spacetrivia.models.Score;
import org.ruscoe.spacetrivia.views.ScoreView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter used to map a list of Alert models to an AlertView. Used to
 * create the list of alerts displayed to the user.
 * 
 * @author Dan Ruscoe
 */
public class ScoreAdapter extends BaseAdapter
{
	Context mContext;
	List<Score> mScores;
	
	/**
	 * Constructor for ScoreAdapter.
	 * 
	 * @param Context context - The current application context.
	 * @param Map<Integer, Score> scores - A map of Score instances
	 * to be added to this adapter. Scores are mapped by trivia category ID.
	 */
	public ScoreAdapter(Context context, Map<Integer, Score> scores)
	{
		mContext = context;
		
		mScores = new ArrayList<Score>();
		
		for (int key : scores.keySet())
		{
			Log.d(Constants.APP_LOG_NAME, "Adding score to adapter for category ID: " + scores.get(key).getCategoryId());
			
			mScores.add(scores.get(key));
		}
	}
	
	/**
	 * Gets the total number of Score instances stored in this adapter.
	 * 
	 * @return int - Total Score instances for this adapter.
	 */
	@Override
	public int getCount()
	{
		if (mScores != null)
		{
			return mScores.size();
		}
		else
		{
			return 0;
		}
	}

	/**
	 * Gets an instance of Score at a given position.
	 * 
	 * @param int position - The position of the Score instance
	 * in the mScores List.
	 * @return Score - The Score instance at the given position.
	 */
	@Override
	public Object getItem(int position)
	{
		if (mScores != null)
		{
			return mScores.get(position);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Returns a unique ID for a Score at a given position.
	 * For Score instances, this returns the category ID.
	 * 
	 * @return long - The category ID of the Score instance at
	 * the given position.
	 */
	@Override
	public long getItemId(int position)
	{
		Score score = mScores.get(position);
		
		if (score != null)
		{
			return score.getCategoryId();
		}
		
		return 0;
	}

	/**
	 * Gets the View instance used to display a score.
	 * 
	 * @param int position - The position of the Score instance
	 * in the mScores List.
	 * @return ScoreView - A populated ScoreView instance.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ScoreView scoreView;
		
		Score score = mScores.get(position);
		
		if (convertView == null)
		{
			scoreView = new ScoreView(mContext, score);
		}
		else
		{
			scoreView = (ScoreView) convertView;
			
			if (score != null)
			{
				scoreView.setCategoryName(score.getCategoryName());
				scoreView.setQuestionsAnswered(score.getCorrectAnswers(), score.getQuestionsAnswered());
				
				int percent = (int)(((double)score.getCorrectAnswers() / (double)score.getQuestionsAnswered()) * 100);
				
				scoreView.setPercentCorrect(percent);
			}
		}
		
		return scoreView;
	}
	
}

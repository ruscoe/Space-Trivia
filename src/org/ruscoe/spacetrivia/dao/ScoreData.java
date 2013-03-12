package org.ruscoe.spacetrivia.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ruscoe.spacetrivia.models.Score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Extends TriviaDAO, providing methods to access data specific to
 * trivia scores in the application.
 * 
 * @author Dan Ruscoe
 */
public class ScoreData extends TriviaDAO
{
	public static final String TABLE_NAME = "scores";
	
	public static final String CATEGORY_ID = "categoryId";
	public static final String QUESTIONS_ANSWERED = "questionsAnswered";
	public static final String CORRECT_ANSWERS = "correctAnswers";
	
	public static final int FIELD_ID_CATEGORY_ID = 0;
	public static final int FIELD_ID_QUESTIONS_ANSWERED = 1;
	public static final int FIELD_ID_CORRECT_ANSWERS = 2;
	
	private String[] selectFields = { CATEGORY_ID, QUESTIONS_ANSWERED, CORRECT_ANSWERS };
	
	/**
	 * Constructor for ScoreData.
	 * 
	 * @param Context context - The current application context.
	 */
	public ScoreData(Context context)
	{
		super(context);
	}

	/**
	 * Returns a list of Scores instances for all trivia category scores.
	 * 
	 * @return List<Score> - Score instances of all trivia category scores.
	 */
	public List<Score> getScores()
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_NAME, selectFields, null, null, null, null, null);

    	List<Score> scores = new ArrayList<Score>();

    	if (cursor != null)
    	{
    		Score score = null;
    		
    		while (cursor.moveToNext())
        	{
    			score = getScoreFromCursor(cursor);
    			
    			scores.add(score);
        	}
    		cursor.close();
    	}
    	
    	db.close();
    	
    	return scores;
	}
	
	/**
	 * Returns a Map of Scores instances for all trivia category scores,
	 * indexed by category ID.
	 * 
	 * @return HashMap<Integer, Scores> - A map of Score instances,
	 * indexed by category ID.
	 */
	public Map<Integer, Score> getScoresByCategoryId()
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_NAME, selectFields, null, null, null, null, null);

    	Map<Integer, Score> scores = new HashMap<Integer, Score>();

    	if (cursor != null)
    	{
    		Score score = null;
    		
    		while (cursor.moveToNext())
        	{
    			score = getScoreFromCursor(cursor);
    			
    			scores.put(score.getCategoryId(), score);
        	}
    		cursor.close();
    	}
    	
    	db.close();
    	
    	return scores;
	}
	
	/**
	 * Returns a Score instance for a given category ID.
	 * 
	 * @param int categoryId - The category ID to return a Score instance for.
	 * @return Score - The Score instance for the given category ID.
	 * May be null if no score has been logged.
	 */
	public Score getScoreByCategoryId(int categoryId)
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_NAME, selectFields, CATEGORY_ID + "=" + categoryId, null, null, null, null);

    	Score score = null;

    	if (cursor != null)
    	{
    		while (cursor.moveToNext())
        	{
    			score = getScoreFromCursor(cursor);
        	}
    		cursor.close();
    	}
    	
    	db.close();
    	
    	return score;
	}
	
	/**
	 * Saves a score for a trivia category.
	 * 
	 * @param Score score - The Score instance to save.
	 */
	public void saveScore(Score score)
	{
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	
    	values.put(CATEGORY_ID, score.getCategoryId());
    	values.put(QUESTIONS_ANSWERED, score.getQuestionsAnswered());
    	values.put(CORRECT_ANSWERS, score.getCorrectAnswers());
    	
    	int affectedRows = db.update(TABLE_NAME, values, CATEGORY_ID + "='" + score.getCategoryId() + "'", null);
    	
    	if (affectedRows < 1)
    	{
    		db.insertOrThrow(TABLE_NAME, null, values);
    	}
    	
    	db.close();
	}
	
	/**
	 * Returns a populated Score instance using data from a Cursor instance.
	 * 
	 * @param Cursor cursor - A Cursor instance resulting from a query
	 * on the scores database table.
	 * @return Score - A populated Score instance.
	 */
	private Score getScoreFromCursor(Cursor cursor)
	{
		Score score = new Score();
		
		score.setCategoryId(cursor.getInt(FIELD_ID_CATEGORY_ID));
		score.setQuestionsAnswered(cursor.getInt(FIELD_ID_QUESTIONS_ANSWERED));
		score.setCorrectAnswers(cursor.getInt(FIELD_ID_CORRECT_ANSWERS));
		
		return score;
	}
}

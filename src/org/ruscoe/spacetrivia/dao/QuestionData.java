package org.ruscoe.spacetrivia.dao;

import java.util.HashMap;

import org.ruscoe.spacetrivia.models.Question;

import static android.provider.BaseColumns._ID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Extends TriviaDAO, providing methods to access data specific to
 * trivia questions in the application.
 * 
 * @author Dan Ruscoe
 */
public class QuestionData extends TriviaDAO
{
	public static final String TABLE_NAME = "questions";
	
	public static final String CATEGORY_ID = "categoryId";
	public static final String DIFFICULTY = "difficulty";
	public static final String TEXT = "text";
	public static final String DESCRIPTION = "description";
	
	public static final int FIELD_ID_ID = 0;
	public static final int FIELD_ID_CATEGORY_ID = 1;
	public static final int FIELD_ID_DIFFICULTY = 2;
	public static final int FIELD_ID_TEXT = 3;
	public static final int FIELD_ID_DESCRIPTION = 4;
	
	private String[] selectFields = { _ID, CATEGORY_ID, DIFFICULTY, TEXT, DESCRIPTION };
	
	/**
	 * Constructor for QuestionData.
	 * 
	 * @param Context context - The current application context.
	 */
	public QuestionData(Context context)
	{
		super(context);
	}

	/**
	 * Returns a Map of Question instances for all questions relating
	 * to a given category ID.
	 * 
	 * @param int categoryId - The ID of the category to get questions for.
	 * @return HashMap<Integer, Question> - A map of Question instances,
	 * indexed by question ID.
	 */
	public HashMap<Integer, Question> getQuestionsByCategoryId(long categoryId)
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_NAME, selectFields, CATEGORY_ID + "=" + categoryId, null, null, null, null);

    	HashMap<Integer, Question> questions = new HashMap<Integer, Question>();

    	if (cursor != null)
    	{
    		while (cursor.moveToNext())
        	{
    			Question question = getQuestionFromCursor(cursor);
    			
    			questions.put(question.getQuestionId(), question);
        	}
    		cursor.close();
    	}
    	
    	db.close();
    	
    	return questions;
	}
	
	/**
	 * Returns a populated Question instance using data from a Cursor instance.
	 * 
	 * @param Cursor cursor - A Cursor instance resulting from a query
	 * on the questions database table.
	 * @return Question - A populated Question instance.
	 */
	private Question getQuestionFromCursor(Cursor cursor)
	{
		Question question = new Question();
		
		question.setQuestionId(cursor.getInt(FIELD_ID_ID));
		question.setCategoryId(cursor.getInt(FIELD_ID_CATEGORY_ID));
		question.setDifficulty(cursor.getInt(FIELD_ID_DIFFICULTY));
		question.setText(cursor.getString(FIELD_ID_TEXT));
		question.setDescription(cursor.getString(FIELD_ID_DESCRIPTION));
		
		return question;
	}
}

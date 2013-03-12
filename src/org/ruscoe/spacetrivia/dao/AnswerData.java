package org.ruscoe.spacetrivia.dao;

import static android.provider.BaseColumns._ID;

import java.util.HashMap;

import org.ruscoe.spacetrivia.models.Answer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Extends TriviaDAO, providing methods to access data specific to
 * trivia answers in the application.
 * 
 * @author Dan Ruscoe
 */
public class AnswerData extends TriviaDAO
{
	public static final String TABLE_NAME = "answers";
	
	public static final String QUESTION_ID = "questionId";
	public static final String TEXT = "text";
	public static final String CORRECT = "correct";
	
	public static final int FIELD_ID_ID = 0;
	public static final int FIELD_ID_QUESTION_ID = 1;
	public static final int FIELD_ID_TEXT = 2;
	public static final int FIELD_ID_CORRECT = 3;
	
	private String[] selectFields = { _ID, QUESTION_ID, TEXT, CORRECT };
	
	/**
	 * Constructor for AnswerData.
	 * 
	 * @param Context context - The current application context.
	 */
	public AnswerData(Context context)
	{
		super(context);
	}

	/**
	 * Returns a Map of Answer instances for all answers relating to a given question ID.
	 * 
	 * @param int questionId - The ID of the question to get answers for.
	 * @return HashMap<Integer, Answer> - A map of Answer instances, indexed by answer ID.
	 */
	public HashMap<Integer, Answer> getAnswersByQuestionId(int questionId)
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_NAME, selectFields, QUESTION_ID + "=" + questionId, null, null, null, null);

    	HashMap<Integer, Answer> answers = new HashMap<Integer, Answer>();

    	if (cursor != null)
    	{
    		while (cursor.moveToNext())
        	{
    			Answer answer = getAnswerFromCursor(cursor);
    			
    			answers.put(answer.getAnswerId(), answer);
        	}
    		cursor.close();
    	}
    	
    	db.close();
    	
    	return answers;
	}
	
	/**
	 * Returns a populated Answer instance using data from a Cursor instance.
	 * 
	 * @param Cursor cursor - A Cursor instance resulting from a query
	 * on the answers database table.
	 * @return Answer - A populated Answer instance.
	 */
	private Answer getAnswerFromCursor(Cursor cursor)
	{
		Answer answer = new Answer();
		
		answer.setAnswerId(cursor.getInt(FIELD_ID_ID));
		answer.setQuestionId(cursor.getInt(FIELD_ID_QUESTION_ID));
		answer.setText(cursor.getString(FIELD_ID_TEXT));
		
		boolean correct = (cursor.getInt(FIELD_ID_CORRECT) == 1);
		
		answer.setCorrect(correct);
		
		return answer;
	}
}

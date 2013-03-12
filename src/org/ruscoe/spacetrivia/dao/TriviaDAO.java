package org.ruscoe.spacetrivia.dao;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.ruscoe.spacetrivia.R;
import org.ruscoe.spacetrivia.constants.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import au.com.bytecode.opencsv.CSVReader;

import static android.provider.BaseColumns._ID;

/**
 * Data Access Object for the trivia database.
 * Handles initial table creation and population.
 * 
 * @author Dan Ruscoe
 */
public class TriviaDAO extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "spacetrivia.db";
	private static final int DATABASE_VERSION = 1;
	
	private static Context mContext;
	
	// Create table statements.
	
	private static final String CREATE_TABLE_USER_PREFS = "CREATE TABLE " + UserPrefsData.TABLE_NAME + " ("
	+ _ID + " INTEGER PRIMARY KEY, "
	+ UserPrefsData.SOUND + " INTEGER DEFAULT 1"
	+ ");";
	
	private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE "
	+ CategoryData.TABLE_NAME + " ("
	+ _ID + " INTEGER PRIMARY KEY, "
	+ CategoryData.NAME + " TEXT"
	+ ");";
	
	private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE "
	+ QuestionData.TABLE_NAME + " ("
	+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	+ QuestionData.CATEGORY_ID + " INTEGER, "
	+ QuestionData.DIFFICULTY + " INTEGER, "
	+ QuestionData.TEXT + " TEXT,"
	+ QuestionData.DESCRIPTION + " TEXT"
	+ ");";
	
	private static final String CREATE_TABLE_ANSWERS = "CREATE TABLE "
	+ AnswerData.TABLE_NAME + " ("
	+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	+ AnswerData.QUESTION_ID + " INTEGER, "
	+ AnswerData.TEXT + " TEXT,"
	+ AnswerData.CORRECT + " INTEGER"
	+ ");";
	
	private static final String CREATE_TABLE_SCORES = "CREATE TABLE "
	+ ScoreData.TABLE_NAME + " ("
	+ ScoreData.CATEGORY_ID + " INTEGER PRIMARY KEY, "
	+ ScoreData.QUESTIONS_ANSWERED + " INTEGER,"
	+ ScoreData.CORRECT_ANSWERS + " INTEGER"
	+ ");";
	
	/**
	 * Constructor for TriviaDAO.
	 * 
	 * @param Context context - The current application context.
	 */
	public TriviaDAO(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		mContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// Create tables.
		
		Log.d(Constants.APP_LOG_NAME, "Creating database tables.");
		
		db.execSQL(CREATE_TABLE_USER_PREFS);
		db.execSQL(CREATE_TABLE_CATEGORIES);
		db.execSQL(CREATE_TABLE_QUESTIONS);
		db.execSQL(CREATE_TABLE_ANSWERS);
		db.execSQL(CREATE_TABLE_SCORES);
		
		// Populate tables.
		
		Log.d(Constants.APP_LOG_NAME, "Populating database tables");
		
		importCategoriesData(db);
		
		importQuestionsData(db, R.raw.questions_space_exploration);
		importQuestionsData(db, R.raw.questions_earth_moon);
		importQuestionsData(db, R.raw.questions_solar_system);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		onCreate(db);
	}
	
	/**
	 * Imports trivia category data from the categories CSV file.
	 * 
	 * @param SQLiteDatabase db - The database connection.
	 */
	private void importCategoriesData(SQLiteDatabase db)
	{
		List<String[]> records = readDataFromFileResource(R.raw.categories_data);
		
		String[] recordData;
		
		for (int i = 0; i < records.size(); i++)
		{
			recordData = records.get(i);
			
			ContentValues values = new ContentValues();
			
			values.put(_ID, recordData[0]);
			values.put(CategoryData.NAME, recordData[1]);
			
			db.insert(CategoryData.TABLE_NAME, null, values);
		}
	}
	
	/**
	 * Imports trivia question data from a given CSV file resource ID.
	 * 
	 * @param SQLiteDatabase db - The database connection.
	 * @param int resourceId - The resource ID of the question data CSV file.
	 */
	private void importQuestionsData(SQLiteDatabase db, int resourceId)
	{
		List<String[]> records = readDataFromFileResource(resourceId);
		
		String[] recordData;
		
		long questionId = 0;
		
		ContentValues values = null;
		
		for (int i = 0; i < records.size(); i++)
		{
			recordData = records.get(i);
			
			if (recordData.length < 7)
			{
				Log.e(Constants.APP_LOG_NAME, "Question record too short at index " + i + " while importing resource ID " + resourceId);
			}
			
			// Add the question data to the data base.
			
			values = new ContentValues();
			values.put(QuestionData.CATEGORY_ID, recordData[0]);
			values.put(QuestionData.DIFFICULTY, recordData[1]);
			values.put(QuestionData.TEXT, recordData[2]);
			values.put(QuestionData.DESCRIPTION, recordData[3]);
			
			questionId = db.insert(QuestionData.TABLE_NAME, _ID, values);
			
			// There are three possible answers per question. They are imported here.
			
			importAnswerData(db, questionId, recordData[4], true);
			importAnswerData(db, questionId, recordData[5], false);
			importAnswerData(db, questionId, recordData[6], false);
		}
	}
	
	/**
	 * Stores a question answer record in the database.
	 * Each question has multiple answers, with only one being correct.
	 * 
	 * @param SQLiteDatabase db - The database connection.
	 * @param long questionId - The ID of the question this answer maps to.
	 * @param String answerText - The text of the answer.
	 * @param boolean correct - True is the answer is the correct choice.
	 */
	private void importAnswerData(SQLiteDatabase db, long questionId, String answerText, boolean correct)
	{
		ContentValues values = new ContentValues();
		values.put(AnswerData.QUESTION_ID, questionId);
		values.put(AnswerData.TEXT, answerText);
		values.put(AnswerData.CORRECT, correct);
		
		db.insert(AnswerData.TABLE_NAME, _ID, values);
	}
	
	/**
	 * Reads rows from a CSV data file and returns each record
	 * as a string array.
	 * 
	 * @param int resourceId - The resource ID of the CSV data file.
	 * @return List<String[]> - The records from the data file.
	 */
	private List<String[]> readDataFromFileResource(int resourceId)
	{
		List<String[]> records = null;
		
		try
		{
			CSVReader reader = new CSVReader(new InputStreamReader(mContext.getResources().openRawResource(resourceId)));
			records = reader.readAll();
		}
		catch (IOException e)
		{
			Log.e(Constants.APP_LOG_NAME, "Failed to read data from file: " + e.getMessage());
			e.printStackTrace();
		}
		
		return records;
	}
}
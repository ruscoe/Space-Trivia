package org.ruscoe.spacetrivia.dao;

import static android.provider.BaseColumns._ID;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserPrefsData extends TriviaDAO
{
	public static final String TABLE_NAME = "userPrefsData";
	
	public static final String SOUND = "sound";
	
	// Only ever one row in prefs table.
	private static final int ROW_ID = 1;
	
	public UserPrefsData(Context context)
	{
		super(context);
	}
	
	public boolean isSoundEnabled()
	{
		return isEnabled(SOUND);
	}
	
	public void setSoundEnabled(boolean enabled)
	{
		setEnabled(SOUND, enabled);
	}
	
	public boolean isEnabled(String preference)
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	String[] from = { _ID, preference };
    	Cursor cursor = db.query(TABLE_NAME, from, _ID + "=" + ROW_ID, null, null, null, null);

    	boolean enabled = true;
    	
    	if (cursor != null)
    	{
    		while (cursor.moveToNext())
        	{    			
    			int prefValue = cursor.getInt(1);
        		enabled = (prefValue == 1);
        	}
    		cursor.close();
    	}
    	
    	db.close();
    	return enabled;
	}
	
	public void setEnabled(String preference, boolean enabled)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	
    	int value = (enabled)? 1 : 0;
    	values.put(preference, value);
    	
    	int affectedRows = db.update(TABLE_NAME, values, _ID + "=" + ROW_ID, null);
    	
    	if (affectedRows < 1)
    	{
    		values.put(_ID, ROW_ID);
    		db.insertOrThrow(TABLE_NAME, null, values);
    	}
    	
    	db.close();
    }
}

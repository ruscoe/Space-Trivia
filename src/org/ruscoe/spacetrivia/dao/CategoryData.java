package org.ruscoe.spacetrivia.dao;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ruscoe.spacetrivia.models.Category;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Extends TriviaDAO, providing methods to access data specific to
 * trivia categories in the application.
 * 
 * @author Dan Ruscoe
 */
public class CategoryData extends TriviaDAO
{
	public static final String TABLE_NAME = "categories";
	
	public static final String NAME = "name";
	
	public static final int FIELD_ID_ID = 0;
	public static final int FIELD_ID_NAME = 1;
	
	private String[] selectFields = { _ID, NAME };
	
	/**
	 * Constructor for CategoryData.
	 * 
	 * @param Context context - The current application context.
	 */
	public CategoryData(Context context)
	{
		super(context);
	}

	/**
	 * Returns a Cursor containing trivia category data from a query on
	 * the categories database table.
	 * 
	 * @return Cursor - A Cursor instance from a query on the categories table.
	 */
	public Cursor getCategoriesData()
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_NAME, selectFields, null, null, null, null, null);
    	
    	return cursor;
	}
	
	/**
	 * Returns a list of Category instances for all trivia categories.
	 * 
	 * @return List<Category> - Category instances for all categories.
	 */
	public List<Category> getCategories()
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_NAME, selectFields, null, null, null, null, null);

    	List<Category> categories = new ArrayList<Category>();

    	if (cursor != null)
    	{
    		Category category = null;
    		
    		while (cursor.moveToNext())
        	{
    			category = getCategoryFromCursor(cursor);
    			
    			categories.add(category);
        	}
    		cursor.close();
    	}
    	
    	db.close();
    	
    	return categories;
	}
	
	/**
	 * Returns a Map of Category instances for all trivia categories,
	 * indexed by category ID.
	 * 
	 * @return Map<Integer, Category> - Category instances for all categories.
	 */
	public Map<Integer, Category> getCategoriesById()
	{
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_NAME, selectFields, null, null, null, null, null);

    	Map<Integer, Category> categories = new HashMap<Integer, Category>();

    	if (cursor != null)
    	{
    		Category category = null;
    		
    		while (cursor.moveToNext())
        	{
    			category = getCategoryFromCursor(cursor);
    			
    			categories.put(category.getCategoryId(), category);
        	}
    		cursor.close();
    	}
    	
    	db.close();
    	
    	return categories;
	}
	
	/**
	 * Returns a populated Category instance using data from a Cursor instance.
	 * 
	 * @param Cursor cursor - A Cursor instance resulting from a query
	 * on the categories database table.
	 * @return Category - A populated Category instance.
	 */
	private Category getCategoryFromCursor(Cursor cursor)
	{
		Category category = new Category();
		
		category.setCategoryId(cursor.getInt(FIELD_ID_ID));
		category.setName(cursor.getString(FIELD_ID_NAME));
		
		return category;
	}
}

package org.ruscoe.spacetrivia;

import org.ruscoe.spacetrivia.constants.Constants;
import org.ruscoe.spacetrivia.dao.CategoryData;
import org.ruscoe.spacetrivia.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Displays a list of trivia categories.
 * The user selects a category to begin a round of questions.
 * 
 * @author Dan Ruscoe
 */
public class CategoriesActivity extends ListActivity
{
	private ListView mListView;
	private CategoryData mCategoryData;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories);
		
		mCategoryData = new CategoryData(this);
		
		Cursor categoriesCursor = mCategoryData.getCategoriesData();

		String[] displayFields = new String[]
		{ CategoryData.NAME };
		int[] displayViews = new int[]
		{ R.id.list_name };

		setListAdapter(new SimpleCursorAdapter(this, R.layout.list_category,
				categoriesCursor, displayFields, displayViews));

		mListView = getListView();
		mListView.setTextFilterEnabled(true);

		// Set up click handler for category names.
		
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Sound.playButtonClick();
				startCategory((int) id);
			}
		});
		
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
	}

	/**
	 * Starts a round of questions in a given trivia category.
	 * 
	 * @param int id - The ID of the selected trivia category.
	 */
	private void startCategory(int id)
	{
		Log.i(Constants.APP_LOG_NAME, "Starting category ID " + id);

		Intent i = new Intent(this, QuestionActivity.class);
		i.putExtra("categoryId", id);
		startActivity(i);
	}
}
package org.ruscoe.spacetrivia.models;

/**
 * Represents a trivia category score.
 * 
 * @author Dan Ruscoe
 */
public class Score
{
	private int categoryId;
	private String categoryName;
	private int questionsAnswered;
	private int correctAnswers;
	
    @Override
    public String toString()
    {
        return "categoryId: " + categoryId + " categoryName: " + categoryName
        		+ " questionsAnswered: " + questionsAnswered + " correctAnswers: " + correctAnswers;
    }

	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}

	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	public int getQuestionsAnswered()
	{
		return questionsAnswered;
	}

	public void setQuestionsAnswered(int questionsAnswered)
	{
		this.questionsAnswered = questionsAnswered;
	}

	public int getCorrectAnswers()
	{
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers)
	{
		this.correctAnswers = correctAnswers;
	}
}

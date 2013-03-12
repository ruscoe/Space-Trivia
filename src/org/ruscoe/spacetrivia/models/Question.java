package org.ruscoe.spacetrivia.models;

/**
 * Represents a trivia question.
 * 
 * @author Dan Ruscoe
 */
public class Question
{
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_NORMAL = 1;
	
	private int questionId;
	private int categoryId;
	private int difficulty;
	private String text;
	private String description;
	
    @Override
    public String toString()
    {
        return "questionId: " + questionId + " text: " + text;
    }
    
	public int getQuestionId()
	{
		return questionId;
	}
	
	public void setQuestionId(int questionId)
	{
		this.questionId = questionId;
	}
	
	public int getCategoryId()
	{
		return categoryId;
	}
	
	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}

	public void setDifficulty(int difficulty)
	{
		this.difficulty = difficulty;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}

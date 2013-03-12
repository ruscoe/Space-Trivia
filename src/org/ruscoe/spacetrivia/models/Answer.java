package org.ruscoe.spacetrivia.models;

/**
 * Represents an answer to a trivia question.
 * 
 * @author Dan Ruscoe
 */
public class Answer
{
	private int answerId;
	private int questionId;
	private String text;
	private String description;
	private boolean correct;
	
    @Override
    public String toString()
    {
        return "answerId: " + answerId + " questionId: " + questionId
        		+ " text: " + text + " correct: " + correct;
    }

	public int getAnswerId()
	{
		return answerId;
	}

	public void setAnswerId(int answerId)
	{
		this.answerId = answerId;
	}

	public int getQuestionId()
	{
		return questionId;
	}

	public void setQuestionId(int questionId)
	{
		this.questionId = questionId;
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

	public boolean isCorrect()
	{
		return correct;
	}

	public void setCorrect(boolean correct)
	{
		this.correct = correct;
	}
}

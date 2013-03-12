package org.ruscoe.spacetrivia.models;

/**
 * Represents a trivia category.
 * 
 * @author Dan Ruscoe
 */
public class Category
{
	public int categoryId;
	public String name;
	
    @Override
    public String toString()
    {
        return "categoryId: " + categoryId + " name: " + name;
    }
    
	public int getCategoryId()
	{
		return categoryId;
	}
	
	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}

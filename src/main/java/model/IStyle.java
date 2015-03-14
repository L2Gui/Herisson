package model;

/**
 * Created by Arnaud on 10/03/2015.
 */
public interface IStyle {
     public int getUsageCount();
    public void setUsageCount(int count);
    public void incrementUsageCount();
    public void decrementUsageCount();
}

package model;

/**
 * Created by Arnaud on 10/03/2015.
 */
public interface IStyle {
     public int getUsageCount();
    public IStyle setUsageCount(int count);
    public IStyle incrementUsageCount();
    public IStyle decrementUsageCount();
}

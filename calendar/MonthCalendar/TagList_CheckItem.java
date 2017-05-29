package MonthCalendar;

public class TagList_CheckItem
{
	private String tagName;
    private String  colorStr;
    private boolean isSelected = true;

    public TagList_CheckItem(String tagName,String colorStr)
    {
    	this.tagName=tagName;
        this.colorStr = colorStr;
    }
    public boolean isSelected()
    {
        return isSelected;
    } 
    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public String toString(){return tagName;}
    public String getColorStr(){return colorStr;}
}
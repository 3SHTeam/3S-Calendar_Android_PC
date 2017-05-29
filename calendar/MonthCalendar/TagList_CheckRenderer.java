package MonthCalendar;

import java.awt.*;
import javax.swing.*;

import Calendar.UI.TagColorSet;

public class TagList_CheckRenderer extends JCheckBox implements ListCellRenderer
{
	private JLabel colorLabel=new JLabel();
	private JList list;
	private TagList_CheckItem tagNameBox;

	public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected, boolean hasFocus)
    {
		this.list=list;
		tagNameBox=(TagList_CheckItem)value;
        setEnabled(list.isEnabled());
        setFont(new Font("¸¼Àº °íµñ",Font.BOLD,16));
        setOpaque(false);
        setPreferredSize(new Dimension(140,50));
        setSelected(tagNameBox.isSelected());
        setText(tagNameBox.toString());
        setLayout(new BorderLayout());
  
        colorLabel.setBackground(TagColorSet.hex2Rgb(tagNameBox.getColorStr()));
        colorLabel.setPreferredSize(new Dimension(5, 5));
        colorLabel.setOpaque(true);
        add(colorLabel, BorderLayout.EAST);
        
        return this;
    	}

    }

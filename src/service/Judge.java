
package wiffiwhip.service;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import java.awt.Toolkit;



/**
 *
 * @author Makz
 */
public class Judge extends VBox{
    private List<TextField> textFields;
    private TextField selected;
    private int maxChars = 3;
    final String restictTo = "[0-9]*";
	private DanceTable mDanceTable;
	private int arrayPos;
    
	/**
	 * Each Judge has the number of callbacks + 2 text fields and will do error
	 * checking for the text fields that the Judge holds
	 * @param pNum the number of callbacks
	 * @param displayCallbackLabels true if 1 -> pNum + 2 needs to be displayed
	 * @param judgeNum the Judge number to determain what lable that judge gets (A - K)
	 * @param pDanceTable the table where the Judge will be stored
	 */
	public Judge(int pNum, Boolean displayCallbackLabels, int judgeNum, DanceTable pDanceTable)
    {
    	arrayPos = -1;
    	mDanceTable = pDanceTable;
        textFields = new ArrayList<TextField>(pNum + 2);
        for(int i = 0; i < pNum + 2; i++)
        {
            final TextField temp1 = new TextField()
            {
                /**
                * this should make sure that the judges are restricted to numbers 0-9
                * and only have 3 of them
                * @param text found in the text field
                * @return if its an acceptable match
                */
                private boolean matchTest(String text)
                {
                    return text.isEmpty() ||
                            (text.matches(restictTo) 
                            && getText().length() < maxChars);
                }
		
                /**
                * no really sure how this works, found the code 
                * @http://fxexperience.com/2012/02/restricting-input-on-a-textfield/
                */
                @Override
                public void replaceText(int start, int end,
                String text)
                {
                    if (matchTest(text))
                    {
                        super.replaceText(start, end, text);
                    }
                }
		
                /**
                * more of our black magic happening... just don't question it
                */
		
                @Override
                public void replaceSelection(String text)
                {
                    if(matchTest(text))
                    {
                        super.replaceSelection(text);
                    }
                }
            };
            temp1.setPromptText("Couple");
            temp1.setPrefWidth(60);

            /*
             * controls what happens when text field unfocused.  This is where 
             * error checking happens
             */
            temp1.focusedProperty().addListener(new ChangeListener<Boolean>() 
            {
            	public void changed(ObservableValue<? extends Boolean> arg0, 
						Boolean oV, Boolean nV)
            	{
            		if (nV)
            		{
            			selected = temp1;
            		}
            		if (oV)
            		{
            			colorChange(temp1);
            		}
            		upDateFocus();
            	}
            });
            
            
            temp1.setOnKeyPressed(keyListener);
            
            textFields.add(temp1);
        }
	
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        int y = 1;
        int callbackNum = 1;
        char[] judgeLetter = Character.toChars(judgeNum + 64);
        //Label judgeNumLabel = new Label("Judge " + judgeNum);
        Label judgeNumLabel = new Label("" + judgeLetter[0]);
        grid.add(judgeNumLabel,1,0);
	
        for(TextField t : textFields)
        {
            if(displayCallbackLabels)
            {
                Label numCallback = new Label("" + callbackNum);
                grid.add(numCallback,0,y);
                callbackNum++;
            }
            grid.add(t, 1, y);
            y += 2;
        }
	
        getChildren().addAll(grid);
    }

    /**
     * checks each textField to see if there are any repeat strings
     * @return true if there are repeats
     */
    public boolean containsError()
    {
        for(TextField t : textFields)
        {
            if(notEmpty(t))
            {
                if (errorChecking(t.getText()))
                {
                	return true;
                }
            }
        }
        return false;			
    }
    
    /**
     * makes it so the text field only accepts 0-9
     * @param input the string that the user is trying to input
     * @return true if the string matches 0-9
     */
    public boolean accept(String input)
    {
        return input.matches("[0-9]");	
    }
    
    /**
     * checks to see if there is more than one occurance of the string
     * passed in
     * @param check the string that needs to be checked
     * @return true if there are more than one occurance of the string in the 
     * list
     */
    public boolean errorChecking(String check)
    {
        int i = 0;
	
        for(TextField t : textFields)
        {
        	if (t.getText().equals(check) && notEmpty(t))
                i++;
        }
        if(i > 1)
            return true;
        else 
            return false;
	}
    
    /**
     * checks to see if the text field is not empty
     * @param t the text field that will be checked
     * @return true if the text field isn't empty
     */
    public boolean notEmpty(TextField t)
	{
    	return (t.getText() != null && !t.getText().trim().isEmpty());
	}
       
    /**
	 * sets all of the judges call backs to "", so that you can start 
	 * a new round of dances
	 */
	public void clear()
	{
		for(TextField t : textFields)
		{
			t.setText("");
		}
	}
    
	/**
	 * gets the list of couples the Judge called back, checks to see if there
	 * is an error already or not
	 * @return list of couples to call back
	 */
    public List<String> callBack()
	{
            List<String> list = new ArrayList<String>(textFields.size());
            if (containsError())
            {
                return list;
            }
            for(TextField t : textFields)
            {
                if ((notEmpty(t)))
                {
                    list.add(t.getText());
                }
            }
            return list;
	}
    
    /**
     * sets the focus to the next text field
     */
    public void nextFocus()
    {
    	arrayPos++;
    	if (arrayPos == textFields.size())
    	{
    		mDanceTable.nextFocus();
    	}
    	else
    	{
    		textFields.get(arrayPos).requestFocus();
    	}
    }
    
    /**
     * sets the focus to the previous text field
     */
    public void prevFocus()
    {
    	arrayPos--;
    	if (arrayPos < 0)
    	{
    		mDanceTable.prevFocus();
    	}
    	else
    	{
    		textFields.get(arrayPos).requestFocus();
    	}
    }
    
    /**
     * sets the focus to the last text field
     */
    public void setFocusLast()
    {
    	textFields.get(textFields.size() - 1).requestFocus();
    }
    /**
     * updates the int keeping track of where the focus is
     */
    public void upDateFocus()
    {
    	int s = 0;
    	for (TextField tF : textFields)
    	{
    		if (tF == selected)
    		{
    			arrayPos = s;
    		}
    		else
    			s++;
    	}
    	vScrPos(arrayPos);
    }
    
    /**
     * sets the focus to the first text field
     */
    public void setFocus()
    {
    	textFields.get(0).requestFocus();
    }
    
    /**
     * checks to see if one of the text fields is focused
     * @return true if a text field is focused
     */
    public boolean hasFocus()
    {
    	for(TextField tF : textFields)
    	{
    		if (tF.isFocused())
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * is responsible for ensuring that the TextFieled gets the correct color,
     * either red or white
     * @param temp1 the TextField to have the color changed... or kept
     */
    public void colorChange(TextField temp1)
    {
    	if (errorChecking(temp1.getText()))
        {
            temp1.setStyle(" -fx-background-color: #ff0000");
            Toolkit.getDefaultToolkit().beep();
        }
        else 
        {
            temp1.setStyle(" -fx-background-color: #ffffff");
        }
    }
    
    /**
     * controls all of directional key presses and what they should do
     * for the judge class
     */
    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>()
    {
    	public void handle(KeyEvent e)
    	{
    		if (e.getCode() == KeyCode.DOWN ||
    				e.getCode() == KeyCode.ENTER)
    		{
    			nextFocus();
    		}
    		else if (e.getCode() == KeyCode.UP)
    		{
    			prevFocus();
    		}
    	}
    	
    };
    
    /**
     * returns the pos that the text field will should reside in the table
     * @param nTextField which text field is selected
     */
    public void vScrPos(int nTextField)
    {

    	double pos = nTextField * (textFields.get(0).getHeight() 
    			+ 12);
    	mDanceTable.vScrPos(pos);
    }
    

}

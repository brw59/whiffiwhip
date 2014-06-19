package wiffiwhip.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.GridPane;


/**
 * holds each of the judges and makes a table from it
 * @author Makz
 */
public class DanceTable extends GridPane{
    private int mNumJudges;
    private int mNumCallbacks;
    private ArrayList theJudges;
    Iterator<Judge> it;
    private TabDisplay mTabDisplay;
    
    /**
     * constructor for the danceTable class
     * @param numJudges number of judges that the table needs
     * @param numCallbacks number of textFields each judge needs
     * @param pTabDisplay the tabDisplay this table will be in
     */
    public DanceTable(int numJudges, int numCallbacks, TabDisplay pTabDisplay)
    {
        mNumJudges = numJudges;
        mNumCallbacks = numCallbacks;
        int column = 2;
        mTabDisplay = pTabDisplay;
        
        theJudges = new ArrayList<Judge> ();
        
        for(int i = 0; i < mNumJudges; i++)
        {
            Judge newJudge;
            if(i == 0)
            {
                newJudge = new Judge(mNumCallbacks, true, i + 1, this);
            }
            else
            {
                newJudge = new Judge(mNumCallbacks, false, i + 1, this);
            }
            theJudges.add(newJudge);
        }
        
        //add judges to the GridPane.
        it = theJudges.iterator();
        while(it.hasNext())
        {
            Judge currJudge = it.next();
            add(currJudge, column, 6);
            column++;
        }
    }
    
    /**
     * provides the callback function for the table.  Using a map it will return
     * the couples and the number of judges that call them back
     * @return Map<couple, number of judges>
     */
    public Map<String, Integer> calculateCallback()
    {
        Map <String, Integer> theMap = 
                new HashMap<String, Integer>();
        
        it = theJudges.iterator();
        while(it.hasNext())
        {
            List<String> tempList = new ArrayList();
            Judge currJudge = it.next();
            tempList = currJudge.callBack();
            Iterator listIter = tempList.iterator();
            for(String couple : tempList)
            {
                if(!theMap.containsKey(couple))
                {
                    theMap.put(couple, 1);
                }
                else
                {
                    int count = theMap.get(couple);
                    count++;
                    theMap.put(couple, count);
                }
            }
        }
        return theMap;
    }
    
    /**
     * calls the clear for each judge
     */
    public void clear()
    {
        it = theJudges.iterator();
        while(it.hasNext())
        {
            Judge currJudge = it.next();
            currJudge.clear();
        }
    }
    
    /**
     * asks each judge if they have an error, and returns false if a 
     * judge has an error
     * @return true if no errors, false if there is an error
     */
    public Boolean errorChecking()
    {
        Boolean okayData = true;
        it = theJudges.iterator();
        while(it.hasNext())
        {
            Judge currJudge = it.next();
            if(currJudge.containsError())
            {
                okayData = false;
                break;
            }
        }
        return okayData;
    }
    
    /**
     * sets the focus to the next judge
     */
    public void nextFocus()
    {
    	int selected = getSelected();
    	if (selected != -1)
    	{
    		if (selected == theJudges.size() - 1)
    			mTabDisplay.nextFocus();
    		else
    		{
    			Judge j = (Judge) theJudges.get(selected + 1);
    			j.setFocus();
    			hScrPos(selected + 1);
    		}	
    	}
    }
    
    /**
     * checks to see if one of the judges has focus 
     * @return true if one of the judges has the focus, otherwise false
     */
    public boolean hasFocus()
    {
    	it = theJudges.iterator();
        while(it.hasNext())
        {
            Judge currJudge = it.next();
            if(currJudge.hasFocus())
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * sets the focus to the first judge, on the first text field
     */
    public void setFocus()
    {
    	Judge j = (Judge) theJudges.get(0);
    	j.setFocus();
    	hScrPos(0);
    }
    
    /**
     * sets the focus to the last judge and the last test field
     */
    public void setFocusLast()
    {
    	Judge j = (Judge) theJudges.get(theJudges.size() -1 );
    	j.setFocusLast();
    	hScrPos(theJudges.size() - 1);
    }
    
    /**
     * sets the focus to the judge before the current judge
     */
    public void prevFocus()
    {
    	int selected = getSelected();
    	if (selected > 0)
    	{
    		Judge j = (Judge) theJudges.get(selected - 1);
    		j.setFocusLast();
    		hScrPos(selected - 1);
    	}
    	else
    	{
    		mTabDisplay.prevFocus();
    	}
    }
    
    /**
     * returns the judge position that has the focus
     * @return the judge position that has focus
     */
    public int getSelected()
    {
    	int selected = -1;
    	for (int i = 0; i < theJudges.size(); i++)
    	{
    		Judge j = (Judge) theJudges.get(i);
    		if (j.hasFocus())
    		{
    			selected = i;
    		}
    	}
    	return selected;
    }
    
    /**
     * This is simply passing this on from the Judge class.  The logic for the
     * position will be handled in the Judge class
     * @param pos double passed from the Judge class that specifies where the 
     * new vValue should be
     */
    public void vScrPos(double pos)
    {
    	mTabDisplay.vScrPos(pos);
    }
    
    /**
     * The logic behind when the scroll pane needs to be moved
     * @param nJudge
     */
    public void hScrPos(int nJudge)
    {
    	Judge temp = (Judge) theJudges.get(nJudge);
    	double pos = nJudge * (temp.getWidth() + 10);
    	mTabDisplay.hScrPos(pos);
    }
    
    /**
     * just a getter
     * @return the number of textfields or callbacks
     */
    public int numOfCalcbacks()
    {
    	return mNumCallbacks;
    }
}

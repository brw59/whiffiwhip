package wiffiwhip.service;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.stage.Stage;



/**
 * this is a Vbox so that we can break it up into multi sections.  The top half
 * will be a HBox that has a infoBox, and a scroll pane(which will have the 
 * dance tables in it), the bottom half will hold the InfoDisplay class
 * @author Jeff
 *
 */
public class TabDisplay 
extends VBox
{
	//this box will hold the infoBox and the scr box
	private HBox top;
	
	//this box will hold the dance tables
	private VBox rightTop;
	
	//this scroll pane will hold the rightTop box
	private ScrollPane scr;
	
	//This is the infoBox
	private InfoBox mInfoBox;
	
	//the list of all the dance tables
	private List<DanceTable> mDanceTables;
	
	//to determine if this pane is used for single or multi Dances
	private boolean mSingle;
	
	//The InfoDisplay
	private InfoDisplay mInfoDisplay;
	
	//the three buttons used 
	private Button genTable;
	private Button callBack;
	private Button clear;
	
	//The container used to pass information around
	private List<String> allInfo;
	
	private TabDisplay mTabDisplay;
	
	//attempting to add this here so that i can get the size of it later on
	private HBox tempHbox;
	
	//
	private List<TextField> labelTexts;
	
	private Stage mStage;
	/**
	 * creates a TabDisplay that will be either based for the single or the
	 * multi-dance tabs
	 * @param single true if its for the single dance, false if its for 
	 * the multi-dance one
	 */
	public TabDisplay(Boolean single, PublicDisplay pPublicDisplay, Stage primaryStage)
	{
		
		mStage = primaryStage;
		/*
		 * initialize all the fields
		 */
		top = new HBox();
		scr = new ScrollPane();
		rightTop = new VBox();
		mTabDisplay = this;
		/*
		 * creates the fields that don't need input to be created
		 * i.e. the infoBox & infoDispaly
		 */
		genTable = new Button("Generate Table");
		callBack = new Button("Calculate Callback");
		clear = new Button("Clear");
		mInfoBox = new InfoBox(single, genTable);
		mSingle = single;
		mInfoDisplay = new InfoDisplay(pPublicDisplay, clear, primaryStage);
		callBack.visibleProperty().set(false);
		top.setStyle("-fx-border-width:3;");
		getChildren().addAll(top, mInfoDisplay.getDisplayBox());
		//labelTexts = new ArrayList<TextField>();
		
		
		/*
		 * scroll creation, if there is time find a way to fix some of the 
		 * problems with this
		 */
		scr.setPrefSize(450, 300);
		/*
		 * the next few option are all just for getting the button callback
		 * in the right place... its kind of a sloppy way to do it, but it 
		 * works.  I've spent a couple of hours trying GridPane's to get it
		 * in the right place, and so since they didn't work this was a 
		 * solution that did.  If we need *time* this might be a time hole
		 * that will work to provide it
		 */
		VBox buttonHolder = new VBox();
		HBox buttonPlacer = new HBox();
		Rectangle pusher = new Rectangle(315, 10);
		pusher.visibleProperty().setValue(false);
		buttonPlacer.getChildren().addAll(pusher, callBack);
		
		buttonHolder.getChildren().addAll(scr, buttonPlacer);
		buttonHolder.setMargin(scr, new Insets(5));
		top.getChildren().addAll(mInfoBox, buttonHolder);
		top.setMargin(mInfoBox, new Insets(5));
		/*
		 * these are all of the button actions getting set here
		 * first one deals with the "Generate Tables" button 
		 */
		genTable.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				if(getInfo())
				{
					int pJudges = Integer.parseInt(allInfo.get(3));
					int pCallBacks = Integer.parseInt(allInfo.get(4));
					DanceTable mTempTable;
					labelTexts = new ArrayList<TextField>();
					rightTop.getChildren().clear();
					// create the dance tables with all their glory
					if (mSingle)
					{
						mDanceTables = new ArrayList<DanceTable>(1);
						mTempTable = new DanceTable(pJudges, pCallBacks, mTabDisplay);
						mDanceTables.add(mTempTable);
					}
					else
					{
						int nDances = Integer.parseInt(allInfo.get(6));
						mDanceTables = new ArrayList<DanceTable>(nDances);
						for(int i = 0; i < nDances; i++)
						{
							mTempTable = new DanceTable(pJudges, pCallBacks - 2, mTabDisplay);
							mDanceTables.add(mTempTable);
						}
					}
					int labelNum = 1;
					/*
					 * this next part might have a better way to do this, but for 
					 * now this works and, if we have time, it may be worth going 
					 * back over
					 */
					if (mDanceTables.size() == 1)
						rightTop.getChildren().add(mDanceTables.get(0));
					else
					{
						for( DanceTable dT : mDanceTables)
						{
							tempHbox = new HBox();
							TextField tempTextField = new TextField();
							// set up the focus for the scroll bar
							tempTextField.focusedProperty().addListener(new ChangeListener<Boolean>() 
						    {
				            	public void changed(ObservableValue<? extends Boolean> arg0, 
									Boolean oV, Boolean nV)
				            	{
				            		if(nV)
				            		{
				            			lableFinder();
				            		}
				            	}
							});
							tempTextField.setOnKeyPressed(keyListener);
							labelTexts.add(tempTextField);
							Label tempLabel = new Label("Dance " + labelNum++);
							tempHbox.getChildren().addAll(tempLabel, tempTextField);
							tempHbox.setMargin(tempLabel, new Insets(10));
							tempHbox.setMargin(tempTextField, new Insets(10));
							rightTop.getChildren().add(tempHbox);
							rightTop.getChildren().add(dT);
						}
					}
					scr.setContent(rightTop);
					callBack.visibleProperty().set(true);
					mDanceTables.get(0).setFocus();
				}
				else
				{
					Alert infoWarning = new Alert(2);
				}
				
			}
		});
		
		/*
		 * This button action is the "Callback button" located near the 
		 * tables
		 */
		callBack.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				boolean errors = false;
				Map<String, Integer> calls = 
						new HashMap<String, Integer>();
				if(mDanceTables != null)
				{
						for (DanceTable t : mDanceTables)
						{	
							//checks each table to ensure no errors report
							if(t.errorChecking())
							{
								join(calls, t.calculateCallback());
							}
							else
							{
								errors = true;
								Alert temp = new Alert(1);
							}
						}	
						if (!errors)
						{
							passInfo(calls);
						}
				}
				else
				{
					Alert infoWarning = new Alert(3);
				}
			}
		});
		
		/*
		 * the clear button new the public display
		 */
		clear.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				clear();
			}
		});
	}
	
	/**
	 * takes the values from one map and adds it to the next
	 * @param all the map that will hold more than just one table at a time
	 * @param one the map that holds only one tables info
	 */
	public void join(Map<String, Integer> all, 
					Map<String, Integer> one)
	{
		Set<String> strings = one.keySet();
		int allTemp;
		
		for (String s : strings)
		{
			if (all.containsKey(s))
			{
				allTemp = all.get(s);
				allTemp += one.get(s);
				all.put(s, allTemp);
			}
			else
			{
				all.put(s, one.get(s));
			}
		}
	}

	/**
	 * passes the information to the InfoDisplay class
	 */
	public void passInfo(Map<String, Integer> calls)
	{
		allInfo = mInfoBox.getTableInfo();
		mInfoDisplay.updateCalcs(calls, allInfo, mSingle);
	}
	
	/**
	 * gets all the information from the infoBox class and stores it into the 
	 * allInfo list.  If the required information has been selected than 
	 * true is returned, if not than false
	 * @return
	 */
	public boolean getInfo()
	{
		allInfo = mInfoBox.getTableInfo();
		if (mSingle)
			return singleCheck();
		else
			return multiCheck();
	}
	
	/**
	 * singleCheck simply checks to make sure that the needed information
	 * for generating tables exists
	 * @return false if the information hasn't been selected yet, true if all 
	 * required selections have been made
	 */
	public boolean singleCheck()
	{
		if (allInfo.get(3).equals("--") || allInfo.get(4).equals("--"))
			return false;
		else
			return true;
	}
	
	/**
	 * multiCheck insures that all the the needed fields have been selected 
	 * @return false if the information hasn't been selected yet, true if all 
	 * required selections have been made
	 */
	public boolean multiCheck()
	{
		if (singleCheck() && !allInfo.get(6).equals("--"))
			return true;
		else
			return false;
	}
	
	/**
	 * clear just calls each of the three main parts clear functions
	 * so that everything is cleared.
	 */
	public void clear()
	{
		mInfoBox.clear();
		if (mDanceTables != null)
		{		
			for(DanceTable d : mDanceTables)
			{
				d.clear();
			}
			for (TextField tF : labelTexts)
			{
				tF.setText("");
			}
		}
		mInfoDisplay.clear();
		
	}
	
	/**
	 * save just calls the save function in the infoDisplay
	 */
	public void save()
	{
		mInfoDisplay.save();
	}
	
	/**
	 * sets the focus on the next field that should get the focus, depending on what
	 * has the current focus
	 */
	public void nextFocus()
	{
		int danceT = tableSelected();
		if (danceT == mDanceTables.size() - 1)
		{
			callBack.requestFocus();
		}
		else 
		{
			labelTexts.get(danceT + 1).requestFocus();
			//mDanceTables.get(danceT+1).setFocus();
		}
	}
	
	/**
	 * sets the focus to the previous field
	 */
	public void prevFocus()
	{
		int danceT = tableSelected();
		//if (danceT > 0)
		{
			labelTexts.get(danceT).requestFocus();
			//mDanceTables.get(danceT - 1).setFocusLast();
		}
		//else
		{
			//genTable.requestFocus();
		}
	}
	
	
	/**
	 * gets the mtable position
	 */
	public int tableSelected()
	{
		int danceT = -1;
		for (DanceTable dT : mDanceTables)
		{
			danceT++;
			if(dT.hasFocus())
			{
				break;
			}
		}
		return danceT;
	}
	
	/**
	 * this is just a current test to get the scroll pane to scroll dynamically
	 * horizontally
	 * @param pos the new position that the scroll pane h wise
	 */
	public void hScrPos(double pos)
	{
		scr.setHvalue(pos / rightTop.getWidth());
	}
	
	/**
	 * so theoretically the input pos should be the size that the scroll pane needs to 
	 * move in order to keep up with the judges.  The rest of it tries to deal with
	 * which table should be focused on, and needs to take into account the textField 
	 * labels... This has not been easy so far.  Of course if we can just figure out how
	 * the system handles the tab press, none of this would be needed.  
	 * @param pos the new position that the scroll pane v wise
	 */
	public void vScrPos(double pos)
	{
		
		if (mSingle)
			scr.setVvalue(pos / rightTop.getHeight());
		else 
		{
			int temp = tableSelected();
			if (temp < (mDanceTables.size() + 1) / 2)
			{
				vValueFinder(temp, pos, 0);
			}
			else
			{
				if (Integer.parseInt(allInfo.get(4)) < 11)
				{
					vValueFinder(temp, pos, 70);
				}
				else
				{
					vValueFinder(temp, pos, 50); 
				}
			}
		}
	}

	/**
	 * @param temp is the table number that is now being focused on
	 * @param pos this number has been calculated by the Judge class
	 * it should be the height that the text field would have inside each table.
	 * @param conditionalAdder This depends on if the table that has
	 * the selection is on the 1/2 of the tables on the upper bound 
	 */
	public void vValueFinder(int temp, double pos, 
							int conditionalAdder)
	{
		
		scr.setVvalue((pos/rightTop.getHeight()) + 
				(temp * (mDanceTables.get(temp).getHeight() + 
						(tempHbox.getHeight() + conditionalAdder)) 
						/ rightTop.getHeight()));
	}
	
	
	/**
	 * sets the focus for the text field thats selected.  This was to take care
	 * of the random jump that the scroll pane would make if a text field was selected
	 * first.
	 */
	public void lableFinder()
	{
		int lableNum = labelNumFinder();
		vValueFinder(lableNum, 0, 0);
	}
	
	/**
	 * gets the dance label number focused on
	 * @return the label number that is focused on
	 */
	public int labelNumFinder()
	{
		int labelNum = 0;
		for(TextField tF : labelTexts)
		{
			if(tF.isFocused())
			{
				break;
			}
			labelNum++;
		}
		return labelNum;
	}
	
	/**
     * controls all of directional key presses and what they should do
     * for the the text fields at the tops of each table
     */
    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>()
    {
    	public void handle(KeyEvent e)
    	{
    		int labelNum = labelNumFinder();
    		if (e.getCode() == KeyCode.DOWN || 
    				e.getCode() == KeyCode.ENTER)
    		{
    			mDanceTables.get(labelNum).setFocus();
       		}
    		else if (e.getCode() == KeyCode.UP)
    		{
    			if (labelNum == 0)
    			{
    				genTable.requestFocus();
    			}
    			else
    			{
    				mDanceTables.get(labelNum - 1).setFocusLast();
    			}
    		}
    	}
    	
    };
}

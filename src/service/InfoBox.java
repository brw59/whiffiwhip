package wiffiwhip.service;



import java.util.List;

import javafx.collections.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

/**
 * InfoBox will is a VBox that contains all of the combobox's to 
 * supply the dance info
 * @author Jeff
 *
 */
public class InfoBox 
extends VBox
{
	private ComboBox mAge;
	private ComboBox mDanceStyle;
	private ComboBox mLevel;
	private ComboBox mNumberOfJudges;
	private ComboBox mCouplesToCallBack;
	private ComboBox mCallBacksFor;
	private ComboBox mSpecial;
	
	/**
	 * a constructor, that requires a boolean telling it if its for 
	 * a single dance view or for a multi-dance view.
	 * @param single true for single view, false for multi-view
	 */
	public InfoBox(boolean single, Button pButton)
	{
		ObservableList<String> ageList = 
				FXCollections.observableArrayList( "--",
												   "Peewee",
												   "Preteen",
												   "Junior",
												   "Youth",
												   "Collegiate",
												   "Adult",
												   "Senior"
													);
		ObservableList<String> numberOfJudges = 
				FXCollections.observableArrayList("--","3", "5", "7",
												  "9", "11"
													);
		ObservableList<String> couplesCalledBack = 
				FXCollections.observableArrayList("--","4", "5", "6",
												  "7", "8", "9",
												  "10", "11", "12",
												  "13", "14", "15",
												  "16", "17", "18",
												  "19", "20", "21",
												  "22", "23", "24",
												  "25", "26", "27",
												  "28", "29", "30",
												  "31", "32", "33",
												  "34", "35", "36");
		ObservableList<String> callBacksFor = 
				FXCollections.observableArrayList("--",
												  "Round 1",
												  "Round 2",
												  "Round 3",
												  "Round 4",
												  "QuarterFinal",
												  "Semifinal",
												  "Final");
		ObservableList<String> changing;
		ObservableList<String> danceStyle;
		ObservableList<String> level;
		String changingLabel;
		if(single)
		{
			danceStyle = FXCollections.observableArrayList("--",
						"--International Standard (IS)",
							"IS--Quickstep",
							"IS--Slow Foxtrot",
							"IS--Tango",
							"IS--Viennese Waltz",
							"IS--Waltz",
						"--International Latin (IL)",
							"IL--Cha Cha",
							"IL--Jive",
							"IL--Paso Doble",
							"IL--Rumba",
							"IL--Samba",
						"--American Smooth(AS)",
							"AS--Foxtrot",
							"AS--Tango",
							"AS--Viennese Waltz",
							"AS--Waltz",
						"--American Rhythm(AR)",
							"AR--Bolero",
							"AR--Cha Cha",
							"AR--East Coast Swing",
							"AR--Mambo",
							"AR--Rumba",
						"--Other",
							"Cabaret",
							"Salsa",
							"NY Huslte",
							"Lindy",
							"Swing",
							"Two Step",
							"West Coast Swing",
							"Bonus Swing"
						);
			changing = FXCollections.observableArrayList("--",
														"A",
														"B"
														);
			
			level = FXCollections.observableArrayList("--",
												"Newcomer",
												"Bronze",
												"Silver 1",
												"Silver 2",
												"Gold 1",
												"Gold 2",
												"Open",
												"Bonus",
												"Novice",
												"Pre-Champion",
												"Champion"
												);
			changingLabel = "A/B";
		}
		else
		{
			danceStyle = FXCollections.observableArrayList("--",
													"Standard",
													"Latin",
													"Smooth",	
													"Rhythm");
			
			changing = FXCollections.observableArrayList("--",
														 "2",
														 "3",
														 "4",
														 "5");
			
			level = FXCollections.observableArrayList("--",
												  "Bronze",
												  "Silver",
												  "Gold",
												  "Open",
												  "Novice",
												  "Pre-Champion",
												  "Champion"
													);
			changingLabel = "Number Of Dances";
		}
		
		mAge = new ComboBox(ageList);
		mDanceStyle = new ComboBox(danceStyle);
		mLevel = new ComboBox(level);
		mSpecial = new ComboBox(changing);
		mNumberOfJudges = new ComboBox(numberOfJudges);
		mCallBacksFor= new ComboBox(callBacksFor);
		mCouplesToCallBack= new ComboBox(couplesCalledBack);
	
		/*
		 * ensures that all the box's start with the "--" and 
		 * also makes sure that all of the box's do not require
		 * a scroll bar to view occupants 
		 */
		clear();
		mAge.setValue("--");
		mDanceStyle.setValue("--");
		mNumberOfJudges.setValue("--");
		mDanceStyle.setVisibleRowCount(33);
		mCouplesToCallBack.setVisibleRowCount(34);
		mLevel.setVisibleRowCount(13);
		
		//this is just to make all of the box's the same size
		// if you want to change it go ahead
		mAge.setMinWidth(222);
		mDanceStyle.setMinWidth(222);
		mDanceStyle.setMaxWidth(222);
		mLevel.setMinWidth(222);
		mSpecial.setMinWidth(222);
		mNumberOfJudges.setMinWidth(222);
		mCallBacksFor.setMinWidth(222);
		mCouplesToCallBack.setMinWidth(222);
		
	
		Button btn = pButton;
		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		int y = 0;
		grid.add(new Label("Age"), 0, y);
		grid.add(mAge, 4, y);
		y+=2;
		grid.add(new Label("Dance Style"), 0, y);
		grid.add(mDanceStyle, 4, y);
		y+=2;
		grid.add(new Label("Level"), 0, y);
		grid.add(mLevel, 4, y);
		y+=2;
		if (single)
		{
			grid.add(new Label(changingLabel), 0, y);
			grid.add(mSpecial, 4, y);
			y+=2;
		}
		grid.add(new Label("Number Of Judges"), 0, y);
		grid.add(mNumberOfJudges, 4, y);
		y+=2;
		if (!single)
		{
			grid.add(new Label(changingLabel), 0, y);
			grid.add(mSpecial, 4, y);
			y+=2;
		}
		grid.add(new Label("Couples to call back"), 0, y);
		grid.add(mCouplesToCallBack, 4, y);
		y+=2;
		grid.add(new Label("Callbacks for"), 0, y);
		grid.add(mCallBacksFor, 4, y);
		y+=2;
		grid.add(btn, 4, y);
		getChildren().addAll(grid);
	}
	
	/**
	 * used when the user wants to create the table/tables  
	 * @return All of the ComboBox's selected values, in a List of Strings, in the order
	 * of age, danceStyle, level, number of Judges, couples to call back, call back for,
	 * special.
	 */
	public List<String> getTableInfo()
	{
		List<String> info = new ArrayList<String>(8);
		info.add(mAge.getValue().toString());
		info.add(mDanceStyle.getValue().toString());
		info.add(mLevel.getValue().toString());
		info.add(mNumberOfJudges.getValue().toString());
		info.add(mCouplesToCallBack.getValue().toString());
		info.add(mCallBacksFor.getValue().toString());
		info.add(mSpecial.getValue().toString());
		return info;		
	}
	
	/**
	 * reset the fields Age, level, A/B, Couples to call back, CallbacksFor to "--"
	 */
	public void clear()
	{
		mLevel.setValue("--");
		mCouplesToCallBack.setValue("--");
		mCallBacksFor.setValue("--");
		mSpecial.setValue("--");
	}
}

package wiffiwhip.service;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * This class displays the information made from calculations by the
 * judges.
 */
class InfoDisplay
{
   /**
    * The textbox for the initial calculations
    */
   TextArea mCalculations;

   /**
    * The textbox for what will be pushed to the display
    */
   TextArea mPreview;

   /**
    * The textbox that will show what is on the display.
    * The text in it will be the exact same as what is
    * in the mPublicDisplay.
    */
   TextArea mDisplay;

   /**
    * The display that this InfoDisplay will push to
    */
   PublicDisplay mPublicDisplay;
   
   /**
    * The button that, when clicked, pushes the text from mPreview to the
    * top of mDisplay and mPublicDisplay
    */
   Button mPostToDisplay;

   /**
    * This button increases the text size in the PublicDisplay
    */
   Button mBiggerText;
   
   /**
    * This button decreases the text size in the PublicDisplay
    */
   Button mSmallerText;

   /**
    * This button clears mPreview and mCalculations. It also clears some
    * things in the other classes, but it is in this class so it's layout
    * is easier to control.
    */
   Button mClear;

   /**
    * The HBox that holds all of the controls and their layout
    */
   HBox mDisplayBox;

   /**
    * The file that we are saving to
    */
   File mSaveFile;

   /**
    * The fileWriter to the mSaveFile
    */
   FileWriter mFileWriter;

   /**
    * The handle to the main window
    */
   Stage mPrimaryStage;
   
   /**
    * Constructor for the InfoDisplay
    *
    * @param pubDisplay the display that this InfoDisplay will push to
    *                   (this will be either the single dance display
    *                    or the multi-dance display, which will both
    *                    be in the PublicWindow
    * @param clearButton the button used to clear most of the controls
    *                    on the main window
    */
   public InfoDisplay(PublicDisplay pubDisplay, Button clearButton, Stage primaryStage)
   {
      mPublicDisplay = pubDisplay;
      mSaveFile = null;

      initializeControls(clearButton);
      initializeLayout();

      mPrimaryStage = primaryStage;
   }
   
   /**
    * Initializes and sets the layout for all of the controls
    *
    * @param clearButton the button used to clear most of the controls
    *                    on the main window
    */
   public void initializeControls(Button clearButton)
   {
      mDisplayBox = new HBox(10);
      mCalculations = new TextArea();
      mCalculations.setPrefSize(400, 190);
      mCalculations.setWrapText(true);

      mPreview = new TextArea();
      mPreview.setPrefSize(400, 190);
      mPreview.setWrapText(true);
      mPreview.setWrapText(true);
      
      mDisplay = new TextArea();
      mDisplay.setPrefSize(445, 410);
      mDisplay.setWrapText(true);
      mDisplay.textProperty().addListener(new ChangeListener<String>() 
      {
         @Override
         public void changed(final ObservableValue<? extends String> observable, 
                             final String oldValue, final String newValue) 
         {
            // this will run whenever text is changed
            mPublicDisplay.setText(mDisplay.getText());
         }
      });
      
      mPostToDisplay = new Button();
      mPostToDisplay.setText("Post to Display");
      mPostToDisplay.setOnAction(new EventHandler<ActionEvent>() 
      {
         @Override
         public void handle(ActionEvent event) 
         {
            mDisplay.setText(mPreview.getText() + "\n\n" + mDisplay.getText());
            save();
         }
      });

      mBiggerText = new Button();
      mBiggerText.setText("Bigger Text");
      mBiggerText.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event) 
         {
            mPublicDisplay.setTextSize(mPublicDisplay.getTextSize() + 2);
         }
      });

      mSmallerText = new Button();
      mSmallerText.setText("Smaller Text");
      mSmallerText.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event) 
         {
            mPublicDisplay.setTextSize(mPublicDisplay.getTextSize() - 2);
         }
      });
      
      mClear = clearButton;
   }

   /**
    * Initializes the layout of the controls
    */
   public void initializeLayout()
   {
      VBox leftBox = new VBox(5);
      leftBox.getChildren().add(new Label("Calculations"));
      leftBox.getChildren().add(mCalculations);
      leftBox.getChildren().add(new Label("Preview"));
      leftBox.getChildren().add(mPreview);
      HBox buttons = new HBox(10);
      buttons.getChildren().addAll(mPostToDisplay, mClear);
      /*
      leftBox.getChildren().add(mPostToDisplay);
      leftBox.getChildren().add(mClear);
      */
      leftBox.getChildren().add(buttons);
      VBox rightBox = new VBox(5);
      rightBox.getChildren().add(new Label("Display"));
      rightBox.getChildren().add(mDisplay);

      HBox textSizeButtonBox = new HBox(10);
      textSizeButtonBox.getChildren().add(mBiggerText);
      textSizeButtonBox.getChildren().add(mSmallerText);
      rightBox.getChildren().add(textSizeButtonBox);

      mDisplayBox.setPadding(new Insets(5));
      mDisplayBox.getChildren().add(leftBox);
      mDisplayBox.getChildren().add(rightBox);
   }

   /**
    * Returns the HBox that has all of the layout of controls
    *
    * @return the HBox that has layout of controls
    */
   public HBox getDisplayBox()
   {      
      return mDisplayBox;
   }

   /**
    * Updates the InfoDisplay with the current calculated info
    *
    * @param couplesMap list of couples and how many judges called them back
    * @param infoStrings the list of information to display for each couple
    */
   public void updateCalcs(Map<String, Integer> couplesMap, List<String> infoStrings, boolean isSingle)
   {
      //put the correct values in each TextArea, based on how many
      //couples were called back, and which info to display
      if (isSingle)
      {
         singleCalcs(couplesMap, (ArrayList)infoStrings);
      }
      else
      {
         multiCalcs(couplesMap, (ArrayList)infoStrings);
      }
   }

   /**
    * Updates the singles tab InfoDisplay with the current calculated info
    *
    * @param couplesMap list of couples and how many judges called them back
    * @param infoStrings the list of information to display for each couple
    */
   private void singleCalcs(Map<String, Integer> couplesMap, ArrayList<String> info)
   {
      //list of lists to keep track of how many couples were called back by 
      //how many judges
      int numJudges = Integer.parseInt(info.get(3));
      ArrayList<ArrayList<String>> judgeCallbacks = new ArrayList<ArrayList<String>>();
      for (int i = 0; i < numJudges; i++)
      {
         judgeCallbacks.add(new ArrayList<String>());
      }

      //iterate through map, putting couples into correct list depending on how
      //many judges called them back
      int callbacksByJudges = 0;
      for (Map.Entry entry : couplesMap.entrySet()) 
      {
         callbacksByJudges = (Integer)entry.getValue();
         judgeCallbacks.get(callbacksByJudges - 1).add((String)entry.getKey());
      }

      //put text in mCalculations
      String calcText = "";
      for (int i = numJudges - 1; i >= 0; i--)
      {
         Collections.sort(judgeCallbacks.get(i));

         calcText += "Couples Called Back by " + (i + 1) + " Judge(s):\n";
         for (int j = 0; j < judgeCallbacks.get(i).size(); j++)
         {
            //space in between couples
            if (j != 0)
            {
               calcText += "  ";
            }
            
            calcText += judgeCallbacks.get(i).get(j);
         }

         //extra lines between callbacks for number of judges
         if (i != 0)
         {
            calcText += "\n\n";
         }
      }

      mCalculations.setText(calcText);


      //mPreview
      String infoText = "";
      infoText += info.get(0) + " " + info.get(2) + " " + 
                  info.get(1) + " " + info.get(6);
      infoText += "\nCallbacks for: " + info.get(5);
      infoText += "\nCouples Called Back:\n";
      
      ArrayList<String> orderedCallbacks = new ArrayList<String>();
      int majority = (numJudges / 2) + 1;
      for (int i = judgeCallbacks.size(); i > 0; i--)
      {
         //only display ones that a majority of judges has called back
         if (i >= majority)
         {
            for (int j = 0; j < judgeCallbacks.get(i - 1).size(); j++)
            {
               orderedCallbacks.add(judgeCallbacks.get(i - 1).get(j));
            }
         }
      }

      //order list and get ready to post to preview box
      Collections.sort(orderedCallbacks);
      for (int i = 0; i < orderedCallbacks.size(); i++)
      {
         infoText += orderedCallbacks.get(i) + "  ";
      }

      mPreview.setText(infoText);
   }

   /**
    * Updates the multi tab InfoDisplay with the current calculated info
    *
    * @param couplesMap list of couples and how many judges called them back
    * @param infoStrings the list of information to display for each couple
    */
   private void multiCalcs(Map<String, Integer> couplesMap, ArrayList<String> info)
   {
      //mCalculations - sorted by number of points 
      //find highest value
      int highestValue = 0;
      for (Map.Entry entry : couplesMap.entrySet()) 
      {
         if ((int)entry.getValue() > highestValue)
         {
            highestValue = (int)entry.getValue();
         }
      }

      //put couples into new ArrayList starting from highest value down to 0
      ArrayList<String> couplesSorted = new ArrayList<String>();
      for (int i = highestValue; i > 0; i--)
      {
         for (Map.Entry entry : couplesMap.entrySet())
         {
            if ((int)entry.getValue() == i)
            {
               couplesSorted.add((String)entry.getKey());
            }
         }
      }

      //mCalculations text update
      String calcText = "Couples to Call Back:\n";
      for (int i = 0; i < couplesSorted.size(); i++)
      {
         calcText += couplesMap.get(couplesSorted.get(i)) + " pts - " +
                     couplesSorted.get(i) + "\n";
      }

      //update mCalculations with new text
      mCalculations.setText(calcText);

      //mPreview - sorted by top couples in numerical order of couples
      String prevText = "Age: " + info.get(0) + "\nDance Style: " + info.get(1) +
                        "\nLevel: " + info.get(2) + "\nCallbacks for: " + info.get(5) +
                        "\n\nCouples to Call Back:\n";
      

      int numCouplesToCallBack = Integer.parseInt(info.get(4));
      ArrayList<String> topCouples = new ArrayList<String>();
      for (int i = 0; i < numCouplesToCallBack; i++)
      {
         if (i == numCouplesToCallBack - 1)
         {
            //get all the ones that tie with the last rank
            for (int j = i; j < couplesSorted.size(); j++)
            {
               if (couplesMap.get(couplesSorted.get(j)) == 
                   couplesMap.get(couplesSorted.get(i)))
               {
                  topCouples.add(couplesSorted.get(j));
               }
            }
         }
         else
         {
            topCouples.add(couplesSorted.get(i));
         }
      }

      //sort list in ascending order
      Collections.sort(topCouples);

      //put couples to call back in text to put on mPreview
      for (int i = 0; i < topCouples.size(); i++)
      {
         if (i != 0)
         {
            prevText += "  ";
         }

         prevText += topCouples.get(i);
      }

      //update mPreview
      mPreview.setText(prevText);
   }

   /**
    * Clears mCalculations and mPreview
    */
   public void clear()
   {
      mCalculations.clear();
      mPreview.clear();
   }

   /**
    * Appends the contents of mPreview to the top of the save file.
    * If there is no save file, a dialog box pops up and has the user
    * select the name and location of the file.
    */
   public void save()
   {
      //if no output file is specified, get one
      if (mSaveFile == null)
      {
         //open save dialog to get filename
         FileChooser fileChooser = new FileChooser();
  
         //Just allow text files
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
         fileChooser.getExtensionFilters().add(extFilter);
              
         //Show save file dialog, get correct file
         mSaveFile = fileChooser.showSaveDialog(mPrimaryStage);

         //make sure mSaveFile ends in ".txt"
         if (mSaveFile != null)
         {
            if (!mSaveFile.getPath().endsWith(".txt"))
            {
               mSaveFile = new File(mSaveFile.getPath() + ".txt");
            }
         }
      }

      try
      {
         //new FileWriter
         mFileWriter = new FileWriter(mSaveFile, true);

         //push whatever is in the mPreview box to the bottom of the output file
         mFileWriter.write(mPreview.getText() + "\n\n");

         mFileWriter.close();
      }
      catch (Exception e)
      {
         System.out.println("Error occurred in saving the file");
      }
   }
}

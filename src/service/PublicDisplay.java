package wiffiwhip.service;

import javafx.scene.control.TextArea;

/**
 * This class is basically a mirror of what is displayed in InfoDisplay's
 * display textbox.
 */
class PublicDisplay
{
   /**
    * The area where the text will be displayed
    */
   TextArea mDisplay;

   /**
    * The size of the text in mDisplay
    */
   int mTextSize;
   
   /**
    * Constructor for Public Display
    */
   public PublicDisplay()
   {
      mDisplay = new TextArea();
      mDisplay.setPrefSize(2000, 2000);
      mDisplay.setWrapText(true);

      //initial font size: 30
      mDisplay.setStyle("-fx-font-size: 30;");
      mTextSize = 30;
   }

   /**
    * Pushes new text to the top of mDisplay
    *
    * @param pushedText the text that will be pushed to the top of the display
    */
   public void updateText(String pushedText)
   {
      mDisplay.setText(pushedText + "\n\n" + mDisplay.getText());
   }

   /**
    * Replaces the text in mDisplay with new text
    *
    * @param newText the text to put in mDisplay
    */
   public void setText(String newText)
   {
      mDisplay.setText(newText);
   }

   /**
    * Returns the TextArea to make drawing it on the window easier
    *
    * @return mDisplay the text area
    */
   public TextArea getDisplay()
   {
      return mDisplay;
   }

   /**
    * Sets the size of the text displayed in the textArea
    *
    * @param textSize the new size of the text
    */
   public void setTextSize(int textSize)
   {
      mDisplay.setStyle("-fx-font-size: " + textSize  + ";");
      mTextSize = textSize;
   }

   /**
    * gets the textSize 
    * @return TextSize
    */
   public int getTextSize()
   {
      return mTextSize;
   }
}

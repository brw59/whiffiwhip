package wiffiwhip.service;

import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This class is a window that displays information
 * about the program - authors, etc..
 */
class AboutWindow
{
   /**
    * The window that holds the "about" info
    */
   Stage mWindow;

   /**
    * Constructor for AboutWindow
    */
   public AboutWindow()
   {
      VBox vbox = new VBox(8); // spacing = 8
      
      String developers = "Developers:\nJeff Bickmore - jbickeff@gmail.com\nMackenzie "
       + "Bodily - mwbodily@gmail.com\nKevin Hardy - console.beep@gmail.com";
      String thankYou = "\n\nThank you for using the CallBack Buddy.  "
       + "\nKevin, Mackenzie and Jeff hope that you enjoyed"
       + " your experience.  \nPlease use this program again "
       + "sometime.  \nIf you would like a new program "
       + "created contact Brother Neff with your \"Project "
       + "proposal\".";
      String specialThanks = "\nSpecial thanks to Brother Felt for "
       + "coming up with this idea and presenting it to Brother "
       + "\nNeff";
      vbox.getChildren().add(
    	         new Label("Callback Buddy\n\n" + developers + "\n"+
    	        		 specialThanks+ thankYou ));
      //set up window
      mWindow = new Stage();
      Scene mScene = new Scene(vbox, 600, 300);
      mScene.setFill(Color.LIGHTGRAY);
      mWindow.setScene(mScene);
      mWindow.setTitle("About Callback Buddy");
      mWindow.show();
   }
}

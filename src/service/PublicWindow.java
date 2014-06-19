package wiffiwhip.service;

import java.util.prefs.Preferences;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * This class is a window that will be displayed on a
 * separate monitor for the public and other dancers
 * to see - the person running the program will not
 * be able to see this window. It displays the
 * results of calculations done by the program.
 */
class PublicWindow
{
   /**
    * The window that holds the tab pane with the displays
    */
   Stage mWindow;

   /**
    * This will hold the single and multi dance tabs
    */
   TabPane mTabs;

   /**
    * The tab that will hold mSingleDisplay
    */
   Tab mSingleTab;

   /**
    * The tab that will hold mMultiDisplay
    */
   Tab mMultiTab;

   /**
    * The display for the single dance competition
    */
   PublicDisplay mSingleDisplay;

   /**
    * The display for the multi-dance competition
    */
   PublicDisplay mMultiDisplay;

   /**
    * Constructor for PublicWindow
    */
   public PublicWindow(PublicDisplay single, PublicDisplay multi)
   {
      //set up tabs
      mSingleTab = new Tab("Single Dance");
      mMultiTab = new Tab("Multi-Dance");

      //tabs can not close
      mSingleTab.setClosable(false);
      mMultiTab.setClosable(false);
     
      //add content to tabs
      mSingleDisplay = single;
      mSingleTab.setContent(mSingleDisplay.getDisplay());

      mMultiDisplay = multi;
      mMultiTab.setContent(mMultiDisplay.getDisplay());

      //set up tabpane
      mTabs = new TabPane();
      mTabs.getTabs().add(mSingleTab);
      mTabs.getTabs().add(mMultiTab);

      //set up window
      mWindow = new Stage();
      mWindow.setScene(new Scene(mTabs, 900, 900));
      mWindow.setTitle("Callbacks - Public Display");
      setLocation();
      mWindow.show();
   }

   /**
    * Switches which tab is displayed
    *
    * @param single if true, shows the single tab - if false, shows the multi tab
    */
   public void showTab(boolean single)
   {
      //switch tabs
      if (single)
      {
         mTabs.getSelectionModel().select(mSingleTab);
      }
      else
      {
         mTabs.getSelectionModel().select(mMultiTab);
      }
   }
   
   /**
    * just controlling where the window is going to open, so that they are both visible
    */
   public void setLocation()
   {
       Preferences prefs = Preferences.userNodeForPackage(FrontEnd.class);
 	   mWindow.setX(prefs.getDouble("locationX", 1000.0));
 	   mWindow.setY(prefs.getDouble("locationY", 20.0));
   }
}

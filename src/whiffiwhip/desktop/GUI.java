package wiffiwhip.desktop;

import wiffiwhip.service.*;

import wiffiwhip.system.*;

import javafx.application.*;

import javafx.geometry.*;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import javafx.stage.*;

/**
 * Sample GUI class.
 */
public class GUI
   extends Application
{
   /**
    * Holds the singleton instance.
    */
   private static GUI cInstance = null;

   /**
    * Gets the singleton instance.
    *
    * @return the singleton instance.
    */
   public static GUI getInstance()
   {
      return cInstance;
   }

   /**
    * Creates a new GUI object.
    */
   public GUI()
   {
      super();

      synchronized (GUI.class)
      {
         if (cInstance != null)
         {
            throw new UnsupportedOperationException(getClass() +
               " is singleton but constructor called more than once.");
         }

         cInstance = this;
      }
   }

   /**
    * Initializes the GUI.
    *
    * @param primaryStage the primary stage.
    */
   private void init(Stage primaryStage)
   {
      Group root = new Group();
      primaryStage.setScene(new Scene(root));

      VBox vbox = new VBox(10);
      Image image = ImageGetter.getImage("duckling.png");
      ImageView imageView = new ImageView(image);
      Label label = new Label("Rubbber Ducky, You're The One!", imageView);
      label.setContentDisplay(ContentDisplay.TOP);

      HBox hbox = new HBox(10);
      hbox.setAlignment(Pos.CENTER);
      Button button = new Button("Exit");
      button.setOnAction(Action.get(button.getText(), this));

      hbox.getChildren().add(button);
      vbox.getChildren().add(label);
      vbox.getChildren().add(hbox);
      root.getChildren().add(vbox);
   }

   /**
    * Starts the GUI.
    *
    * @param primaryStage the primary stage.
    */
   @Override
   public void start(Stage primaryStage)
   {
      init(primaryStage);
      primaryStage.show();
   }

   /**
    * Exits the application.
    */
   public void exit()
   {
      Action.showAll();
      Platform.exit();
      System.exit(0);
   }
}

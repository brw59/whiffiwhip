package wiffiwhip.service;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * Used to display messages to allow the user to know why the program isn't 
 * continuing.  This is meant to handle common errors like multiple couples entered into 
 * the same judge class or required. 
 * @author Jeff
 *
 */
public class Alert 
{
	private Stage warning;
	private int[] easterEgg;
	private int tester;
	private String prev;
	
	/**
	 * 1 - there are duplicate couples
	 * 2 - not all required info in info box
	 * 3 - no tables to call back from 
	 * @param number the type of error
	 */
	public Alert(int number)
	{
		tester = 1;
		prev = "";
		warning = new Stage();
		easterEgg = new int[]{0, 0, 0, 0, 0};
		warning.initModality(Modality.WINDOW_MODAL);
		Group tRoot = new Group();
		Scene tempScene = new Scene(tRoot, 340, 80);
		tempScene.setOnKeyPressed(keyListener);
		BorderPane tV = new BorderPane();
		Text message = new Text("");
		if (number == 1)
		{
			message = new Text("There are duplicate couples from the same Judge");
		}
		else if (number == 2)
		{
			message = new Text("The info Box doesn't have enough info");
		}
		else if (number == 3)
		{
			message = new Text("there are no tables to callBack from");
		}
		tV.setTop(message);
		Button ok = new Button("ok");
		ok.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				warning.close();
			}
		});
		tV.setCenter(ok);
		tV.setMargin(ok, new Insets(10));
		tempScene.setFill(Color.LIGHTGRAY);
		tRoot.getChildren().add(tV);
		warning.setScene(tempScene);
		warning.show();
	}
	
	/**
	 * has the conditions set so that the alert pop up will dance if the conditions 
	 * are right and helps them get carrried out
	 */
	private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>()
    {
		/**
		 * takes the key press and deals with it
		 * @param e the key press
		 */
    	public void handle(KeyEvent e)
    	{
    		if (e.getCode() == KeyCode.D && prev.equals("e"))
    		{
    			easterEgg[0] = 1;
    			step(tester);
    			tester++;
    		}
    		else if(e.getCode() == KeyCode.A)
    		{
    			if (prevSet(1) && prev.equals("d"))
    			{
    				easterEgg[1] = 1;
    				step(tester);
        			tester++;
    			}
    			else
    			{
    				reset();
    			}
    		}
    		else if(e.getCode() == KeyCode.N)
    		{
    			if (prevSet(2) && prev.equals("a"))
    			{
    				easterEgg[2] = 1;
    				step(tester);
        			tester++;
    			}
    			else
    			{
    				reset();
    			}
    		}
    		else if(e.getCode() == KeyCode.C)
    		{
    			if (prevSet(3) && prev.equals("n"))
    			{
    				easterEgg[3] = 1;
    				step(tester);
        			tester++;
    			}
    			else
    			{
    				reset();
    			}
    		}
    		else if(e.getCode() == KeyCode.E)
    		{
    			if (prevSet(4) && prev.equals("c"))
    			{
    				easterEgg[4] = 1;
    				step(tester);
        			tester++;
    			}
    			else
    			{
    				reset();
    			}
    		}
    		else
    		{
    			reset();
    		}
    		prev = e.getText();
    		if (tester == 5)
    		{
    			tester = 1;
    		}
    	}
    	
    	/**
    	 * resets the easterEgg if the conditions where failed
    	 */
    	public void reset()
    	{
    		for(int i : easterEgg)
			{
				i = 0;
			}
    	}
    	
    	/**
    	 * checks to make sure all the conditions in the 
    	 * easterEgg where meet before the current one
    	 * @param num which step the easter egg is checking
    	 * @return false if a previous step was not taken
    	 */
    	public boolean prevSet(int num)
    	{
    		for(int i = 0; i < num; i++)
    		{
    			if(easterEgg[i] == 0)
    			{
    				return false;
    			}
    		}
    		return true;
    	}
    };
    
	/**
	 * instructions for how the window should change based on the step its 
	 * "stepping" into
	 * @param n the step number
	 */
    public void step(int n)
	{
		if(n == 1)
		{
			warning.getScene().setFill(Color.BLUE);
			warning.setX(warning.getX() + 60.0);
		}
		else if(n == 2)
		{
			warning.getScene().setFill(Color.GREY);
			warning.setY(warning.getY() + 60.0);
		}
		else if(n == 3)
		{
			warning.getScene().setFill(Color.LIGHTGREY);
			warning.setX(warning.getX() - 60.0);
		}
		else if(n == 4)
		{
			warning.getScene().setFill(Color.LIGHTBLUE);
			warning.setY(warning.getY() - 60.0);
		}
	}
}


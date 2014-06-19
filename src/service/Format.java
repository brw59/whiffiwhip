package wiffiwhip.service;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

public class Format 
{
	private PublicWindow mWindow;
	private Stage mStage;
	
	
	public Format(PublicWindow pWindow)
	{
		mWindow = pWindow;
		mStage = new Stage();
		mStage.setTitle("Format Options");
		ComboBox test = new ComboBox();
		mStage.setScene(new Scene(test, 500, 500));
		mStage.show();
	}
}

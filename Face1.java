package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.ArcType;

//I was bored so I added the neutral face button during class

public class Face1 extends Application {
	private FacePane fp  = new FacePane();

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.setAlignment(Pos.CENTER);
		Button btnHappy = new Button("Happy");
		Button btnSadge = new Button("Sadge");
		Button btnNeutral = new Button("Neutral");
		hBox.getChildren().add(btnHappy);
		hBox.getChildren().add(btnSadge);
		hBox.getChildren().add(btnNeutral);
		
		hBox.setTranslateY(-70);
    
		btnHappy.setOnAction(new HappyHandler());
		btnSadge.setOnAction(new SadgeHandler());
		btnNeutral.setOnAction(new NeutralHandler());

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(fp);
		borderPane.setBottom(hBox);
		BorderPane.setAlignment(hBox, Pos.CENTER);
    
		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 400, 400);
		primaryStage.setTitle("Face"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
  
	class HappyHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			fp.happy();
		}
	}
	
	class NeutralHandler implements EventHandler<ActionEvent> {
	    @Override 
	    public void handle(ActionEvent e) {
	    	fp.neutral();
	    }
	}
  
	class SadgeHandler implements EventHandler<ActionEvent> {
	    @Override
	    public void handle(ActionEvent e) {
	    	fp.sadge();
	    }
	}
  
	public static void main(String[] args) {
		launch(args);
	}
}

class FacePane extends BorderPane {
	
	private Circle face = new Circle(200, 200, 52, Color.YELLOW);
	
	private Ellipse eye1 = new Ellipse(185, 185, 5, 8);
	private Ellipse eye2 = new Ellipse(215, 185, 5, 8); 
	private Arc lip = new Arc(200, 215, 25, 14, 185, 170);
	
	public FacePane() {
		eye1.setFill(Color.rgb(42, 42, 42));
		eye2.setFill(Color.rgb(42, 42, 42));
		lip.setFill(null);
		lip.setType(ArcType.OPEN);
		lip.setStrokeWidth(3);
		lip.setStroke(Color.BLACK);
		getChildren().addAll(face, eye1, eye2, lip);
	}
  
	public void happy() {
		lip.setStartAngle(185);
		lip.setCenterY(215);
		lip.setRadiusY(14);
	}
	
	public void neutral() {
        lip.setStartAngle(185); // Set lip start angle to make it flat
        lip.setCenterY(220);
        lip.setRadiusY(0);
    }
  
	public void sadge() {
		lip.setStartAngle(5);
		lip.setRadiusY(14);
		lip.setCenterY(225);
  	}
}

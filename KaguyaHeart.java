package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class KaguyaHeart extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
    	double x1 = 150, x2 = x1+90, y1 = 150;
    	
    	Circle circle1 = new Circle(x1, y1, 50, Color.CRIMSON);
    	Circle circle2 = new Circle(x2, y1, 50, Color.CRIMSON);
    	
    	Polygon quadrangle = new Polygon();
    	quadrangle.getPoints().addAll(new Double[]{
    		x1-40, y1+30,
    		(x1+x2)/2, y1,
    		x2+40, y1+30,  
    		(x1+x2)/2, y1+120
    	}); 
    	quadrangle.setFill(Color.CRIMSON);
    	
    	Text text = new Text("告");
    	text.setFont(Font.font("SimSun", 140));
        text.setFill(Color.WHITE);
        text.setX((x1 + x2) / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setY(y1 +95);
    	
    	BorderPane root = new BorderPane();
    	root.getChildren().addAll(circle1, circle2, quadrangle, text);
        Scene scene = new Scene(root, 400, 400, Color.BLACK);
        
        primaryStage.setTitle("Kaguya-sama: Love Is War");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

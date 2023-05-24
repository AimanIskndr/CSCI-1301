package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Face2 extends Application {
    private FacePane2 fp = new FacePane2();

    @Override
    public void start(Stage primaryStage) {
    	
        ScrollBar lipSB = new ScrollBar();
        lipSB.setMin(-20);
        lipSB.setMax(20);
        lipSB.setMinWidth(140);
        lipSB.setMinHeight(20);

        lipSB.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            fp.setLipControlY(value);
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(lipSB);
        hBox.setTranslateY(-70);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(fp);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setTitle("Face");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class FacePane2 extends BorderPane {
	
    private Circle face = new Circle(200, 200, 52, Color.YELLOW);
    private Ellipse eye1 = new Ellipse(185, 185, 5, 8);
    private Ellipse eye2 = new Ellipse(215, 185, 5, 8);
    private QuadCurveTo lip = new QuadCurveTo(200, 225, 220, 225);

    public FacePane2() {
        eye1.setFill(Color.rgb(42, 42, 42));
        eye2.setFill(Color.rgb(42, 42, 42));

        Path lipPath = new Path();
        lipPath.setStrokeWidth(3);
        lipPath.getElements().addAll(new MoveTo(180, 225), lip);

        getChildren().addAll(face, eye1, eye2, lipPath);
    }

    public void setLipControlY(double value) {
        lip.setControlY(225 + value);
    }
}

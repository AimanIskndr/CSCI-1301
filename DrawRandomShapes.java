package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DrawRandomShapes extends Application {

    static final int HEIGHT = 600;
    static final int WIDTH = 800;
    static final int MIN_RADIUS = 25;
    static final int MAX_RADIUS = 70;
    static final int MAX_LEN = 100;
    static final int MIN_LEN = 40;
    
    Pane root = new Pane();
    
    static ArrayList<Shape> shapeList = new ArrayList<>();
    Random rand = new Random();
    
    String[] fontNames = {
            "Arial",
            "Times New Roman",
            "Helvetica",
            "Courier New",
            "Verdana",
            "Consolas",
            "Impact",
            "Courier",
            "Roboto",
            "Lexend"
        };
    
    class Coordinate {
        double x;
        double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    
    ArrayList<Coordinate> points = new ArrayList<>();

    public static boolean hasIntersection() {
        int n = shapeList.size();
        
        for (int i = 0; i < n - 1; i++) {
            Shape shape1 = shapeList.get(i);
            
            for (int j = i + 1; j < n; j++) {
                Shape shape2 = shapeList.get(j);
                
                if (shape1.intersects(shape2.getBoundsInLocal())) {
                    return true; 
                }
            }
        }
        
        return false; 
    }
    
    public void generateShapes(int n) {
    	
    	for(int i = 0; i < n; i++){
    		
    		int type = rand.nextInt(3);
    		
    		if(type == 0) {
    			createCircle();
    		}
    		
    		else if(type == 1){
    			createRectangle();
    		}
    		
    		else if(type == 2){
    			createTriangle();
    		}
    		
    		if (hasIntersection()) {
                shapeList.remove(i);
                i--;
            }
    		
    		else {
    			root.getChildren().add(shapeList.get(i));
    		}
    	}
    }
    
    public void createCircle() {
    	Circle circle = new Circle(MIN_RADIUS + rand.nextInt(MAX_RADIUS - MIN_RADIUS));
    	circle.setCenterX(MAX_RADIUS + rand.nextInt(WIDTH - 2 * MAX_RADIUS));
    	circle.setCenterY(MAX_RADIUS + rand.nextInt(HEIGHT - 2 * MAX_RADIUS));
    	
    	circle.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
    	
    	shapeList.add(circle);
    }
    
    public void createRectangle() {
    	Rectangle rectangle = new Rectangle(MIN_LEN + rand.nextInt(MAX_LEN - MIN_LEN), MIN_LEN  + rand.nextInt(MAX_LEN - MIN_LEN));
    	rectangle.setX(rand.nextInt(WIDTH - MAX_LEN));
    	rectangle.setY(rand.nextInt(HEIGHT - MAX_LEN));
    	rectangle.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
    	
    	shapeList.add(rectangle);
    }
    
    public void createTriangle() {
        double x = rand.nextInt(WIDTH - MAX_LEN);
        double y = MAX_LEN + rand.nextInt(HEIGHT - MAX_LEN);
        double b = MIN_LEN + rand.nextInt(MAX_LEN - MIN_LEN);
        double h = MIN_LEN + rand.nextInt(MAX_LEN - MIN_LEN);

        Polygon triangle = new Polygon();
        triangle.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        triangle.getPoints().addAll(
				x, y,
                x + b, y,
                x + b / 2, y - h
			);
        shapeList.add(triangle);
    }
    
    public void getCoordinates() {
    	
    	for(Shape shape: shapeList) {
    		
            if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                points.add(new Coordinate(circle.getCenterX(), circle.getCenterY()));
            } 
            
            else if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                points.add(new Coordinate(getRectCenterX(rect), getRectCenterY(rect)));
            }
            
            else {
            	Polygon triangle = (Polygon) shape;
                points.add(new Coordinate(getTriCenterX(triangle), getTriCenterY(triangle)));
            }
    	}
    }
    
    public double calculateDistance(Coordinate point1, Coordinate point2) {
    	
        double xDiff = point2.x - point1.x;
        double yDiff = point2.y - point1.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    
    
    public void drawLine(Coordinate point1, Coordinate point2) {
    	
        Line line = new Line(point1.x, point1.y, point2.x, point2.y);

        double length = calculateDistance(point1, point2);

        Text distance = new Text(String.format("%.2f", length));
        distance.setX((point1.x + point2.x) / 2);
        distance.setY((point1.y + point2.y) / 2);
        
        root.getChildren().addAll(line, distance);
    }
    
    public void findClosestPoints() {
    	
        ArrayList<Coordinate> remainingPoints = new ArrayList<>(points);

        Coordinate currentPoint = remainingPoints.get(0);
        remainingPoints.remove(0);

        while (!remainingPoints.isEmpty()) {
        	
            Coordinate closestPoint = null;
            double shortestDistance = Double.MAX_VALUE;

            for (Coordinate otherPoint : remainingPoints) {
            	double distance = calculateDistance(currentPoint, otherPoint);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    closestPoint = otherPoint;
                }
            }

            if (closestPoint != null) {
                drawLine(currentPoint, closestPoint);
                currentPoint = closestPoint;
                int currentIndex = remainingPoints.indexOf(currentPoint);
                remainingPoints.remove(currentIndex);
            }
        }
    }
    
    public void addText(int n) {
    	
    	Shape shape;
    	double x = 0, y  = 0;
    	
    	for(int i = 0; i < n; i++) {
    		shape = shapeList.get(i);
    		
    		if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                x = circle.getCenterX();
                y = circle.getCenterY();
            } 
            
            else if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                x = getRectCenterX(rect);
                y = getRectCenterY(rect);
            }
    		
            else if (shape instanceof Polygon) {
            	Polygon triangle = (Polygon) shape;
                x = getTriCenterX(triangle);
                y = getTriCenterY(triangle);
            }
    		
    		Text txt = new Text(x + 10, y - 10, "Shape");
    		txt.setFont(Font.font(fontNames[rand.nextInt(10)]));
    		root.getChildren().add(txt);
    	}
    }
    
    public double getRectCenterX(Rectangle rect) {
    	return rect.getX() + (rect.getWidth() / 2);
    }
    
    public double getRectCenterY(Rectangle rect) {
    	return rect.getY() + (rect.getHeight() / 2);
    }
    
    public double getTriCenterX(Polygon tri) {
    	ObservableList<Double> val = tri.getPoints();
    	return val.get(4);
    }
    
    public double getTriCenterY(Polygon tri) {
    	ObservableList<Double> val = tri.getPoints();
    	return (val.get(1) + val.get(5)) / 2;
    }

    @Override
    public void start(Stage primaryStage) {

        Label numOfShapeLabel = new Label("Number of Shapes:");
        TextField numOfShapeTextField = new TextField();
        numOfShapeTextField.setPrefWidth(30);

        HBox numOfShapeBox = new HBox(10);
        numOfShapeBox.getChildren().addAll(numOfShapeLabel, numOfShapeTextField);
        numOfShapeBox.setLayoutX(WIDTH / 2 - numOfShapeBox.getLayoutBounds().getWidth() / 2);
        numOfShapeBox.setLayoutY(520);

        Button generateButton = new Button("Generate");
        generateButton.setLayoutX(WIDTH / 2 - generateButton.getLayoutBounds().getWidth() / 2);
        generateButton.setLayoutY(550);
        
        Text warningText = new Text("");
        warningText.setFont(Font.font("Arial", FontWeight.LIGHT, 8));
        
        generateButton.setOnAction(event -> {
        	
        	int x = Integer.parseInt(numOfShapeTextField.getText());
        	
        	if(x > 1 && x < 11) {
        		generateShapes(x);
        		getCoordinates();
        		findClosestPoints();
            	addText(x);
            	
            	numOfShapeBox.setVisible(false);
            	generateButton.setVisible(false);
        	}
        	
        	else {
        		
        		numOfShapeBox.getChildren().remove(warningText);
        		
        		numOfShapeTextField.setStyle("-fx-border-color: red;");
        		
        		if(x <= 1) {
        			warningText.setText("*Number of shapes must be greater than 1");
        		}
        		
        		else if(x > 10) {
        			warningText.setText("*Maximum is number of shapes is 10");
        		}
        		
        		numOfShapeBox.getChildren().add(warningText);
        	}
        	
        });

        root.getChildren().addAll(generateButton, numOfShapeBox);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Assignment"); 
        primaryStage.setResizable(false);
        primaryStage.show(); 
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}

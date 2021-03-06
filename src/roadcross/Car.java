// This entire file is part of my masterpiece.
// Karen Li (kjl32)

package roadcross;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Car extends Parent {

    public static final int TYPE_AUTO1 = 0;
    public static final int TYPE_AUTO2 = 1;
    public static final int TYPE_RICKSHAW = 2;
    public static final int TYPE_MC1 = 3;
    public static final int TYPE_MC2 = 4;
    public static final int TYPE_CAR1 = 5;

    private int type;
    private ImageView content;
    private int carDirection;
    
    private double imageWidth;
    private double imageLength;

    public Car(int type) {
        content = new ImageView();
        getChildren().add(content);
        changeType(type);
        setMouseTransparent(true);
    }

    public double getImageWidth(){
    	return imageWidth;
    }
    
    public double getImageLength(){
    	return imageLength;
    }

    
    public int getType() {
        return type;
    }
    
    public int getCarDirection(){
    	return carDirection;
    }

    private void changeType(int newType) {
        this.type = newType;
        Image image = Config.getCarImages().get(type);
        content.setImage(image);
        content.setFitWidth(Config.CAR_WIDTH); 
        content.setPreserveRatio(true);
        
        imageWidth = content.getFitWidth();
        imageLength = content.getFitHeight();
    }

    

    public static int getCarType(String s) {  //code for each level
        if (s.equals("A")) {
            return TYPE_AUTO1;
        } else if (s.equals("U")) {
            return TYPE_AUTO2;
        } else if (s.equals("R")) {
            return TYPE_RICKSHAW;
        } else if (s.equals("M")) {
            return TYPE_MC1;
        } else if (s.equals("O")) {
            return TYPE_MC2;
        } else if (s.equals("C")) {
            return TYPE_CAR1;
        } 
        return 0;
    }
    
    
    public void getSpeed(int carTypeSymb){  //returns a car speed
    	if (carTypeSymb == TYPE_AUTO1) {
    		this.carDirection = 5;
    	} else if (carTypeSymb == TYPE_AUTO2) {
    		this.carDirection = 5;
    	} else if (carTypeSymb == TYPE_RICKSHAW) {
    		this.carDirection = 2;
    	} else if (carTypeSymb == TYPE_MC1) {
    		this.carDirection = 7;
    	} else if (carTypeSymb == TYPE_MC2) {
    		this.carDirection = 7;
    	} else if (carTypeSymb == TYPE_CAR1) {
    		this.carDirection = 3;
    	} 
    }
    
    public int getSpaceBwCars(int carTypeSymb, int numCarsPerLane) {
    	//numCarsPerLane used to space out cars evenly in each lane
		return Config.SCREEN_HEIGHT/numCarsPerLane;
	}
    
    public void moveCar(double newY) { //moves cars
        double y = newY;
        this.setTranslateY(y);
    }

    
    public void setUpCars(ArrayList<ArrayList<Car>> cars) {
		for(int i=0; i<cars.size(); i++){
        	ArrayList<Car> eachListCars = cars.get(i);
        	for(int j=0; j<eachListCars.size(); j++){
        		Car c = eachListCars.get(j);
        		if (c != null){
        			if (carDirection != 0) {
        				if(i%2 == 0){  //depending on even or odd lane
        					c.moveCar(c.getTranslateY() + carDirection);
        					if (c.getTranslateY() >= Config.SCREEN_HEIGHT){    //if car reaches the bottom of the screen
        						c.moveCar(0 - imageLength);
        					}
        				}else{
        					c.moveCar(c.getTranslateY() - carDirection);
        					if (c.getTranslateY() <= 0){   //if car reaches the top of the screen
        						c.moveCar(Config.SCREEN_HEIGHT - imageLength);
        					}
        				}
        			}
        		}
        	}
        }
	}

}

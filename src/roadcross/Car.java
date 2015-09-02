package roadcross;

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


}

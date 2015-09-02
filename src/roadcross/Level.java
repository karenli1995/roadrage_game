/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package roadcross;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import roadcross.Main.MainFrame;

public class Level extends Parent {
    private static final MainFrame mainFrame = Main.getMainFrame();

    private ArrayList<ArrayList<Car>> cars;
    private Group group;
    ArrayList<ImageView> lives;

    private Car car;
    private Pedestrian ped;
    private int pedDirectionX;
    private int pedDirectionY;
    
    private int levelNumber;
    private ImageView message;
    private Timeline startingTimeline;
    private Timeline timeline;

    public Level(int levelNumber) {
        group = new Group();
        getChildren().add(group);
        initContent(levelNumber); //levelnumber will be 1 first
    }

    private void initStartingTimeline() {  //READY
    	startingTimeline = new Timeline();
    	KeyFrame kf1 = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent event) {
    			message.setImage(Config.getImages().get(Config.IMAGE_WELCOME));
            	message.setFitWidth(900);
            	message.setPreserveRatio(true);
            	message.setTranslateX((Config.SCREEN_WIDTH - message.getImage().getWidth()) / 2);
                message.setTranslateY((Config.SCREEN_HEIGHT - message.getImage().getHeight()) / 2);
            	message.setVisible(true);
    		}
    	}, new KeyValue(message.opacityProperty(), 0));
      KeyFrame kf2 = new KeyFrame(Duration.millis(1500), new KeyValue(message.opacityProperty(), 1));
      KeyFrame kf3 = new KeyFrame(Duration.millis(3000), new KeyValue(message.opacityProperty(), 1));
      KeyFrame kf4 = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
              message.setVisible(false);  //disappears to show game
              ped.setVisible(true);
          }
      }, new KeyValue(message.opacityProperty(), 0));

      startingTimeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4);
    }

    private void initTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	
                // Move pedestrian if needed
                if (pedDirectionX != 0 ) {
                    movePedX(ped.getTranslateX() + pedDirectionX);
                }
                if (pedDirectionY != 0 ) {
                    movePedY(ped.getTranslateY() + pedDirectionY);
                }
                
                //keep pedestrian in bounds
                if (ped.getTranslateX() < 0) {
                    ped.setTranslateX(0);
                }
                if (ped.getTranslateY() < 0) {
                    ped.setTranslateY(0);
                }
                if (ped.getTranslateY() + ped.getImageLength() > Config.SCREEN_HEIGHT) {
                	ped.setTranslateY(Config.SCREEN_HEIGHT - ped.getImageLength());
                }
                
                
                // Move cars
                for(int i=0; i<cars.size(); i++){
                	ArrayList<Car> eachListCars = cars.get(i);
                	for(int j=0; j<eachListCars.size(); j++){
                		Car c = eachListCars.get(j);
                		if (c != null){
                			if (c.getCarDirection() != 0) {
                				if(i%2 == 0){  //depending on even or odd lane
                					c.moveCar(c.getTranslateY() + c.getCarDirection());
                					if (c.getTranslateY() >= Config.SCREEN_HEIGHT){    //if car reaches the bottom of the screen
                						c.moveCar(0 - c.getImageLength());
                					}
                				}else{
                					c.moveCar(c.getTranslateY() - c.getCarDirection());
                					if (c.getTranslateY() <= 0){   //if car reaches the top of the screen
                						c.moveCar(Config.SCREEN_HEIGHT - c.getImageLength());
                					}
                				}
                			}
                		}
                	}
                }
                
                
                
                //check for collision between pedestrian and cars   
                for(int i=0; i<cars.size(); i++){
                	ArrayList<Car> eachListCars = cars.get(i);
                	for(int j=0; j<eachListCars.size(); j++){
                		Car myCar = eachListCars.get(j);
                		if (ped.getBoundsInParent().intersects(myCar.getBoundsInParent())){
                			ped.setTranslateX(0);
                			ped.setTranslateY(Config.SCREEN_HEIGHT/2);
                			lostLife();
                		}
                	}
                }
                
                //if level passed
                if(ped.getTranslateX() + ped.getImageWidth() >= Config.SCREEN_WIDTH){
                	levelNumber += 1;
                	initContent(levelNumber);
                }
                
            }
        });
        timeline.getKeyFrames().add(kf);
    }

    public void start() {
        startingTimeline.play();
        timeline.play();
        group.getChildren().get(0).requestFocus();
    }

    public void stop() {
        startingTimeline.stop();
        timeline.stop();
    }

    private void initLevel() {
    	if(levelNumber > 1){
    		ped.setVisible(true);
    	}
    	String[] level = LevelData.getLevelData(levelNumber); //{"RRRR","RRRR","RAR",...}
    	//System.out.println(Arrays.toString(level));
    	for (int row=0; row<level.length; row++){
    		String type = level[row];
    		//System.out.println("hi " + type);
    		ArrayList<Car> eachRowCars = new ArrayList<Car>(); //keeps track of each row of cars
    		if (type != null) {   //to get to the last level possible
    			for (int col = 0; col < type.length(); col++){
    				int carTypeSymb = Car.getCarType(Character.toString(type.charAt(col)));
    				car = new Car(carTypeSymb);
    				car.getSpeed(carTypeSymb);  //this is where I set the car's speed for each separate car object

    				if (row == 0){
    					car.setTranslateX(Config.SCREEN_WIDTH/9);
    					car.setTranslateY(col*car.getSpaceBwCars(carTypeSymb, type.length())); 
    				}

    				if (row == 1){
    					car.setTranslateX(Config.SCREEN_WIDTH/5);
    					car.setTranslateY(Config.SCREEN_HEIGHT - col*car.getSpaceBwCars(carTypeSymb, type.length()));
    				}

    				if (row == 2){
    					car.setTranslateX(Config.SCREEN_WIDTH/3);
    					car.setTranslateY(col*car.getSpaceBwCars(carTypeSymb, type.length())); 
    				}

    				if (row == 3){
    					car.setTranslateX(Config.SCREEN_WIDTH/2.5);
    					car.setTranslateY(Config.SCREEN_HEIGHT - col*car.getSpaceBwCars(carTypeSymb, type.length()));
    				}
    				if (row == 4){
    					car.setTranslateX(Config.SCREEN_WIDTH/1.75);
    					car.setTranslateY(col*car.getSpaceBwCars(carTypeSymb, type.length())); 
    				}

    				if (row == 5){
    					car.setTranslateX(Config.SCREEN_WIDTH/1.55);
    					car.setTranslateY(Config.SCREEN_HEIGHT - col*car.getSpaceBwCars(carTypeSymb, type.length()));
    				}
    				if (row == 6){
    					car.setTranslateX(Config.SCREEN_WIDTH/1.4);
    					car.setTranslateY(col*car.getSpaceBwCars(carTypeSymb, type.length())); 
    				}

    				if (row == 7){
    					car.setTranslateX(Config.SCREEN_WIDTH/1.27);
    					car.setTranslateY(Config.SCREEN_HEIGHT - col*car.getSpaceBwCars(carTypeSymb, type.length()));
    				}
    				if (row == 8){
    					car.setTranslateX(Config.SCREEN_WIDTH/1.17);
    					car.setTranslateY(col*car.getSpaceBwCars(carTypeSymb, type.length())); 
    				}

    				eachRowCars.add(car);
    			}
    			cars.add(eachRowCars);
    		} else { //win game
    			//close game
    			Platform.exit();
    		}
    	}
    }

    private void movePedX(double newX) {
        double x = newX;
        ped.setTranslateX(x);
    }
    
    private void movePedY(double newY) {
        double y = newY;
        ped.setTranslateY(y);
    }

 
    private void lostLife() {
        mainFrame.decreaseLives();  //decreases COUNT
        if (mainFrame.getLifeCount() < 1) {
        	deleteLifeImage(); //delete the heart image

            message.setImage(Config.getImages().get(Config.IMAGE_GAMEOVER));
            message.setFitWidth(900);
            message.setPreserveRatio(true);
        	message.setTranslateX((Config.SCREEN_WIDTH - message.getImage().getWidth()) / 2 - 200);
            message.setTranslateY((Config.SCREEN_HEIGHT - message.getImage().getHeight()) / 2 - 200);
            message.setVisible(true);
            message.setOpacity(1);
            
            ped.setVisible(false);
            group.getChildren().remove(ped); //check
            for(int i=0; i<cars.size(); i++){
            	ArrayList<Car> myCars = cars.get(i);
            	for(int j=0; j<myCars.size(); j++){
            		myCars.get(j).setVisible(false);
                    group.getChildren().remove(myCars.get(j)); //check
            	}
            }
            
            for(int j=0; j<lives.size(); j++){  //reset lives
                group.getChildren().remove(lives.get(j));
            }
            lives.clear();
            
        } else {
        	deleteLifeImage(); //delete the heart image
        }
    }
    
    private void deleteLifeImage() {
    	ImageView currLife = lives.get(lives.size()-1);
    	currLife.setVisible(false);
    	lives.remove(lives.size()-1);
    }

    private void initContent(int level) {
//        state = STARTING_LEVEL;
        pedDirectionX = 0;
        pedDirectionY = 0;
        levelNumber = level;
        lives = new ArrayList<ImageView>();

        cars = new ArrayList<ArrayList<Car>>();
        
        ped = new Pedestrian();
        ped.setTranslateX(5);
        ped.setTranslateY(Config.SCREEN_HEIGHT/2);
        ped.setVisible(false);
        
        message = new ImageView();
        
        initLevel(); //organizes the cars according to level
        initStartingTimeline(); //welcomes you to the game
        initTimeline(); //starts the game

        ImageView background = new ImageView();  //set background image
        background.setFocusTraversable(true);
        background.setImage(Config.getImages().get(Config.IMAGE_BACKGROUND));
        //background.setOpacity(0.7);

        Rectangle2D viewportRect = new Rectangle2D(100, 0, 730, 810);   //zoom into the background
        background.setViewport(viewportRect);
        
        background.setFitWidth(Config.SCREEN_WIDTH);
        background.setFitHeight(Config.SCREEN_HEIGHT);
        
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {

                if ((ke.getCode() == KeyCode.LEFT || ke.getCode() == KeyCode.TRACK_PREV)) {
                    pedDirectionX = - Config.PED_SPEED;
                }
                if ((ke.getCode() == KeyCode.RIGHT || ke.getCode() == KeyCode.TRACK_NEXT)) {
                    pedDirectionX = Config.PED_SPEED;
                }
                if ((ke.getCode() == KeyCode.UP)) {
                    pedDirectionY = - Config.PED_SPEED;
                }
                if ((ke.getCode() == KeyCode.DOWN)) {
                    pedDirectionY = Config.PED_SPEED;
                }
                
                if ((ke.getCode() == KeyCode.ESCAPE)) {
                	Platform.exit();
                }
                
                //cheats
                if ((ke.getCode() == KeyCode.A)) {  //pass level
                	levelNumber ++;
                	initContent(levelNumber);
                }
                if ((ke.getCode() == KeyCode.B)) {  //game over
                	message.setImage(Config.getImages().get(Config.IMAGE_GAMEOVER));
                	message.setTranslateX((Config.SCREEN_WIDTH - message.getImage().getWidth()) / 2 - 200);
                    message.setTranslateY((Config.SCREEN_HEIGHT - message.getImage().getHeight()) / 2 - 200);
                    message.setVisible(true);
                    message.setOpacity(1);
                    
                    ped.setVisible(false);
                    group.getChildren().remove(ped); //check
                }
                
            }
        });
        background.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.LEFT || ke.getCode() == KeyCode.RIGHT ||
                    ke.getCode() == KeyCode.TRACK_PREV || ke.getCode() == KeyCode.TRACK_NEXT) {
                    pedDirectionX = 0;
                }
                if (ke.getCode() == KeyCode.UP || ke.getCode() == KeyCode.DOWN){
                	pedDirectionY = 0;
                }
            }
        });
        group.getChildren().add(background);
        
        for(int i=0; i<cars.size(); i++){
        	ArrayList<Car> eachListCars = cars.get(i);
        	for(int j=0; j<eachListCars.size(); j++){
        		Car c = eachListCars.get(j);
        		if (c != null){
        			group.getChildren().add(c);
        		}
        	}
        }
        
        //images of the 3 lives
        for(int i=0; i<mainFrame.getLifeCount(); i++){
        	ImageView life = new ImageView();
        	life.setImage(Config.getImages().get(Config.IMAGE_LIFE));
        	life.setFitWidth(70);
        	life.setPreserveRatio(true);
        	life.setTranslateX(Config.SCREEN_WIDTH - ((i+1)*life.getFitWidth() + 10) );
        	life.setTranslateY(10);
        	lives.add(life);
        }
        

        for(int j=0; j<lives.size(); j++){ //add lives to group
        	lives.get(j).setVisible(true);
        	group.getChildren().add(lives.get(j));
        }
        

        group.getChildren().addAll(ped, message);
    }

}
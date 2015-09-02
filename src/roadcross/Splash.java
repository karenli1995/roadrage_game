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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Splash extends Parent {
    
    private ImageView background; 
    private Timeline timeline;
    private ImageView pressanykey;
    private ImageView title;
    private ImageView[] NODES;

    private void initTimeline() {  //press any key fades in
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (pressanykey.getOpacity() < 1) {
                    pressanykey.setOpacity(pressanykey.getOpacity() + 0.15f);
                }
            }
        });
        timeline.getKeyFrames().add(kf);
    }
    
    public void start() {
        background.requestFocus();
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    Splash() {
        initTimeline();
        background = new ImageView();   //sets the background image
        background.setFocusTraversable(true);
        background.setImage(Config.getImages().get(Config.IMAGE_SPLASH_BEGINGAMEBG));
        background.setFitWidth(Config.SCREEN_WIDTH);
        background.setPreserveRatio(true);
        background.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                Main.getMainFrame().startGame();
            }
        });
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {  //start game after pressing any key
            public void handle(KeyEvent ke) {
                Main.getMainFrame().startGame();
            }
        });
        
        title = new ImageView();  //display title image
        title.setImage(Config.getImages().get(Config.IMAGE_SPLASH_TITLE));
        title.setFitHeight(200);
        title.setPreserveRatio(true);
        title.setTranslateX((Config.SCREEN_WIDTH - title.getImage().getWidth()) / 2);
        title.setTranslateY((Config.SCREEN_HEIGHT - title.getImage().getHeight()) / 2  -  200);
        
        pressanykey = new ImageView(); //display pressanykey image
        pressanykey.setImage(Config.getImages().get(Config.IMAGE_SPLASH_PRESSANYKEY));
        pressanykey.setFitWidth(1000);
        pressanykey.setPreserveRatio(true);
        pressanykey.setTranslateX((Config.SCREEN_WIDTH - pressanykey.getImage().getWidth()) / 2 + 100);
        pressanykey.setTranslateY((Config.SCREEN_HEIGHT - pressanykey.getImage().getHeight()) / 2);
        pressanykey.setOpacity(0);

        NODES = new ImageView[] {title, pressanykey};
        
        Group group = new Group();   //add all to group
        group.getChildren().add(background);
        group.getChildren().addAll(NODES);
        getChildren().add(group);
    }

}


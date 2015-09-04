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

import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public final class Config {

    public static final String IMAGE_DIR = "images/desktop/";

    public static final int WINDOW_BORDER = 3; // on desktop platform
    public static final int TITLE_BAR_HEIGHT = 19; // on desktop platform
    public static final int SCREEN_WIDTH = 2200;
    public static final int SCREEN_HEIGHT = 1555;

    // Game field info
    public static final int PED_SPEED = 8;
    public static final int CAR_WIDTH = 70;
  
    private static final String[] CAR_IMAGES = new String[] {
    	//these are all my images that I took on my trip to India
    	"auto1.jpg",
    	"auto2.png",
    	"rickshaw.jpg",
    	"mc1.png",
    	"mc2.png",
    	"car1.png",
    };

    private static ObservableList<Image> carImages = javafx.collections.FXCollections.<Image>observableArrayList();

    public static ObservableList<Image> getCarImages() {
        return carImages;
    }

    public static final int IMAGE_WELCOME = 0;
    public static final int IMAGE_BACKGROUND = 1;
    public static final int IMAGE_PED = 2;
    public static final int IMAGE_LIFE = 3;
    public static final int IMAGE_GAMEOVER = 4;
    public static final int IMAGE_YOUWON = 5;
    public static final int IMAGE_SPLASH_PRESSANYKEY = 6;
    public static final int IMAGE_SPLASH_BEGINGAMEBG = 7;
    public static final int IMAGE_SPLASH_TITLE = 8;


    private static final String[] IMAGES_NAMES = new String[] {
    	//images are mine unless otherwise specified
    	"welcome.png",
        "roads.png",  //image from Google
        "waterbuffalo.png",
        "heart.png", //image from Google
        "gameover.png",
        "youwon.png",
        "splash/pressanykey.png",
        "splash/roadrage.jpg",  //image from Google
        "splash/title.png",
    };

    private static ObservableList<Image> images = javafx.collections.FXCollections.<Image>observableArrayList();

    public static ObservableList<Image> getImages() {
        return images;
    }

    public static void initialize() {
    	//initialize the game
        for (String imageName : IMAGES_NAMES) {
            Image image = new Image(Config.class.getResourceAsStream(IMAGE_DIR+imageName));
            if (image.isError()) {
                System.out.println("Image "+imageName+" not found");
            }
            images.add(image);
        }
        for (String imageName : CAR_IMAGES) {
            final String url = IMAGE_DIR+"cars/"+imageName;
            Image image = new Image(Config.class.getResourceAsStream(url));
            if (image.isError()) {
                System.out.println("Image "+url+" not found");
            }
            carImages.add(image);
        }
    }

}


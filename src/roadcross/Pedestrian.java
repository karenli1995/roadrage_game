// This entire file is part of my masterpiece.
// Karen Li (kjl32)

package roadcross;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;

public class Pedestrian extends Parent{

	private ImageView imageView;
	private double imageLength;
	private double imageWidth;
	
	private int pedDirX;
	private int pedDirY;

	/*
	 * This class sets up the ImageView for the Water Buffalo pedestrian that is crossing the roads.
	 */

	public Pedestrian() {
		imageView = new ImageView();
		imageView.setImage(Config.getImages().get(Config.IMAGE_PED));
		imageView.setFitHeight(100);
		imageView.setPreserveRatio(true);
		getChildren().add(imageView);
		setMouseTransparent(true);
		
		imageLength = imageView.getFitHeight();
		imageWidth = imageView.getFitWidth();
	}
	
	public double getImageWidth(){
		return imageWidth;
	}
	
	public int getPedDirX(){
		return pedDirX;
	}
	
	public int getPedDirY(){
		return pedDirY;
	}

	
	public void setPedDirX(int newPedDirX){
		System.out.println("hi");
		pedDirX = newPedDirX;
	}
	
	public void setPedDirY(int newPedDirY){
		pedDirY = newPedDirY;
	}
	
	public void pedInBounds() {
		if (this.getTranslateX() < 0) {
			this.setTranslateX(0);
        }
        if (this.getTranslateY() < 0) {
        	this.setTranslateY(0);
        }
        if (this.getTranslateY() + imageLength > Config.SCREEN_HEIGHT) {
        	this.setTranslateY(Config.SCREEN_HEIGHT - imageLength);
        }
	}
}

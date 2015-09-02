package roadcross;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;

public class Pedestrian extends Parent{

	private ImageView imageView;

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
	}

	public double getImageLength() {
		return imageView.getFitHeight();
	}

	public double getImageWidth() {
		return imageView.getFitWidth();
	}

}

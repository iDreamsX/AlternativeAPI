package fr.trxyy.alternative.alternative_api_ui.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class LauncherImage extends ImageView {
	
	public LauncherImage(Pane root) {
		root.getChildren().add(this);
	}
	
	public LauncherImage(Pane root, Image image) {
		super(image);
		root.getChildren().add(this);
	}

	public void setSize(int width_, int height_) {
		this.setFitWidth(width_);
		this.setFitHeight(height_);
	}

	public void setPosition(int posX, int posY) {
		this.setLayoutX(posX);
		this.setLayoutY(posY);
	}

}

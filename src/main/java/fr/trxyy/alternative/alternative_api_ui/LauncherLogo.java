package fr.trxyy.alternative.alternative_api_ui;

import java.awt.MouseInfo;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class LauncherLogo {

	public static ImageView logo = new ImageView();
	public int posX = 0;
	public int posY = 0;
	public static double opacity = 1.0;

	public LauncherLogo(Image img, int sizeX, int sizeY, Pane root) {
		logo.setImage(img);
		logo.setFitWidth(sizeX);
		logo.setFitHeight(sizeY);
		logo.setOpacity(opacity);
		root.getChildren().add(logo);
		animateLogo();
	}

	public LauncherLogo(Image img, int sizeX, int sizeY, int posX, int posY, Pane root) {
		logo.setImage(img);
		logo.setFitWidth(sizeX);
		logo.setFitHeight(sizeY);
		logo.setOpacity(opacity);
		logo.setLayoutX(posX);
		logo.setLayoutY(posY);
		root.getChildren().add(logo);
	}

	public LauncherLogo(Image img, int sizeX, int sizeY, double opacit, Pane root) {
		logo.setImage(img);
		logo.setFitWidth(sizeX);
		logo.setFitHeight(sizeY);
		logo.setOpacity(opacit);
		root.getChildren().add(logo);
	}

	private void animateLogo() {
		AnimationTimer animate = new AnimationTimer() {
			@Override
			public void handle(long now) {
				float multplicatorSize = 54.25f;
				int mouseX = MouseInfo.getPointerInfo().getLocation().x;
				int mouseY = MouseInfo.getPointerInfo().getLocation().y;
				logo.setLayoutX(posX - (mouseX / multplicatorSize));
				logo.setLayoutY(posY - (mouseY / multplicatorSize));
			}
		};
		animate.start();
	}

	public void setImagePos(int x, int y) {
		posX = x;
		posY = y;
	}

	public ImageView getLogo() {
		return logo;
	}

	public static void setOpacity(double opaci) {
		opacity = opaci;
	}

}

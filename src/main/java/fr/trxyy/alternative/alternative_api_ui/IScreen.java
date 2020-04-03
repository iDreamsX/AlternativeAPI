package fr.trxyy.alternative.alternative_api_ui;

import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.ResourceLocation;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class IScreen {
	private ResourceLocation RESOURCE_LOCATION = new ResourceLocation();
	private ImageView logoImage;
	private int posX = 0;
	private int posY = 0;

	public void drawLogo(GameEngine engine, Image img, int posX_, int posY_, int sizeX, int sizeY, Pane root, Mover animate) {
		this.logoImage = new ImageView();
		this.logoImage.setImage(img);
		this.logoImage.setFitWidth(sizeX);
		this.logoImage.setFitHeight(sizeY);
	    this.posX = posX_;
	    this.posY = posY_;
	    this.logoImage.setLayoutX(this.posX);
	    this.logoImage.setLayoutY(this.posY);
		root.getChildren().add(this.logoImage);
		if (animate.equals(Mover.MOVE)) {
			animateLogo();
		}
	}
	
	private void animateLogo() {
		AnimationTimer animate = new AnimationTimer() {
			public void handle(long now) {
				float multplicatorSize = 54.25F;
				int mouseX = (MouseInfo.getPointerInfo().getLocation()).x;
				int mouseY = (MouseInfo.getPointerInfo().getLocation()).y;
				logoImage.setLayoutX((posX - mouseX / multplicatorSize));
				logoImage.setLayoutY((posY - mouseY / multplicatorSize));
			}
		};
		animate.start();
	}

	public void drawBackgroundImage(GameEngine engine, Pane root, String img) {
		ImageView backgroundImage = new ImageView();
		backgroundImage.setImage(getResourceLocation().loadImage(engine, img));
		backgroundImage.setFitWidth(engine.getLauncherPreferences().getWidth());
		backgroundImage.setFitHeight(engine.getLauncherPreferences().getHeight());
		backgroundImage.setLayoutX(0);
		backgroundImage.setLayoutY(0);
		root.getChildren().add(backgroundImage);
	}

	public void drawAnimatedBackground(GameEngine engine, Pane root, String media) {
		MediaPlayer player = new MediaPlayer(getResourceLocation().getMedia(engine, media));
		MediaView viewer = new MediaView(player);
		viewer.setFitWidth(engine.getLauncherPreferences().getWidth());
		viewer.setFitHeight(engine.getLauncherPreferences().getHeight());
		player.setAutoPlay(true);
		viewer.setPreserveRatio(false);
		player.setCycleCount(-1);
		player.play();
		root.getChildren().add(viewer);
	}

	public Image loadImage(String image) {
		BufferedImage bufferedImage = null;
		try {
			Logger.log(" " + getResourceLocation() + image);
			bufferedImage = ImageIO.read(IScreen.class.getResourceAsStream(getResourceLocation() + image));
		} catch (IOException e) {
			Logger.log("Echec du chargement de la ressource demandée.");
		}
		Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
		return fxImage;
	}

	public void openLink(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playSound(String sound) {
		URL resourceUrl = IScreen.class.getResource(getResourceLocation() + sound);
		Media theMedia = null;
		try {
			theMedia = new Media(resourceUrl.toURI().toString());
		} catch (URISyntaxException e) {
			Logger.log(e.toString());
		}
		final MediaPlayer mediaPlayer = new MediaPlayer(theMedia);
		mediaPlayer.play();
	}

	public ResourceLocation getResourceLocation() {
		return RESOURCE_LOCATION;
	}

}

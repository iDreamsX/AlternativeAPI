package fr.trxyy.alternative.alternative_api_ui;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.ResourceLocation;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class IScreen {
	private static ResourceLocation RESOURCE_LOCATION = new ResourceLocation();
	public Timeline timeline;

	public IScreen() {
	}

	public void drawLogo(GameEngine engine, Image img, int posX, int posY, int sizeX, int sizeY, Pane root) {
		ImageView logoImage = new ImageView();
		logoImage.setImage(img);
		logoImage.setFitWidth(sizeX);
		logoImage.setFitHeight(sizeY);
		logoImage.setLayoutX(posX);
		logoImage.setLayoutY(posY);
		root.getChildren().add(logoImage);
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

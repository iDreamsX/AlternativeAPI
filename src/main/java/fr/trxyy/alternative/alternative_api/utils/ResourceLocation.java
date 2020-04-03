package fr.trxyy.alternative.alternative_api.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.trxyy.alternative.alternative_api.GameEngine;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class ResourceLocation {
	
	public Media getMedia(GameEngine engine, String media) {
		Media theMedia = null;
		URL resourceUrl = ResourceLocation.class.getResource(String.valueOf(engine.getLauncherPreferences().getResourceLocation()) + media);
		try {
			theMedia = new Media(resourceUrl.toURI().toString());
		} catch (URISyntaxException e) {
		}
		return theMedia;
	}

	public Image loadImage(GameEngine engine, String image) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(ResourceLocation.class.getResource(String.valueOf(engine.getLauncherPreferences().getResourceLocation()) + image));
		} catch (IOException iOException) {
		}
		return SwingFXUtils.toFXImage(bufferedImage, null);
	}
}

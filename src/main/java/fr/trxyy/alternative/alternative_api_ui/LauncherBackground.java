package fr.trxyy.alternative.alternative_api_ui;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class LauncherBackground {
	public int posX;
	public int posY;
	public static MediaPlayer player;
	public static double opacity = 1.0D;
	public static MediaView viewer;

	public LauncherBackground(GameEngine engine, Media f, Pane root) {
		this.posX = 0;
		this.posY = 0;
		player = new MediaPlayer(f);
		viewer = new MediaView(player);
		viewer.setFitWidth(engine.getLauncherPreferences().getWidth());
		viewer.setFitHeight(engine.getLauncherPreferences().getHeight());
		player.setAutoPlay(true);
		viewer.setPreserveRatio(false);
		viewer.setOpacity(opacity);
		player.setCycleCount(-1);
		player.play();

		root.getChildren().add(viewer);
	}

	public LauncherBackground(GameEngine engine, Media f, double opa, Pane root) {
		this.posX = 0;
		this.posY = 0;
		MediaPlayer player = new MediaPlayer(f);
		MediaView viewer = new MediaView(player);
		viewer.setFitWidth(engine.getLauncherPreferences().getWidth());
		viewer.setFitHeight(engine.getLauncherPreferences().getHeight());
		player.setAutoPlay(true);
		viewer.setPreserveRatio(false);
		viewer.setOpacity(opa);
		player.setCycleCount(-1);
		player.play();

		root.getChildren().add(viewer);
	}

	public LauncherBackground(Media f, int posX, int posY, int sizeX, int sizeY, Pane root) {
		this.posX = 0;
		this.posY = 0;
		MediaPlayer player = new MediaPlayer(f);
		MediaView viewer = new MediaView(player);
		viewer.setFitWidth(sizeX);
		viewer.setFitHeight(sizeY);
		viewer.setLayoutX(posX);
		viewer.setLayoutX(posY);
		player.setAutoPlay(true);
		viewer.setPreserveRatio(false);
		viewer.setOpacity(opacity);
		player.setCycleCount(-1);
		player.play();

		root.getChildren().add(viewer);
	}

	public LauncherBackground(Media f, int posX, int posY, int sizeX, int sizeY, double opa, Pane root) {
		this.posX = 0;
		this.posY = 0;
		MediaPlayer player = new MediaPlayer(f);
		MediaView viewer = new MediaView(player);
		viewer.setFitWidth(sizeX);
		viewer.setFitHeight(sizeY);
		viewer.setLayoutX(posX);
		viewer.setLayoutX(posY);
		player.setAutoPlay(true);
		viewer.setPreserveRatio(false);
		viewer.setOpacity(opa);
		player.setCycleCount(-1);
		player.play();

		root.getChildren().add(viewer);
	}

	public static void setOpacity(double opaci) {
		opacity = opaci;
	}

	public static MediaPlayer getPlayer() {
		return player;
	}

	public static MediaView getViewer() {
		return viewer;
	}
}

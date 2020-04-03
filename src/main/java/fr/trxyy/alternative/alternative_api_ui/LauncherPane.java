package fr.trxyy.alternative.alternative_api_ui;

import fr.trxyy.alternative.alternative_api.GameEngine;
import javafx.scene.layout.Pane;

public class LauncherPane extends Pane {

	public LauncherPane(GameEngine engine) {
		this.setPrefSize(engine.getLauncherPreferences().getWidth(), engine.getLauncherPreferences().getHeight());
	}

}

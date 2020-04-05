package fr.trxyy.alternative.alternative_api_ui.base;

import fr.trxyy.alternative.alternative_api.utils.ResourceLocation;
import javafx.application.Application;
import javafx.stage.Stage;

public abstract class AlternativeBase extends Application {
	private static ResourceLocation RESOURCE_LOCATION = new ResourceLocation();

	public abstract void start(Stage primaryStage) throws Exception;

	public ResourceLocation getResourceLocation() {
		return this.RESOURCE_LOCATION;
	}
}

package fr.trxyy.alternative.alternative_api.utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class FontLoader {

	public void loadFont(String s) {
		Font.loadFont(this.getClass().getResourceAsStream(String.valueOf("/resources/") + s), 14.0);
	}

	public void setFont(String fontName, float size) {
		Font.font(fontName, (double) size);
	}

	public static Font loadFont(String fullFont, String fontName, float size) {
		Font.loadFont(FontLoader.class.getResourceAsStream(String.valueOf("/resources/") + fullFont), 14.0);
		final Font font = Font.font(fontName, (double) size);
		return font;
	}
	
	public static Font loadFontItalic(String fullFont, String fontName, float size) {
		Font.loadFont(FontLoader.class.getResourceAsStream(String.valueOf("/resources/") + fullFont), 14.0);
		final Font font = Font.font(fontName, FontPosture.ITALIC, (double) size);
		return font;
	}
}
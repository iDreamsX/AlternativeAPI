package fr.trxyy.alternative.alternative_api;

public class LauncherPreferences {

	public String name;
	public int width;
	public int height;
	public boolean moveable;
	public String resourceLocation;

	public LauncherPreferences(String n, int w, int h, boolean m) {
		this.name = n;
		this.width = w;
		this.height = h;
		this.moveable = m;
		this.resourceLocation = "/resources/";
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public String getName() {
		return this.name;
	}
	
	public String getResourceLocation() {
		return this.resourceLocation;
	}

	public boolean isMoveable() {
		return this.moveable;
	}

}

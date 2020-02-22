package fr.trxyy.alternative.alternative_api;

public enum GameSize {

	DEFAULT("854x480", 854, 480),
	SIZE_1024x768("1024x768", 1024, 768),
	SIZE_1280x1024("1280x1024", 1280, 1024),
	SIZE_1366x768("1366x768", 1366, 768),
	SIZE_1600x900("1600x900", 1600, 900),
	SIZE_1920x1080("1920x1080", 1920, 1080),
	SIZE_2560x1440("2560x1440", 2560, 1440);

	public int width;
	public int height;
	public String desc;

	GameSize(String tos, int w, int h) {
		this.desc = tos;
		this.width = w;
		this.height = h;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}

}

package fr.trxyy.alternative.alternative_api;

public class GameForge {

	public static String launchTarget = "";
	public static String forgeversion = "";
	public static String mcVersion = "";
	public static String forgeGroup = "";
	public static String mcpVersion = "";
	
	public GameForge(String launchTarget2, String forgeVersion2, String mcVersion2, String forgeGroup2, String mcpVersion2) {
		launchTarget = launchTarget2;
		forgeversion = forgeVersion2;
		mcVersion = mcVersion2;
		forgeGroup = forgeGroup2;
		mcpVersion = mcpVersion2;
	}

	public static String getLaunchTarget() {
		return launchTarget;
	}

	public static String getForgeVersion() {
		return forgeversion;
	}

	public static String getMcVersion() {
		return mcVersion;
	}

	public static String getForgeGroup() {
		return forgeGroup;
	}

	public static String getMcpVersion() {
		return mcpVersion;
	}
}

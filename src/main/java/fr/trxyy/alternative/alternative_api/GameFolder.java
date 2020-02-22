package fr.trxyy.alternative.alternative_api;

import java.io.File;

import fr.trxyy.alternative.alternative_api.utils.GameUtils;

public class GameFolder {

	public File gameDir;
	public File binDir;
	public File playDir;
	public File gameJar;
	public File libsDir;
	public File assetsDir;
	public File nativesDir;
	public File nativesCacheDir;

	public GameFolder(String location) {
		gameDir = GameUtils.getWorkingDirectory(location);
		binDir = new File(gameDir, "bin");
		playDir = new File(gameDir, "bin" + File.separator + "game");
		gameJar = new File(gameDir, "bin" + File.separator + "minecraft.jar");
		libsDir = new File(gameDir, "libraries");
		assetsDir = new File(gameDir, "assets");
		nativesDir = new File(gameDir, "bin" + File.separator + "natives");
		nativesCacheDir = new File(gameDir, "bin" + File.separator + "cache_natives");
	}

	public File getGameDir() {
		return gameDir;
	}

	public File getBinDir() {
		return binDir;
	}

	public File getPlayDir() {
		return playDir;
	}

	public File getGameJar() {
		return gameJar;
	}

	public File getLibsDir() {
		return libsDir;
	}

	public File getAssetsDir() {
		return assetsDir;
	}

	public File getNativesDir() {
		return nativesDir;
	}

	public File getNativesCacheDir() {
		return nativesCacheDir;
	}
}

package fr.trxyy.alternative.alternative_api.utils;

import java.io.File;
import java.util.ArrayList;

import fr.trxyy.alternative.alternative_api.GameEngine;

public class GameUtils {

	/** ----- Working Directory ----- */
	public static String mc_dir;

	public static File getWorkingDirectory(String workDir) {
		mc_dir = workDir;
		String userHome = System.getProperty("user.home", ".");
		File workingDirectory;
		switch (getPlatform()) {
		case 1:
		case 2:
			workingDirectory = new File(userHome, "." + mc_dir + "/");
			break;
		case 3:
			String applicationData = System.getenv("APPDATA");
			if (applicationData != null)
				workingDirectory = new File(applicationData, "." + mc_dir + "/");
			else
				workingDirectory = new File(userHome, "." + mc_dir + "/");
			break;
		case 4:
			workingDirectory = new File(userHome, "Library/Application Support/" + mc_dir);
			break;
		default:
			workingDirectory = new File(userHome, "." + mc_dir + "/");
		}
		return workingDirectory;
	}

	private static int getPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("linux"))
			return 1;
		if (osName.contains("unix"))
			return 1;
		if (osName.contains("solaris"))
			return 2;
		if (osName.contains("sunos"))
			return 2;
		if (osName.contains("win"))
			return 3;
		if (osName.contains("mac"))
			return 4;
		return 5;
	}

	public static String constructClasspath(GameEngine engine) {
		String result = "";
		ArrayList<File> libs = list(engine.getGameFolder().getLibsDir());
		String separator = System.getProperty("path.separator");
		for (File lib : libs) {
			result += lib.getAbsolutePath() + separator;
		}
		result += engine.getGameFolder().getGameJar().getAbsolutePath();
		return result;
	}

	public static ArrayList<File> list(File folder) {
		ArrayList<File> files = new ArrayList<File>();
		if (!folder.isDirectory())
			return files;

		File[] folderFiles = folder.listFiles();
		if (folderFiles != null)
			for (File f : folderFiles)
				if (f.isDirectory())
					files.addAll(list(f));
				else
					files.add(f);

		return files;
	}

}

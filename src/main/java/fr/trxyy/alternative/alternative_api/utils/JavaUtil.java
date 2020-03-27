package fr.trxyy.alternative.alternative_api.utils;

import java.lang.reflect.Field;

public class JavaUtil {

	public static String[] getSpecialArgs() {
		return new String[] { "-XX:-UseAdaptiveSizePolicy", "-XX:+UseConcMarkSweepGC" };
	}

	public static String macDockName(String name) {
		return "-Xdock:name=" + name;
	}

	public static String getJavaCommand() {
		if (System.getProperty("os.name").toLowerCase().contains("win"))
			return "\"" + System.getProperty("java.home") + "\\bin\\java" + "\"";

		return System.getProperty("java.home") + "/bin/java";
	}

	public static void setLibraryPath(String path) throws Exception {
		System.setProperty("java.library.path", path);

		Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
		fieldSysPath.setAccessible(true);
		fieldSysPath.set(null, null);
	}
}
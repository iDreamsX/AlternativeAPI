package fr.trxyy.alternative.alternative_api.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public enum OperatingSystem {

	LINUX(new String[] { "linux", "unix" }), WINDOWS(new String[] { "win" }), OSX(new String[] { "mac" }),
	SOLARIS(new String[] { "solaris", "sunos" }), UNKNOWN(new String[] { "unknown" });

	public static final String NAME = System.getProperty("os.name");
	private final String name;
	private final String[] aliases;

	private OperatingSystem(String... aliases) {
		if (aliases == null) {
			throw new NullPointerException();
		}
		this.name = toString().toLowerCase();
		this.aliases = aliases;
	}

	public String getName() {
		return this.name;
	}

	public String[] getAliases() {
		return this.aliases;
	}

	public boolean isSupported() {
		return this != OperatingSystem.UNKNOWN;
	}

	public boolean isUnsupported() {
		return this == UNKNOWN;
	}

	public static String getJavaPath(boolean appendBinFolder) {
		char separator = File.separatorChar;
		String path = System.getProperty("java.home") + separator;
		if (appendBinFolder) {
			path = path + "bin" + separator + "java";
			if (getCurrent() == WINDOWS) {
				path = path + "w.exe";
			}
		}
		return path;
	}

	public String getJavaDir() {
		final String separator = System.getProperty("file.separator");
		final String path = System.getProperty("java.home") + separator + "bin" + separator;
		if (getCurrentPlatform() == OperatingSystem.WINDOWS && new File(path + "javaw.exe").isFile()) {
			return path + "javaw.exe";
		}
		return path + "java";
	}

	public static OperatingSystem getCurrentPlatform() {
		final String osName = System.getProperty("os.name").toLowerCase();
		for (final OperatingSystem os : values()) {
			for (final String alias : os.getAliases()) {
				if (osName.contains(alias)) {
					return os;
				}
			}
		}
		return OperatingSystem.UNKNOWN;
	}

	public static String getJavaPath() {
		return getJavaPath(true);
	}

	public static boolean match(String part) {
		if (part.contains(getCurrentPlatform().getName())) {
			return true;
		}
		List<String> aliases = Arrays.asList(getCurrentPlatform().getAliases());
		for (String alias : aliases) {
			if (part.contains(alias)) {
				return true;
			}
		}
		return false;
	}

	public static OperatingSystem getCurrent() {
		String osName = NAME.toLowerCase();
		OperatingSystem[] var4;
		int var3 = (var4 = values()).length;
		for (int var2 = 0; var2 < var3; var2++) {
			OperatingSystem os = var4[var2];
			String[] var8 = os.aliases;
			int var7 = os.aliases.length;
			for (int var6 = 0; var6 < var7; var6++) {
				String alias = var8[var6];
				if (osName.contains(alias)) {
					return os;
				}
			}
		}
		return UNKNOWN;
	}

	public static void openLink(final URI link) {
		try {
			final Class desktopClass = Class.forName("java.awt.Desktop");
			final Object o = desktopClass.getMethod("getDesktop", (Class[]) new Class[0]).invoke(null, new Object[0]);
			desktopClass.getMethod("browse", URI.class).invoke(o, link);
		} catch (Throwable e2) {
			if (getCurrentPlatform() == OperatingSystem.OSX) {
				try {
					Runtime.getRuntime().exec(new String[] { "/usr/bin/open", link.toString() });
				} catch (IOException e1) {
					Logger.log("Failed to open link " + link.toString());
				}
			} else {
				Logger.log("Failed to open link " + link.toString());
			}
		}
	}

	public static void openFolder(final File path) {
		final String absolutePath = path.getAbsolutePath();
		final OperatingSystem os = getCurrentPlatform();
		if (os == OperatingSystem.OSX) {
			try {
				Runtime.getRuntime().exec(new String[] { "/usr/bin/open", absolutePath });
				return;
			} catch (IOException e) {
				Logger.log("Couldn't open " + path + " through /usr/bin/open");
			}
		}
		if (os == OperatingSystem.WINDOWS) {
			final String cmd = String.format("cmd.exe /C start \"Open file\" \"%s\"", absolutePath);
			try {
				Runtime.getRuntime().exec(cmd);
				return;
			} catch (IOException e2) {
				Logger.log("Couldn't open " + path + " through cmd.exe");
			}
		}
		try {
			final Class desktopClass = Class.forName("java.awt.Desktop");
			final Object desktop = desktopClass.getMethod("getDesktop", (Class[]) new Class[0]).invoke(null,
					new Object[0]);
			desktopClass.getMethod("browse", URI.class).invoke(desktop, path.toURI());
		} catch (Throwable e3) {
			Logger.log("Couldn't open " + path + " through Desktop.browse()");
		}
	}
}
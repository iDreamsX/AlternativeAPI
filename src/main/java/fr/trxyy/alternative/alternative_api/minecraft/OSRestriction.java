package fr.trxyy.alternative.alternative_api.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.trxyy.alternative.alternative_api.utils.OperatingSystem;

public class OSRestriction {
	private OperatingSystem name;
	private String version;

	public OperatingSystem getName() {
		return this.name;
	}

	private String arch;

	public OSRestriction() {
	}

	public String getVersion() {
		return this.version;
	}

	public String getArch() {
		return this.arch;
	}

	public OSRestriction(OSRestriction osRestriction) {
		this.name = osRestriction.name;
		this.version = osRestriction.version;
		this.arch = osRestriction.arch;
	}

	public boolean isCurrentOperatingSystem() {
		if (this.name != null && this.name != OperatingSystem.getCurrentPlatform())
			return false;

		if (this.version != null) {
			try {
				Pattern pattern = Pattern.compile(this.version);
				Matcher matcher = pattern.matcher(System.getProperty("os.version"));
				if (!matcher.matches())
					return false;
			} catch (Throwable throwable) {
			}
		}

		if (this.arch != null) {
			try {
				Pattern pattern = Pattern.compile(this.arch);
				Matcher matcher = pattern.matcher(System.getProperty("os.arch"));
				if (!matcher.matches())
					return false;
			} catch (Throwable throwable) {
			}
		}

		return true;
	}

	public String toString() {
		return "OSRestriction{name=" + this.name + ", version='" + this.version + '\'' + ", arch='" + this.arch + '\''
				+ '}';
	}
}
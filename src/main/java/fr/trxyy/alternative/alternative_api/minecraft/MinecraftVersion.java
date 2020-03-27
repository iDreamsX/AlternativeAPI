package fr.trxyy.alternative.alternative_api.minecraft;

import java.util.List;

import fr.trxyy.alternative.alternative_api.assets.AssetIndexInfo;

public class MinecraftVersion {

	private String id;
	private String inheritsFrom;
	private List<MinecraftLibrary> libraries;
	private String mainClass;
	private String assets;
	private AssetIndexInfo assetIndex;
	private MinecraftClient downloads;

	public MinecraftVersion() {
	}

	public MinecraftVersion(MinecraftVersion other) {
		this.id = other.id;
		if (other.inheritsFrom != null) {
			this.inheritsFrom = other.inheritsFrom;
		}
		if (other.assetIndex != null) {
			this.assetIndex = other.assetIndex;
		}
		this.libraries = other.libraries;
		this.mainClass = other.mainClass;
		this.assets = other.assets;
	}

	public List<MinecraftLibrary> getLibraries() {
		return libraries;
	}

	public MinecraftClient getDownloads() {
		return downloads;
	}

	public String getId() {
		return id;
	}

	public void setId(String idd) {
		this.id = idd;
	}

	public String getInheritsFrom() {
		return inheritsFrom;
	}

	public void setInheritsFrom(String inheritsFrom) {
		this.inheritsFrom = inheritsFrom;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}

	public AssetIndexInfo getAssetIndex() {
		return assetIndex;
	}

	public void setAssetIndex(String s) {
		this.assets = s;
	}
}
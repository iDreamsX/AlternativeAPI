package fr.trxyy.alternative.alternative_api;

import java.io.File;

public class GameLinks {

	public String BASE_URL;
	public String JSON_URL;
	public String IGNORE_LIST;
	public String DELETE_LIST;
	public String CUSTOM_FILES_URL;

	public GameLinks(String baseUrl, String jsonName) {
		this.BASE_URL = baseUrl;
		this.JSON_URL = baseUrl + jsonName;
		this.IGNORE_LIST = baseUrl + "ignore.cfg";
		this.DELETE_LIST = baseUrl + "delete.cfg";
		this.CUSTOM_FILES_URL = baseUrl + "files" + File.separatorChar;
	}

	public String getDownloadUrl() {
		return this.BASE_URL;
	}

	public String getJsonUrl() {
		return this.JSON_URL;
	}

	public String getIgnoreListUrl() {
		return this.IGNORE_LIST;
	}
	
	public String getDeleteListUrl() {
		return this.DELETE_LIST;
	}

	public String getCustomFilesUrl() {
		return this.CUSTOM_FILES_URL;
	}

}

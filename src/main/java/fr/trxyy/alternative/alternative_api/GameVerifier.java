package fr.trxyy.alternative.alternative_api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.file.FileUtil;

public class GameVerifier {
	
	public GameEngine engine;
	public static List<String> allowedFiles = new ArrayList<String>();
	public List<File> filesList;
	public List<String> ignoreList = new ArrayList<String>();
	public List<String> deleteList = new ArrayList<String>();
	
	public GameVerifier(GameEngine gameEngine) {
		this.engine = gameEngine;
	}
	
	public void verify() {
		this.filesList = (List<File>)FileUtils.listFiles(this.engine.getGameFolder().getGameDir(), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File file : this.filesList) {
			
			if (existInDeleteList(file.getAbsolutePath().replace(this.engine.getGameFolder().getGameDir().getAbsolutePath(), ""))) {
				FileUtil.deleteSomething(file.getAbsolutePath());
			}
			
			if (!existInAllowedFiles(file.getAbsolutePath().replace(this.engine.getGameFolder().getGameDir().getAbsolutePath(), ""))) {
				if (existInIgnoreList(file.getAbsolutePath().replace(this.engine.getGameFolder().getGameDir().getAbsolutePath(), ""))) {
//					Logger.log("SKIPPED >> " + file);
				}
				else {
					if (file.getAbsolutePath().startsWith(this.engine.getGameFolder().getPlayDir().getAbsolutePath() + File.separator +  "saves".replace('/', File.separatorChar))) {
//						Logger.log("SKIPPED BECAUSE ITS A WORLD >> " + file);
					}
					else if (file.getAbsolutePath().startsWith(this.engine.getGameFolder().getPlayDir().getAbsolutePath() + File.separator +  "logs".replace('/', File.separatorChar))) {
//						Logger.log("SKIPPED BECAUSE ITS LOGS >> " + file);
					}
					else if (file.getAbsolutePath().startsWith(this.engine.getGameFolder().getPlayDir().getAbsolutePath() + File.separator +  "resourcepacks".replace('/', File.separatorChar))) {
//						Logger.log("SKIPPED BECAUSE ITS RESOURCESPACKS >> " + file);
					}
					else if (file.getAbsolutePath().startsWith(this.engine.getGameFolder().getPlayDir().getAbsolutePath() + File.separator +  "shaderpacks".replace('/', File.separatorChar))) {
//						Logger.log("SKIPPED BECAUSE ITS SHADERPACKS >> " + file);
					}
					else if (file.getAbsolutePath().startsWith(this.engine.getGameFolder().getPlayDir().getAbsolutePath() + File.separator +  "config".replace('/', File.separatorChar))) {
//						Logger.log("SKIPPED BECAUSE ITS FORGE CONFIG FOLDER >> " + file);
					}
					else {
//						Logger.err("DELETING >> " + file);
						FileUtil.deleteSomething(file.getAbsolutePath());
					}
				}
			}
		}
	}
	
	public static void addToFileList(String allowed) {
		allowedFiles.add(allowed);
	}
	
	public boolean existInIgnoreList(String search) {
		for(String str: this.ignoreList) {
		    if(str.trim().contains(search))
		       return true;
		}
		return false;
	}
	
	public boolean existInAllowedFiles(String search) {
		for (String str : allowedFiles) {
			if (str.trim().contains(search))
				return true;
		}
		return false;
	}
	
	public boolean existInDeleteList(String search) {
		for(String str: this.deleteList) {
		    if(str.trim().contains(search))
		       return true;
		}
		return false;
	}

	public void getIgnoreList() {
		URL url = null;
		BufferedReader read = null;
		try {
			url = new URL(this.engine.getGameLinks().getIgnoreListUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			read = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String i;
		try {
			while ((i = read.readLine()) != null) {
				String correctName = i.replace('/', File.separatorChar);
				this.ignoreList.add("" + this.engine.getGameFolder().getGameDir() + File.separatorChar + correctName);
//				Logger.log("Added: " + this.engine.getGameFolder().getGameDir() + File.separatorChar + correctName + " to ignoreList.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** ---------------------------------------------------------------------- */

	public void getDeleteList() {
		URL url = null;
		BufferedReader read = null;
		try {
			url = new URL(this.engine.getGameLinks().getDeleteListUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			read = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String i;
		try {
			while ((i = read.readLine()) != null) {
				String correctName = i.replace('/', File.separatorChar);
				this.deleteList.add("" + this.engine.getGameFolder().getGameDir() + File.separatorChar + correctName);
//				Logger.log("Added: " + this.engine.getGameFolder().getGameDir() + File.separatorChar + correctName + " to deleteList.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

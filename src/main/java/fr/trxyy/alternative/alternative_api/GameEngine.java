package fr.trxyy.alternative.alternative_api;

import fr.trxyy.alternative.alternative_api.maintenance.GameMaintenance;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;

public class GameEngine {

	private GameFolder gameFolder;
	private LauncherPreferences launcherSize;
	private GameVersion gameVersion;
	private GameStyle gameStyle;
	private GameSize gameSize;
	private GameLinks gameLinks;
	private GameForge gameForge;
	private GameConnect gameConnect;
	private GameMemory gameMemory;
	private GameUpdater gameUpdater;
	private GameArguments gameArguments;
	private GameMaintenance gameMaintenance;
	
	public GameEngine(GameFolder folder, LauncherPreferences lSize, GameVersion version, GameStyle style, GameSize size) {
		this.gameFolder = folder;
		this.launcherSize = lSize;
		this.gameVersion = version;
		this.gameStyle = style;
		this.gameSize = size;
	}
	
	public GameEngine(GameFolder folder, LauncherPreferences lSize, GameVersion version, GameStyle style) {
		this.gameFolder = folder;
		this.launcherSize = lSize;
		this.gameVersion = version;
		this.gameStyle = style;
		this.gameSize = GameSize.DEFAULT;
	}
	
	public void reg(GameLinks links, GameForge forge, GameConnect connect, GameMemory memory) {
		this.gameLinks = links;
		this.gameForge = forge;
		this.gameConnect = connect;
		this.gameMemory = memory;
	}
	
	public void reg(GameLinks links, GameConnect connect, GameMemory memory) {
		this.gameLinks = links;
		this.gameForge = null;
		this.gameConnect = connect;
		this.gameMemory = memory;
	}
	
	public void reg(GameLinks links, GameConnect connect) {
		this.gameLinks = links;
		this.gameForge = null;
		this.gameConnect = connect;
	}
	
	public void reg(GameLinks links) {
		this.gameLinks = links;
		this.gameForge = null;
	}
	
	public void reg(GameSize siz) {
		this.gameSize = siz;
	}
	
	public void reg(GameUpdater updater) {
		this.gameUpdater = updater;
	}
	
	public void reg(GameConnect connect) {
		this.gameForge = null;
		this.gameConnect = connect;
	}
	
	public void reg(GameForge forge) {
		this.gameForge = forge;
	}
	
	public void reg(GameMemory memory) {
		this.gameMemory = memory;
	}
	
	public void reg(GameArguments arguments) {
		this.gameArguments = arguments;
	}

	public LauncherPreferences getLauncherPreferences() {
		return this.launcherSize;
	}
	
	public int getWidth() {
		return this.launcherSize.getWidth();
	}
	
	public int getHeight() {
		return this.launcherSize.getHeight();
	}

	public GameVersion getGameVersion() {
		return this.gameVersion;
	}
	
	public GameForge getGameForge() {
		return this.gameForge;
	}

	public GameStyle getGameStyle() {
		return this.gameStyle;
	}

	public GameFolder getGameFolder() {
		return this.gameFolder;
	}
	
	public GameSize getGameSize() {
		return this.gameSize;
	}
	
	public GameLinks getGameLinks() {
		return this.gameLinks;
	}
	
	public GameConnect getGameConnect() {
		return this.gameConnect;
	}
	
	public GameMemory getGameMemory() {
		return this.gameMemory;
	}

	public GameUpdater getGameUpdater() {
		return this.gameUpdater;
	}
	
	public GameArguments getGameArguments() {
		return this.gameArguments;
	}

	public void reg(GameMaintenance mainte) {
		this.gameMaintenance = mainte;
	}
	
	public GameMaintenance getGameMaintenance() {
		return this.gameMaintenance;
	}
}

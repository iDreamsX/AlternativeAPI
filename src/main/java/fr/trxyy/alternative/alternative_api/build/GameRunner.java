package fr.trxyy.alternative.alternative_api.build;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.account.Session;
import fr.trxyy.alternative.alternative_api.utils.FileUtil;
import fr.trxyy.alternative.alternative_api.utils.GameUtils;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.OperatingSystem;

public class GameRunner {

	private GameEngine engine;
	private Session session;
	private String launchCommand = "";

	public GameRunner(GameEngine gameEngine, Session account) {
		this.engine = gameEngine;
		this.session = account;
		this.patchArguments();
		Logger.log("========================================");
		Logger.log("Unpacking natives             [Step 5/7]");
		Logger.log("========================================");
		this.unpackNatives();
		Logger.log("Deleting unrequired Natives   [Step 6/7]");
		Logger.log("========================================");
		this.deleteFakeNatives();
	}
	
	public void launchGame() {
		this.engine.getGameUpdater().setDownloadingFileName("Lancement de Minecraft " + this.engine.getGameVersion().getVersion() + "...");
		String line = getCommands();
		Logger.err("FROM: " + line);
		Logger.log("" + generateLot());
		CommandLine cmdLine = CommandLine.parse(line);
		DefaultExecutor executor = new DefaultExecutor();
		try {
			executor.execute(cmdLine);
		} catch (ExecuteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public String getCommands() {
		this.launchCommand = addJavaPath("java");
		OperatingSystem os = OperatingSystem.getCurrentPlatform();
		
		if (os.equals(OperatingSystem.OSX)) {
			this.launchCommand = addLine("-Xdock:icon=" + engine.getGameFolder().getAssetsDir() + "icons/minecraft.icns");
			this.launchCommand = addLine("-Xdock:name=minecraft");
			this.launchCommand = addLine("-XX:+UseConcMarkSweepGC");
			this.launchCommand = addLine("-XX:+CMSIncrementalMode");
			this.launchCommand = addLine("-XX:-UseAdaptiveSizePolicy");
		} else if (os.equals(OperatingSystem.WINDOWS)) {
			this.launchCommand = addLine("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
		}
		this.launchCommand = addLine("-Dfml.ignoreInvalidMinecraftCertificates=true");
		this.launchCommand = addLine("-Dfml.ignorePatchDiscrepancies=true");
		
		boolean is32Bit = "32".equals(System.getProperty("sun.arch.data.model"));
		String defaultArgument = is32Bit ? "-Xmx512M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M" : "-Xmx1G -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
		if (engine.getGameMemory() != null) {
			defaultArgument = is32Bit ? "-Xmx512M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M" : "-Xmx" + engine.getGameMemory().getCount() + " -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
		}
		this.launchCommand = addLines(defaultArgument.split(" "));
		
		this.launchCommand = addLine("-Djava.library.path=" + engine.getGameFolder().getNativesDir().getAbsolutePath());
		this.launchCommand = addLine("-Dminecraft.launcher.brand=Minecraft");
		this.launchCommand = addLine("-Dminecraft.launcher.version=21");
		
		this.launchCommand = addLine("-cp");
		this.launchCommand = addLine("\"" + GameUtils.constructClasspath(engine) + "\"");
		this.launchCommand = addLine(engine.getGameStyle().getMainClass());
		this.launchCommand = addLines(engine.getGameVersion().getArguments().split(" "));
		/** ----- Size of window -----*/
		this.launchCommand = addLine("--width=" + engine.getGameSize().getWidth());
		this.launchCommand = addLine("--height=" + engine.getGameSize().getHeight());
		/** ----- Tweak Class if required ----- */
		if (!engine.getGameStyle().equals(GameStyle.VANILLA)) {
			if (!engine.getGameStyle().equals(GameStyle.FORGE_1_13_HIGHER)) {
				this.launchCommand = addLine("--tweakClass " + engine.getGameStyle().getTweakArgument());
			}
		}
		/** ----- Change properties of Forge (1.13+) ----- */
		if (engine.getGameStyle().getSpecificsArguments() != null) {
			String specfs = engine.getGameStyle().getSpecificsArguments();
			specfs = specfs.replace("${launch_target_fml}", GameForge.getLaunchTarget())
			.replace("${forge_version_fml}", GameForge.getForgeVersion())
			.replace("${mc_version_fml}", GameForge.getMcVersion())
			.replace("${forge_group_fml}", GameForge.getForgeGroup())
			.replace("${mcp_version_fml}", GameForge.getMcpVersion());
			this.launchCommand = addLine(specfs);
		}
		/** ----- Direct connect to a server if required. ----- */
		if (engine.getGameConnect() != null) {
			this.launchCommand = addLine("--server=" + engine.getGameConnect().getIp());
			this.launchCommand = addLine("--port=" + engine.getGameConnect().getPort());
		}
		return this.launchCommand;
	}

	public String addJavaPath(String next){
		return this.launchCommand + next;
	}
	
	public String addLine(String next){
		return this.launchCommand + " " + next;
	}
	
	public String addLines(String... nexts){
		ArrayList fakeList = new ArrayList(Arrays.asList(nexts));
		for(int index = 0; index < fakeList.size(); index++)
		{
		    this.launchCommand = this.launchCommand + " " + fakeList.get(index);
		}
		return this.launchCommand + " ";
	}

	public void patchArguments() {
		this.engine.getGameVersion().setArguments(this.engine.getGameVersion().getArguments()
		.replace("${auth_player_name}", this.session.getUsername())
		.replace("${auth_uuid}", this.session.getUuid())
		.replace("${auth_access_token}", this.session.getToken())
		.replace("${user_type}", "legacy")
		.replace("${version_name}", this.engine.getGameVersion().getVersion())
		.replace("${version_type}", "release")
		.replace("${game_directory}", this.engine.getGameFolder().getPlayDir().getAbsolutePath())
		.replace("${assets_root}", this.engine.getGameFolder().getAssetsDir().getAbsolutePath())
		.replace("${assets_index_name}", this.engine.getGameVersion().getAssetIndex())
		.replace("${user_properties}", "{}"));
	}
	
	private void unpackNatives() {
		try {
			FileUtil.unpackNatives(engine.getGameFolder().getNativesDir(), engine);
		} catch (IOException e) {
			Logger.log("Couldn't unpack natives!");
			e.printStackTrace();
			return;
		}
	}
	
	private void deleteFakeNatives() {
		try {
			FileUtil.deleteFakeNatives(engine.getGameFolder().getNativesDir(), engine);
		} catch (IOException e) {
			Logger.log("Couldn't unpack natives!");
			e.printStackTrace();
			return;
		}
	}
	
	public static String generateLot() {
		String lot = "";
		SimpleDateFormat year = new SimpleDateFormat("YY");
		SimpleDateFormat hour = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		int julianDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		lot = "L" + year.format(date) + julianDay + "/" + hour.format(date);
		return lot;
	}
}

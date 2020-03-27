package fr.trxyy.alternative.alternative_api.build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.account.Session;
import fr.trxyy.alternative.alternative_api.utils.FileUtil;
import fr.trxyy.alternative.alternative_api.utils.GameUtils;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.OperatingSystem;
import javafx.application.Platform;

public class GameRunner {

	private GameEngine engine;
	private Session session;

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
	
    public Process launch() throws Exception
    {
        ProcessBuilder builder = new ProcessBuilder();
        ArrayList<String> commands = new ArrayList<String>();
		OperatingSystem os = OperatingSystem.getCurrentPlatform();
        commands.add(OperatingSystem.getJavaPath());
        commands.add("-XX:-UseAdaptiveSizePolicy");
        commands.add("-XX:+UseConcMarkSweepGC");
		
		if (os.equals(OperatingSystem.OSX)) {
			commands.add("-Xdock:name=Minecraft");
			commands.add("-Xdock:icon=" + engine.getGameFolder().getAssetsDir() + "icons/minecraft.icns");
			commands.add("-XX:+CMSIncrementalMode");
		} else if (os.equals(OperatingSystem.WINDOWS)) {
			commands.add("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
		}
		commands.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
		commands.add("-Dfml.ignorePatchDiscrepancies=true");
		
		boolean is32Bit = "32".equals(System.getProperty("sun.arch.data.model"));
		String defaultArgument = is32Bit ? "-Xmx512M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M" : "-Xmx1G -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
		if (engine.getGameMemory() != null) {
			defaultArgument = is32Bit ? "-Xmx512M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M" : "-Xmx" + engine.getGameMemory().getCount() + " -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
		}
		
		String str[] = defaultArgument.split(" ");
		List<String> args = Arrays.asList(str);
		commands.addAll(args);
		
		commands.add("-Djava.library.path=" + engine.getGameFolder().getNativesDir().getAbsolutePath());
		commands.add("-Dminecraft.launcher.brand=Minecraft");
		commands.add("-Dminecraft.launcher.version=21");
		
		commands.add("-cp");
		commands.add("\"" + GameUtils.constructClasspath(engine) + "\"");
		commands.add(engine.getGameStyle().getMainClass());
		
		String str_[] = engine.getGameVersion().getArguments().split(" ");
		List<String> args_ = Arrays.asList(str_);
		commands.addAll(args_);
		
		/** ----- Size of window -----*/
		commands.add("--width=" + engine.getGameSize().getWidth());
		commands.add("--height=" + engine.getGameSize().getHeight());
		
		/** ----- Tweak Class if required ----- */
		if (!engine.getGameStyle().equals(GameStyle.VANILLA)) {
			if (!engine.getGameStyle().equals(GameStyle.FORGE_1_13_HIGHER)) {
				commands.add("--tweakClass " + engine.getGameStyle().getTweakArgument());
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
			commands.add(specfs);
		}
		
		/** ----- Direct connect to a server if required. ----- */
		if (engine.getGameConnect() != null) {
			commands.add("--server=" + engine.getGameConnect().getIp());
			commands.add("--port=" + engine.getGameConnect().getPort());
		}
		builder.directory(engine.getGameFolder().getGameDir());
		builder.redirectErrorStream(true);
		builder.command(commands);

		String cmds = "";
		for (String command : commands)
			cmds += command + " ";
		
		this.engine.getGameUpdater().setDownloadingFileName("Lancement de Minecraft " + this.engine.getGameVersion().getVersion() + "...");
		Logger.err("FROM: " + cmds);
		Logger.log("" + generateLot());

		try {
			Process p = builder.start();
			String line;
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
			
			if (!p.isAlive()) {
				Platform.exit();
				System.exit(0);
			}
			return p;
		} catch (IOException e) {
			throw new Exception("Cannot launch !", e);
		}
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

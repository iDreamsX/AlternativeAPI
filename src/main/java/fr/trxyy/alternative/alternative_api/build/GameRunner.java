package fr.trxyy.alternative.alternative_api.build;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.text.StrSubstitutor;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.account.Session;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.OperatingSystem;
import fr.trxyy.alternative.alternative_api.utils.file.FileUtil;
import fr.trxyy.alternative.alternative_api.utils.file.GameUtils;
import javafx.application.Platform;

public class GameRunner {

	private GameEngine engine;
	private Session session;

	public GameRunner(GameEngine gameEngine, Session account) {
		this.engine = gameEngine;
		this.session = account;
		Logger.log("========================================");
		Logger.log("Unpacking natives             [Step 5/7]");
		Logger.log("========================================");
		this.unpackNatives();
		Logger.log("Deleting unrequired Natives   [Step 6/7]");
		Logger.log("========================================");
		this.deleteFakeNatives();
		if (engine.getStage() != null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					engine.getStage().hide();
				}
			});
		}
	}
	
    public Process launch() throws Exception
    {
        ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(engine.getGameFolder().getGameDir());
		processBuilder.redirectErrorStream(true);
		processBuilder.command(getLaunchCommand()); // getLaunchCommandsForge
		String cmds = "";
		for (String command : getLaunchCommand()) {
			cmds += command + " ";
		}
		String[] ary = cmds.split(" ");
		Logger.log("Lancement: " + hideAccessToken(ary));
		Logger.log("" + generateLot());
		try {
			Process process = processBuilder.start();
			String line;
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (line.contains("Stopping!")) {
					Platform.exit();
					System.exit(0);
				}
				Logger.log(line);
			}
			input.close();
			return process;
		} catch (IOException e) {
			throw new Exception("Cannot launch !", e);
		}
	}
    
	private ArrayList<String> getLaunchCommand() {
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
		commands.add("-Djava.library.path=" + engine.getGameFolder().getNativesDir().getAbsolutePath());
		commands.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
		commands.add("-Dfml.ignorePatchDiscrepancies=true");
		
		boolean is32Bit = "32".equals(System.getProperty("sun.arch.data.model"));
		String defaultArgument = is32Bit ? "-Xmx512M -Xmn128M" : "-Xmx1G -Xmn128M";
		if (engine.getGameMemory() != null) {
			defaultArgument = is32Bit ? "-Xmx512M -Xmn128M" : "-Xmx" + engine.getGameMemory().getCount() + " -Xmn128M";
			Logger.err("ERR");
		}
		String str[] = defaultArgument.split(" ");
		List<String> args = Arrays.asList(str);
		commands.addAll(args);
		
		
		commands.add("-cp");
		commands.add("\"" + GameUtils.constructClasspath(engine) + "\"");
		commands.add(engine.getGameStyle().getMainClass());
		
        final String[] argsD = getMinecraftArguments();
        List<String> arguments = Arrays.asList(argsD);
        commands.addAll(arguments);
		
		/** ----- Addons arguments ----- */
		if (engine.getGameArguments() != null) {
			commands.addAll(engine.getGameArguments().getArguments());
		}
		
		/** ----- Size of window ----- */
		if (engine.getGameSize() != null) {
			commands.add("--width=" + engine.getGameSize().getWidth());
			commands.add("--height=" + engine.getGameSize().getHeight());
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
		
		/** ----- Tweak Class if required ----- */
		if (engine.getGameStyle().equals(GameStyle.FORGE_1_7_10_OLD) || engine.getGameStyle().equals(GameStyle.FORGE_1_8_TO_1_12_2) || engine.getGameStyle().equals(GameStyle.OPTIFINE) || engine.getGameStyle().equals(GameStyle.ALTERNATIVE)) {
			commands.add("--tweakClass");
			commands.add(engine.getGameStyle().getTweakArgument());
		}
		return commands;
	}
	
	private ArrayList<String> getLaunchCommandsForge() {
		ArrayList<String> commands = new ArrayList<String>();
		File javaPath = new File("G:\\Minecraft Launcher\\runtime\\jre-x64\\bin\\java");
        commands.add(javaPath.getAbsolutePath());
        
		commands.add("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
		commands.add("-Djava.library.path=" + engine.getGameFolder().getNativesDir().getAbsolutePath());
		commands.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
		commands.add("-Dfml.ignorePatchDiscrepancies=true");
        commands.add("-Dminecraft.launcher.brand=minecraft-launcher");
        commands.add("-Dminecraft.launcher.version=2.1.13508");
        
		commands.add("-cp");
		commands.add(GameUtils.constructClasspath(engine));
		
		commands.add("cpw.mods.modlauncher.Launcher");
		
//		commands.add("--username");
//		commands.add("Trxyy");
//		
//		commands.add("--version");
//		commands.add("1.14.4-forge-28.2.0");
//		
//		commands.add("--gameDir");
//		commands.add(engine.getGameFolder().getPlayDir().getAbsolutePath());
//		
//		commands.add("--assetsDir");
//		commands.add(engine.getGameFolder().getAssetsDir().getAbsolutePath());
//		
//		commands.add("--assetIndex");
//		commands.add("1.14");
//		
//		commands.add("--uuid");
//		commands.add("0");
//		
//		commands.add("--accessToken");
//		commands.add("0");
//		
//		commands.add("--userType");
//		commands.add("mojang");
//		
//		commands.add("--versionType");
//		commands.add("release");
		
        final String[] argsD = getMinecraftArguments();
        List<String> arguments = Arrays.asList(argsD);
        commands.addAll(arguments);
		
		/** ----- Change properties of Forge (1.13+) ----- */
		commands.add("--launchTarget");
		commands.add("fmlclient");
		
		commands.add("--fml.forgeVersion");
		commands.add("28.2.0");
		
		commands.add("--fml.mcVersion");
		commands.add("1.14.4");
		
		commands.add("--fml.forgeGroup");
		commands.add("net.minecraftforge");
		
		commands.add("--fml.mcpVersion");
		commands.add("20190829.143755");
		return commands;
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
	
	private String[] getMinecraftArguments() {
		final Map<String, String> map = new HashMap<String, String>();
		final StrSubstitutor substitutor = new StrSubstitutor(map);
		final String[] split = this.engine.getGameVersion().getArguments().split(" ");
		map.put("auth_player_name", this.session.getUsername());
		map.put("auth_uuid", this.session.getUuid());
		map.put("auth_access_token", this.session.getToken());
		map.put("user_type", "legacy");
		map.put("version_name", this.engine.getGameVersion().getVersion());
		map.put("version_type", "release");
		map.put("game_directory", this.engine.getGameFolder().getPlayDir().getAbsolutePath());
		map.put("assets_root", this.engine.getGameFolder().getAssetsDir().getAbsolutePath());
		map.put("assets_index_name", this.engine.getGameVersion().getAssetIndex());
		map.put("user_properties", "{}");

		for (int i = 0; i < split.length; i++)
			split[i] = substitutor.replace(split[i]);

		return split;
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
	
    static List<String> hideAccessToken(String[] arguments) {
        final ArrayList<String> output = new ArrayList<String>();
        for (int i = 0; i < arguments.length; i++) {
            if (i > 0 && Objects.equals(arguments[i-1], "--accessToken")) {
                output.add("????????");
            } else {
                output.add(arguments[i]);
            }
        }
        return output;
    }
}

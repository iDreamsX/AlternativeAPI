package fr.trxyy.alternative.alternative_api;

public enum GameStyle {
	
	VANILLA("Vanilla", "net.minecraft.client.main.Main", "", ""),
	VANILLA_PLUS("VanillaPlus", "net.minecraft.client.main.Main", "", ""),
	FORGE_1_7_10_OLD("Forge 1.7.10-", "net.minecraft.launchwrapper.Launch", "cpw.mods.fml.common.launcher.FMLTweaker", ""),
	FORGE_1_8_TO_1_12_2("Forge 1.8 -> 1.12.2", "net.minecraft.launchwrapper.Launch", "net.minecraftforge.fml.common.launcher.FMLTweaker", ""),
	FORGE_1_13_HIGHER("Forge 1.13+", "cpw.mods.modlauncher.Launcher", "", "--launchTarget ${launch_target_fml} --fml.forgeVersion ${forge_version_fml} --fml.mcVersion ${mc_version_fml} --fml.forgeGroup ${forge_group_fml} --fml.mcpVersion ${mcp_version_fml}");
	
	private String name;
	private String mainClass;
	private String tweakArgument;
	private String specificsArguments;

	private GameStyle(String name, String main, String tweak, String args) {
		this.name = name;
		this.mainClass = main;
		this.tweakArgument = tweak;
		this.specificsArguments = args;
	}

	public String getTweakName() {
		return this.name;
	}

	public String getMainClass() {
		return this.mainClass;
	}

	public String getTweakArgument() {
		return this.tweakArgument;
	}

	public String getSpecificsArguments() {
		return this.specificsArguments;
	}

}

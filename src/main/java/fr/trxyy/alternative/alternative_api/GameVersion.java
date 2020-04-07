package fr.trxyy.alternative.alternative_api;

public enum GameVersion {

	V_1_7_10("1.7.10", "1.7.10", Arguments.DEFAULT_ARGUMENTS_LOWER),
	
	V_1_8("1.8", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_1("1.8.1", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_2("1.8.2", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_3("1.8.3", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_4("1.8.4", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_5("1.8.5", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_6("1.8.6", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_7("1.8.7", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_8("1.8.8", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	V_1_8_9("1.8.9", "1.8", Arguments.DEFAULT_ARGUMENTS_LOWER),
	
	V_1_9("1.9", "1.9", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_9_1("1.9.1", "1.9", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_9_2("1.9.2", "1.9", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_9_3("1.9.3", "1.9", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_9_4("1.9.4", "1.9", Arguments.DEFAULT_ARGUMENTS_NEWER),
	
	
	V_1_10("1.10", "1.10", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_10_1("1.10.1", "1.10", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_10_2("1.10.2", "1.10", Arguments.DEFAULT_ARGUMENTS_NEWER),
	
	V_1_11("1.11.x", "1.11", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_11_1("1.11.1", "1.11", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_11_2("1.11.2", "1.11", Arguments.DEFAULT_ARGUMENTS_NEWER),
	
	V_1_12("1.12", "1.12", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_12_1("1.12.1", "1.12", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_12_2("1.12.2", "1.12", Arguments.DEFAULT_ARGUMENTS_NEWER),
	
	V_1_13("1.13", "1.13", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_13_1("1.13.1", "1.13.1", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_13_2("1.13.2", "1.13.1", Arguments.DEFAULT_ARGUMENTS_NEWER),
	
	V_1_14("1.14", "1.14", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_14_1("1.14.1", "1.14", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_14_2("1.14.2", "1.14", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_14_3("1.14.3", "1.14", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_14_4("1.14.4", "1.14", Arguments.DEFAULT_ARGUMENTS_NEWER),
	
	V_1_15("1.15", "1.15", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_15_1("1.15.1", "1.15", Arguments.DEFAULT_ARGUMENTS_NEWER),
	V_1_15_2("1.15.2", "1.15", Arguments.DEFAULT_ARGUMENTS_NEWER);

	private String name;
	private String assetIndex;
	private String minecraftArguments;

	GameVersion(String name_, String index, String args) {
		this.name = name_;
		this.assetIndex = index;
		this.minecraftArguments = args;
	}

	public String getVersion() {
		return this.name;
	}

	public String getAssetIndex() {
		return this.assetIndex;
	}

	public String getArguments() {
		return this.minecraftArguments;
	}

	public void setArguments(String buildedArgs) {
		this.minecraftArguments = buildedArgs;
	}
	
	
	private static class Arguments {
		private static String DEFAULT_ARGUMENTS_LOWER = "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userProperties ${user_properties} --userType ${user_type}";
		private static String DEFAULT_ARGUMENTS_NEWER = "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}";
	}

}

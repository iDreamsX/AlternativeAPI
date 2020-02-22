package fr.trxyy.alternative.alternative_api;

public enum GameVersion {

	V_1_7_10("1.7.10", "1.7.10", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userProperties ${user_properties} --userType ${user_type}"),
	V_1_8_ALL("1.8.x", "1.8", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userProperties ${user_properties} --userType ${user_type}"),
	V_1_9_ALL("1.9.x", "1.9", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"),
	V_1_10_ALL("1.10.x", "1.10", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"),
	V_1_11_ALL("1.11.x", "1.11", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"),
	V_1_12_ALL("1.12.x", "1.12", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"),
	V_1_13("1.13-only", "1.13", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"),
	V_1_13_1_OR_1_13_2("1.13-others", "1.13.1", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"),
	V_1_14_ALL("1.14.x", "1.14", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"),
	V_1_15("1.15", "1.15", "--username=${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"),
	NOPE("nope", "nope", "nope");

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

}

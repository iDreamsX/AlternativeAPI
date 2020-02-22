package fr.trxyy.alternative.alternative_api;

public class GameConnect {

	public String IP_ADRESS;
	public String PORT;

	public GameConnect(String ip, String p) {
		this.IP_ADRESS = ip;
		this.PORT = p;
	}

	public String getIp() {
		return this.IP_ADRESS;
	}

	public String getPort() {
		return this.PORT;
	}

}

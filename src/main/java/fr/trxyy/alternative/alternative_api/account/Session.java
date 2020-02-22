package fr.trxyy.alternative.alternative_api.account;

public class Session {

	public String username;
	public String token;
	public String uuId;

	public Session() {}

	public Session(String user, String t, String u) {
		this.username = user;
		this.token = t;
		this.uuId = u;
	}

	public Session(Session same) {
		this.username = same.username;
		this.token = same.token;
		this.uuId = same.uuId;
	}

	public String getUsername() {
		return username;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getUuid() {
		return uuId;
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
	
	public void setToken(String tkn) {
		this.token = tkn;
	}
	
	public void setUuid(String id) {
		this.uuId = id;
	}

}

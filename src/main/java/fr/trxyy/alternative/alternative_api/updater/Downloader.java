package fr.trxyy.alternative.alternative_api.updater;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameVerifier;
import fr.trxyy.alternative.alternative_api.utils.FileUtil;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import javafx.application.Platform;

public class Downloader extends Thread {
	private final String url;
	private final String sha1;
	private final File file;
	private GameEngine engine;

	public void run() {
		try {
			download();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Downloader(File file, String url, String sha1, GameEngine engine_) {
		this.file = file;
		this.url = url;
		this.sha1 = sha1;
		this.engine = engine_;
		GameVerifier.addToFileList(file.getAbsolutePath().replace(engine.getGameFolder().getGameDir().getAbsolutePath(), "").replace("\\", "/"));
		file.getParentFile().mkdirs();
	}

	public void download() throws IOException {
		Logger.log("Acquiring file '" + this.file.getName() + "'");
		engine.getGameUpdater().setCurrentInfoText("Téléchargement '" + this.file.getName() + "'");
		BufferedInputStream bufferedInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			URL url_ = new URL(this.url);
			bufferedInputStream = new BufferedInputStream(url_.openStream());
			fileOutputStream = new FileOutputStream(this.file);
			

			byte[] data = new byte[1024];
			int read;

			while ((read = bufferedInputStream.read(data, 0, 1024)) != -1) {
				fileOutputStream.write(data, 0, read);
			}			
		} finally {
			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
	}

	public boolean requireUpdate() {
		if ((this.file.exists()) && (FileUtil.matchSHA1(this.file, this.sha1))) {
			return false;
		}
		return true;
	}
}
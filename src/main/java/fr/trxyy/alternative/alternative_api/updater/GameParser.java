package fr.trxyy.alternative.alternative_api.updater;

import java.io.File;
import java.net.Proxy;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameVerifier;
import fr.trxyy.alternative.alternative_api.utils.FileUtil;
import fr.trxyy.alternative.alternative_api.utils.LauncherFile;
import fr.trxyy.alternative.alternative_api.utils.Logger;

public class GameParser {

	public static void getFilesToDownload(GameEngine engine) {
		Logger.log("Preparation de la mise a jour.");
		try {
			final URL resourceUrl = new URL(engine.getGameLinks().getCustomFilesUrl() + "downloads.xml");
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			final DocumentBuilder db = dbf.newDocumentBuilder();
			final Document doc = db.parse(resourceUrl.openConnection(Proxy.NO_PROXY).getInputStream());
			final NodeList nodeLst = doc.getElementsByTagName("Contents");

			final long start = System.nanoTime();
			for (int i = 0; i < nodeLst.getLength(); i++) {
				final Node node = nodeLst.item(i);
				if (node.getNodeType() == 1) {
	                  final Element element = (Element) node;
	                  final String key = element.getElementsByTagName("Key").item(0).getChildNodes().item(0).getNodeValue().replace(" ", "").replace("\n", "");
	                  String etag = element.getElementsByTagName("ETag") != null ? element.getElementsByTagName("ETag").item(0).getChildNodes().item(0).getNodeValue() : "-";
	                  final long size = Long.parseLong(element.getElementsByTagName("Size").item(0).getChildNodes().item(0).getNodeValue());

					File localFile = new File(engine.getGameFolder().getGameDir(), key);
					GameVerifier.addToFileList(localFile.getAbsolutePath().replace(engine.getGameFolder().getGameDir().getAbsolutePath(), "").replace('/', File.separatorChar));
					if (!localFile.isDirectory()) {
						if (etag.length() > 1) {
							etag = FileUtil.getEtag(etag);
							if (localFile.exists()) {
								if (localFile.isFile() && localFile.length() == size) {
									final String localMd5 = FileUtil.getMD5(localFile);
									if (!localMd5.equals(etag)) {
										if (!(engine.getGameLinks().getCustomFilesUrl() + key).endsWith("/")) {
											engine.getGameUpdater().files.put(key, new LauncherFile(size, engine.getGameLinks().getCustomFilesUrl() + key, localFile.getAbsolutePath()));
										}
									}
								} else {
									if (!(engine.getGameLinks().getCustomFilesUrl() + key).endsWith("/")) {
										engine.getGameUpdater().files.put(key, new LauncherFile(size, engine.getGameLinks().getCustomFilesUrl() + key, localFile.getAbsolutePath()));
									}
								}
							} else {
								if (!(engine.getGameLinks().getCustomFilesUrl() + key).endsWith("/")) {
									engine.getGameUpdater().files.put(key, new LauncherFile(size, engine.getGameLinks().getCustomFilesUrl() + key, localFile.getAbsolutePath()));
								}
							}

						}
					} else {
						localFile.mkdir();
						localFile.mkdirs();
					}
				}
			}
			final long end = System.nanoTime();
			final long delta = end - start;
			Logger.log("Temps (delta) pour comparer les ressources: " + delta / 1000000L + " ms");
		} catch (final Exception ex) {
			Logger.log("Impossible de telecharger les ressources (" + ex + ")");
		}
	}

}

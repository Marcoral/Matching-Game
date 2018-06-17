package me.s17339.memorygame.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourcesExplorer {
	public static File getFile(String relativePath) {
		String path = System.getProperty("user.dir") + "\\";
		path += relativePath;
		File file = new File(path);
		if(file.exists())
			return file;
		else
			return null;
	}
	
	public static File forceGetFile(String relativePath) {
		try {
			String path = System.getProperty("user.dir") + "\\" + relativePath;
			File file = new File(path);
			if(!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public static void processAllEntriesInDirectory(String directory, Consumer<JarQueryResult> jarCase, Consumer<File> IDECase) {
		try {
			String jarpath = ResourcesExplorer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decodedPath = URLDecoder.decode(jarpath, "UTF-8");
			File jarFile = new File(decodedPath);

			if (jarFile.isFile()) { // Run with JAR file
				JarFile jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					String name = entry.getName();
					int index = name.indexOf(directory);
					if (index != -1 && !entry.isDirectory()) {
						String[] splitName = name.substring(index + directory.length()).split("/");
						if(splitName.length == 1)
							jarCase.accept(new JarQueryResult(jar, entry, splitName[0]));
					}
				}
				jar.close();
			} else { // Run with IDE
				URL url = ResourcesExplorer.class.getResource("/" + directory);
				if (url != null) {
					File apps = new File(url.toURI());
					for (File app : apps.listFiles())
						if(!app.isDirectory())
							IDECase.accept(app);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class JarQueryResult {
		private JarFile jar;
		private JarEntry entry;
		private String shortenedFileName;
		
		private JarQueryResult(JarFile jar, JarEntry entry, String shortenedFileName) {
			this.jar = jar;
			this.entry = entry;
			this.shortenedFileName = shortenedFileName;
		}
		
		public JarFile getJar() {
			return jar;
		}

		public JarEntry getEntry() {
			return entry;
		}

		public String getShortenedFileName() {
			return shortenedFileName;
		}
	}
	
	private static Boolean isInJar;
	public static boolean isInJar() {
		if(isInJar != null)
			return isInJar();
		else
			isInJar = new File(ResourcesExplorer.class.getProtectionDomain().getCodeSource().getLocation().getPath()).isFile();
		return isInJar;
	}
}

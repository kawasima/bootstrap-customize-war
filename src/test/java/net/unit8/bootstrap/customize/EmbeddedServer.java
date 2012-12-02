package net.unit8.bootstrap.customize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import winstone.Launcher;

public class EmbeddedServer {
	public static void main(String[] args) throws IOException {
		Map<String, String> serverArgs = new HashMap<String, String>();
		serverArgs.put("webroot", "src/main/webapp");
		serverArgs.put("prefix", "/");
		Launcher.initLogger(serverArgs);
		Launcher winstone = new Launcher(serverArgs);
		winstone.run();
	}
}

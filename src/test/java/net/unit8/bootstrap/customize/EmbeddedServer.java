package net.unit8.bootstrap.customize;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbeddedServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		SocketConnector socketConnector = new SocketConnector();
		socketConnector.setPort(8090);
		Connector[] connectors = new Connector[]{ socketConnector };
		server.setConnectors(connectors);
		WebAppContext context = new WebAppContext();
		context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
		context.setResourceBase("src/main/webapp");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);
		server.setHandler(context);
		server.start();
		server.join();
	}
}

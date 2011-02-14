package cs2ts6.server;

import java.io.IOException;

public class Server {
	public static void main() throws IOException{
		System.out.println("Launching Server");
		new CollectorServer().start();
		new ServerUDPThread().start();
	}
}

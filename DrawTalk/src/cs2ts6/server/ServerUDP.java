package cs2ts6.server;

import java.io.IOException;
/**
 * UDP Server Implementation
 * @author stephen
 *
 */
public class ServerUDP {
	public static void main(String[] args) throws IOException {
        new ServerUDPThread().start();
    }
}

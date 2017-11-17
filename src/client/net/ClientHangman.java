package client.net;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import client.view.ClientUserInterface;

public class ClientHangman {
	private static final int TIMEOUT=30000;
	public static void main(String [] args)
	{ 
		// Host declared as local host
				String host = "127.0.0.1";
				Socket socket;
				// Select any port greater than 1024
				int port = 4449;
				if (args != null && args.length > 0) {
					host = args[0];
					if (args.length > 1) {
						try {
							port = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				try {
					socket= new Socket();
					socket.connect(new InetSocketAddress(host,port),TIMEOUT);
					socket.setSoTimeout(1800000);
					//socket = new Socket(host, port);
					ClientUserInterface cui = new ClientUserInterface(socket);
					cui.start();
					cui.setupUserInterface(socket);
					System.out.println("start thread");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
}

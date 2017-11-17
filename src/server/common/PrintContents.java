package server.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PrintContents {
	private Socket socket;

	public PrintContents(Socket socket) {
		super();
		this.socket = socket;
	}
	
	public void passdatatogui(StringBuilder sb)
	{
		try {
			PrintWriter writer= new PrintWriter(socket.getOutputStream());
			writer.println(sb);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

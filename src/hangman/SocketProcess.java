package hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import client.view.ClientUserInterface;

public class SocketProcess extends Thread {
  private Socket soc;
  public ClientUserInterface cui;

  public SocketProcess(Socket soc) {
	super();
	this.soc = soc;
}
  


@Override
public void run() {
	// TODO Auto-generated method stub
	super.run();
	System.out.println("The local port:"+" "+soc.getLocalPort());
	String str;
	try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		
		while((str=reader.readLine())!= null )
		{   
			String[] msg= str.split(",");
			cui.wordDisp.setText(msg[0]);
			System.out.println("Message[0]"+msg[0]);
			cui.message.setText(msg[1]);
			cui.attempts.setText(msg[2]);
			cui.score.setText(msg[3]);
		}
		soc.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
  
}

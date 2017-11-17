package server.net;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.controller.Handler;

public class ServerHangman {
	
	private static ArrayList<String> words;
	
   public static void main(String[] args)
   {
	   int port=4449;
	   String data;
	   words= new ArrayList<>();
	   if(args!=null && args.length>0)
	   {
		   try {
			port=Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   }
	   try {
			BufferedReader reader= new BufferedReader(new FileReader("words.txt"));
			while((data=reader.readLine())!=null)
			{
				words.add(data);	
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   try {
		//server opens a listening socket on the specified port number
		ServerSocket listeningsocket = new ServerSocket(port);
		while(true)
		{
			//accepts connection requests on the socket
			Socket clientSocket= listeningsocket.accept();
			Handler ServerThread= new Handler(words,clientSocket);
			//sets maximum priority to the current thread being processed
			ServerThread.setPriority(Thread.MAX_PRIORITY);
			ServerThread.start();
			System.out.println("connection established with client");
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   
}

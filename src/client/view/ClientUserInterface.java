package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientUserInterface extends Thread{
	/**
	 * 
	 */
	public JLabel score;
	public JLabel attempts;
	public JTextField answer;
	public JButton send;
	public JLabel wordDisp;;
	public JLabel message;
	public JButton newgame;
	private Socket socket;
	public JPanel toppanel,middlepanel,bottompanel,panel;
	PrintWriter pw;
	
	public ClientUserInterface(Socket socket) {
		super();
		this.socket = socket;
	}

	public void msgsnd(String msg)
	{  
		PrintWriter pw;
		try {
			pw = new PrintWriter(socket.getOutputStream());
			pw.println(msg);
			System.out.println("message is"+ " "+msg);
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setupUserInterface(Socket soc)
	{
		score= new JLabel("SCORE:");
		attempts= new JLabel("REMAINING:");
		answer= new JTextField();
		answer.setColumns(50);
		send= new JButton("SEND");
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					String answerval= answer.getText();
					if(answerval.length()>0)
					{
						answer.setText("");
						msgsnd(answerval);
					}
					answer.requestFocus();
			}
		});
		wordDisp=new JLabel();
		message= new JLabel();
		newgame= new JButton("NEW GAME");
		newgame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			        	msgsnd("start");
						answer.requestFocus();
					}
		});
		toppanel= new JPanel();
		middlepanel= new JPanel();
		bottompanel= new JPanel();
		toppanel.setLayout(new BoxLayout(toppanel, BoxLayout.Y_AXIS));
		toppanel.setBackground(Color.black);
		toppanel.setForeground(Color.WHITE);
		toppanel.add(score).setForeground(Color.white);
		toppanel.add(Box.createHorizontalGlue());
		toppanel.add(attempts).setForeground(Color.white);
		middlepanel.setBackground(Color.orange);
		middlepanel.setLayout(new BoxLayout(middlepanel,BoxLayout.LINE_AXIS));
		middlepanel.add(message).setForeground(Color.blue);
		middlepanel.add(Box.createHorizontalGlue());
		middlepanel.add(wordDisp);
		middlepanel.add(Box.createHorizontalGlue());
		middlepanel.add(newgame);
		bottompanel.add(answer);
		bottompanel.add(send);
		panel = new JPanel(new BorderLayout());
		panel.add(toppanel, BorderLayout.PAGE_START);
		panel.add(middlepanel, BorderLayout.CENTER);
		panel.add(bottompanel, BorderLayout.PAGE_END);

		JFrame frame = new JFrame("Let's Play Hangman");
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		String str;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while((str=reader.readLine())!= null )
			{   
				String[] msg= str.split(",");
				System.out.println("The message contents:"+ str);
				wordDisp.setText(msg[0]);
				System.out.println("Message[0]"+msg[0]);
				message.setText(msg[1].toUpperCase());
				System.out.println("Message[1]"+msg[1]);
				attempts.setText("REMAINING: "+msg[2]);
				System.out.println("Message[2]"+msg[2]);
				score.setText("SCORE: "+msg[3]);
				System.out.println("Message[3]"+msg[3]);
			}
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}


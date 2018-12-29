import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Client extends JFrame{

	private JTextField usertext;
	private JTextArea chatwindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	//constructor
	public Client(String host) {
		super("Client");
		serverIP=host;
		usertext=new JTextField();
		usertext.setEditable(false);
		usertext.addActionListener(
               new  ActionListener(){
					public void actionPerformed(ActionEvent event) {
						sendmessage(event.getActionCommand());
						usertext.setText("");
					}
				}
				);
		add(usertext,BorderLayout.NORTH);
		chatwindow=new JTextArea();
		add(new JScrollPane(chatwindow),BorderLayout.CENTER);
		setSize(450,200);
		setVisible(true);
	}
	
	//running
	public void startrunning() {
		try {
			connecttoserver();
			setupstreams();
			whilechatting():
		}catch(EOFException eofException) {
			showmessage("\n Client terminated connection");
		}catch(IOException ioException) {
			ioException.printStackTrace():
		}finally {
			closestuff();
		}
	}
	
	//connecting to server
	private void connecttoserver() throws IOException{
		showmessage("Attempting connection.\n");
		connection=new Socket(InetAddress.getByName(serverIP),6789);
        showmessage("Connected to:"+ connection.getInetAddress().getHostName());
	}
	
	
	
	
}

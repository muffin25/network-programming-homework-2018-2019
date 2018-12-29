import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Server extends JFrame
{
	
private JTextField usertext;
private JTextArea chatwindow;
private ObjectOutputStream output;
private ObjectInputStream input;
private ServerSocket server;
private Socket connection;

// constructor
public Server()
{
	super("Message server");
	usertext = new JTextField();
	usertext.setEditable(false);
	usertext.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			sendmessage(event.getActionCommand());
			usertext.setText("");
		}
	});
	add(usertext, BorderLayout.NORTH);
	chatwindow = new JTextArea();
	add(new JScrollPane(chatwindow));
	setSize(450,200);
	setVisible(true);
}

//setting up the server
public void startrunning()
{
try
{
server = new ServerSocket(6789,10);
while(true)
{
try {
waitforconnection();
setupstreams();
whilechatting();
}catch(EOFException eofException) {showmessage("\n Server ended the connection.");
}finally{closestuff();
}
}
}catch(IOException ioException) { ioException.printStackTrace();}
}

//wait for connection , then display info
private void waitforconnection() throws IOException{
	showmessage("Waiting for someone to connect.\n");
	connection=server.accept(); 
	showmessage("Now connected to" + connection.getInetAddress().getHostName());
}

//get stream
private void setupstreams() throws IOException{
	output= new ObjectOutputStream(connection.getOutputStream());
	output.flush();
	input=new ObjectInputStream(connection.getInputStream());
	showmessage("\n Streams are set up. \n");
}

//while the conversation is active
private void whilechatting() throws IOException{
	String message = "You are connected.";
	sendmessage(message);
	abletotype(true);
	do {
		try {
			message=(String)input.readObject();
			showmessage("\n "+message);
		}catch(ClassNotFoundException classNotFoundException) {showmessage("\n Unknown data sent by user");}
	}while(!message.equals("CLIENT-END"));
}

//closing the streams and sockets
private void closestuff() {
	showmessage("\n Closing connections. \n");
	abletotype(false);
	try {
		output.close();
		input.close();
		connection.close();
		
	}catch(IOException ioException) {
		ioException.printStackTrace();
	}
}










}

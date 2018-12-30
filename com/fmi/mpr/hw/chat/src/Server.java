import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Server extends JFrame
{
byte[] receiveData;
byte[] sendData ;	
private String message = "";
private JTextField usertext;
private JTextArea chatwindow;
private DatagramSocket serversocket;

// constructor
public Server()
{
	super("Message server");
	receiveData = new byte [1024];
    sendData = new byte [1024];
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
	add(new JScrollPane(chatwindow),BorderLayout.CENTER);
	setSize(450,200);
	setVisible(true);
}

//running
	public void startrunning()
	{
	try{
		serversocket = new DatagramSocket(6789);
		while(true){
			try{
				whilechatting();
			}catch(EOFException eofException){
				showmessage("\n Server ended the connection! ");
			} finally{
				closestuff(); 
			}
		}
	} catch (IOException ioException){
		ioException.printStackTrace();
	}
	}


//while the conversation is active
private void whilechatting() throws IOException{
	abletotype(true);
	do {
	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    serversocket.receive(receivePacket);
    String message = new String( receivePacket.getData(),0, receivePacket.getLength());
    InetAddress IPAddress = receivePacket.getAddress();
    int port = receivePacket.getPort();
    showmessage("\n"+IPAddress+" - "+port + " - "+message);
    
    sendData=(port+" - "+message).getBytes();
    DatagramPacket sendPacket =
    new DatagramPacket(sendData, sendData.length,IPAddress , port);
    serversocket.send(sendPacket);
    
    
    
	}while(true);
}


//updating the chat
private void showmessage(final String text) {
	SwingUtilities.invokeLater(
			new Runnable(){
				public void run() {
					chatwindow.append(text);
				}
			}
			);
}

//letting the user type
private void abletotype(final boolean tof){
	SwingUtilities.invokeLater(
			new Runnable(){
				public void run() {
					usertext.setEditable(tof);
				}
			}
			);
}

//closing everything
	private void closestuff() {
		showmessage("\n closing eveything down");
		abletotype(false);
		serversocket.close();
	}

//send messages to clients
	private void sendmessage(String message) 
	{
		
			showmessage("\nServer - " + message);	
		
	}

}

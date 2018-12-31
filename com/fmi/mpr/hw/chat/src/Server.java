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
private MulticastSocket multicastsocket;
private InetAddress group;

// constructor
public Server()
{
	super("Message server");
	new File("Server").mkdir();
	receiveData = new byte [1024];
    sendData = new byte [1024];
    try {
		group = InetAddress.getByName("225.4.5.6");
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}
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
		multicastsocket=new MulticastSocket();
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
	int n=0;
	
	//receiving file from client
	/*
	do {

		File f1 = new File("Server\\123.txt");
		//f1.createNewFile();
		boolean exists = f1.exists();
		if(exists==false)
		{
		FileOutputStream bos = new FileOutputStream(f1,true);
		byte[] buf = new byte[63*1024];
		DatagramPacket pkg = new DatagramPacket(buf, buf.length);

		while(true)
		{
		serversocket.receive(pkg);
		if (new String(pkg.getData(), 0, pkg.getLength()).equals("end")) 
		{ 
			n=1;
		System.out.println("Documents received");
		bos.close();
	    break;
		}
		bos.write(pkg.getData(), 0, pkg.getLength());
		bos.flush(); 
		}
		bos.close();
		}
		
	}while(n==0);
	*/
	
	//sending file to all clients
	
		/*
		File f2 =new File("Server\\123.txt");
		FileInputStream bis = new FileInputStream(f2);
		byte[] buf = new byte[63*1024];
		int len;

		DatagramPacket pkg = new DatagramPacket(buf, buf.length,group,1234);
		while((len=bis.read(buf))!=-1)
		{
		serversocket.send(pkg);
		}
		buf = "end".getBytes();
		DatagramPacket endpkg = new DatagramPacket(buf, buf.length,group,1234);
		System.out.println("Send the file.");
		multicastsocket.send(endpkg);
		bis.close();	
		*/
		
		
	/*	
	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    serversocket.receive(receivePacket);
    message = new String( receivePacket.getData(),0, receivePacket.getLength());
    int port = receivePacket.getPort();
    showmessage("\n"+port + " - "+message);
    
    sendData=(port+" - "+message).getBytes();
    DatagramPacket sendPacket =
    new DatagramPacket(sendData, sendData.length,group , 1234);
    multicastsocket.send(sendPacket);
    */
    
    
    
	//}while(true);
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

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Client extends JFrame
{
	byte[] receiveData;
	byte[] sendData ;	
	private JTextField usertext;
	private JTextArea chatwindow;
	private String message = "";
	private String serverIP;
	private DatagramSocket serversocket;
	private MulticastSocket multicastsocket;
	private InetAddress IPAddress ;
	private InetAddress group;
	
	//constructor
	public Client(String host, String name) 
	{
		super(name);
		new File(name).mkdir();
		serverIP=host;
		receiveData = new byte [1024];
	    sendData = new byte [1024]; 
	    try {
	    	group = InetAddress.getByName("225.4.5.6");
			IPAddress = InetAddress.getByName(serverIP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
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
	public void startrunning()
	{
	try{
		multicastsocket=new MulticastSocket(1234);
		multicastsocket.joinGroup(group);
		serversocket = new DatagramSocket();
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
	
	//while chatting
	private void whilechatting() throws IOException{
		abletotype(true);
		//do {

			File f2 =new File("Charlie\\kitty.jpg");
			FileInputStream bis = new FileInputStream(f2);
			byte[] buf = new byte[63*1024];
			int len;

			DatagramPacket pkg = new DatagramPacket(buf, buf.length,InetAddress.getByName("127.0.0.1"),6789);
			while((len=bis.read(buf))!=-1)
			{
			serversocket.send(pkg);
			}
			buf = "end".getBytes();
			DatagramPacket endpkg = new DatagramPacket(buf, buf.length,InetAddress.getByName("127.0.0.1"),6789);
			System.out.println("Send the file.");
			serversocket.send(endpkg);
			bis.close();
			
			/*
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		    multicastsocket.receive(receivePacket);
		    message = new String( receivePacket.getData(),0, receivePacket.getLength());
		    showmessage("\n"+message);
		    */
			//}while(true);
	}
	
	//closing everything
	private void closestuff() {
		showmessage("\n closing eveything down");
		abletotype(false);
		serversocket.close();
		multicastsocket.close();
	}
	
	//send messages to server
	private void sendmessage(String message) 
	{
		sendData=message.getBytes();
		try{
		DatagramPacket sendPacket =
			    new DatagramPacket(sendData, sendData.length, IPAddress, 6789);
			    serversocket.send(sendPacket);
		}catch(IOException ioException){
			chatwindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
		}
			
	}
	
	//updating the chat
	private void showmessage(final String m) {
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run() {
						chatwindow.append(m);
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

	
	
	
}

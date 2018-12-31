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
	private String name;
	private boolean sending;
	
	//constructor
	public Client(String host, String n) 
	{
		super(n);
		name=n;
		sending=false;
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
						send(event.getActionCommand());
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
		do {
		    message=gettingmessage();
			
		    if(message.contentEquals("Sending...")&&(sending==false))
		    {
		     abletotype(false);
		     String filename=gettingmessage();
		     gettingfile(filename);
		    }
		    
		    if(message.contentEquals("Sending...")&&(sending==true))
		    {
		     String filename=gettingmessage();
		     multicastsocket.leaveGroup(group);
		     sendingfile(filename);
		     multicastsocket.joinGroup(group);
		    }
		    
		    
		    
		    
		    if(message.contentEquals("File sent"))
		    	abletotype(true);
		    
			}while(true);
	}
	
	
	
	private String gettingmessage() throws IOException
	{
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    multicastsocket.receive(receivePacket);
	    message = new String( receivePacket.getData(),0, receivePacket.getLength());
	    showmessage("\n"+message);
	    return message;
	}
	
	
	
	
	private void gettingfile(String filename) throws IOException
	{
		//receiving file from server
		
		int n=0;
		
		File f1 = new File(name+"\\"+filename);
		//f1.createNewFile();
		boolean exists = f1.exists();
		if(exists==false)
		{
		FileOutputStream bos = new FileOutputStream(f1,true);
		byte[] b = new byte[63*1024];
		DatagramPacket p = new DatagramPacket(b, b.length);

		while(n==0)
		{
		multicastsocket.receive(p);
		if (new String(p.getData(), 0, p.getLength()).equals("end")) 
		{ 
			n=1;
		bos.close();
	    break;
		}
		bos.write(p.getData(), 0, p.getLength());
		bos.flush(); 
		}
		bos.close();
		}
	}
	
	
	
	
	
	
	private void sendingfile(String filename) throws IOException
	{
		//sending file to server
		
		File f2 =new File(name+"\\"+filename);
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
	
	
	
	private void send(String message) 
	{
		if(!message.contentEquals("Send file"))
		{
		sendmessage(message);
		}
		else 
		{
		sending=true;
		sendmessage(message);
					
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

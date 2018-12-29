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
}

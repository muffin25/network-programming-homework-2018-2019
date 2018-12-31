import javax.swing.JFrame;

public class ClientTest3 {
	public static void main(String[] args) 
	{
		Client dary;
		dary = new Client("127.0.0.1", "Dary" );
		dary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dary.startrunning();
	}
}
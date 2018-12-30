import javax.swing.JFrame;

public class ClientTest2 {
	public static void main(String[] args) 
	{
		Client daisy;
		daisy = new Client("127.0.0.1", "Daisy" );
		daisy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		daisy.startrunning();
	}
}
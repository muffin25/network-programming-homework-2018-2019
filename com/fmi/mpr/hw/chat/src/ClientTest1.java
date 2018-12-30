import javax.swing.JFrame;

public class ClientTest1 {
	public static void main(String[] args) 
	{
		Client charlie;
		charlie = new Client("127.0.0.1", "Charlie" );
		charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		charlie.startrunning();
	}
}
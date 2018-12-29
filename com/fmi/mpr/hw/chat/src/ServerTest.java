import javax.swing.JFrame;

public class ServerTest {
public static void main(String[] args) {
	Server muffin = new Server();
	muffin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	muffin.startrunning();
}
}

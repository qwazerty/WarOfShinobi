
//import java.awt.Frame;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

//import javax.swing.JFrame;

public class Server {
	
	public static final int POSITION = 1;
	public static final int CHAT     = 2;
	
	//private JFrame frame;
	private ServerSocket server;
	private Socket socket;
	private DataInputStream dIn;
	private DataOutputStream dOut;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private int idPlayer;

	protected static int nbConnection;
	@SuppressWarnings("rawtypes")
	public static ArrayList aPosition = new ArrayList();
	@SuppressWarnings("rawtypes")
	public static ArrayList aChat = new ArrayList();
	
	@SuppressWarnings("unchecked")
	Server() throws IOException {
		
		/*frame = new JFrame("War of Shinobi - Server");
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setState(Frame.ICONIFIED);*/
		nbConnection = 0;
		idPlayer = 0;
		server = new ServerSocket(25565);
		server.setSoTimeout(1000);
		// ID Position Array
		aPosition.add(1);
		// Nb Player in Array
		aPosition.add(-1);
		// Init Chat
		aChat.add(2);
		System.out.println("Server opened !");
		while(true) {
			try {
				socket = server.accept();
				nbConnection++;
				idPlayer++;
				// Nb Player in Array
				aPosition.set(1, idPlayer);
				// New ID of new Player
				aPosition.add(idPlayer);
				System.out.print("[INFO] New Player ! ID : " + idPlayer + "\n[INFO] Online Players : " + nbConnection + "\n");
				dIn = new DataInputStream(socket.getInputStream());
				objIn = new ObjectInputStream(dIn);
				dOut = new DataOutputStream(socket.getOutputStream());
				objOut = new ObjectOutputStream(dOut);
				objOut.writeObject(idPlayer);
				SendThread sendThread = new SendThread(objOut, idPlayer);
				Thread sThread = new Thread(sendThread);
				sThread.start();

				ReceiveThread receiveThread = new ReceiveThread(objIn, idPlayer);
				Thread rThread = new Thread(receiveThread);
				rThread.start();
			}
			catch (SocketTimeoutException se) {
				System.out.println(" #" + aPosition);
				System.out.println(" #" + aChat);
			}
			
		}
	}
	
	public static void main(String[] args)
	{
		try {
			new Server();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

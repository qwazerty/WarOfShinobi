
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

class ReceiveThread implements Runnable, Serializable{

	private ObjectInputStream objIn;
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	private ArrayList arrayNet = new ArrayList();
	private int id;
	
	ReceiveThread(ObjectInputStream objIn, int id) {
		this.objIn = objIn;
		this.id = id;
	}
	
	public void run() {
		try {
			while(true)
			{
				arrayNet = (ArrayList<?>)objIn.readObject();
				CheckArrayCode();
			}
		}
		catch(SocketTimeoutException se) {
			System.out.println("[INFO] ReceiveThread Killed !");
		}
		catch(Exception oe) {
			System.out.println("[INFO] ReceiveThread Killed !");
		}
	}
	
	private void CheckArrayCode() {
		switch ((Integer) arrayNet.get(0)) {
			case Server.POSITION: UpdatePosition(); break;
			case Server.CHAT    : UpdateChat();     break;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void UpdatePosition() {
		Server.aPosition.set(id *6 - 3, arrayNet.get(3));
		Server.aPosition.set(id *6 - 2, arrayNet.get(4));
		Server.aPosition.set(id *6 - 1, arrayNet.get(5));
		Server.aPosition.set(id *6 + 0, arrayNet.get(6));
		Server.aPosition.set(id *6 + 1, arrayNet.get(7));
	}
	
	@SuppressWarnings("unchecked")
	private void UpdateChat() {
		Server.aChat.add(id);
		Server.aChat.add(arrayNet.get(2));
	}
}

class SendThread implements Runnable, Serializable{
	
	private static final long serialVersionUID = 1L;
	private ObjectOutputStream objOut;
	//private int id;
	
	SendThread(ObjectOutputStream objOut, int id) {
		this.objOut = objOut;
		//this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			Server.aPosition.add(300); // Position X
			Server.aPosition.add(245); // Position Y
			Server.aPosition.add(0);   // Speed X
			Server.aPosition.add(0);   // Speed Y
			Server.aPosition.add(0);   // Clip XY
			while(true)
			{
				objOut.writeObject(Server.aPosition);
				objOut.writeObject(Server.aChat);
				objOut.reset();
				Thread.sleep(10);
			}
		}
		catch(SocketTimeoutException se) {
			Server.nbConnection--;
			System.out.print("[INFO] Player Disconnect by Timeout ! Online Players : " + Server.nbConnection + "\n");
		}
		catch(Exception oe) {
			Server.nbConnection--;
			System.out.print("[INFO] Player Left ! Online Players : " + Server.nbConnection + "\n");
		}
	}
}

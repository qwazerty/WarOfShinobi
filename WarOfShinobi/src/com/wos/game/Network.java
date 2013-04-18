package com.wos.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;



public class Network {
	
	private static Socket socket;
	private static DataInputStream dIn;
	private static DataOutputStream dOut;
	
	protected static ObjectInputStream objIn;
	protected static ObjectOutputStream objOut;
	protected static int myID;
	@SuppressWarnings("rawtypes")
	protected static ArrayList aPosition = new ArrayList();
	@SuppressWarnings("rawtypes")
	protected static ArrayList aChat = new ArrayList();
	@SuppressWarnings("rawtypes")
	protected static ArrayList arrayNet = new ArrayList(); 
	@SuppressWarnings("rawtypes")
	protected static ArrayList aPositionNet = new ArrayList();
	@SuppressWarnings("rawtypes")
	protected static ArrayList aChatNet = new ArrayList();
	protected static boolean received = false;
	
	public static void Init()
	{
		try {
			socket = new Socket("192.168.0.1" ,25565);
			dIn = new DataInputStream(socket.getInputStream());
			dOut = new DataOutputStream(socket.getOutputStream());
			objOut = new ObjectOutputStream(dOut);
			objIn = new ObjectInputStream(dIn);
			myID = (Integer)objIn.readObject();
			System.out.println("Hello ! Player ID : " + myID);
			ReceiveThread receiveThread = new ReceiveThread();
			Thread rThread = new Thread(receiveThread);
			rThread.start();
			while (!received) {
				Thread.sleep(100);
			}
			Player.Init(aPositionNet);
			SendThread sendThread = new SendThread();
			Thread sThread = new Thread(sendThread);
			sThread.start();
		} 
		catch (UnknownHostException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); } 
		catch (ClassNotFoundException e) { e.printStackTrace(); } 
		catch (InterruptedException e) { e.printStackTrace(); } 
		catch (SlickException e) { e.printStackTrace();	}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void AddChat(String message) {
		aChat.add(myID);
		aChat.add(message);
	}
	
	@SuppressWarnings("rawtypes")
	public static ArrayList getPosition()
	{
		return aPositionNet;
	}
	
	@SuppressWarnings("rawtypes")
	public static void setPosition(ArrayList arr)
	{
		aPositionNet = arr;
	}
	
	@SuppressWarnings("rawtypes")
	public static ArrayList getChat()
	{
		return aChatNet;
	}
	
	@SuppressWarnings("rawtypes")
	public static void setChat(ArrayList arr)
	{
		aChatNet = arr;
	}
	
	public static int getID()
	{
		return myID;
	}
	
	public static boolean getReceived()
	{
		return received;
	}
	
	public static void setReceived()
	{
		received = false;
	}
}

class ReceiveThread extends Network implements Runnable {

	private static final long serialVersionUID = 1L;
	
	ReceiveThread() {
	}
	
	public void run() {
		while(true) {
			try {
				arrayNet = (ArrayList<?>)objIn.readObject();
				CheckArrayCode();
			}
			catch(Exception oe){ }
		}
	}
	
	private static void CheckArrayCode() {
		switch ((Integer) arrayNet.get(0)) {
			case 1:
				aPositionNet = arrayNet;
				received = true;
				break;
			case 2:
				aChatNet = arrayNet;
				break;
		}
	}
}

class SendThread extends Network implements Runnable {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	SendThread() {
		aPosition = aPositionNet;
		aChat.add(2);
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		while(true) {
			try {
				aPosition.set(3, (Player.getX() - Map.getX()));
				aPosition.set(4, (Player.getY() - Map.getY()));
				aPosition.set(5, Player.getSpeedX());
				aPosition.set(6, Player.getSpeedY());
				aPosition.set(7, Player.ClipToArray()); 
				objOut.writeObject(aPosition);
				SendChat();
				objOut.reset();
				Thread.sleep(10);
			}
			catch(Exception oe){ }
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void SendChat() throws IOException {
		if (aChat.size() != 1) {
			objOut.writeObject(aChat);
			aChat.clear();
			aChat.add(2);
		}
	}
}
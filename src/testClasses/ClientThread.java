package testClasses;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread{

	private Socket socket;

	public ClientThread(Socket socket) {
		this.socket = socket;
		start();
	}

	public void run() {
		try {
			DataInputStream din = new DataInputStream( socket.getInputStream() );
			while (true) {
				System.out.println(din.readUTF());
			}
		} catch( EOFException ie ) {
			// This doesn't need an error message
		} catch( IOException ie ) {
			// This does; tell the world!
			System.out.println(ie.toString());
			ie.printStackTrace();
		} finally {
			System.out.println("Connection lost.");
		}
	}
}
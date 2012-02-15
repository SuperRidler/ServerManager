package testClasses;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ViewAllGames {

	String user = "Jamie";
	String address = "127.0.0.1";
	int port = 4444;
	Socket socket;
	DataInputStream din;
	DataOutputStream dout;
	Scanner s;
	
	
	public ViewAllGames(){
		if(connectToServer(address, port)){
			new ClientThread(socket);
			try {
				dout.writeUTF("getAllServers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		while(true){
			
		}
	}

	public static void main(String[] args) {
		new ViewAllGames();
	}

	public boolean connectToServer(String address, int port){
		 boolean con = true;
		  try {
			  socket = new Socket(address, port);
			  System.out.println( "Connected to "+socket );
			  din = new DataInputStream( socket.getInputStream() );
			  dout = new DataOutputStream( socket.getOutputStream() );
			  } catch( IOException ie ) {
				  System.out.println("Cannot connect to "+address+" on port "+String.valueOf(port)+".");
				  con = false;
				  System.exit(0);
				  }
		  return con;
	}
}
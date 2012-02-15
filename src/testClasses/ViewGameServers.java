package testClasses;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ViewGameServers {
	
	String game = "Minecraft";
	String user = "Jamie";
	String address = "127.0.0.1";
	int port = 4444;
	Socket socket;
	DataInputStream din;
	DataOutputStream dout;
	Scanner s;
	
	
	public ViewGameServers(){
		if(connectToServer(address, port)){
			new ClientThread(socket);
			try {
				dout.writeUTF("getServers;"+game);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		while(true){
			
		}
	}

	public static void main(String[] args) {
		new ViewGameServers();
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
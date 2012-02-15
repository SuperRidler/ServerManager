
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	private ServerSocket ss;
	private Hashtable<Socket, DataOutputStream> outputStreams = new Hashtable<Socket, DataOutputStream>();
	static final int PORT = 4444;
	static Manager manager;
	
	public Server( int port ) throws IOException {
		listen( port );
	}
	
	private void listen( int port ) throws IOException {
		ss = new ServerSocket( port );
		System.out.println("Listening on "+ ss);
		while(true){
			Socket s = ss.accept();
			System.out.println("Connection from "+s);
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			outputStreams.put(s, dout);
			new ServerThread(this, s);
		}
	}

	
	Enumeration<DataOutputStream> getOutputStreams(){
		return outputStreams.elements();
	}
	
	void sendToAll(String message){
		//System.out.println("Recieved: "+message);
		synchronized(outputStreams){
			for(Enumeration<DataOutputStream> e = getOutputStreams(); e.hasMoreElements();){
				DataOutputStream dout = (DataOutputStream)e.nextElement();
				try{
					dout.writeUTF(message);
				}catch(IOException ie){
					ie.printStackTrace();
				}
			}
		}
	}
	
	
	void removeConnection(Socket s){
		synchronized(outputStreams){
			outputStreams.remove(s);
			try{
				s.close();
			} catch(IOException ie){
				ie.printStackTrace();
			}
		}
	}
	
	static public void main(String args[]) throws Exception{
		manager = new Manager();
		new Server(PORT);
	}
	
}
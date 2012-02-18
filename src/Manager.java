import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class Manager {
	
	ArrayList<GameServer> serverList = new ArrayList<GameServer>();
	long time = System.nanoTime();
	
	public Manager(){

	}
	
	public void interpret(Socket socket, String message){
		String[] mParts;
		mParts = message.split(";");
		try{
			if(mParts[0].equals("addServer")){
				addServer(socket, mParts[1], mParts[2], mParts[3], Integer.parseInt(mParts[4]), Integer.parseInt(mParts[5]));
			}else if(mParts[0].equals("getServers")){
				getServers(socket, mParts[1]);
			}else if(mParts[0].equals("getAllServers")){
				getAllServers(socket);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Probably bad message.");
		}
	}
	
	
	private void addServer(Socket socket, String game, String name, String description, int maxPlayers, int currentPlayers){
		/*if((System.nanoTime()-time)>50000000){
			updateServer();
			time = System.nanoTime();
		}*/
		synchronized(serverList){
			boolean inAlready = false;
			for(GameServer gs : serverList){
				if(gs.getSocket().equals(socket)){
					inAlready = true;
					gs.updatePlayers(currentPlayers);
					gs.updateTime(System.nanoTime());
					break;
				}
			}
			if(!inAlready){
				serverList.add(new GameServer(socket, game, name, description, maxPlayers, currentPlayers, System.currentTimeMillis()));
			}
		}
	}
	
	public void updateServer() {
		try{
			synchronized(serverList){
				for(GameServer gs : serverList){
					if((System.currentTimeMillis()-gs.getTime())>10000.0){
						serverList.remove(gs);
					}
				}
			}
		}catch(ConcurrentModificationException cme){
			//Shouldn't be a problem as it is sychronized.
		}
	}
	
	private void getServers(Socket socket, String game){
		/*if((System.nanoTime()-time)>5000000){
			updateServer();
			time = System.nanoTime();
		}*/
		synchronized(serverList){
			String serverString = "";
			boolean flag = false;
			for(GameServer gs : serverList){
				if(gs.getGame().equals(game)){
					if(flag){
						serverString += ";";
					}
					serverString += gs.getSocket().getInetAddress().toString() +";"+ gs.getSocket().getPort() +";"+gs.getGame()+";"+gs.getName()+";"+gs.getDescription()+";"+gs.getCurrentPlayers()+";"+gs.getMaxPlayers();
					flag = true;
				}
			}
			if(serverString.equals("")){
				serverString = "None";
			}
			try{
				DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF(serverString);
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
	
	private void getAllServers(Socket socket){
		/*if((System.nanoTime()-time)>5000000){
			updateServer();
			time = System.nanoTime();
		}*/
		synchronized(serverList){
			String serverString = "";
			int j = serverList.size();
			for(int i=0; i<j; i+=1){
				GameServer gs = serverList.get(i);
				serverString += gs;
				if(i != j-1){
					serverString += ";";
				}
			}
			if(serverString.equals("")){
				serverString = "None";
			}
			try{
				DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF(serverString);
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
	
	public void consoleInput(String message){
		if(message.equals("update")){
			updateServer();
			System.out.println("Server Updated.");
		}else if(message.equals("show")){
			displayAllServers();
		}
	}
	
	private void displayAllServers(){
		synchronized(serverList){
			System.out.println("Servers:");
			if(serverList.isEmpty()){
				System.out.println("No Servers");
			}else{
				for(GameServer gs : serverList){
					System.out.println(gs);
					System.out.println("Done");
				}
			}
		}
	}
	
}

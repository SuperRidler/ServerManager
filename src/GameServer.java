import java.net.Socket;


public class GameServer {
	
	private String game, name, description;
	private int maxPlayers, currentPlayers;
	private Socket socket;
	private long time;
	
	public GameServer(Socket socket, String game, String name, String description, int maxPlayers, int currentPlayers, long time){
		this.socket = socket;
		this.game = game;
		this.name = name;
		this.description = description;
		this.maxPlayers = maxPlayers;
		this.currentPlayers = currentPlayers;
		this.time = time;
	}
	
	public void updatePlayers(int currentPlayers){
		this.currentPlayers = currentPlayers;
	}
	
	public void updateTime(long time){
		this.time = time;
	}
	
	public String getName(){
		return name;
	}
	
	public String getGame(){
		return game;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getMaxPlayers(){
		return maxPlayers;
	}
	
	public int getCurrentPlayers(){
		return currentPlayers;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public long getTime(){
		return time;
	}
	
	@Override
	public String toString(){
		return socket.getInetAddress()+";"+socket.getPort() +";"+game+";"+name+";"+description+";"+currentPlayers+";"+maxPlayers; 
	}
	
}

import java.util.Scanner;


public class ConsoleThread extends Thread {
	
	private Manager manager;
	private Scanner scanner;
	
	public ConsoleThread(Manager manager){
		this.manager = manager;
		scanner = new Scanner(System.in);
		start();
	}
	
	public void run(){
		while(true){
			String message = "";
			if((message=scanner.nextLine())!=null){
				manager.consoleInput(message);
				message = null;
			}
		}
	}
	
}

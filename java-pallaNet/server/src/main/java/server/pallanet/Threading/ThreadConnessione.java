package server.pallanet.Threading;

import java.io.IOException;
import java.net.ServerSocket;

import server.pallanet.PallaNetServer;

public class ThreadConnessione implements Runnable {
	
	private PallaNetServer finestra;
	private Thread me;
	
	public ThreadConnessione(PallaNetServer finestra) {
		this.finestra = finestra;
		me = new Thread(this);
		me.start();
	}
	
	public void run() {
		try {
			ServerSocket server = new ServerSocket(6789);
			finestra.setConnessione(server.accept());
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

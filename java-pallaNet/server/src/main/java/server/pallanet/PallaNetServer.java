package server.pallanet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import server.pallanet.Threading.ThreadConnessione;
import server.pallanet.interazioni.PannelloAnimazione;

public class PallaNetServer extends JFrame {

	private Socket connessione;
	private DataOutputStream out;
	private DataInputStream in;
	
	public PallaNetServer() {
		 super("PallaNet - Server");
		 this.setSize(500, 400);
		 this.setLocationRelativeTo(null);
		 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 ThreadConnessione waitConnessione = new ThreadConnessione(this);
		 this.setVisible(true);
	}

	public void setConnessione(Socket accept) throws IOException {
		
		this.connessione = accept;
		
		out = new DataOutputStream(connessione.getOutputStream());
		in = new DataInputStream(connessione.getInputStream());
		
		PannelloAnimazione pan = new PannelloAnimazione(this, this.getSize());
		this.add(pan);
	}

	public DataOutputStream getOut() {
		return out;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}

	public DataInputStream getIn() {
		return in;
	}

	public void setIn(DataInputStream in) {
		this.in = in;
	}

	public Socket getConnessione() {
		return connessione;
	}
	
	
	
}

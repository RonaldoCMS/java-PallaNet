package client.pallanet;

import java.awt.Container;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import client.pallanet.interazioni.PannelloAnimazione;
import client.pallanet.interazioni.PannelloClient;

public class PallaNetServer extends JFrame {

	private Socket connessione;
	private DataOutputStream out;
	private DataInputStream in;
	
	public PallaNetServer() {
		 super("PallaNet - Client");
		 this.setSize(500, 400);
		 this.setLocationRelativeTo(null);
		 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 connetti();
		 this.setVisible(true);
	}

	public void connetti() {
		try {
			connessione = new Socket("localhost", 6789);
			out = new DataOutputStream(connessione.getOutputStream());
			in = new DataInputStream(connessione.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Container contenitore = this.getContentPane();
		PannelloClient pan = new PannelloClient(this, contenitore.getSize());
		contenitore.add(pan);
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

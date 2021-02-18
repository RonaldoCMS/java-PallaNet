package server.pallanet.interazioni;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import server.pallanet.PallaNetServer;

public class PannelloAnimazione extends JPanel implements ActionListener{

	private PallaNetServer finestra;
	private Dimension dimensioni;
	private Image bufferVirtuale;
	private Graphics offScreen;
	private Timer tim;
	private int xPallina;
	private int yPallina;
	private int diametroPallina;
	private int spostamento;
	private int timerDelay;
	private boolean destra;
	private boolean basso;
	private boolean pallinaPresente;
	private boolean arrivoComunicato;
	
public PannelloAnimazione(PallaNetServer finestra, Dimension dimensioni) {
	super();
	
	this.finestra = finestra;
	this.setSize(dimensioni);
	
	destra = true;
	basso = true;
	pallinaPresente = true;
	arrivoComunicato = false;
	
	xPallina = 0;
	yPallina = 0;
	diametroPallina = 40;
	spostamento = 3;
	timerDelay = 10;

	tim = new Timer(timerDelay, this);
	tim.start();

}

	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		bufferVirtuale = createImage(getWidth(), getHeight());
		offScreen = bufferVirtuale.getGraphics();
		Graphics2D screen = (Graphics2D) g;
		offScreen.setColor(new Color(254, 138, 22));
		
		if(pallinaPresente) {
			offScreen.fillOval(xPallina, yPallina, diametroPallina, diametroPallina);
		}
		
		screen.drawImage(bufferVirtuale, 0, 0, this);
		offScreen.dispose();
	}
	public void actionPerformed(ActionEvent e) {

		if(pallinaPresente) {
			if(basso) {
				if(yPallina > (this.getHeight() - diametroPallina)) {
					basso = false;
					yPallina -= spostamento;
				}
				else {
					yPallina += spostamento;
				}
			}
			else {
				if(yPallina <= 0) {
					basso = true;
					yPallina += spostamento;
				}
				else {
					yPallina -= spostamento;
				}
			}
			if(destra) {
				if((!arrivoComunicato) && (xPallina > (this.getWidth() - diametroPallina))) {
					try {
						finestra.getOut().writeBoolean(basso);
						finestra.getOut().writeInt(yPallina);
						arrivoComunicato = true;
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1.toString());
						System.exit(-1);
					}
					
				}
				else {
					xPallina += spostamento;
					if(xPallina > this.getWidth()) {
						pallinaPresente = false;
						arrivoComunicato = false;
					}
				}
			}
			else {
				if(xPallina <= 0) {
					destra = true;
					xPallina += spostamento;
				}
				else {
					xPallina -= spostamento;
				}
			}
		}
			else {
				boolean direzione = false;
				int y = 0;
				try {
					direzione = finestra.getIn().readBoolean();
					y = finestra.getIn().readInt();
					basso = direzione;
					destra = false;
					yPallina = y;
					xPallina = this.getWidth();
					pallinaPresente = true;
				} catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2.toString());
					System.exit(-1);
				}
			}
			repaint();
	}
}

package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JLabel;

public class URILinkField extends JLabel implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color defaultColor = new Color(30, 30, 220);
	private static final Color hoverColor = new Color(100, 100, 100);
	private static final Color activeColor = new Color(50, 50, 50);
	private static final Color transparentColor = new Color(0, 0, 0, 0);
	
	private URI target;
	
	public URILinkField(URI target, String text) {
		super(text);
		this.target = target;
		
		addMouseListener(this);
		
		setToolTipText(target.toString());
		setForeground(defaultColor);
		setBackground(transparentColor);
		setBorder(null);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//open URI
		changeColor(activeColor);
		try {
			Desktop.getDesktop().browse(target);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		changeColor(defaultColor);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		changeColor(hoverColor);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		changeColor(defaultColor);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private void changeColor(Color targetColor) {
		setForeground(targetColor);
	}
	
}

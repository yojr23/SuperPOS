package graphicInterfaceInventory;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class inventoryNorthPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private JLabel label;

	public inventoryNorthPanel() throws IOException {
		this.image = ImageIO.read(new File("./data/banner.png"));
		label = new JLabel(new ImageIcon(image));
		add(label);
		setBackground(Color.pink);
		setOpaque(true);
	}
}
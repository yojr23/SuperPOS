package graphicInterfaceInventory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import utilities.TextPrompt;
import javax.imageio.ImageIO;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class inventoryWestPanel extends JPanel implements ActionListener{
	
	/**
	 * Comando de boton 1
	 */
	public static final String BT_1 = "buscar";
	
	/**
	 * Comando de boton 2
	 */
	public static final String BT_2 = "configure";
	
	/**
	 * Comando de boton 3
	 */
	public static final String BT_3 = "grafica";
	
	
	private static final long serialVersionUID = 1L;
	private InventoryWindow principal;
	private JButton buscar;
	private JButton configureImg;
	private JButton graficar;
	private JTextField nombre;
	private JLabel image;
	private JLabel productImage;
	private JLabel heading;
	private BufferedImage img;
	
	public inventoryWestPanel(InventoryWindow pPrincipal) throws IOException {
		
		principal = pPrincipal;
		this.img = ImageIO.read(new File("./data/productImg/graysquare.png"));
		GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
		
		//Heading label
		heading = new JLabel();
		heading.setText("BUSCAR LOTE POR CODIGO Y CAMBIO IMAGEN");
		Font font = new Font("Courier", Font.BOLD,12);
		heading.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
		add(heading, gbc);
		
		//Image label
		image = new JLabel(new ImageIcon(img));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
		add(image, gbc);
		
		//Product image label
		productImage = new JLabel("No se ha buscado ningun producto", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
		add(productImage, gbc);
		
		//Text field code
		nombre = new JTextField();
		TextPrompt placeholder = new TextPrompt("Digite el codigo del producto", nombre);
	    placeholder.changeAlpha(0.75f);
	    placeholder.changeStyle(Font.ITALIC);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
	    add(nombre, gbc);
        
        //Register button
        buscar = new JButton();
		buscar.setText("BUSCAR");
		buscar.setActionCommand(BT_1);
		buscar.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
		add(buscar, gbc);
		
		//Configure img button
		configureImg = new JButton();
		configureImg.setText("CONFIGURAR IMAGEN");
		configureImg.setActionCommand(BT_2);
		configureImg.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
		add(configureImg, gbc);
		
		//Configure graficar button
		graficar = new JButton();
		graficar.setText("GRAFICAR DESEMPEÑO");
		graficar.setActionCommand(BT_3);
		graficar.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
		add(graficar, gbc);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		try {
			int code = Integer.parseInt(nombre.getText());
			if (command.equals(BT_1)) {
				principal.showElementSearched(code);
				principal.changeImageLabel(image, code);
			}
			if (command.equals(BT_2)) {
				String resp = "Digite el nombre de la imagen";
				String input = JOptionPane.showInputDialog(null, resp);
				principal.changeImageIcon(code, input);
				this.img = ImageIO.read(new File("./data/productImg/" + input + ".png"));
				image.setIcon(new ImageIcon(img));
				nombre.setText("");
			}
			
			if (command.equals(BT_3)) {
				String name = principal.nameBy(code);
				JDateChooser jd = new JDateChooser();
				String message ="Seleccione fecha inicial:\n";
				Object[] params = {message,jd};
				JOptionPane.showConfirmDialog(null,params,"Start date", JOptionPane.PLAIN_MESSAGE);
				String s="";
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				s=sdf.format(((JDateChooser)params[1]).getDate());
				String message2 ="Seleccione fecha final:\n";
				Object[] params2 = {message2,jd};
				JOptionPane.showConfirmDialog(null,params2,"Final date", JOptionPane.PLAIN_MESSAGE);
				String s2="";
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
				s2=sdf2.format(((JDateChooser)params2[1]).getDate());
				Date init = new SimpleDateFormat("dd/MM/yyyy").parse(s);
				Date fin = new SimpleDateFormat("dd/MM/yyyy").parse(s2);
				Map<Date,Integer> mapita = principal.mapDatos(code, init, fin);
		        var ex = new chartsInventory(mapita, name);
		        ex.setVisible(true);
				nombre.setText("");
			
			}
		}
		catch (NumberFormatException e2) {
			principal.handleError(principal,1);
		} catch (IOException e1) {
			principal.handleError(principal,3);
		} catch (ParseException e1) {
			principal.handleError(principal,3);
		}
	}
}
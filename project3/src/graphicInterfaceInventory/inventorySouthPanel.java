package graphicInterfaceInventory;

import javax.swing.*;
import java.awt.*;
import utilities.TextPrompt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;


public class inventorySouthPanel extends JPanel implements ActionListener{
	
	/**
	 * Comando de boton 1
	 */
	public static final String BT_1 = "cargar";
	
	/**
	 * Comando de boton 2
	 */
	public static final String BT_2 = "products";
	
	private static final long serialVersionUID = 1L;
	private InventoryWindow principal;
	private JTextField archivo;
	private JButton cargar;
	private JLabel cargarMessage;
	private JButton products;

	public inventorySouthPanel(InventoryWindow inventoryWindow) {
		
		principal = inventoryWindow;
		setLayout(new GridLayout(1, 4, 1, 1));
		
		//TextField for writing the file name
		archivo = new JTextField();
		TextPrompt placeholder = new TextPrompt("Digite el nombre del archivo", archivo);
	    placeholder.changeAlpha(0.75f);
	    placeholder.changeStyle(Font.ITALIC);
	    add(archivo);
	    
	    //Button for loading
		cargar = new JButton();
		cargar.setText("CARGAR");
		cargar.setActionCommand(BT_1);
		cargar.addActionListener(this);
		add(cargar);
		
		//Label for telling the worker whether the file was loaded
		cargarMessage = new JLabel("Aun no se ha cargado ningun archivo", SwingConstants.CENTER);
		add(cargarMessage);
		
		//Button for showing products available
		products = new JButton();
		products.setText("MOSTRAR LOTES");
		products.setActionCommand(BT_2);
		products.addActionListener(this);
		add(products);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == "cargar") {
			try {
				principal.loadInventory(archivo.getText(), 0);
				cargarMessage.setText("Cargado correctamente");
				archivo.setText("");
			} catch (IOException | ParseException e1) {
				cargarMessage.setText("Ha ocurrido un error al cargar el inventario, digite de nuevo");
			}
		}
		if (command == "products") {
			principal.addElementsLote();
		}
	}
}
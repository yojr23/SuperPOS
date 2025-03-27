package graphicInterface;

import javax.swing.*;
import java.awt.*;
import utilities.TextPrompt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class cajeroEastPanel extends JPanel implements ActionListener{
	
	/**
	 * Comando de boton 1
	 */
	public static final String BT_1 = "agregar";
	
	/**
	 * Comando de boton 2
	 */
	public static final String BT_2 = "consultar";
	
	/**
	 * Comando de boton 3
	 */
	public static final String BT_3 = "finish";
	
	/**
	 * Comando de boton 4
	 */
	public static final String BT_4 = "list";
	
	
	private static final long serialVersionUID = 1L;
	private cajeroWindow principal;
	private JLabel heading;
	private JTextField codigo;
	private JButton agregar;
	private JButton consultarCarrito;
	private JButton finish;
	private JButton listP;
	private JComboBox<String> box;
	

	public cajeroEastPanel(cajeroWindow pPrincipal) {
		principal = pPrincipal;
		
		GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
		
		//Heading label
		heading = new JLabel("CARRITO DE COMPRAS", SwingConstants.CENTER);
		Font font = new Font("Courier", Font.BOLD,12);
		heading.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
		add(heading, gbc);
		
		//TextField for barcode
		codigo = new JTextField();
		TextPrompt placeholder = new TextPrompt("Digite el codigo de barras del producto", codigo);
	    placeholder.changeAlpha(0.75f);
	    placeholder.changeStyle(Font.ITALIC);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.ipady = 15; 
	    add(codigo, gbc);
	    
	    //Combo box for civil state
	    box = new JComboBox<String>();
		gbc.gridx = 0;
		gbc.gridy = 2;
        add(box, gbc);
        box.addItem("Empaquetado");
        box.addItem("No empaquetado");

	    //Button for adding products to the cart
	    agregar = new JButton();
	    agregar.setText("AGREGAR");
	    agregar.setActionCommand(BT_1);
		agregar.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
		add(agregar, gbc);
		
	    //Button for checking the products currently in the shopping cart
	    consultarCarrito = new JButton();
		consultarCarrito.setText("CONSULTAR CARRITO");
		consultarCarrito.setActionCommand(BT_2);
		consultarCarrito.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
		add(consultarCarrito, gbc);
		
		//Button for finishing the purchase
	    finish = new JButton();
		finish.setText("TERMINAR");
		finish.setActionCommand(BT_3);
		finish.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
		add(finish, gbc);
		
		//Button for finishing the purchase
	    listP = new JButton();
	    listP.setText("LISTA PRODUCTOS");
	    listP.setActionCommand(BT_4);
	    listP.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 6;
		add(listP, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		try {
			if (command == BT_1) {
				int code = Integer.parseInt(codigo.getText());
				String combo = String.valueOf(box.getSelectedItem());
				if (combo == "Empaquetado") {
					String m = principal.addProduct(code);
					if (m == "0") {
						JOptionPane.showMessageDialog(principal, "No se ha podido agregar, no hay unidades disponibles", "Message",
						        JOptionPane.WARNING_MESSAGE);
					}
					else if (m == "1") {
						JOptionPane.showMessageDialog(principal, "Producto agregado correctamente", "Message",
						        JOptionPane.INFORMATION_MESSAGE);
					}
					else if (m == "2") {
						JOptionPane.showMessageDialog(principal, "No se ha encontrado el codigo del producto", "Message",
						        JOptionPane.WARNING_MESSAGE);
					}

					
				}
				else if (combo == "No empaquetado") {
					String pet = "Digite el peso del producto, puede incluir decimales";
					float input = Float.parseFloat(JOptionPane.showInputDialog(null, pet));
					String m = principal.addProductPack(code, input);
					if (m == "0") {
						JOptionPane.showMessageDialog(principal, "No se ha podido agregar, no hay unidades disponibles", "Message",
						        JOptionPane.WARNING_MESSAGE);
					}
					else if (m == "1") {
						JOptionPane.showMessageDialog(principal, "Producto agregado correctamente", "Message",
						        JOptionPane.INFORMATION_MESSAGE);
					}
					else if (m == "2") {
						JOptionPane.showMessageDialog(principal, "No se ha encontrado el codigo del producto", "Message",
						        JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			else if(command == BT_2) {
				principal.showCartW(1, 0);
			}
			else if(command == BT_3) {
				int n = JOptionPane.showConfirmDialog(
                        principal, "¿El cliente acumula puntos?",
                        "Cliente",
                        JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.NO_OPTION)
					principal.showCartW(2, 0);
				else if (n == JOptionPane.YES_OPTION) {
					String pet = "Digite la cedula del cliente";
					int input = Integer.parseInt(JOptionPane.showInputDialog(null, pet));
					int m = JOptionPane.showConfirmDialog(
	                        principal, "¿Desea usar puntos en su compra?",
	                        "Puntos",
	                        JOptionPane.YES_NO_OPTION);
					if (m == JOptionPane.YES_OPTION) {
						String pet1 = "Digite los puntos a utilizar";
						int input1 = Integer.parseInt(JOptionPane.showInputDialog(null, pet1));
						principal.showCartWh(input,1,input1);
					}
					else if (m == JOptionPane.NO_OPTION) {
						principal.showCartWh(input,0,0);
					}
					
				}
			}
			else if(command == BT_4) {
				principal.showProducts();
			}
		}
		catch (NumberFormatException | NullPointerException | IOException e2) {
			principal.handleError(principal, 3);
			e2.printStackTrace();
		}
	}
}
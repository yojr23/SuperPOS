package graphicInterface;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class cajeroSouthPanel extends JPanel implements ActionListener{
	
	/**
	 * Comando de boton 1
	 */
	public static final String BT_1 = "products";
	
	private static final long serialVersionUID = 1L;
	private cajeroWindow principal;
	private JLabel heading;
	private JButton products;
	private JComboBox<String> combo;

	public cajeroSouthPanel(cajeroWindow pPrincipal) {
		
		principal = pPrincipal;
		GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
		
		//Heading label
		heading = new JLabel();
		heading.setText("BUSCAR CATEGORIA");
		Font font = new Font("Courier", Font.BOLD,12);
		heading.setFont(font);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.ipadx = 30;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(heading, gbc);
		
		//Text field for categories
		
		//Combo box for categories
	    combo = principal.catCombo();
		gbc.gridx = 3;
		gbc.gridy = 0;
		add(combo, gbc);
        
		//Button for showing products available
		products = new JButton();
		products.setText("MOSTRAR PRODUCTOS");
		products.setActionCommand(BT_1);
		products.addActionListener(this);
		gbc.gridx = 4;
		gbc.gridy = 0;
		add(products, gbc);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == BT_1) {
			String estado = String.valueOf(combo.getSelectedItem());
			principal.categoryProducts(estado);

		}
	}
}
package graphicInterfaceInventory;

import javax.swing.*;
import javax.swing.border.Border;

import utilities.TextPrompt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class inventoryEastPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Comando de boton 1
	 */
	public static final String BT_1 = "actualizar";
	
	/**
	 * Comando de boton 2
	 */
	public static final String BT_2 = "mostrar";

	
	private InventoryWindow principal;
	private JLabel heading;
	private JLabel vencidos;
	private JLabel numVencidos;
	private JLabel desempeño;
	private JLabel pDesempeño;
	private JPanel panelVencidos;
	private JButton actualizar;
	private JButton mostrar;
	private JTextField nombre;
	private List<String> list = new ArrayList<>();
	
	
	public inventoryEastPanel(InventoryWindow inventoryWindow) {
		principal = inventoryWindow;
		GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
		panelVencidos = new JPanel();
		panelVencidos.setLayout(new GridLayout(2, 2));
		
		//Heading label
		heading = new JLabel("ELIMINAR VENCIDOS", SwingConstants.CENTER);
		Font font = new Font("Courier", Font.BOLD,12);
		heading.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipady = 25;
		add(heading, gbc);
				
		//Text field for client name
		nombre = new JTextField();
		TextPrompt placeholder = new TextPrompt("Digite el codigo del lote", nombre);
	    placeholder.changeAlpha(0.75f);
	    placeholder.changeStyle(Font.ITALIC);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
	    add(nombre, gbc);
		
		//Button for adding products to the cart
	    actualizar = new JButton();
	    actualizar.setText("ACTUALIZAR");
	    actualizar.setActionCommand(BT_1);
		actualizar.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		add(actualizar, gbc);
		
	    //Setting up panelVencidos
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		vencidos = new JLabel("Vencidos ");
		vencidos.setBorder(border);
		numVencidos = new JLabel("0", SwingConstants.CENTER);
		numVencidos.setBorder(border);
		desempeño = new JLabel("Desempeño");
		desempeño.setBorder(border);
		pDesempeño = new JLabel("0%", SwingConstants.CENTER);
		pDesempeño.setBorder(border);
		panelVencidos.add(vencidos);
		panelVencidos.add(numVencidos);
		panelVencidos.add(desempeño);
		panelVencidos.add(pDesempeño);
		panelVencidos.setPreferredSize(new Dimension(200,150));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 20;
		gbc.gridx = 1;
        gbc.gridy = 3;
		add(panelVencidos, gbc);
		
		
		
		//Button for finishing the purchase
	    mostrar = new JButton();
		mostrar.setText("MOSTRAR VENCIDOS");
		mostrar.setActionCommand(BT_2);
		mostrar.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 4;
		add(mostrar, gbc);
		
	}

	private void setList(List<String> list2) {
		this.list = list2;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		try {
			int code = Integer.parseInt(nombre.getText());
			if (command == "actualizar") {
				setList(principal.UpdateInventory(code));
				numVencidos.setText(list.get(2));
				if (list.get(2)=="0") {
					pDesempeño.setText("0%");
				}
				else {
					pDesempeño.setText(list.get(1));
				}
			}
			if (command == "mostrar") {
				principal.showProducts(list);
			}
		}
		catch (IndexOutOfBoundsException | NumberFormatException e2) {
			principal.handleError(principal,1);
		}
	}
}
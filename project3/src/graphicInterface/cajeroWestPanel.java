package graphicInterface;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.*;
import utilities.TextPrompt;

public class cajeroWestPanel extends JPanel implements ActionListener{
	
	/**
	 * Comando de boton 1
	 */
	public static final String BT_1 = "registrar";
	
	private static final long serialVersionUID = 1L;
	private cajeroWindow principal;
	private JButton registrar;
	private JComboBox<String> estadoCivil; 
	private JComboBox<String> situacionLaboral;
	private JTextField nombre;
	private JTextField edad;
	private JTextField cedula;
	private JLabel heading;
	
	public cajeroWestPanel(cajeroWindow pPrincipal) {
		
		principal = pPrincipal;
		GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
		
		//Heading label
		heading = new JLabel();
		heading.setText("REGISTRAR CLIENTE");
		Font font = new Font("Courier", Font.BOLD,12);
		heading.setFont(font);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.ipadx = 60;
		gbc.ipady = 20;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(heading, gbc);
		
		//Text field for client name
		nombre = new JTextField();
		TextPrompt placeholder = new TextPrompt("Digite el nombre del cliente", nombre);
	    placeholder.changeAlpha(0.75f);
	    placeholder.changeStyle(Font.ITALIC);
		gbc.gridx = 1;
		gbc.gridy = 1;
	    add(nombre, gbc);
		
		//Text field for client age
		edad = new JTextField();
		TextPrompt placeholder2 = new TextPrompt("Digite la edad del cliente", edad);
	    placeholder2.changeAlpha(0.75f);
	    placeholder2.changeStyle(Font.ITALIC);
		gbc.gridx = 1;
		gbc.gridy = 2;
	    add(edad, gbc);
	    
		//Text field for id
		cedula = new JTextField();
		TextPrompt placeholder3 = new TextPrompt("Digite la cedula del cliente", cedula);
	    placeholder3.changeAlpha(0.75f);
	    placeholder3.changeStyle(Font.ITALIC);
		gbc.gridx = 1;
		gbc.gridy = 3;
	    add(cedula, gbc);
	    
	    //Combo box for civil state
	    estadoCivil = new JComboBox<String>();
		gbc.gridx = 1;
		gbc.gridy = 4;
        add(estadoCivil, gbc);
        estadoCivil.addItem("Soltero");
        estadoCivil.addItem("Casado");
        estadoCivil.addItem("Divorciado");
        estadoCivil.addItem("Viudo");
        estadoCivil.addItem("Union libre");
        
        
	    //Combo box for civil state
	    situacionLaboral = new JComboBox<String>();
		gbc.gridx = 1;
		gbc.gridy = 5;
        add(situacionLaboral, gbc);
        situacionLaboral.addItem("Estudiante");
        situacionLaboral.addItem("Independiente");
        situacionLaboral.addItem("Empleado");
        situacionLaboral.addItem("Desempleado");
        
        //Register button
        registrar = new JButton();
		registrar.setText("REGISTRAR");
		registrar.setActionCommand(BT_1);
		registrar.addActionListener(this);
		gbc.gridx = 1;
		gbc.gridy = 6;
		add(registrar, gbc);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String command = e.getActionCommand();
			String name = nombre.getText();
			int id = Integer.parseInt(cedula.getText());
			int age = Integer.parseInt(edad.getText());
			String estado = String.valueOf(estadoCivil.getSelectedItem());
			String situacion = String.valueOf(situacionLaboral.getSelectedItem());
			if (command == "registrar") {
				try {
					principal.loadClient(name, id, age, estado, situacion, age);
					cedula.setText("");
					nombre.setText("");
					edad.setText("");
					estadoCivil.setSelectedIndex(0);
					situacionLaboral.setSelectedIndex(0);
					JOptionPane.showMessageDialog(principal, "Se ha cargado el cliente satisfactoriamente", "Info",
					        JOptionPane.INFORMATION_MESSAGE);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
			}	
		}
		catch (NumberFormatException exception) {
			principal.handleError(principal, 2);
		}
	}
}
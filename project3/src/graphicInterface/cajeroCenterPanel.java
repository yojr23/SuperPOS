package graphicInterface;

import javax.swing.*;
import java.awt.*;
import utilities.TextPrompt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cajeroCenterPanel extends JPanel implements ActionListener{
	
	/**
	 * Comando de boton 1
	 */
	public static final String BT_1 = "consultar";
	
	/**
	 * Comando de boton 2
	 */
	public static final String BT_2 = "frecuencia";
	
	private static final long serialVersionUID = 1L;
	private cajeroWindow principal;
	private JLabel heading;
	private JButton consulta;
	private JButton frecuencia;
	private JTextField cedula;

	public cajeroCenterPanel(cajeroWindow pPrincipal) {
		principal = pPrincipal;
		GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
		
		//Heading label
		heading = new JLabel("INFO CLIENTE", SwingConstants.CENTER);
		Font font = new Font("Courier", Font.BOLD,12);
		heading.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
		add(heading,gbc);
		
		//TextField for writing the file name
		cedula = new JTextField();
		TextPrompt placeholder = new TextPrompt("Digite la cedula del cliente", cedula);
	    placeholder.changeAlpha(0.75f);
	    placeholder.changeStyle(Font.ITALIC);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipady = 15; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
	    add(cedula,gbc);
		
		//Button for checking client's points
		consulta = new JButton();
		consulta.setText("CONSULTAR INFORMACION Y PUNTOS");
		consulta.setActionCommand(BT_1);
		consulta.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
		add(consulta,gbc);
		
        //Button for checkimg shopping frecuency
		frecuencia = new JButton();
		frecuencia.setText("CONSULTAR FRECUENCIA DE COMPRA");
		frecuencia.setActionCommand(BT_2);
		frecuencia.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
		add(frecuencia,gbc);
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		try {
			int id = Integer.parseInt(cedula.getText());
			if (command == "consultar") {
				principal.infoClient(id);
			}
		}
		catch(NumberFormatException exception) {
			principal.handleError(principal,1);
		}
	}
}

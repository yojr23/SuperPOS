package graphicInterface;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import processing.Inventory;
import processing.PosSystem;
import processing.SupermarketLoader;

public class cajeroWindow extends JFrame{

	private static final long serialVersionUID = 1L;

	//Panel for registering clients
	private cajeroWestPanel wPanel;
	
	//Panel for shopping cart
	private cajeroEastPanel rPanel;
	
	//Panel for loading file
	private cajeroSouthPanel sPanel;
		
	//Panel for banner
	private cajeroNorthPanel nPanel;
	
	//Panel for checking client info
	private cajeroCenterPanel cPanel; 
	
	//Inventory for system pos
	private Inventory inventory; 
	
	//System pos
	private PosSystem system;

	public cajeroWindow() throws IOException, ParseException{
		inventory = SupermarketLoader.loadSuperMarket("./data/datafileff.csv", 1);
		system = new PosSystem(inventory);
		system.findAllFilesInFolder(new File("./data/Promociones"));
		loadFile();
		GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

		//Panel for managing the cart
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.insets = new Insets(0,0,0,80);
        rPanel = new cajeroEastPanel( this );
        add( rPanel, gbc);
        
		
        //Panel for checking client info
        gbc.gridx = 2;  
        gbc.gridy = 1;
        gbc.insets = new Insets(0,60,0,0);
        cPanel = new cajeroCenterPanel( this );
        add( cPanel, gbc);
        
        //Panel botones register client
        gbc.gridx = 0;  
        gbc.gridy = 1;  
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.insets = new Insets(0,80,0,0); 
        wPanel = new cajeroWestPanel( this );
        add( wPanel, gbc );

        //Panel for loading inventory
        gbc.gridx = 1;  
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(60,0,0,0);
        sPanel = new cajeroSouthPanel( this );
        add(sPanel, gbc);
        
        //Panel for banner
        gbc.gridx = 1;  
        gbc.gridy = 0;  
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0,0,60,209);
        nPanel = new cajeroNorthPanel();
        add( nPanel, gbc );
        
		setTitle( "Sistema de caja" );
		pack();
		Component c = this.getContentPane();
		Color color = new Color(188,255,215);
		c.setBackground(color);
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		setVisible(true);
		addWindowListener(new WindowAdapter()
    	{
    	public void windowClosing(WindowEvent e)
    	{
    		try {
				system.saveClients();
				system.saveInventoryP();
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    	}
    	});
	}
	
	/**
	 * Loads the client using the JTextFields and ComboBox in WestPanel
	 * @throws IOException 
	 */
	public void loadClient(String name, int id, int age, String estado, String situacion, int points) throws IOException {
		system.registerClient(name, id, age, estado, situacion);
	}
	
	public void loadFile() throws NumberFormatException, IOException {
		system.loadClients();
	}
	
	/**
	 * Add product to shopping cart
	 */
	public String addProduct(int code) {
		String result = "";
		result = system.addItemsToShoppingCart(code);
		return result;
	}
	
	/**
	 * Add products without a package
	 */
	public String addProductPack(int code, float size) {
		String result = "";
		result = system.addItemsToShoppingCartPack(code, size);
		return result;
	}
	
	/**
	 * Shows an option panel showing products
	 * @throws IOException 
	 */
	public void showProducts() throws IOException {
		JPanel m = system.labelList();
		JScrollPane pane = new JScrollPane(m,
		        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setMinimumSize(new Dimension(225, 500));
	    pane.setPreferredSize(new Dimension(225, 500));
		pane.getViewport().getView().setBackground(Color.pink);
		pane.getViewport().getView().setForeground(Color.black);
		Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
		pane.getViewport().getView().setFont(font);
		JOptionPane.showMessageDialog(null, pane, "Productos", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Gets info client and shows it in an option pane
	 * @param id
	 */
	public void infoClient(int id) {
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		demoList = system.defineList(id);
		JList<String> list = new JList<String>(demoList);
		JScrollPane pane = new JScrollPane(list);
		pane.getViewport().getView().setBackground(Color.pink);
		pane.getViewport().getView().setForeground(Color.black);
		Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
		pane.getViewport().getView().setFont(font);
		JOptionPane.showMessageDialog(null, pane, "Info cliente", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Shows cart in an option panel
	 */
	public void showCartW(int num, int resta) {
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		if (num == 1) {
			demoList = system.showCart(1, resta);
			JList<String> list = new JList<String>(demoList);
			JScrollPane pane = new JScrollPane(list);
			pane.getViewport().getView().setBackground(Color.pink);
			pane.getViewport().getView().setForeground(Color.black);
			Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
			pane.getViewport().getView().setFont(font);
			JOptionPane.showMessageDialog(null, pane, "Info carrito", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(num == 2) {
			demoList = system.finishPurchase();
			JList<String> list = new JList<String>(demoList);
			JScrollPane pane = new JScrollPane(list);
			pane.getViewport().getView().setBackground(Color.pink);
			pane.getViewport().getView().setForeground(Color.black);
			Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
			pane.getViewport().getView().setFont(font);
			JOptionPane.showMessageDialog(null, pane, "Factura", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	/**
	 * Finishes purchase and shows receipt
	 */
	public void showCartWh(int id, int num, int points) {
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		demoList = system.finishPurchaseWthId(id, num, points);
		JList<String> list = new JList<String>(demoList);
		JScrollPane pane = new JScrollPane(list);
		pane.getViewport().getView().setBackground(Color.pink);
		pane.getViewport().getView().setForeground(Color.black);
		Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
		pane.getViewport().getView().setFont(font);
		JOptionPane.showMessageDialog(null, pane, "Factura", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Gets info of products of the same category
	 */
	public void categoryProducts(String category) {
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		demoList = system.listByCategory(category);
		String res = "No se han podido encontrar productos de esta categoria";
		if (demoList.getSize() == 1) {
			demoList.remove(0);
			demoList.addElement(res);
		}
		JList<String> list = new JList<String>(demoList);
		JScrollPane pane = new JScrollPane(list);
		pane.getViewport().getView().setBackground(Color.pink);
		pane.getViewport().getView().setForeground(Color.black);
		Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
		pane.getViewport().getView().setFont(font);
		JOptionPane.showMessageDialog(null, pane, "Productos por categoria", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	/**
	 * Gets JComboBox with all categories
	 */
	public JComboBox<String> catCombo() {
		List<String> catList = system.categories();
		JComboBox<String> combo = new JComboBox<String>(); 
		for(String i : catList) {
			combo.addItem(i);
		}
		return combo;
		
	}
	
	/**
	 * Gives a warning in case theres nothing in the JTextField
	 * @param principal
	 */
	public void handleError(cajeroWindow principal,int code) {
		if (code == 1) {
			JOptionPane.showMessageDialog(principal, "Tiene que digitar la cedula del cliente", "Warning",
			        JOptionPane.WARNING_MESSAGE);
		}
		else if (code == 2) {
			JOptionPane.showMessageDialog(principal, "Llene los campos seleccionados", "Warning",
			        JOptionPane.WARNING_MESSAGE);
		}
		else if (code == 3) {
			JOptionPane.showMessageDialog(principal, "Digite un numero valido", "Warning",
			        JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		new cajeroWindow();
		FlatLightLaf.install();
	}
}

package graphicInterfaceInventory;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import processing.Inventory;
import processing.SupermarketLoader;

public class InventoryWindow extends JFrame{

	private static final long serialVersionUID = 1L;

	//Panel for registering clients
	private inventoryWestPanel wPanel;
	
	//Panel for shopping cart
	private inventoryEastPanel rPanel;
	
	//Panel for loading file
	private inventorySouthPanel sPanel;
	
	//Panel for banner
	private inventoryNorthPanel nPanel;
	
	//Supermarket inventory
	private Inventory inventory;
	
	public InventoryWindow() throws IOException, ParseException {
		loadInventory("datafileff", 1);
		GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        
        //Panel for updating
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0,100,0,0);
        rPanel = new inventoryEastPanel( this );
        add( rPanel, gbc );
        
		//Panel botones for lotes
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0,80,0,0);
        wPanel = new inventoryWestPanel( this );
        add( wPanel, gbc );
        
        //Panel for banner
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0,0,0,0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nPanel = new inventoryNorthPanel();
        add( nPanel, gbc );
        
        //Panel for loading inventory
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(50,0,0,0);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        sPanel = new inventorySouthPanel( this );
        add( sPanel, gbc );
        
		setTitle( "Sistema de inventario" );
		pack();
		Component c = this.getContentPane();
		c.setBackground(Color.pink);
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		setVisible(true);
    	addWindowListener(new WindowAdapter()
    	{
    	public void windowClosing(WindowEvent e)
    	{
    		try {
				inventory.saveInventoryProducts();
				inventory.saveDatos();
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    	}
    	});
	}
	
	/**
	 * Loads the inventory to be used in the interface
	 * @param file
	 * @throws IOException
	 * @throws ParseException
	 */
	public void loadInventory(String file, int code) throws IOException, ParseException {
		file = "./data/" + file + ".csv"; 
		if (inventory == null) {
			Inventory newInventory = null;
			try {
				newInventory = SupermarketLoader.loadSuperMarket(file, code);
				this.inventory = newInventory;
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		else {
			SupermarketLoader.cargarIf(file, inventory);
		}
	}
	

	
	/**
	 * Calls method from inventory and updates the inventory used in the interface
	 * @param code
	 * @return
	 */
	public List<String> UpdateInventory(int code) {
		List<String> list;
		list = inventory.updateInventory(code);
		return list;
	}
	
	/**
	 * Add elements to a JList to later be used in a JScrollPanel
	 * @param list
	 * @return
	 */
	private JList<String> addElements(List<String> list) {
		String[] mList = (list.get(0).split("\n"));
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		int contador = 1;
		for (String i : mList) {
			if(i != "") {
				String p = "";
				p += contador;
				p += " " + i;
				demoList.addElement(p);
				contador += 1;
			}
		}
		JList<String> list1 = new JList<String>(demoList);
		
		return list1;
	}
	

	
	/**
	 * Shows an Option Panel showing the available lotes
	 */
	public void addElementsLote(){
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		demoList = inventory.getDemoList();
		JList<String> list = new JList<String>(demoList);
		JScrollPane pane = new JScrollPane(list);
		Color color = new Color(255,178,102);
	    pane.getViewport().getView().setBackground(color);
	    pane.getViewport().getView().setForeground(Color.black);
	    Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
	    pane.getViewport().getView().setFont(font);
	    JOptionPane.showMessageDialog(null, pane, "Lotes", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void changeImageLabel(JLabel label, int code) throws IOException {
		String img = inventory.getImage(code);
		BufferedImage img2 = ImageIO.read(new File(img));
		label.setIcon(new ImageIcon(img2));
	}
	
	/**
	 * Shows an option panel showing a specific lote
	 * @param code
	 */
	public void showElementSearched(int code) {
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		demoList = inventory.getDemoListCode(code);
		JList<String> list = new JList<String>(demoList);
		JScrollPane pane = new JScrollPane(list);
		Color color = new Color(255,178,102);
	    pane.getViewport().getView().setBackground(color);
	    pane.getViewport().getView().setForeground(Color.black);
	    Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
	    pane.getViewport().getView().setFont(font);
	    JOptionPane.showMessageDialog(null, pane, "Lotes buscado", JOptionPane.INFORMATION_MESSAGE);
		}
	
	/**
	 * Shows an option panel showing all the expired lotes
	 * @param list2
	 */
	public void showProducts(List<String> list2) {
		JList<String> list = addElements(list2);
		JScrollPane pane = new JScrollPane(list);
		Color color = new Color(255,178,102);
        pane.getViewport().getView().setBackground(color);
        pane.getViewport().getView().setForeground(Color.black);
        Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);
        pane.getViewport().getView().setFont(font);
        JOptionPane.showMessageDialog(null, pane, "Vencidos", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Obtiene un hash para hacer display de los lotes
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public Map<Date, Integer> mapDatos(int code, Date initial, Date fin) throws IOException, ParseException{
		Map<Date, Integer> mapita = inventory.cargarDatos(code, initial, fin);
		return mapita;
		
	}
	
	public String nameBy(int code) {
		String nom = inventory.nombreByCode(code);
		return nom;
	}
	
	/**
	 * Changes the image of a product
	 * @param code
	 * @param image
	 * @return
	 */
	public String changeImageIcon(int code, String image) {
		inventory.changeImage(code, image);
		String image2 = inventory.getImage(code);
		return image2;
	}
	
	/**
	 * Gives a warning in case theres nothing in the JTextField
	 * @param principal
	 */
	public void handleError(InventoryWindow principal,int code) {
		if (code == 1) {
			JOptionPane.showMessageDialog(principal, "Tiene que digitar un codigo de producto valido", "Warning",
			        JOptionPane.WARNING_MESSAGE);
		}
		else if (code == 2) {
			JOptionPane.showMessageDialog(principal, "El archivo no fue encontrado", "Warning",
			        JOptionPane.WARNING_MESSAGE);
		}
		else if (code == 3) {
			JOptionPane.showMessageDialog(principal, "El archivo de imagen no fue encontrado", "Warning",
			        JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	public static void main(String[] args) throws IOException, ParseException {
		new InventoryWindow();
		FlatLightLaf.install();
	}
}
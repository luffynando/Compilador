import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class InterfazCompilado extends JFrame
{

	private JPanel variables, consola;
	
	
	 GridBagConstraints c = new GridBagConstraints();
	 public JTextArea cons;
	 
	

	InterfazCompilado()
	{	Toolkit t = Toolkit.getDefaultToolkit();
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	this.setSize(screenSize);
		 this.setTitle(Sintactico.semantico.nombrePrograma);
		 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 this.setLocationRelativeTo(null);
		this.setLayout(new GridBagLayout());
		 c.insets = new Insets(3,3,3,3);
		 c.weightx=0.5;
		 c.weighty=1;
		 c.fill = GridBagConstraints.BOTH;
		 c.gridx = 0;
		 c.gridy = 0;
		 
		 
		 variables= new JPanel();
		 variables.setLayout(new BoxLayout(variables, BoxLayout.PAGE_AXIS) );
		 JScrollPane scroll = new JScrollPane(variables);
		 JTabbedPane pestana = new JTabbedPane();
		 pestana.add("Variables",scroll);
		 variables.setVisible(true);
		 
		 this.add(pestana,c);
		 
		cons= new JTextArea();
		
		 JTabbedPane pestana2 = new JTabbedPane();
		 consola= new JPanel();
		 consola.setLayout(new BoxLayout(consola,BoxLayout.PAGE_AXIS));
		 consola.add(cons);
		 JScrollPane scrollPane = new JScrollPane(cons);
		 pestana2.add("Consola", scrollPane);
		 consola.setVisible(true);
		 c.weightx = 0.5;
		 c.gridx = 1;
		 c.gridy = 0;
		 this.add(pestana2, c);
		 
		 this.setVisible(true);

	       

	}
	
	
	public  void agregaTabla(String[][] datos, String title) {
		
        
		JLabel titulo= new JLabel(title,SwingConstants.CENTER);
		
		String[] columnNames = {"Lexema","tipo","valor"}; 
		DefaultTableModel dtm;
		JTable table;
		
		// Initializing the JTable 
		dtm= new DefaultTableModel(datos,columnNames);
		table = new JTable(dtm); 
		//table.setBounds(30, 40, 200, 300); 
		
		
		JScrollPane sp = new JScrollPane(table); 
		
		variables.add(titulo);
        variables.add(sp);
	}

	

}
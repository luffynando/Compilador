import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class Interfaz extends JFrame{
    JTextArea textArea;
    static JTextArea textSalida;
    String src, automata, reservadas;
    Boolean automataready= false;
    AFDVault automa = null;

    public Interfaz(){
        this.setTitle("Analizador");
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBag = new GridBagLayout ();
        GridBagConstraints restricciones = new GridBagConstraints ();
        restricciones.insets = new Insets(3,3,3,3);
        src=new String();
        automata=new String();

        this.setLayout(gridBag);

        restricciones.fill = GridBagConstraints.BOTH; //modifica altura y anchura

        restricciones.weightx = 1.5;
        restricciones.gridwidth = GridBagConstraints.REMAINDER;
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new FlowLayout() );
        gridBag.setConstraints (panelNorte, restricciones);
        this.add(panelNorte);

        //botones norte
        JButton abrir=new JButton("abrir Código");
        JButton guardar=new JButton("guardar Código");
        JButton compilar=new JButton("compilar");
        abrir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                src=abrirGUI();
                if(!src.isEmpty()) {
                    textArea.setText("");
                    ponerEnTextField(src);
                }


            }
        });

        guardar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(!src.isEmpty()) {
                    String texto= textArea.getText();
                    String[] textArray= texto.split("\n");
                    WriteFile data= new WriteFile(src,true);
                    File file= new File(src);
                    file.delete();
                    for(int i=0;i<textArray.length;i++) {
                        //System.out.println("lineas");
                        //System.out.println(textArray[i]);
                        try {
                            data.writeToFile(textArray[i]);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    textSalida.append("\nguardado exitoso");
                }
                else
                {
                 src=guardarGUI();
                 if(!src.isEmpty()) {
	                 String texto= textArea.getText();
	                 String[] textArray= texto.split("\n");
	                 WriteFile data= new WriteFile(src,true);
	                 File file= new File(src);
	                 file.delete();
	                 for(int i=0;i<textArray.length;i++) {
	                        //System.out.println("lineas");
	                        //System.out.println(textArray[i]);
	                        try {
	                            data.writeToFile(textArray[i]);
	                        } catch (IOException e1) {
	                            e1.printStackTrace();
	                        }
	                    }
	                 textSalida.append("\nguardado exitoso");
                 }
                }
            }

        });
        
        compilar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] codigo= textArea.getText().split("\n");
				if(automataready) {
				    textSalida.setText("");
				    automa.resetTokens();
					for(int i=0;i<codigo.length;i++) {
					    try {
                            automa.validaSt(codigo[i],(i+1));
                        }catch (ParserException ex){
					        textSalida.append("\n"+ex.getMessage());
                        }
					}
					textSalida.append("\n-------------------------------------------------");
					Sintactico analsin = new Sintactico(automa);
                    try {
                        analsin.Iniciar();
                        textSalida.append("\nTodo bien");
                    }catch (ParserException ex){
                        textSalida.append("\n"+ex.getMessage());
                    }
				}
				else {
					textSalida.append("\nno hay autómata cargado");
				}
				
			}
        	
        });

        panelNorte.add(abrir);
        panelNorte.add(guardar);
        panelNorte.add(compilar);

        restricciones.gridwidth = 1;  // Restablecer a los valores predeterminados
        restricciones.gridheight = 2;
        restricciones.weighty = 1.0;
        JTabbedPane pestana = new JTabbedPane();
        gridBag.setConstraints (pestana, restricciones);
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(true);
        pestana.add("Editor",scrollPane);
        this.add(pestana);

        restricciones.weightx = 0.5;
        restricciones.weighty = 0.0;  // Restablecer a los valores predeterminados
        restricciones.gridwidth = GridBagConstraints.REMAINDER;  // Fila final
        restricciones.gridheight = 1;  // Restablecer a los valores predeterminados
        //config panelEste
        JPanel panelEste=new JPanel();
        panelEste.setLayout(new FlowLayout() );
        JButton abrirAutomata=new JButton("Cargar/Cambiar Automata");
        abrirAutomata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String src;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(getParent());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textSalida.append("\nAutómata seleccionado " + selectedFile.getAbsolutePath() );
                    automa = null;
                    automataready = false;
                    compilar.setEnabled(false);
                    try {
                        src = selectedFile.getAbsolutePath();
                        automa = new AFDVault(src);
                        automataready = automa.allready();
                        if (automataready){
                            compilar.setEnabled(true);
                            textSalida.append("\nAutomata cargado");
                            //automa.PrintAFD();
                        }else{
                            compilar.setEnabled(false);
                            textSalida.append("\nAutomata no cargado verifique el archivo");
                        }
                    }catch (IOException|ParserException ex){
                        textSalida.append(ex.getMessage()+"\n");
                    }
                }
            }

        });
        JButton abrirReservadas=new JButton("Cargar/Cambiar Reservadas");
        abrirReservadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(automataready) {
            	 String reservadas;
                 JFileChooser fileChooser = new JFileChooser();
                 fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                 int result = fileChooser.showOpenDialog(getParent());
                 if (result == JFileChooser.APPROVE_OPTION) {
                     File selectedFile = fileChooser.getSelectedFile();
                     textSalida.append("\nArchivo de palabras reservadas seleccionado: " + selectedFile.getAbsolutePath() );
                     //automataready = false;
                     compilar.setEnabled(false);
                    	 try {
							if(automa.cargarReservadas(selectedFile.getAbsolutePath()) );
							 textSalida.append("\n Palabras reservadas cargadas");
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    	 compilar.setEnabled(true);
                    
                 }
            	} else {
               	 textSalida.append("\n Cargue el autómata primero");
                }
            }
        });
       
        panelEste.add(abrirAutomata);
        panelEste.add(abrirReservadas);
        JTabbedPane pestana2 = new JTabbedPane();
        textSalida= new JTextArea();
        JScrollPane scrollPaneS = new JScrollPane(textSalida);
        textSalida.setEditable(false);
        pestana2.add("Salida",scrollPaneS);
        gridBag.setConstraints (panelEste, restricciones);
        gridBag.setConstraints (pestana2, restricciones);
        this.add(panelEste);this.add(pestana2);

        JTextArea textcarga = new JTextArea();
        textcarga.setEditable(false);
        gridBag.setConstraints (textcarga, restricciones);
        this.add(textcarga);

        this.setVisible(true);
    }

    public void ponerEnTextField(String src){
        try{
            File archivo = new File(src);
            BufferedReader bufferreader = new BufferedReader(new FileReader(archivo));
            String line = bufferreader.readLine();
            while(line!=null) {
                textArea.append(line);
                line= bufferreader.readLine();
                //textSalida.setText(line);
                textArea.append("\n");
            }
            bufferreader.close();
        }catch(Exception e) {

        }
    }

    public String abrirGUI() {
        String src;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(getParent());
        if (result == JFileChooser.APPROVE_OPTION) {
            src= new String();
            File selectedFile = fileChooser.getSelectedFile();
            textSalida.append("\nArchivo seleccionado: " + selectedFile.getAbsolutePath() );
            src=selectedFile.getAbsolutePath();
            return src;

        }else {
           // textSalida.append("cerrado"+"\n");
            return new String();
        }

    }
    
    public String guardarGUI() {
        String src;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Guardar"); 
        fileChooser.setDialogTitle("Save");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(getParent());
        if (result == JFileChooser.APPROVE_OPTION) {
            src= new String();
            File selectedFile = fileChooser.getSelectedFile();
            textSalida.append("\narchivo seleccionado: " + selectedFile.getAbsolutePath());
            src=selectedFile.getAbsolutePath();
            return src;

        }else {
            //textSalida.append("cerrado"+"\n");
            return new String();
        }

    }

}

package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

import sonido.FactoriaSonido;
import sonido.Sonido;
import statisticbpm.BPMCalculator;
import statisticbpm.BPMCalculatorImpl;

public class GUI {

	private JFrame frmBpmcalculator;
	private JTextField textField;
	private JTextField textField_1;

	private Sonido s;
	private BPMCalculator calculator;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmBpmcalculator.setVisible(true);
					window.frmBpmcalculator.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBpmcalculator = new JFrame();
		frmBpmcalculator.setTitle("Statistic BPMCalculator");
		frmBpmcalculator.setBounds(100, 100, 579, 397);
		frmBpmcalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmBpmcalculator.getContentPane().setLayout(springLayout);
		
		JButton btnAbrirArchivo = new JButton("Abrir Archivo");
		btnAbrirArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				FactoriaSonido f = new FactoriaSonido();
				s = f.createSonido();
				calculator = new BPMCalculatorImpl(s);
				
				
				
				
				if(s!=null){
					textField.setText(s.getAbsPath());
				}
				
			}
			
		});
		springLayout.putConstraint(SpringLayout.WEST, btnAbrirArchivo, 22, SpringLayout.WEST, frmBpmcalculator.getContentPane());
		btnAbrirArchivo.setIcon(new ImageIcon(GUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		frmBpmcalculator.getContentPane().add(btnAbrirArchivo);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, btnAbrirArchivo);
		springLayout.putConstraint(SpringLayout.WEST, textField, 27, SpringLayout.EAST, btnAbrirArchivo);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, 27, SpringLayout.NORTH, btnAbrirArchivo);
		springLayout.putConstraint(SpringLayout.EAST, textField, 267, SpringLayout.EAST, btnAbrirArchivo);
		frmBpmcalculator.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnDetect = new JButton("Calculate!");
		springLayout.putConstraint(SpringLayout.WEST, btnDetect, 176, SpringLayout.WEST, frmBpmcalculator.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnDetect, -46, SpringLayout.SOUTH, frmBpmcalculator.getContentPane());
		frmBpmcalculator.getContentPane().add(btnDetect);
		
		JTextPane txtpnSoloArchivosWave = new JTextPane();
		springLayout.putConstraint(SpringLayout.NORTH, btnAbrirArchivo, 36, SpringLayout.SOUTH, txtpnSoloArchivosWave);
		txtpnSoloArchivosWave.setBackground(SystemColor.menu);
		txtpnSoloArchivosWave.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, txtpnSoloArchivosWave, 35, SpringLayout.NORTH, frmBpmcalculator.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtpnSoloArchivosWave, 120, SpringLayout.WEST, frmBpmcalculator.getContentPane());
		txtpnSoloArchivosWave.setText("Solo archivos WAVE compatibles");
		frmBpmcalculator.getContentPane().add(txtpnSoloArchivosWave);
		
		final JCheckBox chckbxCVariable = new JCheckBox("C Variable");
		chckbxCVariable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxCVariable.isSelected()){
					textField_1.disable();
				}else{
					textField_1.enable();
				}
			}
		});
		
		chckbxCVariable.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxCVariable, 23, SpringLayout.SOUTH, btnAbrirArchivo);
		springLayout.putConstraint(SpringLayout.WEST, chckbxCVariable, 0, SpringLayout.WEST, txtpnSoloArchivosWave);
		frmBpmcalculator.getContentPane().add(chckbxCVariable);
		
		textField_1 = new JTextField();
		textField_1.setText("1.3");
		textField_1.setBackground(new Color(255, 255, 255));
		springLayout.putConstraint(SpringLayout.NORTH, textField_1, 1, SpringLayout.NORTH, chckbxCVariable);
		springLayout.putConstraint(SpringLayout.WEST, textField_1, 88, SpringLayout.EAST, chckbxCVariable);
		springLayout.putConstraint(SpringLayout.EAST, textField_1, 82, SpringLayout.EAST, btnDetect);
		textField_1.setToolTipText("");
		textField_1.setEnabled(false);
		frmBpmcalculator.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JTextPane txtpnC = new JTextPane();
		springLayout.putConstraint(SpringLayout.NORTH, txtpnC, 0, SpringLayout.NORTH, chckbxCVariable);
		springLayout.putConstraint(SpringLayout.EAST, txtpnC, -11, SpringLayout.WEST, textField_1);
		txtpnC.setText("C:");
		txtpnC.setEditable(false);
		txtpnC.setBackground(SystemColor.menu);
		frmBpmcalculator.getContentPane().add(txtpnC);
		
		btnDetect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean rutaArchivo = textField.getText().length()>0;
				
				if(rutaArchivo){
//					BPMCalculator calculator = new BPMCalculatorImpl(s);
					Double bpm = null;
					if(chckbxCVariable.isSelected()){
						//arrancamos run con c variable
						try {
							bpm = calculator.run();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//Ventana de mensaje con BPM
						JOptionPane.showMessageDialog(frmBpmcalculator, "El tempo de la cancion es: "+bpm+" BPM");
						
					}else if(!(chckbxCVariable.isSelected()) && (textField_1.getText().length()>0)){
						Double c = new Double(textField_1.getText());
						//arrancamos run con c fijo
						try {
							bpm = calculator.run(c);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//Ventana de mensaje con BPM
						JOptionPane.showMessageDialog(frmBpmcalculator, "El tempo de la cancion es: "+bpm+" BPM");
					}else{
						JOptionPane.showMessageDialog(frmBpmcalculator, "Indique el C fijo a usar");
					}
					
				}else{
					//Ventana de mensaje de error
					JOptionPane.showMessageDialog(frmBpmcalculator, "Elija correctamente el archivo");
				}
			}
		});
		
	}
}

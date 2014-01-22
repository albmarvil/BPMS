package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
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
import utiles.Utiles;
import charts.ChartEnergy;
import charts.ChartMuestras;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class GUI {

	private JFrame frmBpmcalculator;
	private JTextField textField;
	private JTextField textField_1;
	private static JLabel label_playing;
	private static JLabel label_samplerate;
	private static JLabel label_bitssample;
	private static JLabel label_mode;
	private static JLabel label_samples;
	private JCheckBox chckbxA;
	private JCheckBox chckbxEnerga;
	private JButton btnChart;
	
	private static FactoriaSonido f;
	private static Sonido s;
	private BPMCalculator calculator;
	private ChartMuestras chart;
	private ChartEnergy chartE;
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
		frmBpmcalculator.setBounds(100, 100, 467, 588);
		frmBpmcalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmBpmcalculator.getContentPane().setLayout(springLayout);
		
		JButton btnAbrirArchivo = new JButton("Abrir Archivo");
		springLayout.putConstraint(SpringLayout.NORTH, btnAbrirArchivo, 91, SpringLayout.NORTH, frmBpmcalculator.getContentPane());
		btnAbrirArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				f = new FactoriaSonido();
				s = f.createSonido();
				calculator = new BPMCalculatorImpl(s);
				
				chart = new ChartMuestras(f.createSonido(s));
				chartE = new ChartEnergy(f.createSonido(s));
				
				if(s!=null){
					textField.setText(s.getAbsPath());
				}
				actualizaInfo();
				
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
		
		JTextPane txtpnSoloArchivosWave = new JTextPane();
		springLayout.putConstraint(SpringLayout.WEST, txtpnSoloArchivosWave, 142, SpringLayout.WEST, frmBpmcalculator.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtpnSoloArchivosWave, -35, SpringLayout.NORTH, textField);
		txtpnSoloArchivosWave.setBackground(SystemColor.menu);
		txtpnSoloArchivosWave.setEditable(false);
		txtpnSoloArchivosWave.setText("Solo archivos WAVE compatibles");
		frmBpmcalculator.getContentPane().add(txtpnSoloArchivosWave);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 60, SpringLayout.SOUTH, btnAbrirArchivo);
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, btnAbrirArchivo);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 188, SpringLayout.SOUTH, btnAbrirArchivo);
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, textField);
		panel.setLayout(null);
		frmBpmcalculator.getContentPane().add(panel);
		
		JLabel lblArchivo = new JLabel("Archivo:");
		lblArchivo.setBounds(44, 0, 51, 14);
		panel.add(lblArchivo);
		
		label_playing = new JLabel("-");
		label_playing.setBounds(113, 0, 172, 14);
		panel.add(label_playing);
		
		label_samplerate = new JLabel("-");
		label_samplerate.setBounds(113, 25, 172, 14);
		panel.add(label_samplerate);
		
		JLabel label_3 = new JLabel("Sample Rate:");
		label_3.setBounds(20, 25, 93, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Bits per sample:");
		label_4.setBounds(10, 50, 93, 14);
		panel.add(label_4);
		
		label_bitssample = new JLabel("-");
		label_bitssample.setBounds(113, 50, 172, 14);
		panel.add(label_bitssample);
		
		JLabel label_6 = new JLabel("Mode:");
		label_6.setBounds(52, 75, 51, 14);
		panel.add(label_6);
		
		label_mode = new JLabel("-");
		label_mode.setBounds(113, 75, 172, 14);
		panel.add(label_mode);
		
		JLabel lblNSamples = new JLabel("N\u00BA Samples:");
		lblNSamples.setBounds(20, 100, 75, 14);
		panel.add(lblNSamples);
		
		label_samples = new JLabel("-");
		label_samples.setBounds(113, 100, 259, 14);
		panel.add(label_samples);
		
		JPanel panel_1 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 21, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 103, SpringLayout.WEST, frmBpmcalculator.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel_1, -126, SpringLayout.SOUTH, frmBpmcalculator.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_1, -120, SpringLayout.EAST, frmBpmcalculator.getContentPane());
		frmBpmcalculator.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		final JCheckBox chckbxCVariable = new JCheckBox("C Variable");
		chckbxCVariable.setBounds(10, 7, 98, 23);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxCVariable, 5, SpringLayout.NORTH, panel_1);
		springLayout.putConstraint(SpringLayout.WEST, chckbxCVariable, 21, SpringLayout.WEST, panel_1);
		panel_1.add(chckbxCVariable);
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
		
		JTextPane txtpnC = new JTextPane();
		txtpnC.setBounds(154, 7, 17, 20);
		springLayout.putConstraint(SpringLayout.NORTH, txtpnC, 6, SpringLayout.NORTH, panel_1);
		springLayout.putConstraint(SpringLayout.WEST, txtpnC, 99, SpringLayout.WEST, panel_1);
		panel_1.add(txtpnC);
		txtpnC.setText("C:");
		txtpnC.setEditable(false);
		txtpnC.setBackground(SystemColor.menu);
		
		textField_1 = new JTextField();
		textField_1.setBounds(177, 8, 42, 20);
		springLayout.putConstraint(SpringLayout.NORTH, textField_1, 17, SpringLayout.SOUTH, chckbxCVariable);
		springLayout.putConstraint(SpringLayout.WEST, textField_1, 20, SpringLayout.WEST, panel_1);
		springLayout.putConstraint(SpringLayout.EAST, textField_1, -15, SpringLayout.EAST, frmBpmcalculator.getContentPane());
		panel_1.add(textField_1);
		textField_1.setText("1.3");
		textField_1.setBackground(new Color(255, 255, 255));
		textField_1.setToolTipText("");
		textField_1.setEnabled(false);
		textField_1.setColumns(10);
		
		JButton btnDetect = new JButton("Calculate!");
		btnDetect.setBounds(62, 62, 98, 23);
		panel_1.add(btnDetect);
		springLayout.putConstraint(SpringLayout.WEST, btnDetect, 120, SpringLayout.WEST, frmBpmcalculator.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnDetect, -24, SpringLayout.SOUTH, frmBpmcalculator.getContentPane());
		
		JPanel panel_2 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_2, 12, SpringLayout.SOUTH, panel_1);
		springLayout.putConstraint(SpringLayout.WEST, panel_2, 103, SpringLayout.WEST, frmBpmcalculator.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel_2, -27, SpringLayout.SOUTH, frmBpmcalculator.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_2, 0, SpringLayout.EAST, panel_1);
		frmBpmcalculator.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		btnChart = new JButton("Gr\u00E1ficos");
		btnChart.setBounds(62, 53, 98, 23);
		panel_2.add(btnChart);
		springLayout.putConstraint(SpringLayout.WEST, btnChart, 0, SpringLayout.WEST, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, btnChart, -23, SpringLayout.SOUTH, frmBpmcalculator.getContentPane());
		
		chckbxA = new JCheckBox("Amplitud");
		chckbxA.setSelected(true);
		chckbxA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(!chckbxA.isSelected() && !chckbxEnerga.isSelected()){
					btnChart.setEnabled(false);
				}else{
					btnChart.setEnabled(true);
				}
			}
		});
		chckbxA.setBounds(6, 7, 97, 23);
		panel_2.add(chckbxA);
		
		chckbxEnerga = new JCheckBox("Energ\u00EDa");
		chckbxEnerga.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!chckbxA.isSelected() && !chckbxEnerga.isSelected()){
					btnChart.setEnabled(false);
				}else{
					btnChart.setEnabled(true);
				}
			}
		});
		chckbxEnerga.setSelected(true);
		chckbxEnerga.setBounds(125, 7, 97, 23);
		panel_2.add(chckbxEnerga);
		
		btnChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean rutaArchivo = textField.getText().length()>0;
				
				if(rutaArchivo){

						int confirm = JOptionPane.showConfirmDialog(frmBpmcalculator, "Le advertimos que la herramienta para representar los gráficos falla \n para conjuntos de muestras más grandes que el umbral marcado. \n¿Desea continuar?");
						
						if (JOptionPane.OK_OPTION == confirm){
							if(!(chckbxA.isSelected()) & !(chckbxEnerga.isSelected())){
								JOptionPane.showMessageDialog(frmBpmcalculator, "Seleccione el gráfico a mostrar" );
							}else{
								if(chckbxA.isSelected()){
									chart.run();
								}
								if(chckbxEnerga.isSelected()){
									chartE.run();								
								}								
							}
						}
				}else{
					//Ventana de mensaje de error
					JOptionPane.showMessageDialog(frmBpmcalculator, "Elija correctamente el archivo");
				}
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, txtpnC, -47, SpringLayout.NORTH, btnChart);
		springLayout.putConstraint(SpringLayout.SOUTH, textField_1, -5, SpringLayout.NORTH, btnChart);
		springLayout.putConstraint(SpringLayout.EAST, btnDetect, -41, SpringLayout.WEST, btnChart);
		
		
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
	
	public static void actualizaInfo(){
		String nombre = s.getNombre();
		String samplerate = ""+s.getClip().getFormat().getSampleRate()+" Hz";
		Integer samples = 0;
		try {
			samples = cuentaMuestras(f.createSonido(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String mode = ""+s.getClip().getFormat().getEncoding();
		String bitspersample = s.getClip().getFormat().getSampleSizeInBits()+" - "+s.getClip().getFormat().getChannels()+"canales";
		label_playing.setText(nombre);
		label_samplerate.setText(samplerate);
		if(samples>=5000000){
			label_samples.setForeground(Color.RED);
			label_samples.setText(""+samples+" - Umbral gráfico sobrepasado");
		}else{
			label_samples.setText(""+samples);
			label_samples.setForeground(Color.BLACK);
		}
		label_mode.setText(mode);
		label_bitssample.setText(bitspersample);
	}
	
	public static Integer cuentaMuestras(Sonido s) throws IOException{
		Integer res = 0;
		Integer frameSize = s.getClip().getFormat().getFrameSize();
		AudioInputStream input = s.getAudioStream();
		/*EXTRACCION DE LOS BYTES DE LAS MUESTRAS*/
		int nBufferSize = 1024 * frameSize;
		byte[]	abBuffer = new byte[nBufferSize];
//		System.out.println("Abriendo el fichero");
		
		while (true){
//			System.out.println(("trying to read (bytes): " + abBuffer.length));
			int	nBytesRead = input.read(abBuffer);
//			System.out.println("read (bytes): " + nBytesRead);
			if (nBytesRead == -1){
				break;
			}
			res = res + 1024;
		}
		return res;
	}
}

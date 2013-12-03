package charts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import sonido.Sonido;
import utiles.Utiles;

public class ChartEnergy {
	private AudioInputStream input;
	private XYSeries serieA;
	private Integer count;
	
	
	public ChartEnergy(Sonido s){
		this.input = s.getAudioStream();
<<<<<<< HEAD:src/charts/CreaChart.java
		this.serieA = new XYSeries ("Canal A");
		this.serieB = new XYSeries ("Canal B");
=======
		this.serieA = new XYSeries ("Energía");
>>>>>>> origin/Charts:src/charts/ChartEnergy.java
		this.count = 0;
	}
	
	private void añadeMuestras() throws IOException{
<<<<<<< HEAD:src/charts/CreaChart.java
		
=======
>>>>>>> origin/Charts:src/charts/ChartEnergy.java
		
		/*EXTRACCION DE LOS BYTES DE LAS MUESTRAS*/
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int nBufferSize = 1024 * input.getFormat().getFrameSize();
		byte[]	abBuffer = new byte[nBufferSize];
		while (true){
//			System.out.println(("trying to read (bytes): " + abBuffer.length));
			int	nBytesRead = input.read(abBuffer);
//			System.out.println("read (bytes): " + nBytesRead);
			if (nBytesRead == -1){
				break;
			}
			baos.write(abBuffer, 0, nBytesRead);
<<<<<<< HEAD:src/charts/CreaChart.java
		}
=======
			
			byte[] abAudioData = baos.toByteArray();
			
			/*extracción de un canal*/
			List<List<Short>> muestras = Utiles.extraeMuestras(abAudioData);
			List<Short> a = muestras.get(0);//canal left
			List<Short> b = muestras.get(1);//canal right
			
			beatDetector(a,b);
>>>>>>> origin/Charts:src/charts/ChartEnergy.java
			
		byte[] abAudioData = baos.toByteArray();
		List<List<Short>> muestras = Utiles.extraeMuestras(abAudioData);
		List<Short> a = muestras.get(0);//canal left
		List<Short> b = muestras.get(1);//canal right
		
		for(Short s:a){
			serieA.add(count,s);
			count++;
		}
	}
	
<<<<<<< HEAD:src/charts/CreaChart.java
	public XYSeriesCollection getDataset(){
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serieA);
		return dataset;
=======
	private void beatDetector(List<Short> a, List<Short> b){
		
		
		int n_samples = a.size();
		
		Double energy = 0.0;
		for (int i = 0; i< n_samples; i++){
			energy = energy + a.get(i)*a.get(i) + b.get(i)*b.get(i);
			serieA.add(count, energy);
			count++;
		}
	}
	
	public XYSeriesCollection getDataset(){		
		XYSeriesCollection datasetAB = new XYSeriesCollection();
		datasetAB.addSeries(serieA);
		return datasetAB;
>>>>>>> origin/Charts:src/charts/ChartEnergy.java
	}
	
	public void muestraChart() throws IOException{
		añadeMuestras();
<<<<<<< HEAD:src/charts/CreaChart.java
		JFreeChart chart = ChartFactory.createXYLineChart( 
				"Line Chart Demo 2", // chart title 
				"X", // x axis label 
				"Y", // y axis label 
=======
		JFreeChart chartAB = ChartFactory.createXYLineChart( 
				"Energía", // chart title 
				"Muestras", // x axis label 
				"Energía", // y axis label 
>>>>>>> origin/Charts:src/charts/ChartEnergy.java
				getDataset(), // data 
				PlotOrientation.VERTICAL, 
				true, // include legend 
				true, // tooltips 
				false // urls 
				);
		ChartFrame frameAB = new ChartFrame("Energy", chartAB); 
		frameAB.pack(); 
		frameAB.setVisible(true);
	}
}

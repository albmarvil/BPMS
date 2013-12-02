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
import utiles.Iterables2;
import utiles.Utiles;

public class CreaChart {
	
	private AudioInputStream input;
	private XYSeries serieA;
	
	private XYSeries serieB;
	private Integer count;
	
	public CreaChart(Sonido s){
		this.input = s.getAudioStream();
		this.serieB = new XYSeries ("Canal A");
		this.serieB = new XYSeries ("Canal B");
		this.count = 0;
	}
	
	private void añadeMuestras() throws IOException{
		/*EXTRACCION DE LOS BYTES DE LAS MUESTRAS*/
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int nBufferSize = 1024 * 4;
		byte[]	abBuffer = new byte[nBufferSize];
		while (true){
//			System.out.println(("trying to read (bytes): " + abBuffer.length));
			int	nBytesRead = input.read(abBuffer);
//			System.out.println("read (bytes): " + nBytesRead);
			if (nBytesRead == -1){
				break;
			}
			baos.write(abBuffer, 0, nBytesRead);
			
			
		}
		byte[] abAudioData = baos.toByteArray();
		
		/*extracción de un canal*/
		List<List<Short>> muestras = Utiles.extraeMuestras(abAudioData);
		List<Short> a = muestras.get(0);//canal left
		List<Short> b = muestras.get(1);//canal right
		
		for(Short s:a){
			serieA.add(3.0, count);
//			serieB.add(new Double(b.get(count)), count);
			count++;
		}

	}
	
	public XYSeriesCollection getDataset(){
		XYSeriesCollection dataset = new XYSeriesCollection(); 
		
		dataset.addSeries(serieA);
		dataset.addSeries(serieB);
		return dataset;
	}
	
	public void muestraChart() throws IOException{
		añadeMuestras();
		JFreeChart chart = ChartFactory.createXYLineChart( 
				"Line Chart Demo 2", // chart title 
				"X", // x axis label 
				"Y", // y axis label 
				getDataset(), // data 
				PlotOrientation.VERTICAL, 
				true, // include legend 
				true, // tooltips 
				false // urls 
				);
		ChartFrame frame = new ChartFrame("Test", chart); 
		frame.pack(); 
		frame.setVisible(true);
	}
	
}

package charts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

public class CreaChart {
	
	private AudioInputStream input;
	private XYSeries serieA;
	
	private XYSeries serieB;
	private Integer count;
	
	public CreaChart(Sonido s){
		this.input = s.getAudioStream();
		this.serieA = new XYSeries ("Canal A");
		this.serieB = new XYSeries ("Canal B");
		this.count = 0;
	}
	
	private void añadeMuestras() throws IOException{
		/*EXTRACCION DE LOS BYTES DE LAS MUESTRAS*/
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int nBufferSize = 1024 * 4;
		byte[]	abBuffer = new byte[nBufferSize];
		while (true){
			System.out.println(("trying to read (bytes): " + abBuffer.length));
			int	nBytesRead = input.read(abBuffer);
			System.out.println("read (bytes): " + nBytesRead);
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
			serieA.add(count, s);
			serieB.add(count, new Double(b.get(count)));
			count++;
		}

	}
	
	public XYSeriesCollection getDataset(){		
		XYSeriesCollection datasetAB = new XYSeriesCollection();
		datasetAB.addSeries(serieA);
		datasetAB.addSeries(serieB);
		return datasetAB;
	}
	
	public void muestraChart() throws IOException{
		añadeMuestras();
		
		
		
		JFreeChart chartAB = ChartFactory.createXYLineChart( 
				"Canales AB", // chart title 
				"Muestras", // x axis label 
				"Amplitud", // y axis label 
				getDataset(), // data 
				PlotOrientation.VERTICAL, 
				true, // include legend 
				true, // tooltips 
				false // urls 
				);
		ChartFrame frameAB = new ChartFrame("Canales AB", chartAB); 
		frameAB.pack(); 
		frameAB.setVisible(true);
	}
	
}

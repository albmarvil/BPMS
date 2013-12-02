package charts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
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
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int nBufferSize = 1024 * 4;
		byte[]	abBuffer = new byte[nBufferSize];
//		System.out.println("Abriendo el fichero");
		
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
		List<List<Short>> muestras = Utiles.extraeMuestras(abAudioData);
		List<Short> a = muestras.get(0);//canal left
		List<Short> b = muestras.get(1);//canal right
		
		for(Short s:a){
			serieA.add(count,s);
			count++;
		}
	}
	
	public XYSeriesCollection getDataset(){
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serieA);
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

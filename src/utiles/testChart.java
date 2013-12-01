package utiles;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class testChart {
	public static void main(String[] args){
		// create a dataset... 
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		dataset.setValue("Category 1", 43.2); 
		dataset.setValue("Category 2", 27.9); 
		dataset.setValue("Category 3", 79.5);
		
		// create a chart... 
		JFreeChart chart = ChartFactory.createPieChart("chart", dataset, true, true, false);
		
		// create and display a frame... 
		ChartFrame frame = new ChartFrame("Test", chart); 
		frame.pack(); 
		frame.setVisible(true);

	}
}

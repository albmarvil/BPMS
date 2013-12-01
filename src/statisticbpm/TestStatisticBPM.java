package statisticbpm;

import java.io.IOException;

import sonido.FactoriaSonido;
import sonido.Sonido;

public class TestStatisticBPM {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
		FactoriaSonido f = new FactoriaSonido();
		
		Sonido s = f.createSonido();
		
		BPMCalculator calculator = new BPMCalculatorImpl(s);
		System.out.println(calculator);
		System.out.println(calculator.run());
//		calculator = new BPMCalculatorImpl(f.createSonido(s));
		System.out.println(calculator.run(1.1));
	}

}

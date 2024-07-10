package explicit;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.jas.structure.Value;
import prism.PrismComponent;
import prism.PrismException;

public class BuchholzTest {

	public static final int MAXnumberOfStates = (int) 5;
	public static final int MAXnumberOfLabels = 2;
	
	public static DTMCSimple<Double> GenerateModel(int numberOfStates){
		
		Random random = new Random();
		DTMCSimple<Double> dtmcSimple = new DTMCSimple<Double>(numberOfStates);
		
		double threshold = 2 * Math.log(numberOfStates) / numberOfStates;
		for (int source = 0; source < numberOfStates; source++) {
			int outgoing = 0; // number of outgoing transitions of source
			
			
			double[] probability = new double[numberOfStates];
			
			for (int target = 0; target < numberOfStates; target++) {
				if (random.nextDouble() < threshold) {
					probability[target] = 1;
					outgoing++;
				}
			}
			if (outgoing > 0) {
				for (int target = 0; target < numberOfStates; target++) {
					probability[target] /= outgoing;
					if(probability[target]/outgoing > 0.0) {
						//System.out.println(source + " " + target + " " + probability[target]/outgoing);
						dtmcSimple.setProbability(source, target, probability[target]/outgoing);						
					}
					
				}
			} else {
				dtmcSimple.setProbability(source, source, 1.0);
			//	System.out.println(source + " " + source + " " + 1);
			}
		}
		return dtmcSimple;
	}
	
	public static List<BitSet> Generatelabels(int numberOfStates, int numberOfLabels){
		Random random = new Random();
		List<BitSet> propBSs = new ArrayList<>();
		for(int s = 0; s < numberOfStates; s++) {
			
			BitSet bitSet = new BitSet(numberOfLabels);
			//System.out.print(s + ": ");
			int mask = random.nextInt((1<<numberOfLabels));
			
			for(int i = 0; i < numberOfLabels; i++) {
				if(((mask >> i)&1) == 1) {
					bitSet.set(i, true);
				//	System.out.print(i + " ");
				}else {
					bitSet.set(i, false);
				}
			}
			propBSs.add(bitSet);
		}
		System.out.println("Labels");
		for(int i = 0; i < numberOfStates; i++) {
			System.out.print(i + ": ");
			for(int j = 0; j < numberOfLabels; j++) {
				//System.out.print(i+ " "+ j +" ::");
				if(propBSs.get(i).get(j))
					System.out.print(1);
				else
					System.out.print(0);
			}
			System.out.println(" ");
		}
		
		return propBSs;
	}
	
	
	
    public static void main(String[] args) {
        try {
            PrismComponent parent = new PrismComponent() {
            };

            
            Random random = new Random();
    		int numberOfStates = random.nextInt(MAXnumberOfStates) + 1;
    		int numberOfLabels = random.nextInt(MAXnumberOfLabels) + 1;
    		System.out.println(numberOfStates + " " + numberOfLabels);
    		DTMCSimple<Double> dtmc = GenerateModel(numberOfStates);
    		List<BitSet> propBSs = Generatelabels(numberOfStates, numberOfLabels);
    		
       
            System.out.println(dtmc.toString());
            
            Buchholz<Double> buchholz = new Buchholz<>(parent);
            boolean[] buch = buchholz.bisimilar(dtmc, propBSs);
            //*
    		System.out.println("buchholz:");
    		for(int i = 0; i < numberOfStates; i++) {
    			for(int j = 0; j < numberOfStates; j++) {
    				if(buch[i*numberOfStates + j]) {
    					System.out.print(1 + " ");						
    				}else {
    					System.out.print(0 + " ");
    				}
    				
    			}
    			System.out.println('\n');
    		}
    		//*/
    		
    		
    		 Bisimulation<Double> bism = new Bisimulation<>(parent);
             boolean[] bisimilation = bism.bisimilar(dtmc, propBSs);
             //*
     		System.out.println("bisimilation:");
     		for(int i = 0; i < numberOfStates; i++) {
     			for(int j = 0; j < numberOfStates; j++) {
     				if(bisimilation[i*numberOfStates + j]) {
     					System.out.print(1 + " ");						
     				}else {
     					System.out.print(0 + " ");
     				}
     				
     			}
     			System.out.println('\n');
     		}
     		//*/

        } catch (PrismException e) {
            e.printStackTrace();
        }
    }
}

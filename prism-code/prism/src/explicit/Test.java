package explicit;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.jas.structure.Value;
import prism.PrismComponent;
import prism.PrismException;

public class Test {

	public static final int MAXnumberOfStates = (int) 10;
	public static final int MAXnumberOfLabels = 1;
	
	public static DTMCSimple<Double> GenerateModel(int numberOfStates){
		
		Random random = new Random();
		DTMCSimple<Double> dtmcSimple = new DTMCSimple<Double>(numberOfStates);
		
		double threshold = 2 * Math.log(numberOfStates) / numberOfStates;
		for (int source = 0; source < numberOfStates; source++) {
			double outgoing = 0; // number of outgoing transitions of source
			
			
			double[] probability = new double[numberOfStates];
			
			for (int target = 0; target < numberOfStates; target++) {
				if (random.nextDouble() < threshold) {
					probability[target] = 1;
					outgoing++;
				}
			}
			if (outgoing > 0) {
				for (int target = 0; target < numberOfStates; target++) {
					
					if(probability[target]/outgoing > 0.0) {
						dtmcSimple.setProbability(source, target, probability[target]/outgoing);						
					}
					
				}
			} else {
				dtmcSimple.setProbability(source, source, 1.0);
			}
		}
		return dtmcSimple;
	}
	
	public static List<BitSet> Generatelabels(int numberOfStates, int numberOfLabels){
		Random random = new Random();
		List<BitSet> propBSs = new ArrayList<>();
		for(int s = 0; s < numberOfLabels; s++) {
			BitSet bitSet = new BitSet(numberOfStates);
			propBSs.add(bitSet);
		}
		
		for(int s = 0; s < numberOfStates; s++) {
			int mask = random.nextInt((1<<numberOfLabels));
			for(int i = 0; i < numberOfLabels; i++) {
				if(((mask >> i)&1) == 1) {
					propBSs.get(i).set(s, true);
				}else {
					propBSs.get(i).set(s, false);
				}
			}
		}
			
//		System.out.println("Labels");
//		for(int i = 0; i < numberOfLabels; i++) {
//			System.out.print(i + ": ");
//			for(int j = 0; j < numberOfStates; j++) {
//				//System.out.print(i+ " "+ j +" ::");
//				if(propBSs.get(i).get(j))
//					System.out.print(1);
//				else
//					System.out.print(0);
//			}
//			System.out.println(" ");
//		}
		
		return propBSs;
	}
	
	
	 private static void RandomModel() {
        try {
           
        	PrismComponent parent = new PrismComponent() {};
            
            Random random = new Random();
    		int numberOfStates = random.nextInt(MAXnumberOfStates) + 1;
    		int numberOfLabels = random.nextInt(MAXnumberOfLabels) + 1;
    		DTMCSimple<Double> dtmc = GenerateModel(numberOfStates);
    		List<BitSet> propBSs = Generatelabels(numberOfStates, numberOfLabels);
    		

    		Buchholz<Double> buchholz = new Buchholz<>(parent);
            boolean[] Buch = buchholz.bisimilar(dtmc, propBSs);
    
    		
    		ZeroDerisavi<Double> zero = new ZeroDerisavi<>(parent);
            boolean[] Zero = zero.bisimilar(dtmc, propBSs);

    		
            ZeroDerisaviRedBlack<Double> zerorb = new ZeroDerisaviRedBlack<>(parent);
            boolean[] ZeroRB = zerorb.bisimilar(dtmc, propBSs);    		
    		
    		Bisimulation<Double> bism = new Bisimulation<>(parent);
            boolean[] bisimilation = bism.bisimilar(dtmc, propBSs);
            
            Bisimulation<Double> prim = new Primitive<>(parent);
            boolean[] primitive = prim.bisimilar(dtmc, propBSs);
     		
     		// compare the result
    		for(int i = 0; i < numberOfStates; i++) {
    			for(int j = 0; j < numberOfStates; j++) {
    				if(primitive[i*numberOfStates+j] != ZeroRB[i*numberOfStates+j] || ZeroRB[i*numberOfStates+j]!= bisimilation[i*numberOfStates+j] || Zero[i*numberOfStates+j] != Buch[i*numberOfStates+j] || Zero[i*numberOfStates+j] != bisimilation[i*numberOfStates+j]) {
    					System.out.println("Erorr!! " + i + " " + j + " " + ZeroRB[i*numberOfStates + j] + " " + primitive[i*numberOfStates + j]);
    					System.out.println(dtmc.toString());
    					System.exit(0);
    				}
    				
    			}
    		}
    		
    		System.out.println("okay");		
     		
        } catch (PrismException e) {
            e.printStackTrace();
        }
    }
	 
	 
	 
	 
		
		
	 public static void main(String[] args) {
		
		 for(int i = 0; i < 1; i++)
				RandomModel();
	 }
	 
	 
}

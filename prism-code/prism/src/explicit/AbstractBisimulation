package explicit;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.jas.structure.Value;
import parser.State;
import prism.PrismComponent;
import prism.PrismException;
import prism.PrismNotSupportedException;

public abstract class AbstractBisimulation<Value> extends Bisimulation<Value> {

    public AbstractBisimulation(PrismComponent parent) throws PrismException {
        super(parent);
    }

    @Override
    public Model<Value> minimise(Model<Value> model, List<String> propNames, List<BitSet> propBSs) throws PrismException
	{
    	if(model instanceof DTMCSimple)
			return minimiseDTMC((DTMC<Value>) model, propNames, propBSs);
    	else
			throw new PrismNotSupportedException("Bisimulation minimisation not yet supported for " + model.getModelType() + "s");
	}
   
   

    @Override
    protected CTMC<Value> minimiseCTMC(CTMC<Value> ctmc, List<String> propNames, List<BitSet> propBSs) {
        throw new UnsupportedOperationException("Bisimulation minimisation not yet supported for CTMCs");
    }

    
   
	

   
}

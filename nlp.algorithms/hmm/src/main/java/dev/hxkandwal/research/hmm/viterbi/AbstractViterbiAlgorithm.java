package dev.hxkandwal.research.hmm.viterbi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AbstractViterbiAlgorithm {

	protected static final String HOT = "HOT";
	protected static final String COLD = "COLD";
	
	protected List<Map<String,Double>> emissionProbabilitiesMapList;
	protected Map<String,Double> transitionProbabilitiesMap;

	@SuppressWarnings("serial")
	public AbstractViterbiAlgorithm() {
		
		emissionProbabilitiesMapList = new LinkedList<>();

		emissionProbabilitiesMapList.add(new HashMap<String, Double>() {{ 
			put(HOT, Double.valueOf(".2"));
			put(COLD, Double.valueOf(".5"));
		}});
		emissionProbabilitiesMapList.add(new HashMap<String, Double>() {{ 
			put(HOT, Double.valueOf(".4"));
			put(COLD, Double.valueOf(".4"));
		}});
		emissionProbabilitiesMapList.add(new HashMap<String, Double>() {{ 
			put(HOT, Double.valueOf(".4"));
			put(COLD, Double.valueOf(".1"));
		}});

		transitionProbabilitiesMap = new HashMap<String, Double>() {{ 
			put("HOT|HOT", Double.valueOf(".7"));
			put("COLD|HOT", Double.valueOf(".3"));
			put("COLD|COLD", Double.valueOf(".6"));
			put("HOT|COLD", Double.valueOf(".4"));
			put("Pi|HOT", Double.valueOf(".8"));
			put("Pi|COLD", Double.valueOf(".2"));
		}};
	}
}
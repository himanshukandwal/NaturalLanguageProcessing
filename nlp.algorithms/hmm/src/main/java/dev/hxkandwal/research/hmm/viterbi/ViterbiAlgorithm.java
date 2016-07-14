package dev.hxkandwal.research.hmm.viterbi;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViterbiAlgorithm extends AbstractViterbiAlgorithm {

	private Map<String, Double> lastStoredHiddenStatesProbabilities;
	
	public static List<String> inputSequence1 = Arrays.asList("3 3 1 1 2 2 3 1 3".split("\\s+"));
	public static List<String> inputSequence2 = Arrays.asList("3 3 1 1 2 3 3 1 2".split("\\s+"));

	private List<String> backPointerStates = new LinkedList<>();
	
	public ViterbiAlgorithm() {
		super();
		lastStoredHiddenStatesProbabilities = new LinkedHashMap<>();

		// starting hidden state probability
		lastStoredHiddenStatesProbabilities.put(HOT, Double.valueOf("0"));
		lastStoredHiddenStatesProbabilities.put(COLD, Double.valueOf("0"));
	}

	public void recursiveViterbi(List<String> inputSequence, int currentIndex)
	{
		if (inputSequence == null || inputSequence.size() <= currentIndex) {
			return;
		}

		String observation = inputSequence.get(currentIndex);
		Set<String> hiddenMarkovStates = lastStoredHiddenStatesProbabilities.keySet();

		boolean isFirstTime = false;
		if (Collections.frequency(lastStoredHiddenStatesProbabilities.values(), 0d) == lastStoredHiddenStatesProbabilities.size()) {
			isFirstTime = true;
			System.out.println(" Viterbi running for the first time !");
		}

		if (isFirstTime) {
			for (String hiddenMarkovState : hiddenMarkovStates) {
				Double hiddenMarkovStateValue = BigDecimal.valueOf(transitionProbabilitiesMap.get("Pi|" + hiddenMarkovState))
						.multiply(new BigDecimal(emissionProbabilitiesMapList.get(Integer.valueOf(observation) - 1).get(hiddenMarkovState))).doubleValue();
				
				System.out.println(" observation [" + observation + " ] hiddenMarkovState [" + hiddenMarkovState + " | " + hiddenMarkovStateValue + "]");
				System.out.println(" \t computed : " + hiddenMarkovStateValue + " \t max : " + hiddenMarkovStateValue);
				lastStoredHiddenStatesProbabilities.put(hiddenMarkovState, hiddenMarkovStateValue);
			}
			
			Double bestPreviousStateValue = 0d;
			String bestPreviousState = null;
			for (String hiddenMarkovState : hiddenMarkovStates) {
				if (lastStoredHiddenStatesProbabilities.get(hiddenMarkovState) > bestPreviousStateValue) {
					bestPreviousStateValue = lastStoredHiddenStatesProbabilities.get(hiddenMarkovState);
					bestPreviousState = hiddenMarkovState;
				}
			}
			backPointerStates.add(bestPreviousState);
		}
		else {
			Map<String, Double> previousStatesDataForBackTracking = new LinkedHashMap<>();
			Map<String, Double> temporalStatesData = new LinkedHashMap<>();
			
			for (String hiddenMarkovState : hiddenMarkovStates) {
				Double maxHiddenMarkovStateValue = 0d;
				Double bestHiddenMarkovStateValue = 0d;
				
				for (String previousHiddenMarkovState : hiddenMarkovStates) {
					BigDecimal existingHiddenmarkovStateValue = BigDecimal.valueOf(lastStoredHiddenStatesProbabilities.get(previousHiddenMarkovState));

					/*System.out.println(existingHiddenmarkovStateValue + " * " +
							(BigDecimal.valueOf(transitionProbabilitiesMap.get(hiddenMarkovState + "|" + previousHiddenMarkovState)) + " * [" + hiddenMarkovState + "] " + 
							BigDecimal.valueOf(emissionProbabilitiesMapList.get(Integer.valueOf(observation) - 1).get(hiddenMarkovState)))); */
					
					Double hiddenMarkovStateValue = existingHiddenmarkovStateValue
							.multiply(BigDecimal.valueOf(transitionProbabilitiesMap.get(hiddenMarkovState + "|" + previousHiddenMarkovState)))
							.multiply(BigDecimal.valueOf(emissionProbabilitiesMapList.get(Integer.valueOf(observation) - 1).get(hiddenMarkovState))).doubleValue();
					
					Double hiddenMarkovStateValueForBackTracking = existingHiddenmarkovStateValue
							.multiply(BigDecimal.valueOf(transitionProbabilitiesMap.get(hiddenMarkovState + "|" + previousHiddenMarkovState))).doubleValue();
					
					previousStatesDataForBackTracking.put(previousHiddenMarkovState, hiddenMarkovStateValueForBackTracking);	
					
					if (hiddenMarkovStateValue > maxHiddenMarkovStateValue)
						maxHiddenMarkovStateValue = hiddenMarkovStateValue;
					
					if (hiddenMarkovStateValueForBackTracking > bestHiddenMarkovStateValue) 
						bestHiddenMarkovStateValue = hiddenMarkovStateValueForBackTracking;
					
					System.out.println(" \t computed : " + hiddenMarkovStateValue + " \t max : " + maxHiddenMarkovStateValue);
				}
				
				System.out.println(" observation [" + observation + " ] hiddenMarkovState [" + hiddenMarkovState + " | " + maxHiddenMarkovStateValue + "]");
				
				temporalStatesData.put(hiddenMarkovState, maxHiddenMarkovStateValue);
				previousStatesDataForBackTracking.put(hiddenMarkovState, bestHiddenMarkovStateValue);
			}
			lastStoredHiddenStatesProbabilities.clear();
			lastStoredHiddenStatesProbabilities.putAll(temporalStatesData);
			
			Double bestPreviousStateValue = 0d;
			Map.Entry<String, Double> bestPreviousStateEntry = null;
			
			for (Map.Entry<String, Double> previousStatesDataEntry : previousStatesDataForBackTracking.entrySet()) {
				if (previousStatesDataEntry.getValue() > bestPreviousStateValue) {
					bestPreviousStateValue = previousStatesDataEntry.getValue();
					bestPreviousStateEntry = previousStatesDataEntry;
				}
			}
			backPointerStates.add(bestPreviousStateEntry.getKey());
		}
		currentIndex ++;
		recursiveViterbi(inputSequence, currentIndex);
	}

	public void displayProbabilities() {

		StringBuffer displayBuffer = new StringBuffer();
		displayBuffer.append("------ EMISSION PROBABILITIES ------ ").append("\n");

		int index = 1;
		for (Iterator<Map<String, Double>> iterator = emissionProbabilitiesMapList.iterator(); iterator.hasNext();) {
			Map<String, Double> emissionMap =  iterator.next();
			Set<String> emissionMapKeys = emissionMap.keySet();

			for (String emissionMapKey : emissionMapKeys) {
				displayBuffer.append(index + "|" + emissionMapKey + " : " + emissionMap.get(emissionMapKey)).append("\t\t");
			}

			displayBuffer.append("\n");
			index ++;
		}

		displayBuffer.append("\n------ TRANSITION PROBABILITIES ------ ").append("\n");
		Set<String> transitionProbabilitiesMapKeys = transitionProbabilitiesMap.keySet();

		int pair = 0;
		for (String transitionProbabilitiesMapKey : transitionProbabilitiesMapKeys) {
			displayBuffer.append(transitionProbabilitiesMapKey + " : " + transitionProbabilitiesMap.get(transitionProbabilitiesMapKey))
					.append((pair % 2 == 0 ? "\t\t" : "\n"));
			pair ++;
		}
		displayBuffer.append("\n");
		System.out.println(displayBuffer.toString());
	}

	public void displayViterbiOutput() {
		StringBuffer displayBuffer = new StringBuffer("\n  ESTIMATED HIDDEN SEQUENCE : ");
		for (String backPointerState : backPointerStates) {
			displayBuffer.append(backPointerState).append(" ");
		}
		System.out.println(displayBuffer.toString());
		clearProcessedData();
	}
	
	public void clearProcessedData() {
		lastStoredHiddenStatesProbabilities.clear();

		// starting hidden state probability
		lastStoredHiddenStatesProbabilities.put(HOT, Double.valueOf("0"));
		lastStoredHiddenStatesProbabilities.put(COLD, Double.valueOf("0"));
		backPointerStates.clear();
		System.out.println();
	}

	public static void main(String[] args) {
		ViterbiAlgorithm viterbiALgorithm = new ViterbiAlgorithm();

		// display assumed probabilities
		viterbiALgorithm.displayProbabilities();
		
		// execute and display viterbi on input sequence 1
		viterbiALgorithm.recursiveViterbi(inputSequence1, 0);
		viterbiALgorithm.displayViterbiOutput();
		
		// execute and display viterbi on input sequence 2
		viterbiALgorithm.recursiveViterbi(inputSequence2, 0);
		viterbiALgorithm.displayViterbiOutput();
		
	}

}
package dev.hxkandwal.research.hmm;

import dev.hxkandwal.research.hmm.viterbi.PartOfSpeechTagAnalyzer;
import dev.hxkandwal.research.hmm.viterbi.ViterbiAlgorithm;
import dev.hxkandwal.research.hmm.viterbi.model.NLPAssignmentException;

public class Main {

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.out.println(" Please provide any input !");
			System.out.println(" java -cp <ViterbiAlgoImplementation.jar> viterbi");
			System.out.println(" OR ");
			System.out.println(" java -cp <ViterbiAlgoImplementation.jar> POS");
			System.exit(1);
		}
		
		if (args[0].equalsIgnoreCase("viterbi")) {
			ViterbiAlgorithm viterbiALgorithm = new ViterbiAlgorithm();

			// display assumed probabilities
			viterbiALgorithm.displayProbabilities();
			
			// execute and display viterbi on input sequence 1
			System.out.println("\n -------- Viterbi running for sequence : " + ViterbiAlgorithm.inputSequence1 + " -------- ");
			viterbiALgorithm.recursiveViterbi(ViterbiAlgorithm.inputSequence1, 0);
			viterbiALgorithm.displayViterbiOutput();
			
			// execute and display viterbi on input sequence 2
			System.out.println("\n -------- Viterbi running for sequence : " + ViterbiAlgorithm.inputSequence2 + " -------- ");
			viterbiALgorithm.recursiveViterbi(ViterbiAlgorithm.inputSequence2, 0);
			viterbiALgorithm.displayViterbiOutput();
		} 
		else if (args[0].equalsIgnoreCase("POS")) {
			PartOfSpeechTagAnalyzer analyzer = new PartOfSpeechTagAnalyzer();
			try {
				analyzer.readCorpusData();

				analyzer.displayReadCorpus();

				analyzer.estimateCorrectTagForEachWord();

				analyzer.recreateCorpusFileWithBestTagProbabilities();
				
			} catch (NLPAssignmentException exception) {
				exception.printStackTrace();
			}
		} 
		else {
			System.out.println(" Please provide valid input !");
			System.out.println(" java -cp <ViterbiAlgoImplementation.jar> viterbi");
			System.out.println(" OR ");
			System.out.println(" java -cp <ViterbiAlgoImplementation.jar> POS");
			System.exit(1);
		}
	}

}

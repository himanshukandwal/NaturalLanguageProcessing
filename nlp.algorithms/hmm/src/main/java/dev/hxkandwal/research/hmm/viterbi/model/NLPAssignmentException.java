package dev.hxkandwal.research.hmm.viterbi.model;

@SuppressWarnings("serial")
public class NLPAssignmentException extends Exception {

	public NLPAssignmentException() {
		super();
	}

	public NLPAssignmentException(String message) {
		super(" [ NLP ] " + message);
	}

	public NLPAssignmentException(Throwable cause) {
		super(cause);
	}

	public NLPAssignmentException(String message, Throwable cause) {
		super(" [ NLP ] " + message, cause);
	}

	public NLPAssignmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(" [ NLP ] " + message, cause, enableSuppression, writableStackTrace);
	}

}

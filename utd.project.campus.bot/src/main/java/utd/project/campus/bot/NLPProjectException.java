package utd.project.campus.bot;

@SuppressWarnings("serial")
public class NLPProjectException  extends Exception {

	public static final String NLP_PROJECT_EXCEPTION = " [ NLP Project Exception ] ";

	public NLPProjectException() {
		super();
	}

	public NLPProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(NLP_PROJECT_EXCEPTION + message, cause, enableSuppression, writableStackTrace);
	}

	public NLPProjectException(String message, Throwable cause) {
		super(NLP_PROJECT_EXCEPTION + message, cause);
	}

	public NLPProjectException(String message) {
		super(NLP_PROJECT_EXCEPTION + message);
	}

	public NLPProjectException(Throwable cause) {
		super(cause);
	}

}

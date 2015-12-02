package utd.project.campus.bot.nlp;

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.nlp.activities.LocationFindingNLPActivity;
import utd.project.campus.bot.nlp.activities.NameFindingNLPActivity;
import utd.project.campus.bot.nlp.activities.OrganizationFindingNLPActivity;
import utd.project.campus.bot.nlp.activities.PosTaggingNLPActivity;
import utd.project.campus.bot.nlp.activities.SentenceDetectorNLPActivity;
import utd.project.campus.bot.nlp.activities.SentimentDetectorNLPActivity;
import utd.project.campus.bot.nlp.activities.TokenizationNLPActivity;

public enum NLPActivityGenerationFactory {

	SENTENCE_DETECTION() {
		public NLPActivity getActivity() throws NLPProjectException {
			return new SentenceDetectorNLPActivity();
		}
	},
	TOKENIZATION() {
		public NLPActivity getActivity() throws NLPProjectException {
			return new TokenizationNLPActivity();
		}
	},
	NAME_FINDING() {
		public NLPActivity getActivity() throws NLPProjectException {
			return new NameFindingNLPActivity();
		}
	},
	LOCATION_FINDING() {
		public NLPActivity getActivity() throws NLPProjectException {
			return new LocationFindingNLPActivity();
		}
	},
	ORGANIZATION_FINDING() {
		public NLPActivity getActivity() throws NLPProjectException {
			return new OrganizationFindingNLPActivity();
		}
	},
	POS_TAGGING() {
		public NLPActivity getActivity() throws NLPProjectException {
			return new PosTaggingNLPActivity();
		}
	},
	SENTIMENT_DETECTION() {
		public NLPActivity getActivity() throws NLPProjectException {
			return SentimentDetectorNLPActivity.getInstance();
		}
	};

	public abstract NLPActivity getActivity() throws NLPProjectException;
	
}

package nlp.utdallas.edu.universitybot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.knowledge.KnowledgeProvider;
import utd.project.campus.bot.nlp.NLPActivity;
import utd.project.campus.bot.nlp.NLPActivityGenerationFactory;

import static android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH;
import static android.speech.RecognizerIntent.EXTRA_LANGUAGE;
import static android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL;
import static android.speech.RecognizerIntent.EXTRA_RESULTS;
import static android.widget.CompoundButton.OnCheckedChangeListener;
import static nlp.utdallas.edu.universitybot.util.UTDConstants.ERROR;
import static nlp.utdallas.edu.universitybot.util.UTDConstants.NEGATIVE;
import static nlp.utdallas.edu.universitybot.util.UTDConstants.NEUTRAL;
import static nlp.utdallas.edu.universitybot.util.UTDConstants.NEW_LINE;
import static nlp.utdallas.edu.universitybot.util.UTDConstants.POSITVE;
import static nlp.utdallas.edu.universitybot.util.UTDConstants.TAB;

public class MainActivity extends Activity implements RecognitionListener {

    private static final String TAG = "MainActivity";

    protected static final int REQUEST_OK = 1;

    private Drawable defaultMainSearchResultBackGround;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private ToggleButton m_mainVoiceButton;
    private ProgressBar m_progressBar;
    private TextView m_mainSearchResult;
    private TextView m_mainContentConsole;

    private TextToSpeech speechFeature;

    private class NLPActivityTask extends AsyncTask<Void, Void, Void> {

        private String recordedInput;
        private TextView m_mainContentConsole;
        private TextView m_mainSearchResult;
        private KnowledgeProvider knowledgeProvider;
        private StringBuffer displayConsole;
        private String response;
        private String sentiment;

        public NLPActivityTask(String recordedInput, TextView m_mainContentConsole, TextView m_mainSearchResult) {
            this.recordedInput = recordedInput;
            this.m_mainContentConsole = m_mainContentConsole;
            this.m_mainSearchResult = m_mainSearchResult;
        }

        public String getRecordedInput() {
            return recordedInput;
        }

        public TextView getMainContentConsole() {
            return m_mainContentConsole;
        }

        public TextView getMainSearchResult() {
            return m_mainSearchResult;
        }

        public KnowledgeProvider getKnowledgeProvider() {
            if (knowledgeProvider == null)
                knowledgeProvider = KnowledgeProvider.getInstance();
            return knowledgeProvider;
        }

        public StringBuffer getDisplayConsole() {
            if (null == displayConsole) {
                displayConsole = new StringBuffer();
            }
            return displayConsole;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public String getSentiment() {
            if (null == sentiment)
                sentiment = NEUTRAL;
            return sentiment;
        }

        public void setSentiment(String sentiment) {
            this.sentiment = sentiment;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(TAG, "onCancelled()");
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
            Log.d(TAG, "onCancelled(result): " + String.valueOf(result));
        }

        @Override
        protected Void doInBackground(Void... params) {
            getDisplayConsole().setLength(0);
            processRecorededInput(getRecordedInput());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute");
            getMainContentConsole().setText(getDisplayConsole().toString());

            if (getSentiment().equalsIgnoreCase(NEUTRAL))
                getMainSearchResult().setBackgroundColor(Color.parseColor("#BCE8FE"));
            else if (getSentiment().equalsIgnoreCase(POSITVE))
                getMainSearchResult().setBackgroundColor(Color.parseColor("#83F741"));
            else if (getSentiment().equalsIgnoreCase(NEGATIVE))
                getMainSearchResult().setBackgroundColor(Color.parseColor("#F96B24"));


            if (null != getResponse()) {
                Toast.makeText(getApplicationContext(), getResponse(), Toast.LENGTH_SHORT).show();
                speechFeature.speak(getResponse(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }

        private void processRecorededInput(String recordedInput) {
            try {
                NLPActivity activity;
                List responses;

                /* Creating tokens */
                activity = NLPActivityGenerationFactory.TOKENIZATION.getActivity();
                responses = activity.perform(recordedInput);

                List<String> tokens = new ArrayList<>();
                for (Object response : responses)
                    tokens.add(((String) response).toUpperCase());

                if (tokens.size() > 0) {

                    /* POS Tagging */
                    /*
                    activity = NLPActivityGenerationFactory.POS_TAGGING.getActivity();
                    responses = activity.perform(tokens.toArray(new String[0]));

                    if (responses.size() > 0) {
                        displayConsole.append(" POS Tags : ");
                        for (Iterator responseIterator = responses.iterator(); responseIterator.hasNext(); )
                            displayConsole.append(TAB).append((String) responseIterator.next());
                        displayConsole.append(NEW_LINE);
                    }

                    Log.v(TAG, displayConsole.toString());
                    m_mainContentConsole.setText(displayConsole.toString());
                    */

                    getDisplayConsole().append("Response found : ").append(NEW_LINE).append(TAB);

                    String response = getKnowledgeProvider().respond(tokens.toArray(new String[tokens.size()]));
                    getDisplayConsole().append(response);
                    Log.v(TAG, getDisplayConsole().toString());

                    if (null != response)
                        setResponse(response);
                    else
                        setResponse("I am sorry, I did not get that");


                    activity = NLPActivityGenerationFactory.SENTIMENT_DETECTION.getActivity();
                    responses = activity.perform((tokens.toArray(new String[tokens.size()])));
                    Log.v(TAG, "Received sentiment : " + responses.get(0));
                    setSentiment((String) responses.get(0));
                }

            } catch (NLPProjectException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");

        m_mainContentConsole = (TextView) findViewById(R.id.mainContentConsole);
        m_mainSearchResult = (TextView) findViewById(R.id.mainSearchResult);
        defaultMainSearchResultBackGround = m_mainSearchResult.getBackground();

        m_mainVoiceButton = (ToggleButton) findViewById(R.id.mainVoiceButton);
        m_progressBar = (ProgressBar) findViewById(R.id.mainSearchProgressBar);
        m_progressBar.setVisibility(View.INVISIBLE);

        speechFeature = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speechFeature.setLanguage(Locale.US);
                }
            }
        });

        m_mainSearchResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* do nothing */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { /* do nothing */ }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().contains(ERROR) && s.toString().length() > 0) {
                    Log.v(TAG, "launching activity for NLP processing! Found recorded content as - " + s.toString());

                    new NLPActivityTask(s.toString(), m_mainContentConsole, m_mainSearchResult).execute();
                }
            }
        });

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        recognizerIntent = new Intent(ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(EXTRA_LANGUAGE_MODEL, "en-US");
        recognizerIntent.putExtra(EXTRA_LANGUAGE, "en-US");

        m_mainVoiceButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m_mainSearchResult.setText("");
                m_mainContentConsole.setText("");
                m_mainSearchResult.setBackground(defaultMainSearchResultBackGround);

                if (isChecked) {
                    m_progressBar.setVisibility(View.VISIBLE);
                    m_progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);
                } else {
                    m_progressBar.setIndeterminate(false);
                    m_progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_OK  && resultCode == RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(EXTRA_RESULTS);
            Log.v(TAG, thingsYouSaid.get(0));

            m_mainSearchResult.setText(thingsYouSaid.get(0));
        }
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(TAG, "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech");
        m_progressBar.setIndeterminate(false);
        m_progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(TAG, "onBufferReceived: " + buffer.length);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech");
        m_progressBar.setIndeterminate(true);
        speech.stopListening();
        m_mainVoiceButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(TAG, "FAILED " + errorMessage);
        m_mainSearchResult.setText(ERROR + errorMessage + ". Please try again.");
        m_mainVoiceButton.setChecked(false);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(TAG, "onResults");
        Toast.makeText(getBaseContext(), "got voice results!", Toast.LENGTH_SHORT).show();
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        m_mainSearchResult.setText((matches == null || matches.size() == 0 ? "" : matches.get(0)));
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(TAG, "onRmsChanged: " + rmsdB);
        m_progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}

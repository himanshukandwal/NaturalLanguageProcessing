package nlp.utdallas.edu.universitybot;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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

import utd.project.campus.bot.NLPProjectException;
import utd.project.campus.bot.nlp.NLPActivity;
import utd.project.campus.bot.nlp.NLPActivityGenerationFactory;

import static android.widget.CompoundButton.OnCheckedChangeListener;
import static nlp.utdallas.edu.universitybot.util.UTDConstants.NEW_LINE;
import static nlp.utdallas.edu.universitybot.util.UTDConstants.TAB;

public class MainActivity extends Activity implements RecognitionListener {

    private static final String TAG = "MainActivity";

    protected static final int REQUEST_OK = 1;

    private SpeechRecognizer speech;
    private Intent recognizerIntent;
    private ToggleButton m_mainVoiceButton;
    private ProgressBar m_progressBar;
    private TextView m_mainSearchResult;
    private TextView m_mainContentConsole;

    private class NLPActivityTask extends AsyncTask<Void, Void, Void> {

        private String recordedInput;
        private TextView m_mainContentConsole;

        public NLPActivityTask(String recordedInput, TextView m_mainContentConsole) {
            this.recordedInput = recordedInput;
            this.m_mainContentConsole = m_mainContentConsole;
        }

        @Override
        protected Void doInBackground(Void... params) {
            processRecorededInput(recordedInput);
            return null;
        }

        private void processRecorededInput(String recordedInput) {
            StringBuffer displayConsole = new StringBuffer();

            displayConsole.append(("Recorded Input - " + recordedInput)).append(NEW_LINE);

            try {
                /* Detecting sentences */
                NLPActivity activity = NLPActivityGenerationFactory.SENTENCE_DETECTION.getActivity();
                List responses = activity.perform(recordedInput);

                if (responses.size() > 0) {
                    displayConsole.append(" Sentence(s) Detected : ");
                    for (Iterator responseIterator = responses.iterator(); responseIterator.hasNext(); )
                        displayConsole.append(TAB).append((String) responseIterator.next()).append(NEW_LINE);
                }

                /* Creating tokens */
                activity = NLPActivityGenerationFactory.TOKENIZATION.getActivity();
                responses = activity.perform(recordedInput);

                List<String> tokens = new ArrayList<>();
                for (Object response : responses)
                    tokens.add((String) response);

                if (tokens.size() > 0) {

                    /* Detecting names */
                    activity = NLPActivityGenerationFactory.NAME_FINDING.getActivity();
                    responses = activity.perform(tokens.toArray(new String[0]));

                    if (responses.size() > 0) {
                        displayConsole.append(" Names Detected : ");
                        for (Iterator responseIterator = responses.iterator(); responseIterator.hasNext(); )
                            displayConsole.append(TAB).append((String) responseIterator.next());
                        displayConsole.append(NEW_LINE);
                    }

                    Log.v(TAG, displayConsole.toString());

                    /* Detecting Locations */
                    activity = NLPActivityGenerationFactory.LOCATION_FINDING.getActivity();
                    responses = activity.perform(tokens.toArray(new String[0]));

                    if (responses.size() > 0) {
                        displayConsole.append(" Locations Detected : ");
                        for (Iterator responseIterator = responses.iterator(); responseIterator.hasNext(); )
                            displayConsole.append(TAB).append((String) responseIterator.next());
                        displayConsole.append(NEW_LINE);
                    }

                    Log.v(TAG, displayConsole.toString());
                    /* POS Tagging */
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

        m_mainContentConsole = (TextView) findViewById(R.id.mainContentConsole);
        m_mainSearchResult = (TextView) findViewById(R.id.mainSearchResult);
        m_mainVoiceButton = (ToggleButton) findViewById(R.id.mainVoiceButton);
        m_progressBar = (ProgressBar) findViewById(R.id.mainSearchProgressBar);
        m_progressBar.setVisibility(View.INVISIBLE);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, "en-US");

        m_mainVoiceButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m_mainSearchResult.setText("");
                if (isChecked) {
                    m_progressBar.setVisibility(View.VISIBLE);
                    m_progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);
                } else {
                    m_progressBar.setIndeterminate(false);
                    m_progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
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
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
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
        Log.i(TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech");
        m_progressBar.setIndeterminate(true);
        m_mainVoiceButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(TAG, "FAILED " + errorMessage);
        m_mainSearchResult.setText(errorMessage + ". Please try search again.");
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
        Toast.makeText(getBaseContext(), "got voice results!", Toast.LENGTH_SHORT);
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + " ";

        m_mainSearchResult.setText(text);
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

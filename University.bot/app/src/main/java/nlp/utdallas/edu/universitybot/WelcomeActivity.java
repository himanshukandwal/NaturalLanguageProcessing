package nlp.utdallas.edu.universitybot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import nlp.utdallas.edu.universitybot.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class WelcomeActivity extends Activity {

    private static final String TAG = "WelcomeActivity";

    private View.OnClickListener welcomeScreenClicksListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent routeToMainActivityIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(routeToMainActivityIntent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_welcome);

        TextView mWelcomeTitleTextView = (TextView) findViewById(R.id.welcomeTitle);
        mWelcomeTitleTextView.setOnClickListener(welcomeScreenClicksListener);

        ImageView mWelcomeLogoImageView = (ImageView) findViewById(R.id.welcomeUtdLogo);
        mWelcomeLogoImageView.setOnClickListener(welcomeScreenClicksListener);

        TextView mWelcomeTitleDescTextView = (TextView) findViewById(R.id.welcomeTitleDesc);
        mWelcomeTitleDescTextView.setOnClickListener(welcomeScreenClicksListener);

        TextView mWelcomeScreenFillerTextView = (TextView) findViewById(R.id.welcomeScreenFiller);
        mWelcomeScreenFillerTextView.setOnClickListener(welcomeScreenClicksListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}

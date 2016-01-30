/**
 * Class: Buyer_Voicesearch_Activity
 * This class Buyer_Voicesearch_Activity extends the super class Activity, so this class will
 * override the methods defined in the super class Activity. This class will implement the
 * functionality of searching a book by voice.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_search;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apple.buyoldbooksunit3.R;

import java.util.ArrayList;
import java.util.List;

public class Buyer_Voicesearch_Activity extends Activity {

    //private static variable
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    //private instance variable
    private ListView resultListView;
    private String key;
    private Bundle bundle;

    @Override
    /*
     * onCreate method:
     * This method is overriden the onCreate method defined in the super class Activitiy.
     * This class will implement the functionality of searching a book by voice.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__voicesearch_);
        bundle = new Bundle();
        Button speakButton = (Button) findViewById(R.id.speakbutton);
        resultListView = (ListView) findViewById(R.id.voiceSearchlistView);

        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0)
        {
            speakButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    startVoiceRecognitionActivity();
                }
            });
        } else {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }

        //set the result list into the ListView
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                key = (String) ((TextView) view).getText();
                bundle.putString("key", key);
                Intent intent =new Intent();
                intent.setClass(Buyer_Voicesearch_Activity.this, Buyer_Search_Activity.class);
                intent.putExtras(bundle);
                Buyer_Voicesearch_Activity.this.startActivity(intent);
            }
        });
    }

    /*
     * startVoiceRecognitionActivity method:
     * This method can implement the voice recognizing.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    /*
     * onActivityResult method:
     * This method will return the result while the voice recognizing process is finished.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK)
        {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            resultListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

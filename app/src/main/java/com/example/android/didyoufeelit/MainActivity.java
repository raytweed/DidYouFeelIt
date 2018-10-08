package com.example.android.didyoufeelit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Displays the perceived strength of a single earthquake event based on responses from people who
 * felt the earthquake.
 */
public class MainActivity extends AppCompatActivity {

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Perform the HTTP request for earthquake data and process the response.
        FeelitAsyncTask task=new FeelitAsyncTask();
        // Update the information displayed to the user.
        task.execute(USGS_REQUEST_URL);
    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(Event earthquake) {
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        TextView tsunamiTextView = (TextView) findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.numOfPeople));

        TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.perceivedStrength);
    }
     private class FeelitAsyncTask extends AsyncTask<String,Void,Event>{
         @Override
         protected Event doInBackground(String... urls) {
             Event earthquake = Utils.fetchEarthquakeData(urls[0]);
             return earthquake;
         }

         @Override
         protected void onPostExecute(Event earthuake) {
             updateUi(earthuake);
         }
     }
}

package com.shockn745.workoutmotivationaltool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

/**
 * Main activity displaying a duration picker, the main "motivate me" button and a secondary
 * button used to change the gym location
 * @author Florian Kempenich
 */
public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        // Set the default values for the very first launch of the application.
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent startSettings = new Intent(this, SettingsActivity.class);
            startActivity(startSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The main fragment of the application
     */
    public static class MainFragment extends Fragment {

        private Button mMotivateButton;
        private Button mChangeLocationButton;
        private NumberPicker mDurationPicker;

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            // Find elements by id
            mMotivateButton = (Button) rootView.findViewById(R.id.motivate_button);
            mChangeLocationButton = (Button) rootView.findViewById(R.id.change_location_button);
            mDurationPicker = (NumberPicker) rootView.findViewById(R.id.duration_picker);


            // Set listeners
            mMotivateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // TEST location
                    // TODO Delete test
                    SharedPreferences prefs = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());

                    long latLong = prefs.getLong(GymLocationActivity.LATITUDE_KEY, 5);
                    long lngLong = prefs.getLong(GymLocationActivity.LONGITUDE_KEY, 5);
                    int warmup = prefs.getInt(getString(R.string.pref_warmup_key), -1);
                    int stretching = prefs.getInt(getString(R.string.pref_stretching_key), -1);


                    // TODO use contains to check if a preference has been set
                    // If not tell te user to set it (for the location)
                    // For the duration use the default value
                    if (!prefs.contains(GymLocationActivity.LATITUDE_KEY)) {
                        Log.v(LOG_TAG, "No preference saved : latitude");
                    }
                    if (!prefs.contains(GymLocationActivity.LONGITUDE_KEY)) {
                        Log.v(LOG_TAG, "No preference saved : longitude");
                    }
                    if (!prefs.contains(getString(R.string.pref_warmup_key))) {
                        Log.v(LOG_TAG, "No preference saved : warmup");
                    }
                    if (!prefs.contains(getString(R.string.pref_stretching_key))) {
                        Log.v(LOG_TAG, "No preference saved : stretching");
                    }

                    double lat = Double.longBitsToDouble(latLong);
                    double lng = Double.longBitsToDouble(lngLong);
                    Log.v(LOG_TAG, "lat : " + lat);
                    Log.v(LOG_TAG, "long : " + lng);
                    Log.v(LOG_TAG, "warmup : " + warmup);
                    Log.v(LOG_TAG, "stretching : " + stretching);

                    Intent startMotivation = new Intent(getActivity(), MotivationActivity.class);
                    startActivity(startMotivation);
                }
            });
            mChangeLocationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startGymLocation = new Intent(getActivity(), GymLocationActivity.class);
                    startActivity(startGymLocation);
                }
            });
            mDurationPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    Toast.makeText(getActivity(),
                            "Coucou, numero choisi : " + newVal,
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });

            // Configure mDurationPicker
            mDurationPicker.setMinValue(getResources().getInteger(R.integer.main_duration_min));
            mDurationPicker.setMaxValue(getResources().getInteger(R.integer.main_duration_max));
            // Disable focus for the elements of the picker (disable keyboard)
            mDurationPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);


            return rootView;
        }
    }
}

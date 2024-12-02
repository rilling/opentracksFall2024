package de.dennisguse.opentracks.publicapi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import de.dennisguse.opentracks.data.models.Track;
import de.dennisguse.opentracks.services.TrackRecordingServiceConnection;
import de.dennisguse.opentracks.ui.markers.MarkerEditActivity;
import de.dennisguse.opentracks.util.IntentUtils;

/**
 * Public API to create a Marker for a given track with a given location
 */
public class CreateMarkerActivity extends AppCompatActivity {

    public static final String EXTRA_TRACK_ID = "track_id";
    public static final String EXTRA_LOCATION = "location";

    private static final String CUSTOM_PERMISSION = "de.dennisguse.opentracks.permission.CREATE_MARKER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the activity was started with the required permission
        if (!isCallingAppAllowed()) {
            finish();
            return;
        }

        // Retrieve the track ID and location from the Intent
        Track.Id trackId = new Track.Id(getIntent().getLongExtra(EXTRA_TRACK_ID, 0L));
        Location location;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            location = getIntent().getParcelableExtra(EXTRA_LOCATION, Location.class);
        } else {
            location = null;
        }

        // Validate the input data
        if (trackId == null || location == null) {
            finish();
            return;
        }

        TrackRecordingServiceConnection.execute(this, (service, self) -> {
            Intent intent = IntentUtils
                    .newIntent(this, MarkerEditActivity.class)
                    .putExtra(MarkerEditActivity.EXTRA_TRACK_ID, trackId)
                    .putExtra(MarkerEditActivity.EXTRA_LOCATION, location);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Checks if the calling app has the required permission to start this activity.
     * @return true if the calling app is allowed, false otherwise.
     */
    private boolean isCallingAppAllowed() {
        // Check if the calling app has the custom permission
        return checkCallingOrSelfPermission(CUSTOM_PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }
}
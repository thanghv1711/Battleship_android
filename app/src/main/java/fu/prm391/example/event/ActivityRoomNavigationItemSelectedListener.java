package fu.prm391.example.event;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import fu.prm391.example.view.R;

public class ActivityRoomNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;

    public ActivityRoomNavigationItemSelectedListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileItem:                      // click profile item menu
                Toast.makeText(activity, "Click profile", Toast.LENGTH_LONG).show();
                break;
            case R.id.logoutItem:                       // click log out item menu
                activity.finish();
                break;
            default:
                break;
        }
        return true;
    }
}

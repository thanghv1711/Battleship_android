package fu.prm391.example.event;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

public class DInviteEventInviteRefusedListener implements Emitter.Listener {

    private Activity activity;
    private Dialog dialog;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public DInviteEventInviteRefusedListener(Activity activity, Dialog dialog) {
        this.activity = activity;
        this.dialog = dialog;
    }

    @Override
    public void call(Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Opponent refuse you invitation!!!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }
}

package fu.prm391.example.event;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import fu.prm391.example.adapter.PlayerAdapter;
import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.Player;
import fu.prm391.example.view.LoadDialog;
import fu.prm391.example.view.NotifyDialog;
import fu.prm391.example.view.RoomBattleActivity;

public class APlayGameEventPlayerDisconnectListener implements Emitter.Listener {

    private Activity activity;

    public APlayGameEventPlayerDisconnectListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void call(final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                NotifyDialog notifyDialog = new NotifyDialog(activity, "VICTORY", 5, Color.RED);
                notifyDialog.show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadDialog ld = new LoadDialog(activity, "Back to Room Battle", 2);
                        ld.show();
                    }
                }, 5000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SocketCommon mSocket = ((ApplicationCustom) activity.getApplicationContext()).getmSocket();
                        mSocket.sendEvent(EventSocket.EVENT_CLIENT_END_GAME);
                        activity.finish();
                    }
                }, 7000);
            }
        });
    }
}

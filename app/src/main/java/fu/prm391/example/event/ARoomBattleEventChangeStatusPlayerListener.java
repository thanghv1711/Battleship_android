package fu.prm391.example.event;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fu.prm391.example.adapter.PlayerAdapter;
import fu.prm391.example.entity.Player;
import fu.prm391.example.view.RoomBattleActivity;

public class ARoomBattleEventChangeStatusPlayerListener implements Emitter.Listener {
    private Activity activity;
    private ListView lvPlayer;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ARoomBattleEventChangeStatusPlayerListener(Activity activity, ListView lvPlayer) {
        this.activity = activity;
        this.lvPlayer = lvPlayer;
    }

    @Override
    public void call(final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject data = (JSONObject) args[0];
                JSONObject check = (JSONObject) args[1];
                try {
                    String id = data.getString("id");
                    int bl = check.getInt("isPlay");
                    Log.i("Trace", "Change status id = " +id );
                    for (Player u : RoomBattleActivity.players) {
                        if(u.getId().equals(id)){
                            if (bl == 1) {
                                u.setAvailable(true);
                            }else {
                                u.setAvailable(false);
                            }
                        }
                    }
                    PlayerAdapter adapter = new PlayerAdapter(activity, RoomBattleActivity.players);
                    lvPlayer.setAdapter(adapter);
                } catch (JSONException e) {
                    return;
                }
            }
        });
    }
}

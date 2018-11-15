package fu.prm391.example.event;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fu.prm391.example.entity.Player;
import fu.prm391.example.view.ArrangeBoardActivity;

public class DInviteEventInviteAcceptedListener implements Emitter.Listener {

    private Activity activity;
    private Dialog dialog;
    private List<Player> players;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public DInviteEventInviteAcceptedListener(Activity activity, Dialog dialog, List<Player> players) {
        this.activity = activity;
        this.players = players;
        this.dialog = dialog;
    }

    @Override
    public void call( final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject data = (JSONObject) args[0];

                try {
                    final String id = data.getString("id");
                    for(int i = 0; i< players.size(); i++){
                        if(players.get(i).getId().equals(id)){
                            Intent intent = new Intent(activity, ArrangeBoardActivity.class);
                            intent.putExtra("idEnemy",id);
                            Log.i("Trace", "idEnemy" + id);
                            dialog.dismiss();
                            activity.startActivity(intent);
                            break;
                        }
                    }
                } catch (JSONException e) {
                    return;
                }
            }
        });
    }

}

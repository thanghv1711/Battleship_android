package fu.prm391.example.event;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import fu.prm391.example.adapter.PlayerAdapter;
import fu.prm391.example.entity.Player;
import fu.prm391.example.view.RoomBattleActivity;


public class ARoomBattleEventAddNewPlayerListener implements Emitter.Listener {

    private Activity activity;
    private ListView lvPlayer;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ARoomBattleEventAddNewPlayerListener(Activity activity, ListView lvPlayer) {
        this.activity = activity;
        this.lvPlayer = lvPlayer;
    }

    @Override
    public void call( final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    String name = data.getString("name");
                    boolean isplay = data.getBoolean("isPlay");

                    Player u = new Player(name,id, isplay);
                    RoomBattleActivity.players.add(u);

                    PlayerAdapter adapter = new PlayerAdapter(activity, RoomBattleActivity.players);
                    lvPlayer.setAdapter(adapter);
                } catch (JSONException e) {
                    return;
                }
            }
        });
    }

    public  void showMessage(String message){
        Toast.makeText(activity, message,Toast.LENGTH_LONG).show();
    }
}

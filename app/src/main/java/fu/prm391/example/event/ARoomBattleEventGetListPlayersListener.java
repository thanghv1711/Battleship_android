package fu.prm391.example.event;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fu.prm391.example.adapter.PlayerAdapter;
import fu.prm391.example.entity.Player;
import fu.prm391.example.view.RoomBattleActivity;

public class ARoomBattleEventGetListPlayersListener implements Emitter.Listener {
    private Activity activity;
    private ListView lvPlayer;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ARoomBattleEventGetListPlayersListener(Activity activity, ListView lvPlayer) {
        this.activity = activity;
        this.lvPlayer = lvPlayer;
    }

    @Override
    public void call( final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray data = (JSONArray) args[0];
                try {
                    List<Player> listPlayer = new ArrayList<>();
                    for(int i = 0; i< data.length(); i++){
                        JSONObject object =(JSONObject)data.get(i);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        boolean isPlay = object.getBoolean("isPlay");
                        Player u = new Player(name, id, isPlay);
                        listPlayer.add(u);
                    }
                    RoomBattleActivity.players = listPlayer;
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

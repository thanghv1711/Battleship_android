package fu.prm391.example.event;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fu.prm391.example.entity.ArrangeBoard;
import fu.prm391.example.entity.Ship;
import fu.prm391.example.view.ArrangeBoardActivity;
import fu.prm391.example.view.PlayGameActivity;
import fu.prm391.example.view.R;

public class AArrangeBoardEventGetEnemyShipCoordinatesListener implements Emitter.Listener {
    private Activity activity;

    private ArrangeBoard board;
    private String idEnemy;
    private boolean turn;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public AArrangeBoardEventGetEnemyShipCoordinatesListener(Activity activity, String idEnemy) {
        this.activity = activity;
        this.idEnemy = idEnemy;
    }

    @Override
    public void call( final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("Trace", "event get ship");
                JSONArray dtmap = (JSONArray) args[0];
                JSONObject dtturn = (JSONObject) args[1];
                try {
                    for(int i = 0; i< dtmap.length(); i++){
                        JSONArray col = (JSONArray) dtmap.get(i);
                        for( int j = 0; j< col.length(); j++){
                           ArrangeBoardActivity.EnemyShipCoordinates[i][j] =(boolean) col.get(j);
                        }
                    }
                    turn = dtturn.getBoolean("turn");
                    board = activity.findViewById(R.id.aArrangeBoardBoard);
                    Intent intent = new Intent(activity, PlayGameActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("tileSize", board.getTileSize());
                    intent.putExtra("idEnemy",idEnemy);
                    intent.putExtra("turn", turn);
                    activity.startActivity(intent);
                    activity.finish();
                } catch (JSONException e) {
                    return;
                }
            }
        });
    }
}

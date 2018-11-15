package fu.prm391.example.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.view.ArrangeBoardActivity;
import fu.prm391.example.view.InvitedDialog;
import fu.prm391.example.view.RoomBattleActivity;


public class ARoomBattleEventInvitedBattleListener implements Emitter.Listener {

    private Activity activity;
    private ListView lvPlayer;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ARoomBattleEventInvitedBattleListener(Activity activity, ListView lvPlayer) {
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
                    final String id = data.getString("id");
//                    Toast.makeText(activity, id, Toast.LENGTH_SHORT).show();
                    for(int i = 0; i< RoomBattleActivity.players.size(); i++){
                        if(RoomBattleActivity.players.get(i).getId().equals(id)){
                            InvitedDialog invitedDialog = new InvitedDialog(activity, RoomBattleActivity.players.get(i), lvPlayer);
                            invitedDialog.show();
                            break;
                        }
                    }
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

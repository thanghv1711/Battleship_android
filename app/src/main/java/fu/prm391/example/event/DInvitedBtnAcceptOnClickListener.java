package fu.prm391.example.event;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import fu.prm391.example.adapter.PlayerAdapter;
import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.Player;
import fu.prm391.example.view.ArrangeBoardActivity;
import fu.prm391.example.view.RoomBattleActivity;

public class DInvitedBtnAcceptOnClickListener implements View.OnClickListener {

    private Activity activity;
    private Dialog dialog;
    private String idEnemy;
    private ListView lvPlayer;

    public DInvitedBtnAcceptOnClickListener(Activity activity, Dialog dialog, String idEnemy, ListView lvPlayer) {
        this.activity = activity;
        this.dialog = dialog;
        this.idEnemy = idEnemy;
        this.lvPlayer = lvPlayer;
    }

    @Override
    public void onClick(View v) {
        SocketCommon socket = ((ApplicationCustom)activity.getApplicationContext()).getmSocket();
        socket.sendEvent(EventSocket.EVENT_CLIENT_ACCEPT_INVITED, idEnemy);
        Intent intent = new Intent(activity, ArrangeBoardActivity.class);
        intent.putExtra("idEnemy",idEnemy);
        Log.i("Trace", "idEnemy" + idEnemy);
        for (Player u : RoomBattleActivity.players) {
            if(u.getId().equals(idEnemy)){
                u.setAvailable(true);
            }
        }
        PlayerAdapter adapter = new PlayerAdapter(activity, RoomBattleActivity.players);
        lvPlayer.setAdapter(adapter);
        dialog.dismiss();
        activity.startActivity(intent);
    }
}

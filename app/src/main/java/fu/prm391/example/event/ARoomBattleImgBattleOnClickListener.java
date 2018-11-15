package fu.prm391.example.event;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.Player;
import fu.prm391.example.view.InviteDialog;

public class ARoomBattleImgBattleOnClickListener implements View.OnClickListener {

    private Activity activity;
    private Player enemy;
    private List<Player> listPlayer;

    public ARoomBattleImgBattleOnClickListener(Activity activity, Player enemy, List<Player> listPlayer) {
        this.activity = activity;
        this.enemy = enemy;
        this.listPlayer = listPlayer;
    }

    @Override
    public void onClick(View v) {
        if (!enemy.isAvailable()) {
            Toast.makeText(activity, "Player is in other battle!!!", Toast.LENGTH_LONG).show();
            return;
        }
        SocketCommon mSocket = ((ApplicationCustom)activity.getApplicationContext()).getmSocket();
        mSocket.sendEvent(EventSocket.EVENT_CLIENT_INVITE_PLAYER, enemy.getId());
        // => invite dialog
        InviteDialog id = new InviteDialog(activity, listPlayer, enemy.getId());
        id.show();
    }
}

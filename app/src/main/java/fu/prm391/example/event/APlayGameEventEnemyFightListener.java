package fu.prm391.example.event;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;

import fu.prm391.example.adapter.PlayerAdapter;
import fu.prm391.example.configure.ApplicationCustom;
import fu.prm391.example.configure.Constant;
import fu.prm391.example.configure.EventSocket;
import fu.prm391.example.configure.SocketCommon;
import fu.prm391.example.entity.Player;
import fu.prm391.example.view.ArrangeBoardActivity;
import fu.prm391.example.view.LoadDialog;
import fu.prm391.example.view.NotifyDialog;
import fu.prm391.example.view.PlayGameActivity;
import fu.prm391.example.view.PlayGameFragment;
import fu.prm391.example.view.R;
import fu.prm391.example.view.RoomBattleActivity;

public class APlayGameEventEnemyFightListener implements Emitter.Listener {

    private Activity activity;
    private String idEnemy;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public APlayGameEventEnemyFightListener(Activity activity, String idEnemy) {
        this.activity = activity;
        this.idEnemy = idEnemy;
    }

    @Override
    public void call( final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int row = 0;
                int col = 0;
                JSONArray data = (JSONArray) args[0];
                try {
                    row = data.getInt(0);
                    col = data.getInt(1);

                    ImageView imageView =  (ImageView) PlayGameFragment.myBoard.getChildAt(row*Constant.BOARD_ROWS+col);
                    imageView.setBackgroundColor(Color.rgb(102, 178, 255));
                    int point [] = {row, col};
                    if(ArrangeBoardActivity.ShipCoordinates[row][col]) {
                        imageView.setImageResource(R.drawable.project_fire);
                        PlayGameFragment.yourPoint--;
                        PlayGameFragment.lbYourPoint.setText(PlayGameFragment.yourPoint+"");

                    } else {
                        imageView.setImageResource(R.drawable.project_wave);
                    }

                    Handler handler = new Handler();
                    if (PlayGameFragment.yourPoint == 0) {
                        NotifyDialog notifyDialog = new NotifyDialog(activity, "DEFEAT", 5, Color.RED);
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
                    } else {
                        NotifyDialog nd = new NotifyDialog(activity, "Your Turn", 1, Color.BLACK);
                        nd.show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((PlayGameActivity) activity).getViewPager().setCurrentItem(1, true);
                            }
                        }, 1000);
                        PlayGameFragment.turn = !PlayGameFragment.turn;
                    }
                } catch (JSONException e) {
                    return;
                }
            }
        });
    }

}

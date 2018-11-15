package fu.prm391.example.event;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import fu.prm391.example.view.RoomBattleActivity;

public class DLoginEventLoginListener implements Emitter.Listener {
    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public DLoginEventLoginListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void call( final Object... args) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject data = (JSONObject) args[0];
                String message;
                try {
                    message = data.getString("message");
                    if("error".equals(message)){
                        showMessage("Name be used");
                    }else{
                        showMessage(message);
                        Intent intent = new Intent(activity, RoomBattleActivity.class);
                        activity.startActivity(intent);
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

package fu.prm391.example.event;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class ARoomBattleFabChatOnClickListener implements View.OnClickListener {

    private Activity activity;

    public ARoomBattleFabChatOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(activity,"go to chat box", Toast.LENGTH_LONG).show();
    }
}

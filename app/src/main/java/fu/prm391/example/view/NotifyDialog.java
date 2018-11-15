package fu.prm391.example.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NotifyDialog extends Dialog implements IActivity {
    private TextView lbNotify;

    private String notify;
    private int timer;
    private int color;

    public NotifyDialog(Activity activity, String notify, int timer, int color) {
        super(activity);
        this.notify = notify;
        this.timer = timer;
        this.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notify);
        getViewById();
        configureComponent();
        loadData();
    }

    @Override
    public void configureScreen() {
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void getViewById() {
        lbNotify = findViewById(R.id.dNotiLbNoti);
    }

    @Override
    public void configureComponent() {
        lbNotify.setText(notify);
        lbNotify.setTextColor(color);
    }

    @Override
    public void loadData() {
        // run timer
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        timer--;
                        if (timer == 0) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dismiss();
            }
        };
        thread.start();
    }

    @Override
    public void implementEvent() {

    }
}

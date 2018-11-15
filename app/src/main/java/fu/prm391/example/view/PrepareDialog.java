package fu.prm391.example.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

public class PrepareDialog extends Dialog implements IActivity{

    private Activity activity;
    private TextView lbPrepare;
    private int countdowwn;

    public PrepareDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notify);
        getViewById();
        configureComponent();
        loadData();
        implementEvent();
    }

    @Override
    public void getViewById() {
        lbPrepare = findViewById(R.id.dNotiLbNoti);
    }

    @Override
    public void configureScreen() {
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void configureComponent() {
    }

    @Override
    public void loadData() {
        // run prepare
        Thread thread = new Thread() {
            @Override
            public void run() {
                countdowwn = 4;
                while (true) {
                    try {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (countdowwn == 4) {
                                    lbPrepare.setText("Prepare");
                                } else {
                                    lbPrepare.setText(String.valueOf(countdowwn));
                                }
                            }
                        });
                        Thread.sleep(1000);
                        countdowwn--;
                        if (countdowwn == 0) {
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

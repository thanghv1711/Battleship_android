package fu.prm391.example.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadDialog extends Dialog implements IActivity{
    private Activity activity;
    private TextView lbTitle;
    private ImageView imgLoad;

    private String title;
    private int timer;

    public LoadDialog(Activity activity, String title, int timer) {
        super(activity);
        this.activity = activity;
        this.title = title;
        this.timer = timer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);
        getViewById();
        configureComponent();
        loadData();
    }

    @Override
    public void configureScreen() {
        getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void getViewById() {
        lbTitle = findViewById(R.id.dLoadLbTitle);
        imgLoad = findViewById(R.id.dLoadImgLoad);
    }

    @Override
    public void configureComponent() {
        lbTitle.setText(title);
    }

    @Override
    public void loadData() {
        // run timer
        final Drawable d = imgLoad.getDrawable();
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (d instanceof AnimatedVectorDrawableCompat) {
                                    AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat)d;
                                    avd.start();
                                } else if (d instanceof AnimatedVectorDrawable) {
                                    AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
                                    avd.start();
                                }
                            }
                        });
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

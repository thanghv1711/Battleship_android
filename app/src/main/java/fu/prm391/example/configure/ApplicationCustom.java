package fu.prm391.example.configure;

import android.app.Application;

public class ApplicationCustom extends Application {
   private  SocketCommon mSocket;

    public SocketCommon getmSocket() {
        return mSocket;
    }

    public void setmSocket(SocketCommon mSocket) {
        this.mSocket = mSocket;
    }
}

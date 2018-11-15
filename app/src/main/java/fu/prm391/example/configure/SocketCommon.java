package fu.prm391.example.configure;

import com.github.nkzawa.emitter.Emitter;

import java.io.Serializable;

public interface SocketCommon extends Serializable {

    // connect socket to server;
    public void connect();

    // disconnect socket
    public void disconnect();

    // listent event from server
    public void listen(String event, Emitter.Listener listener);

    // send event to server
    public void sendEvent(final String action, final Object... args);

}

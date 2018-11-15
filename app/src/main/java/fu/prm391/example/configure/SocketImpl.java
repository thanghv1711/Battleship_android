package fu.prm391.example.configure;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketImpl implements SocketCommon {
    private Socket socket;
    private  String  uri;


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public SocketImpl(String uri) {
        this.uri = uri;
        createSocket();
    }


    private void createSocket(){
        try{
            socket = IO.socket(this.uri);
        }catch (URISyntaxException e){

        }
    }

    @Override
    public void listen(String event, Emitter.Listener listener){
        socket.on(event,listener);
    };

    @Override
    public void connect(){
        if(!socket.connected())
            socket.connect();
    }

    @Override
    public void disconnect(){
        socket.disconnect();
    }

    @Override
    public void sendEvent(final String action, final Object... args){
        socket.emit(action,args);
    }

}


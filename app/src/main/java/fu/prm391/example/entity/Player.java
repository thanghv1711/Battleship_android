package fu.prm391.example.entity;

import java.io.Serializable;

public class Player implements Serializable {
    private String nickname;
    private String id;  // format #xxx
    private boolean available;
    private int online;

    public Player(String nickname, String id, boolean available) {
        this.nickname = nickname;
        this.id = id;
        this.available = available;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
}

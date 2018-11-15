package fu.prm391.example.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fu.prm391.example.entity.Player;
import fu.prm391.example.event.ARoomBattleImgBattleOnClickListener;
import fu.prm391.example.view.R;

public class PlayerAdapter extends BaseAdapter {

    private Activity activity;
    private List<Player> listPlayer;

    public PlayerAdapter(Activity activity, List<Player> listPlayer) {
        this.activity = activity;
        this.listPlayer = listPlayer;
    }

    public List<Player> getListPlayer() {
        return this.listPlayer;
    }

    public void setListPlayer(List<Player> listPlayer) {
        this.listPlayer = listPlayer;
    }

    @Override
    public int getCount() {
        return listPlayer.size();
    }

    @Override
    public Object getItem(int position) {
        return listPlayer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.adapter_item_room_player, parent, false);
        }
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.color.colorLightBrown);
        } else {
            convertView.setBackgroundResource(R.color.colorDarkBrown);
        }
        ImageView imgPlayer = (ImageView) convertView.findViewById(R.id.aptItemRoomImgPlayer);
        TextView txtName = (TextView) convertView.findViewById(R.id.aptItemRoomTxtName);
        ImageView imgBattle = (ImageView) convertView.findViewById(R.id.aptItemRoomImgBattle);
        ImageView imgAvai = (ImageView) convertView.findViewById(R.id.aptItemRoomImgAvai);

        Player player = (Player) getItem(position);
        txtName.setText(player.getNickname());
        if (!player.isAvailable()) {
            imgAvai.setImageResource(R.drawable.project_offline);
        } else {
            imgAvai.setImageResource(R.drawable.project_online);
        }

        imgBattle.setOnClickListener(new ARoomBattleImgBattleOnClickListener(activity, player, listPlayer));
        return convertView;
    }
}

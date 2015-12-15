package utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tk.lachev.evestats.R;

/**
 * Created by Boris Lachev on 12/15/2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CharacterViewHolder> {

    List<Character> _charactersList;

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CharacterViewHolder cvh = new CharacterViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        holder.name.setText(_charactersList.get(position).get_name());
        holder.avatar.setImageResource(R.mipmap.eve);
    }

    @Override
    public int getItemCount() {
        return _charactersList.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView currentlyTraining;
        ImageView avatar;
        public CharacterViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.name);
            currentlyTraining = (TextView)itemView.findViewById(R.id.currently_training);
            avatar = (ImageView)itemView.findViewById(R.id.avatar);
        }
    }
    public RVAdapter(List<Character> characterList) {
        this._charactersList = characterList;
    }
}

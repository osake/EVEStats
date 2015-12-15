package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.List;

import tk.lachev.evestats.R;

/**
 * Created by Boris Lachev on 12/15/2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CharacterViewHolder> {

    private final Context _context;
    private final List<Character> _charactersList;

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CharacterViewHolder cvh = new CharacterViewHolder(v);
        return cvh;
    }

    public Bitmap getAvatar(Context context, String holder) throws FileNotFoundException {
        PortraitDownload portraitDownload =  new PortraitDownload();
        Bitmap b = portraitDownload.viewimage(context, holder);
        return b;
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        PortraitDownload portraitHandler = new PortraitDownload();
        holder.name.setText(_charactersList.get(position).get_name());
        try {
            holder.avatar.setImageBitmap(getAvatar(_context, _charactersList.get(position).get_name()));
            new GetTraining(position);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
    public RVAdapter(List<Character> characterList, Context context) {
        this._charactersList = characterList;
        this._context = context;
    }
    private class GetTraining extends AsyncTask<Void, Void, Void> {
        int position;
        String apiKey;
        String keyId;

        GetTraining(int position) {
            this.position = position;
            this.apiKey = _charactersList.get(position).get_apiKey();
            this.keyId = _charactersList.get(position).get_keyId();
        }

        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }
    }
}

package com.sampleapp.module.musiclist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sampleapp.R;
import com.sampleapp.base.BaseApplication;
import com.sampleapp.model.response.ituneresponse.Entry;
import com.sampleapp.utils.ImageUtility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kuljeetsingh on 8/8/17.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<Entry> musicListResponseArrayList = new ArrayList<>();
    @Inject
    ImageUtility imageUtility;



    /**
     * Constructor
     *
     * @param context
     * @param mDataArrayList
     */
    public MusicAdapter(Context context, ArrayList<Entry> mDataArrayList) {
        this.context = context;
        this.musicListResponseArrayList = mDataArrayList;

        ((BaseApplication) context.getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item, parent, false);
        return new ItemViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        int adapterPosition = holder.getAdapterPosition();
        Entry entry = musicListResponseArrayList.get(adapterPosition);
        holder.trackTitle.setText(entry.getTitle().getLabel());
        holder.trackArtist.setText(entry.getImArtist().getLabel());
        imageUtility.LoadImage(entry.getImImage().get(entry.getImImage().size() - 1).getLabel(), holder.songImageView);
    }

    /**
     * add items in list
     *
     * @param items items to be added
     */
    public void updateData(List<Entry> items) {
        musicListResponseArrayList.clear();
        musicListResponseArrayList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return musicListResponseArrayList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.song_image_view)
        ImageView songImageView;
        @BindView(R.id.track_title)
        TextView trackTitle;
        @BindView(R.id.track_artist)
        TextView trackArtist;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
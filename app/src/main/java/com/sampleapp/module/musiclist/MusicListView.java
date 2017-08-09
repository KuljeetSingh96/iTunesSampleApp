package com.sampleapp.module.musiclist;

import com.sampleapp.model.response.ituneresponse.Entry;
import com.sampleapp.mvpbase.MvpView;

import java.util.List;

/**
 * Created by kuljeetsingh on 8/7/17.
 */

public interface MusicListView extends MvpView{

    void onMusicSuccess(List<Entry> entry);

    void onTitleFetched(String label);
}

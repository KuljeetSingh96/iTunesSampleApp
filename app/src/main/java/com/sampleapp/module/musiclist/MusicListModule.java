package com.sampleapp.module.musiclist;

/**
 * Created by kuljeetsingh on 8/7/17.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * provides dependencies for {@link MusicListActivity}
 */
@Module
public class MusicListModule {
    private Context mContext;

    MusicListModule(Context mContext) {
        this.mContext = mContext;
    }

    //returns MusicListPresenter object
    @Provides
    MusicListPresenter getPresenter() {
        return new MusicListPresenter(mContext);
    }

    //returns MusicAdapter
    @Provides
    MusicAdapter getMusicAdapter() {
        return new MusicAdapter(mContext,new ArrayList<>());
    }

    //returns MusicAdapter
    @Provides
    LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mContext);
    }


}

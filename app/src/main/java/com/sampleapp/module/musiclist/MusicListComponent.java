package com.sampleapp.module.musiclist;

/**
 * Created by kuljeetsingh on 8/7/17.
 */

import com.sampleapp.utils.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component class for {@link MusicListActivity}
 * indicates the modules that will be used by the component and the activities that will use this component
 */
@Singleton
@Component(modules = {MusicListModule.class, UtilsModule.class})
public interface MusicListComponent {

    void inject(MusicListActivity musicListActivity);
}

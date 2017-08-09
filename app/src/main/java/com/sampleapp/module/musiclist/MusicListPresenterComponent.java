package com.sampleapp.module.musiclist;

/**
 * Created by kuljeetsingh on 8/7/17.
 */

import com.sampleapp.api.NetModule;

import com.sampleapp.utils.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component class for {@link MusicListPresenter}
 * indicates the modules that will be used by the component and the class that will use this component
 */
@Singleton
@Component(modules = {NetModule.class, UtilsModule.class})
public interface MusicListPresenterComponent {
    void inject(MusicListPresenter musicListPresenter);
}

package com.sampleapp.module.musiclist;

/**
 * Created by kuljeetsingh on 8/7/17.
 */

import android.content.Context;

import com.sampleapp.R;
import com.sampleapp.api.NetModule;
import com.sampleapp.api.RestService;
import com.sampleapp.base.RetryInterface;
import com.sampleapp.constants.ValueConstants;
import com.sampleapp.model.response.ituneresponse.Entry;
import com.sampleapp.model.response.ituneresponse.Feed;
import com.sampleapp.mvpbase.BasePresenter;
import com.sampleapp.utils.UtilsModule;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Scheduler;

/**
 * Presenter class for {@link MusicListActivity}
 * Contains all the business logic for main activity including API calls
 */
public class MusicListPresenter extends BasePresenter<MusicListView> implements RetryInterface {

    //Injecting dependencies required  by our presenter
    @Inject
    @Named(ValueConstants.MAIN_THREAD)
    Scheduler mMainThread;

    @Inject
    @Named(ValueConstants.NEW_THREAD)
    Scheduler mNewThread;

    @Inject
    RestService mRestService;

    private Context context;

    MusicListPresenter(Context context) {
        this.context = context;
        //creating component for injections
        /**
         * {@link MusicListPresenterComponent}
         */
        DaggerMusicListPresenterComponent.builder()
                .netModule(new NetModule())
                .utilsModule(new UtilsModule(context)).build().inject(this);
    }

    /**
     * fetch itunes top  album API method using retrofit and Rx java
     * after successful  data to save over ORM for easy offline access in app
     */
    void fetchMusicList() {

        getMvpView().showProgress();

        mRestService.fetchMusicList().
                subscribeOn(mNewThread).
                observeOn(mMainThread).
                subscribe(loginResponse -> {
                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        if (loginResponse.getFeed().getEntry().size() > 0) {
                            //delete realm data if exist
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            RealmResults<Entry> feed = realm.where(Entry.class)
                                    .findAll();
                            feed.deleteAllFromRealm();
                            realm.commitTransaction();
                            //Realm copy new data to ORM
                            Realm.getDefaultInstance().beginTransaction();
                            List<Entry> feedResponse = Realm.getDefaultInstance().copyToRealmOrUpdate(loginResponse.getFeed().getEntry());
                            Realm.getDefaultInstance().commitTransaction();
                            //update list
                            getMvpView().onMusicSuccess(loginResponse.getFeed().getEntry());
                            //update title
                            getMvpView().onTitleFetched(loginResponse.getFeed().getTitle().getLabel());
                        } else {
                            getMvpView().onException(context.getString(R.string.no_music_error));
                        }
                    }
                }, throwable -> {
                    if (isViewAttached()) {
                        getMvpView().hideProgress();
                        getMvpView().showServerError(this, throwable.getMessage());
                        fetchMusicOffline();
                    }
                });
    }

    /**
     * retry method
     */
    @Override
    public void onRetry() {
        fetchMusicList();
    }

    /**
     * method to fetch data from ORM
     */
    public void fetchMusicOffline() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Entry> feed = realm.where(Entry.class)
                .findAll();
        if (feed.size() > 0) {
            getMvpView().onMusicSuccess(feed);
            getMvpView().onTitleFetched("iTunes Store (Offline)");

        } else {
            getMvpView().onException(context.getString(R.string.no_music_error));
        }
        realm.commitTransaction();


    }
}

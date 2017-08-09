package com.sampleapp.module.musiclist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.sampleapp.R;
import com.sampleapp.base.BaseActivity;
import com.sampleapp.base.BaseApplication;
import com.sampleapp.model.response.ituneresponse.Entry;
import com.sampleapp.receivers.ConnectivityReceiver;
import com.sampleapp.utils.AppUtils;
import com.sampleapp.utils.UtilsModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MusicListActivity extends BaseActivity implements MusicListView, ConnectivityReceiver.ConnectivityReceiverListener {
    //bind views
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_button)
    FloatingActionButton refreshButton;

    //dependency injections
    @Inject
    AppUtils appUtils;
    @Inject
    MusicListPresenter musicListPresenter;
    @Inject
    MusicAdapter musicAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**creating component {@link LoginComponent}for dependency injection*/

        DaggerMusicListComponent.builder()
                .utilsModule(new UtilsModule(this))
                .musicListModule(new MusicListModule(this))
                .build().inject(this);

        /**attaching view{@link LoginView} to our presenter*/
        musicListPresenter.attachView(this);
        /**
         * set up recycler view {@link MusicAdapter}
         */
        setUpRecyclerView();
    }

    /**
     * recycler view set up
     */
    private void setUpRecyclerView() {
        recyclerView.setAdapter(musicAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        BaseApplication.getContext().setConnectivityListener(this);

        //musicListPresenter fetch music play list

        fetchMusicFromDataSource();
    }

    private void fetchMusicFromDataSource() {
        if (appUtils.isOnline(recyclerView))
            musicListPresenter.fetchMusicList();
        else {
            musicListPresenter.fetchMusicOffline();
        }
    }

    /**
     * set layout for this activity
     */
    @Override
    public int getLayout() {
        return R.layout.activity_music_list;
    }

    /**
     * set tool bar title
     *
     * @return
     */
    @Override
    protected String getToolbarTitle() {
        return getString(R.string.music_list);
    }

    /**
     * return intent for this activity
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MusicListActivity.class);
    }

    /**
     * if change in internet broad cast received
     *
     * @param isConnected
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        String message = (isConnected) ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
        if (!TextUtils.isEmpty(message)) {
            appUtils.showSnackBar(refreshButton, message);
        }

        refreshButton.setEnabled(isConnected);
    }

    /**
     * on fetch music success
     *
     * @param entry
     */
    @Override
    public void onMusicSuccess(List<Entry> entry) {
        musicAdapter.updateData(entry);
    }

    /**
     * on title fetched
     *
     * @param label
     */
    @Override
    public void onTitleFetched(String label) {
        setToolbarTitle(label);

    }


    @OnClick(R.id.refresh_button)
    public void onRefreshClick() {
        fetchMusicFromDataSource();
    }
}

package com.sampleapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.sampleapp.R;
import com.sampleapp.mvpbase.MvpView;
import com.sampleapp.utils.AppUtils;
import com.sampleapp.utils.ProgressBarHandler;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * To be extended by all Activities
 * binds butterknife injection and layout for Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements MvpView {
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Inject
    ProgressBarHandler mProgressHandler;
    @Inject
    AppUtils mAppUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        //bind view here for all activities extending this Activity
        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getToolbarTitle());
            mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    /**
     * set toolbar title
     * @param title
     */
    public void setToolbarTitle(String title) {
        if (mToolbar != null)
            mToolbar.setTitle(title);

    }

    /**
     * get layout to inflate
     */
    public abstract
    @LayoutRes
    int getLayout();

    /**
     * @return return toolbar title
     */
    protected abstract String getToolbarTitle();

    //calligraphy configuration
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * show ProgressBar for long running operations and
     * disable views so that user cannot perform any task
     */
    @Override
    public void showProgress() {
        mProgressHandler.showProgress();
        ButterKnife.apply(getWindow().getDecorView().getRootView(), mAppUtils.ENABLED, false);
    }

    /**
     * hide Progressbar and enable views for user interaction
     */
    @Override
    public void hideProgress() {
        mProgressHandler.hideProgress();
        ButterKnife.apply(getWindow().getDecorView().getRootView(), mAppUtils.ENABLED, true);
    }

    /**
     * handle any error during any operation and display proper message to user
     */
    @Override
    public void onError(Throwable t) {
        mAppUtils.showErrorMessage(getWindow().getDecorView().getRootView(), t);
    }

    /**
     * handle any logical error here and display message to user (Maybe in case of invalid credentials)
     *
     * @param message warning message to be displayed to user
     */
    @Override
    public void onException(String message) {
        mAppUtils.showSnackBar(getWindow().getDecorView().getRootView(), message);
    }


    /**
     * show server error dialog
     *
     * @param retry retry response callback
     */
    @Override
    public void showServerError(RetryInterface retry, String Message) {
        if (TextUtils.isEmpty(Message)) {
            Message = "Server error, please try again later";
        }

        showRetryDialog("Error", Message, retry);
    }

    /**
     * show retry dialog
     *
     * @param title   title to show
     * @param message message of error
     * @param retry   callback
     */
    private void showRetryDialog(String title, String message, final RetryInterface retry) {
        hideProgress();

        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Retry", (dialogInterface, i) -> retry.onRetry())
                .setNegativeButton("Dismiss", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

        showBuild(builder);
    }

    /**
     * create alert dialog from builder
     */
    public void showBuild(@NonNull AlertDialog.Builder builder) {
        // create alert dialog
        AlertDialog alertDialog = builder.create();
//        mAlertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        // show it
        alertDialog.show();
    }
}
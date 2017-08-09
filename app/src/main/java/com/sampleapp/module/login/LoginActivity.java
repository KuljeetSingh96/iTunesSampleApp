package com.sampleapp.module.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sampleapp.R;
import com.sampleapp.base.BaseActivity;
import com.sampleapp.module.login.DaggerLoginComponent;
import com.sampleapp.utils.AppUtils;
import com.sampleapp.utils.ProgressBarHandler;
import com.sampleapp.utils.UtilsModule;
import com.sampleapp.views.RemoveErrorEditText;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.MinLength;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.annotations.RegExp;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

/**
 * contains login related view and data presentation
 */

public class LoginActivity extends BaseActivity implements LoginView {

    /**
     * Injecting dependencies for this Activity using Dagger2
     */
    @Inject
    ProgressBarHandler mProgressHandler;
    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    AppUtils mAppUtils;

    /**
     * View binding using Butterknife and applying validations using Komensky validations library
     */
    @NotEmpty(messageId = R.string.warning_field_empty, order = 1)
    @RegExp(value = RegExp.EMAIL, messageId = R.string.warning_email, order = 2)
    @BindView(R.id.edtUserName)
    RemoveErrorEditText mEdtUserName;

    @MinLength(value = 6, messageId = R.string.warning_password_length, order = 2)
    @BindView(R.id.edtPassword)
    RemoveErrorEditText mEdtPassword;

    @BindViews({R.id.edtUserName, R.id.edtPassword, R.id.txtLogin})
    List<View> nameViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**creating component {@link LoginComponent}for dependency injection*/

        DaggerLoginComponent.builder()
                .utilsModule(new UtilsModule(this))
                .loginModule(new LoginModule(this))
                .build().inject(this);

        /**attaching view{@link LoginView} to our presenter*/
        mLoginPresenter.attachView(this);
    }

    /**
     * set layout for this activity
     */
    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected String getToolbarTitle() {
        return "Login";
    }

    /**
     * return intent for this activity
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    /**
     * handle successful login event here
     * (maybe move to next Activity depending on your app requirements)
     */
    @Override
    public void onLoginSuccess() {

    }


    /**
     * click event on views using Butterknife library
     */
    @OnClick(R.id.txtLogin)
    public void onClick(View view) {
        //check if all validations are passed
        boolean isValid = FormValidator.validate(LoginActivity.this, new SimpleErrorPopupCallback(LoginActivity.this));
        if (isValid) {
            //call to login user function in presenter class
            mLoginPresenter.loginUser(mEdtUserName.getText().toString(), mEdtPassword.getText().toString());
        }
    }

    /**
     * detach view from presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();

    }






}

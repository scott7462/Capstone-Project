package scott.com.workhard.app.ui.init.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import scott.com.workhard.R;
import scott.com.workhard.app.base.view.BaseActivity;
import scott.com.workhard.app.base.view.BaseFragment;
import scott.com.workhard.app.ui.init.InitActivity;
import scott.com.workhard.app.ui.init.login.presenter.LoginPresenter;
import scott.com.workhard.app.ui.init.login.presenter.LoginPresenterListeners;
import timber.log.Timber;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/12/16.
 *          <p>
 *          Copyright (C) 2015 The Android Open Source Project
 *          <p/>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p/>
 * @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class LoginFragment extends BaseFragment implements LoginPresenterListeners, Validator.ValidationListener {

    @Email
    @BindView(R.id.eTFrgLoginEmail)
    AppCompatEditText eTFrgLoginEmail;

    @Password
    @NotEmpty
    @BindView(R.id.eTFrgLoginPassword)
    AppCompatEditText eTFrgLoginPassword;

    @BindView(R.id.lBFrgLoginFacebook)
    LoginButton lBFrgLoginFacebook;

    @BindView(R.id.lBFrgLoginTwitter)
    TwitterLoginButton lBFrgLoginTwitter;

    @BindView(R.id.lBFrgLoginGooglePlus)
    SignInButton lBFrgLoginGooglePlus;
//
//    @BindView(R.id.fBFrgSingInBUU)
//    FloatingActionButton fBFrgSingInBUU;


    private LoginPresenter presenter;
    private Validator validator;
    private ProgressDialog progress;

    public static Fragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_login, container, false);
        ButterKnife.bind(this, view);
        intViews();
        iniListeners();
        return view;
    }

    private void iniListeners() {
        eTFrgLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.FLAG_EDITOR_ACTION) {
                    cleanValidations();
                    validator.validate();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void intViews() {
        facebookManger();
        twitterManager();
    }

    public TwitterLoginButton getlBFrgLoginTwitter() {
        return lBFrgLoginTwitter;
    }

    private void twitterManager() {
        lBFrgLoginTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Timber.e(msg);
            }

            @Override
            public void failure(TwitterException exception) {
                Timber.e("TwitterKit Login with Twitter failure " + exception);
            }
        });

    }

    @OnClick(R.id.lBFrgLoginGooglePlus)
    public void onClickToSingInGoogle() {
        if (getActivity() instanceof InitActivity) {
            ((InitActivity) getActivity()).startLoginGooglePlus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    private void facebookManger() {
        lBFrgLoginFacebook.setReadPermissions("email");
        lBFrgLoginFacebook.setFragment(this);
        lBFrgLoginFacebook.registerCallback(((InitActivity) getActivity()).getCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Timber.e(loginResult.toString());
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        Timber.e("Error");
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Timber.e(exception.toString());
                        // App code
                    }
                });
    }


    @Override
    public void showMessage(int stringId) {

    }

    @Override
    public void showProgressIndicator() {
        progress = ProgressDialog.show(getActivity(), "Login",
                "login message ...", true);
    }

    @Override
    public void removeProgressIndicator() {
        progress.dismiss();
    }

    @Override
    public void navigateToMain() {
        ((InitActivity) getActivity()).navigateToMain();
    }

    @OnClick({R.id.bTFrgLoginButton, R.id.bTFrgLoginGoogleButton, R.id.bTFrgLoginFacebookButton, R.id.bTFrgLoginTwitterButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bTFrgLoginButton: {
                cleanValidations();
                validator.validate();
                break;
            }
            case R.id.bTFrgLoginGoogleButton: {
                lBFrgLoginGooglePlus.callOnClick();
                break;
            }
            case R.id.bTFrgLoginFacebookButton: {
                lBFrgLoginFacebook.callOnClick();
                break;
            }
            case R.id.bTFrgLoginTwitterButton: {
                lBFrgLoginTwitter.callOnClick();
                break;
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        presenter.doLogin(eTFrgLoginEmail.getText().toString(),
                eTFrgLoginPassword.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            if (view instanceof AppCompatEditText) {
                ((TextInputLayout) view.getParent()).setError(message);
            }
        }
    }

    private void cleanValidations() {
        ((TextInputLayout) eTFrgLoginEmail.getParent()).setError(null);
        ((TextInputLayout) eTFrgLoginPassword.getParent()).setError(null);
    }

    @OnFocusChange(R.id.eTFrgLoginEmail)
    public void checkWithFocus(boolean focus) {
        if (!focus) {
            cleanValidations();
            validator.validate();
        }
    }

}
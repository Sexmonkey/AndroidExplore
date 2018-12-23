package com.jerryzhu.androidexplore.ui.main.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.jakewharton.rxbinding2.view.RxView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.activity.BaseActivity;
import com.jerryzhu.androidexplore.bridge.main.LoginBridge;
import com.jerryzhu.androidexplore.presenter.main.LoginPresenter;
import com.jerryzhu.androidexplore.utils.CommonUtils;
import com.jerryzhu.androidexplore.utils.StatusBarUtil;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginBridge.View {


    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.login_user_name)
    EditText mLoginUserName;
    @BindView(R.id.login_user_password)
    EditText mLoginUserPassword;

    @Override
    protected void initDataAndEvent() {
        subscribeLoginClickEvent();

    }

    private void subscribeLoginClickEvent() {
        mPresenter.addRxBindingSubscribe(RxView.clicks(mBtnLogin)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        return mPresenter != null;
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.getLoginData(mLoginUserName.getText().toString().trim(),
                                mLoginUserPassword.getText().toString().trim());
                    }
                }));

    }

    @Override
    protected void initToolBar() {

        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void showLoginSuccess() {
        CommonUtils.showMessage(this,getString(R.string.login_success));
        onBackPressedSupport();

    }


    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                registerEvent();
                break;
        }
    }


    private void registerEvent() {

        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(mBtnRegister, mBtnRegister.getWidth() / 2, mBtnRegister.getHeight() / 2, 0, 0);
        startActivity(new Intent(this,RegisterActivity.class),options.toBundle());

    }

}

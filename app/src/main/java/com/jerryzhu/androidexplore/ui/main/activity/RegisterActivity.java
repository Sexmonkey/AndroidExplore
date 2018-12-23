package com.jerryzhu.androidexplore.ui.main.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.app.Constants;
import com.jerryzhu.androidexplore.base.activity.BaseActivity;
import com.jerryzhu.androidexplore.bridge.main.RegisterBridge;
import com.jerryzhu.androidexplore.presenter.main.RegisterPresenter;
import com.jerryzhu.androidexplore.utils.StatusBarUtil;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterBridge.View {

    @BindView(R.id.common_toolbar_title_tv)
    TextView commonToolbarTitleTv;
    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.edit_register_password)
    EditText editRegisterPassword;
    @BindView(R.id.edit_register_account)
    EditText editRegisterAccount;
    @BindView(R.id.edit_register_passord_confirm)
    EditText editRegisterPassordConfirm;
    @BindView(R.id.btn_register_signup)
    Button btnRegisterSignup;

    @Override
    protected void initDataAndEvent() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        System.out.println("inputMethodManager"+inputMethodManager.toString());
        if (inputMethodManager != null){
           editRegisterAccount.requestFocus();
           inputMethodManager.showSoftInput(editRegisterAccount,0);
        }

        mPresenter.addRxBindingSubscribe(RxView.clicks(btnRegisterSignup)
                .throttleFirst(Constants.CLICK_TIME_AREA,TimeUnit.MILLISECONDS)
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        return mPresenter != null;
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.getRegisterData(
                                editRegisterAccount.getText().toString().trim(),
                                editRegisterPassword.getText().toString().trim(),
                                editRegisterPassordConfirm.getText().toString().trim());
                    }
                }));

    }

    @Override
    protected void initToolBar() {

        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this,commonToolbar);
        commonToolbarTitleTv.setText(R.string.register);
        commonToolbarTitleTv.setTextColor(ContextCompat.getColor(this,R.color.white));
        commonToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.register_bac));
        commonToolbarTitleTv.setTextSize(R.dimen.dp_20);
        commonToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void showRegisterSuccess() {

        showSnackBar(AndroidExploreApp.getInstance().getString(R.string.register_success));
        onBackPressedSupport();

    }


}

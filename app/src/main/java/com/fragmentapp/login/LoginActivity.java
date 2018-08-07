package com.fragmentapp.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.helper.SharedPreferencesUtils;
import com.fragmentapp.home.MainActivity;
import com.fragmentapp.login.bean.LoginDataBean;
import com.fragmentapp.login.imple.ILoginView;
import com.fragmentapp.login.presenter.LoginPresenter;
import com.fragmentapp.view.water.WaterBgView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/7.
 */

public class LoginActivity extends BaseActivity implements ILoginView{

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @BindView(R.id.root)
    View root;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.bg)
    WaterBgView waterBgView;

    @Override
    public int layoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {

    }

    @Override
    public void success(LoginDataBean dataBean) {
        if (isNull(dataBean)){
            Toast.makeText(context,"param is null",Toast.LENGTH_SHORT).show();
            return;
        }
        MainActivity.start(context);
        finish();
//        SharedPreferencesUtils.setParam("token",dataBean.getHCACCESSTOKEN());
    }

    @Override
    public void error() {
        Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_submit})
    public void click(View view){
        String name = et_name.getText().toString();
        String pwd = et_pwd.getText().toString();
        if (isNull(name,pwd)){
            Toast.makeText(context,"param is null",Toast.LENGTH_SHORT).show();
            return;
        }
        new LoginPresenter(this).login(name,pwd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waterBgView.cancel();
    }
}

package com.fragmentapp.login;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.helper.SharedPreferencesUtils;
import com.fragmentapp.login.bean.LoginDataBean;
import com.fragmentapp.login.imple.ILoginView;
import com.fragmentapp.login.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/7.
 */

public class LoginActivity extends BaseActivity implements ILoginView{

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_pwd)
    EditText et_pwd;

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
        SharedPreferencesUtils.setParam("token",dataBean.getHCACCESSTOKEN());
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

}

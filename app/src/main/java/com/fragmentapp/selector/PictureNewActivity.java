package com.fragmentapp.selector;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.fragmentapp.R;
import com.luck.picture.lib.PictureSelectorActivity;

/**
 * Created by liuzhen on 2018/8/20.
 */

public class PictureNewActivity extends PictureSelectorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout id_ll_ok = findViewById(R.id.id_ll_ok);

        View bottom = getLayoutInflater().inflate(R.layout.layout_photo_checkbox, null);
        id_ll_ok.addView(bottom, 0);
        CheckBox picture_cb_tag = id_ll_ok.findViewById(R.id.cb_photo);
        picture_cb_tag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                config.isCompress = !isChecked;
            }
        });

    }
}

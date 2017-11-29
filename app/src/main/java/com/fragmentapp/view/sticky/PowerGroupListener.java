package com.fragmentapp.view.sticky;

import android.view.View;

/**
 * Created by gavin
 * Created date 17/5/25
 * 显示自定义View的Group监听
 */

public interface PowerGroupListener extends GroupListener {

    View getGroupView(int position);
}

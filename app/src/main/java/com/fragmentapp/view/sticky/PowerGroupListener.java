package com.fragmentapp.view.sticky;

import android.view.View;

/**
 * Created by ${Bryan} on 2017/8/20.
 */

public interface PowerGroupListener {
    String getGroupName(int position);

    View getGroupView(int position);
}

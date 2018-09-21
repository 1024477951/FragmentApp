package com.fragmentapp.view.alert;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fragmentapp.R;

/**
 * Created by binIoter on 16/6/17.
 */
public class SimpleComponent implements Component {

  @Override
  public View getView(LayoutInflater inflater) {

    View ll = inflater.inflate(R.layout.layout_alert, null);
    ll.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show();
      }
    });
    return ll;
  }

  @Override
  public int getAnchor() {
    return Component.ANCHOR_BOTTOM;
  }

  @Override
  public int getFitPosition() {
    return Component.FIT_END;
  }

  @Override
  public int getXOffset() {
    return 0;
  }

  @Override
  public int getYOffset() {
    return 10;
  }
}

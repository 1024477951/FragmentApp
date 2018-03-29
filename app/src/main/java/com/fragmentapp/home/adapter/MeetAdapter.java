package com.fragmentapp.home.adapter;

import android.content.res.Resources;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.helper.TimeUtil;
import com.fragmentapp.home.bean.MeetDataBean;

import java.text.ParseException;

/**
 * Created by liuzhen on 2017/11/28.
 */

public class MeetAdapter extends BaseQuickAdapter<MeetDataBean, BaseViewHolder> {

    private Resources resources;

    public MeetAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeetDataBean item) {

        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_location = helper.getView(R.id.tv_location);
        TextView tv_status = helper.getView(R.id.tv_status);

        tv_title.setText(item.getTitle());
        tv_location.setText(item.getLocal());
        setStatus(item.getRegister_status(),tv_status);
        try {
            String startTime = TimeUtil.getMonthDayHourTime(item.getStart_time());
            String endTime = TimeUtil.getHourTime(item.getEnd_time());
            tv_time.setText(startTime +"-"+ endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setStatus(String status,TextView tv_name){
        if (tv_name == null)
            return;
        if (resources == null)
            resources = mContext.getResources();
        int color = resources.getColor(R.color.color_999999);
        int res = R.drawable.shape_radius_999999_6;
        String result = null;
        if (status == null)
            result = "无签到信息";
        else {
            switch (status) {
                case "0":
                    result = "未签到";
                    res = R.drawable.shape_radius_ffa126_6;
                    color = resources.getColor(R.color.color_FFA126);
                    break;
                case "1":
                    res = R.drawable.shape_radius_5fcc29_6;
                    color = resources.getColor(R.color.color_5fcc29);
                    result = "已签到";
                    break;
                case "2":
                    res = R.drawable.shape_radius_999999_6;
                    color = resources.getColor(R.color.color_999999);
                    result = "已签到";
                    break;

            }
        }
        tv_name.setText(result);
        tv_name.setTextColor(color);
        tv_name.setBackgroundResource(res);
    }

    public void clean(){
        mData.clear();
        notifyDataSetChanged();
    }

}

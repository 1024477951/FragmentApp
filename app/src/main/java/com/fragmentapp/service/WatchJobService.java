package com.fragmentapp.service;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.orhanobut.logger.Logger;

/**
 * Created by liuzhen on 2019/2/28.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WatchJobService extends JobService {

    private final static String TAG = "WatchJobService";
    private int JobWakeUpId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e(TAG, TAG+" onStartCommand");
        //开启轮寻
        JobInfo.Builder mJobBulider = new JobInfo.Builder(
                JobWakeUpId,new ComponentName(this,WatchJobService.class));
        //设置轮寻时间
        mJobBulider.setPeriodic(2000);
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.schedule(mJobBulider.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //开启定时任务 定时轮寻 判断应用Service是否被杀死
        //如果被杀死则重启Service
        boolean messageServiceAlive = ServiceAliveUtils.isServiceAlice(WatchOneService.class.getName());
        if(!messageServiceAlive){
            startService(new Intent(this,WatchOneService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}

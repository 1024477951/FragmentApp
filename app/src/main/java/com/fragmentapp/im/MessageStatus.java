package com.fragmentapp.im;

/**
 * Created by liuzhen on 2018/8/10.
 */

public class MessageStatus {
    /**
     * 发送状态（ 0:消息占位（在发送图片、文件等耗时任务是）1:发送中 2:发送成功 3:已收到 4:已读 5:撤销 10:发送失败 11:已删除 ）
     */
    public static final int SPACE = 0;
    public static final int CREATED = 1;
    public static final int SEND_SUCCEED = 2;
    public static final int RECEIVE_SUCCEED = 3;
    public static final int READ = 4;
    public static final int CANCEL = 5;

    public static final int SEND_ERROR = 10;
    public static final int DEL = 11;
}

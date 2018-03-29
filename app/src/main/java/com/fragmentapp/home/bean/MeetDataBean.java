package com.fragmentapp.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuzhen on 2017/11/30.
 */

public class MeetDataBean implements Parcelable {

    private String id;
    private String title;
    private String content;
    private String start_time;
    private String end_time;
    private String local;
    private String activity_type;
    private String status;
    private String full_name;
    private String register_status;
    private String update_time;

    protected MeetDataBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        content = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        local = in.readString();
        activity_type = in.readString();
        status = in.readString();
        full_name = in.readString();
        register_status = in.readString();
        update_time = in.readString();
    }

    public static final Creator<MeetDataBean> CREATOR = new Creator<MeetDataBean>() {
        @Override
        public MeetDataBean createFromParcel(Parcel in) {
            return new MeetDataBean(in);
        }

        @Override
        public MeetDataBean[] newArray(int size) {
            return new MeetDataBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getRegister_status() {
        return register_status;
    }

    public void setRegister_status(String register_status) {
        this.register_status = register_status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(local);
        dest.writeString(activity_type);
        dest.writeString(status);
        dest.writeString(full_name);
        dest.writeString(register_status);
        dest.writeString(update_time);
    }
}

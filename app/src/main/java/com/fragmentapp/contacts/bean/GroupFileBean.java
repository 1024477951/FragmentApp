package com.fragmentapp.contacts.bean;

import java.io.Serializable;

/**
 * Created by liuzhen on 2018/3/15.
 */

public class GroupFileBean implements Serializable{

    private int fileSize;
    private String fileType;
    private String filename;
    private String src;

    public String getFileSizeM(){ // 该参数表示kb的值
        return formatFileSize(fileSize); //返回kb转换之后的M值
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }


    public String formatFileSize(long size) {
        return size < 1024L?String.format("%d B", new Object[]{Integer.valueOf((int)size)}):(size < 1048576L?String.format("%.2f KB", new Object[]{Float.valueOf((int)size / 1024.0F)}):(size < 1073741824L?String.format("%.2f MB", new Object[]{Float.valueOf((int)size / 1048576.0F)}):String.format("%.2f G", new Object[]{Float.valueOf((int)size / 1.07374182E9F)})));
    }
}

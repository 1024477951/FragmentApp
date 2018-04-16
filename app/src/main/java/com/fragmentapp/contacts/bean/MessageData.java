package com.fragmentapp.contacts.bean;

import com.fragmentapp.helper.TimeUtil;

import java.util.List;

/**
 * Created by liuzhen on 2018/3/14.
 */

public class MessageData {

    /**
     * month : 2018年03月
     * list : [{"id":"94228","from_id":"1447","from_name":"李路平","content":"{\"fileSize\":568762,\"fileType\":\".zip\",\"filename\":\"c83c4f20ae75e1739f497f0106834a81.zip\",\"src\":\"https://upload.hanmaker.com/im/files/d42c8b4019881b387b.zip\"}","timeline":"1520934866","relationship":"1000050_1000050_G","team_name":"内部系统部"},{"id":"94154","from_id":"131","from_name":"覃亚平","content":"{\"fileSize\":3526,\"fileType\":\".enex\",\"filename\":\"内部系统工作台首页增加小模块计划v1.enex\",\"src\":\"https://upload.hanmaker.com/im/files/7a3d4f47c19652c33e.enex\"}","timeline":"1520932630","relationship":"1000050_1000050_G","team_name":"内部系统部"},{"id":"90051","from_id":"131","from_name":"覃亚平","content":"{\"fileSize\":214581,\"fileType\":\".enex\",\"filename\":\"hanmission功能需求小调研反馈报告v1.enex\",\"src\":\"https://upload.hanmaker.com/im/files/58011048b886c2aaef.enex\"}","timeline":"1520585983","relationship":"1000050_1000050_G","team_name":"内部系统部"},{"id":"79288","from_id":"1376","from_name":"钟巧思","content":"{\"fileSize\":43270991,\"fileType\":\".exe\",\"name\":\"HanTalkInstall_3.0.22.1.exe\",\"filename\":\"HanTalkInstall_3.0.22.1.exe\",\"src\":\"https://upload.hanmaker.com/im/files/b69961484b9a3af602.exe\"}","timeline":"1519900391","relationship":"1000050_1000050_G","team_name":"内部系统部"},{"id":"79189","from_id":"1376","from_name":"钟巧思","content":"{\"fileSize\":47879404,\"fileType\":\".7z\",\"name\":\"Source.7z\",\"filename\":\"Source.7z\",\"src\":\"https://upload.hanmaker.com/im/files/f6f46542ce9cfc49a9.7z\"}","timeline":"1519899278","relationship":"1000050_1000050_G","team_name":"内部系统部"}]
     */

    private String month;
    private List<ListBean> list;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean {
        /**
         * id : 94228
         * from_id : 1447
         * from_name : 李路平
         * content : {"fileSize":568762,"fileType":".zip","filename":"c83c4f20ae75e1739f497f0106834a81.zip","src":"https://upload.hanmaker.com/im/files/d42c8b4019881b387b.zip"}
         * timeline : 1520934866
         * relationship : 1000050_1000050_G
         * team_name : 内部系统部
         */

        private String id;
        private String from_id;
        private String from_name;
        private String content;
        private String timeline;
        private String relationship;
        private String team_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFrom_id() {
            return from_id;
        }

        public void setFrom_id(String from_id) {
            this.from_id = from_id;
        }

        public String getFrom_name() {
            return from_name;
        }

        public void setFrom_name(String from_name) {
            this.from_name = from_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTimeline() {
            String result = TimeUtil.getYearMonthDay(timeline);
            return result;
        }

        public String getGroupName() {
            String result = TimeUtil.getYearMonthTime(timeline);
            return result;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }
    }
}

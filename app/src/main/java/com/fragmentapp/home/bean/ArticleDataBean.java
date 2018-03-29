package com.fragmentapp.home.bean;

import java.util.List;

/**
 * Created by liuzhen on 2017/12/15.
 */

public class ArticleDataBean {


    private String count;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean {
        /**
         * id : 1000275
         * article_name : 宠妻狂魔扎克伯格，教起女儿来也是超暖心的硅谷范儿
         * article_addtime : 2017-10-25 16:51:01
         * article_pic : https://pic2.hanmaker.com/article/small/20171025/59f0507a09aab.jpg
         * article_view : 0
         * article_urlcom : 品玩
         * article_attribute : 1
         * article_info : 说起扎克伯格，人们最开始会想到的可能是他的富豪身份和Facebook，或者是他标志性的灰色T恤。事实上，在这些金钱和名气背后的小扎，还是一个超级温暖的宠妻狂魔和暖心爸比。而且，为了让宝贝女儿们能够生活...
         * article_user_id : 0
         * article_uri : https://hc.hanmaker.com/article/1000275
         */

        private String id;
        private String article_name;
        private String article_addtime;
        private String article_pic;
        private String article_view;
        private String article_urlcom;
        private String article_attribute;
        private String article_info;
        private String article_user_id;
        private String article_uri;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArticle_name() {
            return article_name;
        }

        public void setArticle_name(String article_name) {
            this.article_name = article_name;
        }

        public String getArticle_addtime() {
            return article_addtime;
        }

        public void setArticle_addtime(String article_addtime) {
            this.article_addtime = article_addtime;
        }

        public String getArticle_pic() {
            return article_pic;
        }

        public void setArticle_pic(String article_pic) {
            this.article_pic = article_pic;
        }

        public String getArticle_view() {
            return article_view;
        }

        public void setArticle_view(String article_view) {
            this.article_view = article_view;
        }

        public String getArticle_urlcom() {
            return article_urlcom;
        }

        public void setArticle_urlcom(String article_urlcom) {
            this.article_urlcom = article_urlcom;
        }

        public String getArticle_attribute() {
            return article_attribute;
        }

        public void setArticle_attribute(String article_attribute) {
            this.article_attribute = article_attribute;
        }

        public String getArticle_info() {
            return article_info;
        }

        public void setArticle_info(String article_info) {
            this.article_info = article_info;
        }

        public String getArticle_user_id() {
            return article_user_id;
        }

        public void setArticle_user_id(String article_user_id) {
            this.article_user_id = article_user_id;
        }

        public String getArticle_uri() {
            return article_uri;
        }

        public void setArticle_uri(String article_uri) {
            this.article_uri = article_uri;
        }
    }

}
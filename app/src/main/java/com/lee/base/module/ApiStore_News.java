package com.lee.base.module;

import java.util.List;

/**
 * Created by liqg
 * 2016/11/7 16:06
 * Note :
 */
public class ApiStore_News {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-04-13 12:02","title":"《连线》杂志：看扎克伯格将如何统治世界？","description":"腾讯科技","picUrl":"http://mat1.gtimg.com/tech/00Jamesdu/2014/index/remark/2.png","url":"http://tech.qq.com/a/20160413/034253.htm"}]
     */

    private int code;
    private String msg;
    /**
     * ctime : 2016-04-13 12:02
     * title : 《连线》杂志：看扎克伯格将如何统治世界？
     * description : 腾讯科技
     * picUrl : http://mat1.gtimg.com/tech/00Jamesdu/2014/index/remark/2.png
     * url : http://tech.qq.com/a/20160413/034253.htm
     */

    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

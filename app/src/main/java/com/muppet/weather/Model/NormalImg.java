package com.muppet.weather.Model;

import com.muppet.weather.Utils.JsonRespondParseRemind;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

@HttpResponse(parser = JsonRespondParseRemind.class)
public class NormalImg implements Serializable {

    /**
     * msg : success
     * res : {"ads":[],"vertical":[{"preview":"http://img5.adesk.com/5bc595cde7bce75db261e20f","thumb":"http://img5.adesk.com/5bc595cde7bce75db261e20f?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5bc595cde7bce75db261e20f?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4fb47a465ba1c65561000028"],"ncos":0,"rank":4351,"url":[],"tag":["寿司","美食","日系","碗","物语"],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5bc595cde7bce75db261e20f","xr":false,"cr":false,"favs":43,"atime":1.541918419E9,"id":"5bc595cde7bce75db261e20f","store":"qiniu","desc":""}]}
     * code : 0
     */

    private String msg;
    private ResBean res;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResBean implements Serializable{
        private List<?> ads;
        private List<VerticalBean> vertical;

        public List<?> getAds() {
            return ads;
        }

        public void setAds(List<?> ads) {
            this.ads = ads;
        }

        public List<VerticalBean> getVertical() {
            return vertical;
        }

        public void setVertical(List<VerticalBean> vertical) {
            this.vertical = vertical;
        }

        public static class VerticalBean implements Serializable{
            /**
             * preview : http://img5.adesk.com/5bc595cde7bce75db261e20f
             * thumb : http://img5.adesk.com/5bc595cde7bce75db261e20f?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540
             * img : http://img5.adesk.com/5bc595cde7bce75db261e20f?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280
             * views : 0
             * cid : ["4fb47a465ba1c65561000028"]
             * ncos : 0
             * rank : 4351
             * url : []
             * tag : ["寿司","美食","日系","碗","物语"]
             * rule : ?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>
             * wp : http://img5.adesk.com/5bc595cde7bce75db261e20f
             * xr : false
             * cr : false
             * favs : 43
             * atime : 1.541918419E9
             * id : 5bc595cde7bce75db261e20f
             * store : qiniu
             * desc :
             */

            private String preview;
            private String thumb;
            private String img;
            private int views;
            private int ncos;
            private int rank;
            private String rule;
            private String wp;
            private boolean xr;
            private boolean cr;
            private int favs;
            private double atime;
            private String id;
            private String store;
            private String desc;
            private List<String> cid;
            private List<?> url;
            private List<String> tag;

            public String getPreview() {
                return preview;
            }

            public void setPreview(String preview) {
                this.preview = preview;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public int getNcos() {
                return ncos;
            }

            public void setNcos(int ncos) {
                this.ncos = ncos;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getRule() {
                return rule;
            }

            public void setRule(String rule) {
                this.rule = rule;
            }

            public String getWp() {
                return wp;
            }

            public void setWp(String wp) {
                this.wp = wp;
            }

            public boolean isXr() {
                return xr;
            }

            public void setXr(boolean xr) {
                this.xr = xr;
            }

            public boolean isCr() {
                return cr;
            }

            public void setCr(boolean cr) {
                this.cr = cr;
            }

            public int getFavs() {
                return favs;
            }

            public void setFavs(int favs) {
                this.favs = favs;
            }

            public double getAtime() {
                return atime;
            }

            public void setAtime(double atime) {
                this.atime = atime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStore() {
                return store;
            }

            public void setStore(String store) {
                this.store = store;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public List<String> getCid() {
                return cid;
            }

            public void setCid(List<String> cid) {
                this.cid = cid;
            }

            public List<?> getUrl() {
                return url;
            }

            public void setUrl(List<?> url) {
                this.url = url;
            }

            public List<String> getTag() {
                return tag;
            }

            public void setTag(List<String> tag) {
                this.tag = tag;
            }
        }
    }
}

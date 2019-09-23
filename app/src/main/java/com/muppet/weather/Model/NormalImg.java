package com.muppet.weather.Model;

import com.muppet.weather.Utils.JsonRespondParseRemind;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

@HttpResponse(parser = JsonRespondParseRemind.class)
public class NormalImg implements Serializable {
    /**
     * msg : success
     * res : {"vertical":[{"preview":"http://img5.adesk.com/5d832f8ce7bce75dca87c850","thumb":"http://img5.adesk.com/5d832f8ce7bce75dca87c850?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d832f8ce7bce75dca87c850?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4e4d610cdf714d2966000002"],"ncos":1,"rank":27855,"url":[],"tag":[],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d832f8ce7bce75dca87c850","xr":false,"cr":false,"favs":379,"atime":1.5688797E9,"id":"5d832f8ce7bce75dca87c850","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/5d4d4ffbe7bce75defa5dced","thumb":"http://img5.adesk.com/5d4d4ffbe7bce75defa5dced?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d4d4ffbe7bce75defa5dced?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4ef0a3330569795757000000"],"ncos":1,"rank":31419,"url":[],"tag":[],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d4d4ffbe7bce75defa5dced","xr":false,"cr":false,"favs":611,"atime":1.568890214E9,"id":"5d4d4ffbe7bce75defa5dced","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/5d81e4a6e7bce75defa5df02","thumb":"http://img5.adesk.com/5d81e4a6e7bce75defa5df02?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d81e4a6e7bce75defa5df02?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4fb47a195ba1c60ca5000222"],"ncos":6,"rank":12839,"url":[],"tag":[],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d81e4a6e7bce75defa5df02","xr":false,"cr":false,"favs":216,"atime":1.568971214E9,"id":"5d81e4a6e7bce75defa5df02","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/5d59fcaee7bce75ea7b3108c","thumb":"http://img5.adesk.com/5d59fcaee7bce75ea7b3108c?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d59fcaee7bce75ea7b3108c?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4fb47a305ba1c60ca5000223"],"ncos":7,"rank":129200,"url":[],"tag":["黄昏","晚霞","迷人","风景","城市"],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d59fcaee7bce75ea7b3108c","xr":false,"cr":false,"favs":1668,"atime":1.56629522E9,"id":"5d59fcaee7bce75ea7b3108c","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/5d5109b1e7bce75db261f6db","thumb":"http://img5.adesk.com/5d5109b1e7bce75db261f6db?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d5109b1e7bce75db261f6db?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["5109e04e48d5b9364ae9ac45"],"ncos":16,"rank":86830,"url":[],"tag":["励志","重新开始","文字","手写"],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d5109b1e7bce75db261f6db","xr":false,"cr":false,"favs":1114,"atime":1.56704461E9,"id":"5d5109b1e7bce75db261f6db","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/5d662c1be7bce75db261f7e1","thumb":"http://img5.adesk.com/5d662c1be7bce75db261f7e1?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d662c1be7bce75db261f7e1?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["5109e04e48d5b9364ae9ac45"],"ncos":0,"rank":609,"url":[],"tag":[],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d662c1be7bce75db261f7e1","xr":false,"cr":false,"favs":9,"atime":1.569212411E9,"id":"5d662c1be7bce75db261f7e1","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/5d492483e7bce75e1ab2cef3","thumb":"http://img5.adesk.com/5d492483e7bce75e1ab2cef3?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d492483e7bce75e1ab2cef3?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4e4d610cdf714d2966000002"],"ncos":0,"rank":5076,"url":[],"tag":[],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d492483e7bce75e1ab2cef3","xr":false,"cr":false,"favs":76,"atime":1.569133804E9,"id":"5d492483e7bce75e1ab2cef3","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/59701f8ee7bce77a990ce3d3","thumb":"http://img5.adesk.com/59701f8ee7bce77a990ce3d3?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/59701f8ee7bce77a990ce3d3?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4e4d610cdf714d2966000002"],"ncos":42,"rank":1118124,"url":[],"tag":["流星","山","风景","天空","唯美"],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/59701f8ee7bce77a990ce3d3","xr":false,"cr":false,"favs":7870,"atime":1.5008826E9,"id":"59701f8ee7bce77a990ce3d3","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/5d497273e7bce75e91a08012","thumb":"http://img5.adesk.com/5d497273e7bce75e91a08012?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d497273e7bce75e91a08012?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4e4d610cdf714d2966000002"],"ncos":1,"rank":6805,"url":[],"tag":[],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d497273e7bce75e91a08012","xr":false,"cr":false,"favs":104,"atime":1.569067803E9,"id":"5d497273e7bce75e91a08012","store":"qiniu","desc":""},{"preview":"http://img5.adesk.com/5d637842e7bce75d8f8d001c","thumb":"http://img5.adesk.com/5d637842e7bce75d8f8d001c?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540","img":"http://img5.adesk.com/5d637842e7bce75d8f8d001c?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280","views":0,"cid":["4e4d610cdf714d2966000004"],"ncos":2,"rank":11400,"url":[],"tag":["派大星","表情","卡通"],"rule":"?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>","wp":"http://img5.adesk.com/5d637842e7bce75d8f8d001c","xr":false,"cr":false,"favs":130,"atime":1.5669744E9,"id":"5d637842e7bce75d8f8d001c","store":"qiniu","desc":""}]}
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

        private List<VerticalBean> vertical;

        public List<VerticalBean> getVertical() {
            return vertical;
        }

        public void setVertical(List<VerticalBean> vertical) {
            this.vertical = vertical;
        }

        public static class VerticalBean implements Serializable{
            /**
             * preview : http://img5.adesk.com/5d832f8ce7bce75dca87c850
             * thumb : http://img5.adesk.com/5d832f8ce7bce75dca87c850?imageMogr2/thumbnail/!350x540r/gravity/Center/crop/350x540
             * img : http://img5.adesk.com/5d832f8ce7bce75dca87c850?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280
             * views : 0
             * cid : ["4e4d610cdf714d2966000002"]
             * ncos : 1
             * rank : 27855
             * url : []
             * tag : []
             * rule : ?imageMogr2/thumbnail/!$<Width>x$<Height>r/gravity/Center/crop/$<Width>x$<Height>
             * wp : http://img5.adesk.com/5d832f8ce7bce75dca87c850
             * xr : false
             * cr : false
             * favs : 379
             * atime : 1.5688797E9
             * id : 5d832f8ce7bce75dca87c850
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
            private List<?> tag;

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

            public List<?> getTag() {
                return tag;
            }

            public void setTag(List<?> tag) {
                this.tag = tag;
            }
        }
    }
}

package com.muppet.weather.Model;

import com.muppet.weather.Utils.JsonRespondParseRemind;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

@HttpResponse(parser = JsonRespondParseRemind.class)
public class CategoryList implements Serializable {

    /**
     * msg : success
     * res : {"category":[{"count":50741,"ename":"girl","rname":"美女","cover_temp":"56a964df69401b2866828acb","name":"美女","cover":"http://img5.adesk.com/5d8318c0e7bce76faacd76e5?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":1,"filter":[],"sn":1,"icover":"582c34f869401b347e0b43fb","atime":1.291266021E9,"type":1,"id":"4e4d610cdf714d2966000000","picasso_cover":"5d8318c0e7bce76faacd76e5"},{"count":93572,"ename":"animation","rname":"动漫","cover_temp":"56a221c969401b3f4aa6700a","name":"动漫","cover":"http://img5.adesk.com/5d6546b2e7bce75ddbaf0884?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":4,"id":"4e4d610cdf714d2966000003","icover":"5880889ae7bce7755f3607d9","sn":2,"atime":1.291266057E9,"type":1,"filter":[],"picasso_cover":"5d6546b2e7bce75ddbaf0884"},{"count":72666,"ename":"landscape","rname":"风景","cover_temp":"56a770e269401b756c748b28","name":"风景","cover":"http://img5.adesk.com/5d774133e7bce73981fab813?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":3,"id":"4e4d610cdf714d2966000002","icover":"581b0f2a69401b34865e6cd2","sn":3,"atime":1.291266049E9,"type":1,"filter":[],"picasso_cover":"5d774133e7bce73981fab813"},{"count":14459,"ename":"game","rname":"游戏","cover_temp":"569f40fa69401b26c648eb87","name":"游戏","cover":"http://img5.adesk.com/5d83c00431f613328000790c?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":15,"filter":[],"sn":4,"icover":"5866127069401b347e0bd82b","atime":1.300683934E9,"type":1,"id":"4e4d610cdf714d2966000007","picasso_cover":"5d83c00431f613328000790c"},{"count":9644,"ename":"text","rname":"文字","cover_temp":"56a1f92369401b3f529d3a3f","name":"文字","cover":"http://img5.adesk.com/5d78e153e7bce73981fab89c?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":5,"filter":[],"sn":5,"icover":"5864e5a769401b34865f1ccc","atime":1.359601742E9,"type":1,"id":"5109e04e48d5b9364ae9ac45","picasso_cover":"5d78e153e7bce73981fab89c"},{"count":8134,"ename":"vision","rname":"视觉","cover_temp":"56a076f769401b323d865538","name":"视觉","cover":"http://img5.adesk.com/5d6099a3c8cfb77e58abbea4?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":8,"filter":[],"sn":6,"icover":"57f8be3d69401b347e0ab423","type":1,"id":"4fb479f75ba1c65561000027","picasso_cover":"5d6099a3c8cfb77e58abbea4"},{"count":15103,"ename":"emotion","rname":"情感","cover_temp":"56a03f5369401b26beeaea1d","name":"情感","cover":"http://img5.adesk.com/5d830a3931f613337719ac4a?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":2,"id":"4ef0a35c0569795756000000","icover":"57c53c8769401b644d2782fb","sn":7,"type":1,"filter":[],"picasso_cover":"5d830a3931f613337719ac4a"},{"count":8214,"ename":"creative","rname":"设计","cover_temp":"569b34af69401b7dd39e9fc3","name":"设计","cover":"http://img5.adesk.com/5d78e081e7bce739af75f8dc?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":9,"id":"4fb47a195ba1c60ca5000222","icover":"575e7a9869401b01d8ef3ece","sn":8,"type":1,"filter":[],"picasso_cover":"5d78e081e7bce739af75f8dc"},{"count":19797,"ename":"celebrity","rname":"明星","cover_temp":"56a9a70669401b338161138c","name":"明星","cover":"http://img5.adesk.com/5d7f8ac1042208672f2f1c72?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":6,"id":"5109e05248d5b9368bb559dc","icover":"5460349269401b3a428a47a7","sn":9,"atime":1.359601746E9,"type":1,"filter":[],"picasso_cover":"5d7f8ac1042208672f2f1c72"},{"count":23969,"ename":"stuff","rname":"物语","cover_temp":"56a61f1c69401b54eff72f31","name":"物语","cover":"http://img5.adesk.com/5d5d09fee7bce70132cc4132?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":10,"filter":[],"sn":10,"icover":"557b8cf269401b1704e91bfc","type":1,"id":"4fb47a465ba1c65561000028","picasso_cover":"5d5d09fee7bce70132cc4132"},{"count":10872,"ename":"art","rname":"艺术","cover_temp":"569f927669401b26beeae9e4","name":"艺术","cover":"http://img5.adesk.com/5d511540e7bce72126747a94?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":16,"filter":[],"sn":11,"icover":"586381ea69401b34865f1729","type":1,"id":"4ef0a3330569795757000000","picasso_cover":"5d511540e7bce72126747a94"},{"count":4229,"ename":"man","rname":"男人","cover_temp":"569b541d69401b7dc8ce2c68","name":"男人","cover":"http://img5.adesk.com/5d78e012e7bce739990b61b2?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":13,"id":"4e4d610cdf714d2966000006","icover":"548aa5db69401b7439ac1aea","sn":12,"atime":1.29825154E9,"type":1,"filter":[],"picasso_cover":"5d78e012e7bce739990b61b2"},{"count":26161,"ename":"cartoon","rname":"卡通","cover_temp":"56a03cda69401b26beeae9f4","name":"卡通","cover":"http://img5.adesk.com/5d78de13e7bce739af75f8d3?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":11,"id":"4e4d610cdf714d2966000004","icover":"57b7cb8b69401b644d2765a1","sn":13,"atime":1.291266067E9,"type":1,"filter":[],"picasso_cover":"5d78de13e7bce739af75f8d3"},{"count":23698,"ename":"machine","rname":"机械","cover_temp":"56a99e1f69401b1ce58c12dc","name":"机械","cover":"http://img5.adesk.com/5d7a19cde7bce739db121e36?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":12,"id":"4e4d610cdf714d2966000005","icover":"5028b42aedd6a9410c002552","sn":13,"atime":1.297756191E9,"type":1,"filter":[],"picasso_cover":"5d7a19cde7bce739db121e36"},{"count":13628,"ename":"cityscape","rname":"城市","cover_temp":"569b540969401b7dd39ea06d","name":"城市","cover":"http://img5.adesk.com/5d75fa13e7bce7396550c202?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":7,"filter":[],"sn":14,"icover":"5792cf7369401b71e3555741","type":1,"id":"4fb47a305ba1c60ca5000223","picasso_cover":"5d75fa13e7bce7396550c202"},{"count":19477,"ename":"animal","rname":"动物","cover_temp":"56a4d1da69401b753a684e69","name":"动物","cover":"http://img5.adesk.com/5d78d83ee7bce7393fd118da?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":14,"filter":[],"sn":16,"icover":"58636cda69401b34865f1406","atime":1.291266042E9,"type":1,"id":"4e4d610cdf714d2966000001","picasso_cover":"5d78d83ee7bce7393fd118da"},{"count":18343,"ename":"movie","rname":"影视","cover_temp":"56a59cbe69401b753a684f7a","name":"影视","cover":"http://img5.adesk.com/5d835809e7bce739af75fcd9?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480","rank":18,"filter":[],"sn":18,"icover":"58aecf3369401b34865f35d1","type":1,"id":"4e58c2570569791a19000000","picasso_cover":"5d835809e7bce739af75fcd9"}]}
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
        private List<CategoryBean> category;

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class CategoryBean implements Serializable{
            /**
             * count : 50741
             * ename : girl
             * rname : 美女
             * cover_temp : 56a964df69401b2866828acb
             * name : 美女
             * cover : http://img5.adesk.com/5d8318c0e7bce76faacd76e5?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480
             * rank : 1
             * filter : []
             * sn : 1
             * icover : 582c34f869401b347e0b43fb
             * atime : 1.291266021E9
             * type : 1
             * id : 4e4d610cdf714d2966000000
             * picasso_cover : 5d8318c0e7bce76faacd76e5
             */

            private int count;
            private String ename;
            private String rname;
            private String cover_temp;
            private String name;
            private String cover;
            private int rank;
            private int sn;
            private String icover;
            private double atime;
            private int type;
            private String id;
            private String picasso_cover;
            private List<?> filter;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getEname() {
                return ename;
            }

            public void setEname(String ename) {
                this.ename = ename;
            }

            public String getRname() {
                return rname;
            }

            public void setRname(String rname) {
                this.rname = rname;
            }

            public String getCover_temp() {
                return cover_temp;
            }

            public void setCover_temp(String cover_temp) {
                this.cover_temp = cover_temp;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public int getSn() {
                return sn;
            }

            public void setSn(int sn) {
                this.sn = sn;
            }

            public String getIcover() {
                return icover;
            }

            public void setIcover(String icover) {
                this.icover = icover;
            }

            public double getAtime() {
                return atime;
            }

            public void setAtime(double atime) {
                this.atime = atime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPicasso_cover() {
                return picasso_cover;
            }

            public void setPicasso_cover(String picasso_cover) {
                this.picasso_cover = picasso_cover;
            }

            public List<?> getFilter() {
                return filter;
            }

            public void setFilter(List<?> filter) {
                this.filter = filter;
            }
        }
    }
}

package com.muppet.weather.Model;

import com.muppet.weather.Utils.JsonRespondParseRemind;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

@HttpResponse(parser = JsonRespondParseRemind.class)
public class UserInfo implements Serializable {
    private int id;
    private String name;
    private String addr;
    private String password;
    private String user_name;
    private String file;
    private Integer age;
    private List<CommonCity> commonCitie;

    public List<CommonCity> getCommonCitie() {
        return commonCitie;
    }

    public void setCommonCitie(List<CommonCity> commonCitie) {
        this.commonCitie = commonCitie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

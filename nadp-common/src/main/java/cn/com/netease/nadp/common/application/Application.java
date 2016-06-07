package cn.com.netease.nadp.common.application;

import java.io.Serializable;

/**
 * Created by bianlanzhou on 16/6/3.
 * Description
 */
public class Application implements Serializable{
    private static final long serialVersionUID = 1L; //Serializable ID
    private String name;  //名称
    private String type;  //类型

    public Application(String name,String type){
        this.name = name;
        this.type = type;
    }

    public final String getName() {
        return name;
    }

    public final String getType() {
        return type;
    }
}

package cn.com.netease.nadp.common.vo;

import java.io.Serializable;
import java.sql.Date;

/**
 * CONFIG实体类
 * cn.com.netease.nadp.web.monitor.vo
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
public class ConfigVO implements Serializable{

    private static final long serialVersionUID = 1L; //Serializable ID

    private int id ;
    private String key;
    private String value;
    private String description;
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

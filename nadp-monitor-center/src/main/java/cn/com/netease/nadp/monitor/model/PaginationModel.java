package cn.com.netease.nadp.monitor.model;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/8.
 */
public class PaginationModel {

    /**
     * 请求服务器端次数
     */
    private String sEcho ;
    /**
     * 数据行数
     */
    private int iTotalRecords;
    /**
     * 实际行数
     */
    private int iTotalDisplayRecords;

    /**
     * 实际数据
     * @return
     */
    private List<?> aaData;

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public List<?> getAaData() {
        return aaData;
    }

    public void setAaData(List<?> aaData) {
        this.aaData = aaData;
    }
}

package main.SchoolModule;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Feng Jiang on 2018/2/15.
 */

public class StuAssignmentTopics {

    private int idstuassignmentopics;
    private UUID _id;
    private UUID stuassignment_id;
    private UUID topicons_id;
    private String pyresult; //批阅结果，对，错，等等
    private int pyscore;//批阅分数
    private String pymemo; //批注文本
    private String pypicname; //批阅图片的的名字，路径默认
    private Date pylastime;  //教师批阅最后时间
    private UUID teapy_id; //教师id
    private String teaname; //教师姓名

    public int getPyscore() {
        return pyscore;
    }

    public void setPyscore(int pyscore) {
        this.pyscore = pyscore;
    }

    public Date getPylastime() {
        return pylastime;
    }

    public void setPylastime(Date pylastime) {
        this.pylastime = pylastime;
    }

    public UUID getTeapy_id() {
        return teapy_id;
    }

    public void setTeapy_id(UUID teapy_id) {
        this.teapy_id = teapy_id;
    }

    public String getTeaname() {
        return teaname;
    }

    public void setTeaname(String teaname) {
        this.teaname = teaname;
    }

    public int getIdstuassignmentopics() {
        return idstuassignmentopics;
    }

    public void setIdstuassignmentopics(int idstuassignmentopics) {
        this.idstuassignmentopics = idstuassignmentopics;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public UUID getStuassignment_id() {
        return stuassignment_id;
    }

    public void setStuassignment_id(UUID stuassignment_id) {
        this.stuassignment_id = stuassignment_id;
    }

    public UUID getTopicons_id() {
        return topicons_id;
    }

    public void setTopicons_id(UUID topicons_id) {
        this.topicons_id = topicons_id;
    }

    public String getPyresult() {
        return pyresult;
    }

    public void setPyresult(String pyresult) {
        this.pyresult = pyresult;
    }

    public String getPymemo() {
        return pymemo;
    }

    public void setPymemo(String pymemo) {
        this.pymemo = pymemo;
    }

    public String getPypicname() {
        return pypicname;
    }

    public void setPypicname(String pypicname) {
        this.pypicname = pypicname;
    }
}

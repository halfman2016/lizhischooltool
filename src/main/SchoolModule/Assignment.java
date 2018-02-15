package main.SchoolModule;

import com.boteteam.yper.yyxy.Module.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


/**
 * Created by Feng Jiang on 2017/9/30.
 * 作业类，每个作业的独立类，运行时，不断填充数据内容。
 *  这里的内容是应用程序内，关于一次作业的全部信息的封装。
 */
public class Assignment {
    private ArrayList<Topicons> topicons; //作业里面的题目的列表，从sql数据查询出来，填充，用_id查询。
    private ArrayList<Topics> pics;
    private UUID _id=UUID.randomUUID();
    private Date bztime; //布置作业的时间
    private Date jstime;  //交收作业的时间
    private UUID bzrid;  //布置人id varchar(80)
    private String bzrname;//布置人的姓名
    private String bjname; //班级名称
    private UUID bj_id; //班级id
    private String subject; //科目
    private ArrayList<Student> stus; //布置给甚么学生
    private ArrayList<Student> stuworkeds; // 已交作业集合
    private String assignmentmemo; //作业备注
    private String assigncode;//作业编码

    public void setPics(ArrayList<Topics> pics) {
        this.pics = pics;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getAssignmentmemo() {
        return assignmentmemo;
    }

    public void setAssignmentmemo(String assignmentmemo) {
        this.assignmentmemo = assignmentmemo;
    }

    public String getAssigncode() {
        return assigncode;
    }

    public void setAssigncode(String assigncode) {
        this.assigncode = assigncode;
    }

    /**
     * Gets bjname.
     *
     * @return the bjname
     */
    public String getBjname() {
        return bjname;
    }

    /**
     * Sets bjname.
     *
     * @param bjname the bjname
     */
    public void setBjname(String bjname) {
        this.bjname = bjname;
    }

    /**
     * Gets bj id.
     *
     * @return the bj id
     */
    public UUID getBj_id() {
        return bj_id;
    }

    /**
     * Sets bj id.
     *
     * @param bj_id the bj id
     */
    public void setBj_id(UUID bj_id) {
        this.bj_id = bj_id;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets topicons.
     *
     * @return the topicons
     */
    public ArrayList<Topicons> getTopicons() {
        return topicons;
    }

    /**
     * Sets topicons.
     *
     * @param topicons the topicons
     */
    public void setTopicons(ArrayList<Topicons> topicons) {
        this.topicons = topicons;
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public UUID get_id() {
        return _id;
    }


    /**
     * Gets bztime.
     *
     * @return the bztime
     */
    public Date getBztime() {
        return bztime;
    }

    /**
     * Sets bztime.
     *
     * @param bztime the bztime
     */
    public void setBztime(Date bztime) {
        this.bztime = bztime;
    }

    /**
     * Gets jstime.
     *
     * @return the jstime
     */
    public Date getJstime() {
        return jstime;
    }

    /**
     * Sets jstime.
     *
     * @param jstime the jstime
     */
    public void setJstime(Date jstime) {
        this.jstime = jstime;
    }

    /**
     * Gets bzrid.
     *
     * @return the bzrid
     */
    public UUID getBzrid() {
        return bzrid;
    }

    /**
     * Sets bzrid.
     *
     * @param bzrid the bzrid
     */
    public void setBzrid(UUID bzrid) {
        this.bzrid = bzrid;
    }

    /**
     * Gets bzrname.
     *
     * @return the bzrname
     */
    public String getBzrname() {
        return bzrname;
    }

    /**
     * Sets bzrname.
     *
     * @param bzrname the bzrname
     */
    public void setBzrname(String bzrname) {
        this.bzrname = bzrname;
    }

    /**
     * Gets stus.
     *
     * @return the stus
     */
    public ArrayList<Student> getStus() {
        return stus;
    }

    /**
     * Sets stus.
     *
     * @param stus the stus
     */
    public void setStus(ArrayList<Student> stus) {
        this.stus = stus;
    }

    /**
     * Gets stuworkeds.
     *
     * @return the stuworkeds
     */
    public ArrayList<Student> getStuworkeds() {
        return stuworkeds;
    }

    /**
     * Sets stuworkeds.
     *
     * @param stuworkeds the stuworkeds
     */
    public void setStuworkeds(ArrayList<Student> stuworkeds) {
        this.stuworkeds = stuworkeds;
    }
}

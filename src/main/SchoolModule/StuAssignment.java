package main.SchoolModule;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Feng Jiang on 2018/2/14.
 * 学生作业类，记录学生和作业的关系
 * 1、何时交作业；
 * 2、布置的是甚么作业；
 * 3、
 *
 */

public class StuAssignment {
    private UUID _id=UUID.randomUUID();
    private UUID stu_id;
    private UUID assign_id;
    private  int  assignment_idassignmnet;
    private String stumanme;
    private String status;
    private Date jstime;

    private ArrayList<StuAssignmentTopics> stuAssignmentTopics;

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public UUID getStu_id() {
        return stu_id;
    }

    public void setStu_id(UUID stu_id) {
        this.stu_id = stu_id;
    }

    public UUID getAssign_id() {
        return assign_id;
    }

    public void setAssign_id(UUID assign_id) {
        this.assign_id = assign_id;
    }

    public int getAssignment_idassignmnet() {
        return assignment_idassignmnet;
    }

    public void setAssignment_idassignmnet(int assignment_idassignmnet) {
        this.assignment_idassignmnet = assignment_idassignmnet;
    }

    public String getStumanme() {
        return stumanme;
    }

    public void setStumanme(String stumanme) {
        this.stumanme = stumanme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getJstime() {
        return jstime;
    }

    public void setJstime(Date jstime) {
        this.jstime = jstime;
    }

    public ArrayList<StuAssignmentTopics> getStuAssignmentTopics() {
        return stuAssignmentTopics;
    }

    public void setStuAssignmentTopics(ArrayList<StuAssignmentTopics> stuAssignmentTopics) {
        this.stuAssignmentTopics = stuAssignmentTopics;
    }
}

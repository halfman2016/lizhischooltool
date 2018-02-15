package main.SchoolModule;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Feng Jiang on 2017/9/30.
 */

public class Topicons {
    private UUID _id=UUID.randomUUID();  //数据库里面是 varchar字段
    private String topicon; //题目文本
    private int topicno; //题号
    private String answer; //题目答案 ，保留字段
    private int idtopic;//数据库id
    private  int assignment_idassignment;

    public int getAssignment_idassignment() {
        return assignment_idassignment;
    }

    public void setAssignment_idassignment(int assignment_idassignment) {
        this.assignment_idassignment = assignment_idassignment;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getTopicon() {
        return topicon;
    }

    public void setTopicontxt(String topicon) {
        this.topicon = topicon;
    }

    public int getTopicno() {
        return topicno;
    }

    public void setTopicno(int topicno) {
        this.topicno = topicno;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getIdtopic() {
        return idtopic;
    }

    public void setIdtopic(int idtopic) {
        this.idtopic = idtopic;
    }
}

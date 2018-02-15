package main.SchoolModule;

/**
 * Created by Feng Jiang on 2018/2/12.
 */

public class Topics {
    private int idtopics;
    private String picname;
   private int picseq; //图片次序
     private     int assignment_idassignment;

    public int getIdtopics() {
        return idtopics;
    }

    public void setIdtopics(int idtopics) {
        this.idtopics = idtopics;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public int getPicseq() {
        return picseq;
    }

    public void setPicseq(int picseq) {
        this.picseq = picseq;
    }

    public int getAssignment_idassignment() {
        return assignment_idassignment;
    }

    public void setAssignment_idassignment(int assignment_idassignment) {
        this.assignment_idassignment = assignment_idassignment;
    }
}

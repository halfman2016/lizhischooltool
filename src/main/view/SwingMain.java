/*
 * Created by JFormDesigner on Mon Dec 26 10:38:31 CST 2016
 */

package main.view;

import java.beans.*;
import javax.swing.event.*;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import javafx.application.Application;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Label;
import jxl.write.biff.RowsExceededException;
import main.Module.*;
import main.util.MDBTools;
import main.util.MySqlTools;
import main.util.Utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.lang.Boolean;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author Halfman
 */


public class SwingMain extends JFrame {

    private MDBTools mdb = new MDBTools();
    Vector listname=new Vector();
    Vector teaname=new Vector();
    Vector subname=new Vector();
    Vector gclist=new Vector();
    Vector stulist=new Vector();
    Vector gcstulist=new Vector();
    ArrayList<Subject> subjects;
    ArrayList<Teacher> teachers;
    ArrayList<Student> students;  //学生清单
    ArrayList<Student> gcstudents; //班级学生清单
    ArrayList<GradeClass> gradeClasses;  //班级清单


    String timelabel;
    String filePath="";
    public SwingMain() {
        initComponents();

    }

    private void thisWindowClosed(WindowEvent e) {
        // TODO add your code here
    }

    private void button2ActionPerformed(ActionEvent e) {
        // 导入学生数据
        String path = "";
        JFileChooser chooser = new JFileChooser("D:\\1newbote\\lizhi");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "2003~2007 Excel files", "xls");
        chooser.setFileFilter(filter);
        int ii = chooser.showOpenDialog(null);
        if (ii == chooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();

            try {
                Workbook workbook = Workbook.getWorkbook(new File(path));
                Sheet sheet = workbook.getSheet(0);

                for (int i = 1; i < sheet.getColumns(); i++) {
                    Cell[] cells = sheet.getColumn(i);
                    GradeClass gradeClass = new GradeClass(cells[1].getContents());
                    java.util.List<Student> stus = new ArrayList<Student>();
                    System.out.print("cells has " + cells.length);

                    for (int j = 2; j < cells.length; j++) {
                        Cell cell1 = sheet.getCell(i, j);
                        Student stu = new Student(cells[j].getContents());
                        stu.setGradeclass(gradeClass.getName());
                        stu.setGradeclassid(gradeClass.get_id());
                        stu.setPwd("123456");
                      mdb.addStu(stu);
                        stus.add(stu);

                        System.out.print(cell1.getContents());
                    }

                    gradeClass.setStus(stus);
                 mdb.saveGradeClass(gradeClass);

                }


            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (BiffException e1) {
                e1.printStackTrace();
            }

        }

    }

    private void button3ActionPerformed(ActionEvent e) {
        // 导入教师数据
        String path = "";
        JFileChooser chooser = new JFileChooser("D:\\1newbote\\lizhi");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "2003~2007 Excel files", "xls");
        chooser.setFileFilter(filter);
        int ii = chooser.showOpenDialog(null);
        if (ii == chooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();

            try {
                Workbook workbook = Workbook.getWorkbook(new File(path));
                Sheet sheet = workbook.getSheet(0);

                for (int i = 0; i < sheet.getColumns(); i++) {
                    Cell[] cells = sheet.getColumn(i);
                    GradeClass gradeClass = mdb.getGradeClassByName(cells[0].getContents());
                    java.util.List<Teacher> teachers = new ArrayList<Teacher>();
                    System.out.print("cells has " + cells.length);

                    for (int j = 1; j < cells.length; j++) {
                        Cell cell1 = sheet.getCell(i, j);
                        Teacher tea = new Teacher(cells[j].getContents());
                        tea.setPwd("123123");
                        tea.setOnDutyGradeClassId(gradeClass.get_id());
                        tea.setOnDutyGradeClassName(gradeClass.getName());
                        tea.setTid("t" + i + j);
                        mdb.addTea(tea);
                        teachers.add(tea);

                        System.out.print(cell1.getContents());
                    }


                }


            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (BiffException e1) {
                e1.printStackTrace();
            }


        }
    }

    private void button4ActionPerformed(ActionEvent e) {
        // TODO add your code here
        DayCheckListAction day1 = new DayCheckListAction("不用心听讲", "上课纪律检查", -2);
        DayCheckListAction day2 = new DayCheckListAction("用心听讲", "上课纪律检查", 2);
        mdb.addDaycheckListAction(day1);
        mdb.addDaycheckListAction(day2);
//        DayCheckListAction action1=new DayCheckListAction("毛巾摆放","早晨宿舍检查",2);
//        DayCheckListAction action2=new DayCheckListAction("口杯摆放","早晨宿舍检查",2);
//        DayCheckListAction action3=new DayCheckListAction("被子折叠","早晨宿舍检查",2);
//        action1.setDayaddscore(true);
//        action2.setDayaddscore(true);
//        action3.setDayaddscore(true);
//        mdb.addDaycheckListAction(action1);
//        mdb.addDaycheckListAction(action2);
//        mdb.addDaycheckListAction(action3);
    }

    private void panel4ComponentShown(ComponentEvent e) {
        // TODO add your code here
        System.out.print("llook");
    }

    private void button5ActionPerformed(ActionEvent e) {

        String path = "";
        JFileChooser chooser = new JFileChooser("D:\\1newbote\\lizhi");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "2003~2007 Excel files", "xls");
        chooser.setFileFilter(filter);
        int ii = chooser.showOpenDialog(null);
        if (ii == chooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();

            try {
                Workbook workbook = Workbook.getWorkbook(new File(path));
                Sheet sheet = workbook.getSheet(1);

                for (int i = 2; i < sheet.getRows(); i++) {
                    Cell[] cells = sheet.getRow(i);
                    System.out.print("cells has " + cells.length);

                    PinAction pinAction = new PinAction(cells[1].getContents(), cells[2].getContents(), Integer.parseInt(cells[5].getContents()), Integer.parseInt(cells[3].getContents()), Integer.parseInt(cells[4].getContents()));

                    for (int j = 0; j < cells.length; j++) {
                        Cell cell1 = sheet.getCell(j, i);


                        System.out.print(cell1.getContents());
                    }


                    mdb.addPinAction(pinAction);


                }


            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (BiffException e1) {
                e1.printStackTrace();
            }

        }

    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
        //设置需要操作账号的AK和SK


//        String ACCESS_KEY = "M69EWymSG8o0Y7Tl8T5iZ3pZlyceiCvMalynnli2";
//
//        String SECRET_KEY = "1g3e5MsedHliY86oSZSH9RXFAdTeX0eD3eb9L3_-";
//
//        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//
//
//        Configuration conf=new Configuration(Zone.zone0());
//        //实例化一个BucketManager对象
//
//        BucketManager bucketManager = new BucketManager(auth,conf);
//
//
//
//        //要列举文件的空间名
//
//        String bucket = "lizhibutian";
//
//        listname=new Vector();
//
//
//        try {
//
//            //调用listFiles方法列举指定空间的指定文件
//
//            //参数一：bucket    空间名
//
//            //参数二：prefix    文件名前缀
//
//            //参数三：marker    上一次获取文件列表时返回的marker
//
//            //参数四：limit     每次迭代的长度限制，最大1000，推荐值100
//
//            //参数五：delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
//
//            FileListing fileListing = bucketManager.listFiles(bucket,null,null,999,null);
//
//            FileInfo[] items = fileListing.items;
//
//            for(FileInfo fileInfo:items){
//
//                Utils.downLoadFromUrl("http://lizhibutian.boteteam.com/"+fileInfo.key,fileInfo.key+".jpeg","D:\\1newbote\\lizhi\\serverpic");
//                listname.add(fileInfo.key);
//                System.out.println(fileInfo.key);
//
//
//
//            }
//
//            System.out.println();
//
//            System.out.println(fileListing.marker);
//
//
//
//            list2.setListData(listname);
//
//
//        } catch (QiniuException ee) {
//
//            //捕获异常信息
//
//            Response r = ee.response;
//
//          //  System.out.println(r.toString());
//
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }

        listname=new Vector();
        File file=new File(filePath);
    //    File file=new File("D:\\1newbote\\lizhi\\serverpic");
        File[] filelist=file.listFiles();
        for (File temp:filelist){
            listname.add(temp.getName().substring(0,temp.getName().length()-5));
            System.out.print(temp.getName());
        }
        list2.setListData(listname);




    }

    private void list2ValueChanged(ListSelectionEvent e) {
        // TODO add your code here  图片列表点击

        Icon icon;
        String url="http://lizhibutian.boteteam.com/" + listname.get(list2.getSelectedIndex());
            //icon=new ImageIcon(new URL(url));
          //  icon=new ImageIcon("D:\\1newbote\\lizhi\\serverpic"+File.separator+listname.get(list2.getSelectedIndex())+".jpeg");
           icon=new ImageIcon(filePath+File.separator+listname.get(list2.getSelectedIndex())+".jpeg");
            labelimage.setIcon(icon);

        txtPicDateTime.setText(listname.get(list2.getSelectedIndex()).toString());
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd_HHmmssSSS");
        try {
            Date date=sdf.parse(listname.get(list2.getSelectedIndex()).toString());
            sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            txtPicDateTime.setText(sdf.format(date));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }


        timelabel=listname.get(list2.getSelectedIndex()).toString();


    }

    private void button6ActionPerformed(ActionEvent e) {
        // TODO add your code here 加载专题数据
         subjects=mdb.getSubjects();
        listname=new Vector();

        for(Subject sub:subjects)
        {
            listname.add(sub.getSubjectName());
        }

        list3.setListData(listname);
    }

    private void list3ValueChanged(ListSelectionEvent e) {
        // 专题列表点击
        Subject subject=subjects.get(list3.getSelectedIndex());
        txtSubName.setText(subject.getSubjectName());
        txtSubInfo.setText(subject.getSubjectInfo());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        txtSdate.setText(simpleDateFormat.format(subject.getStartTime()));
        txtEdate.setText(simpleDateFormat.format(subject.getEndTime()));
        txt_id.setText(subject.get_id().toString());
    }

    private void addSubjectActionPerformed(ActionEvent e) {
        // 增加专题

        Subject subject=new Subject(txtSubName.getText());
        subject.setSubjectInfo(txtSubInfo.getText());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            subject.setStartTime(simpleDateFormat.parse(txtSdate.getText()));
            subject.setEndTime(simpleDateFormat.parse(txtEdate.getText()));

        } catch (ParseException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(this,"日期格式不对");
            return;
        }

        mdb.saveSubject(subject);

    }

    private void modSubActionPerformed(ActionEvent e) {
        // 修改专题
        Subject subject=new Subject(txtSubName.getText());
        subject.set_id(UUID.fromString(txt_id.getText()));
        subject.setSubjectInfo(txtSubInfo.getText());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            subject.setStartTime(simpleDateFormat.parse(txtSdate.getText()));
            subject.setEndTime(simpleDateFormat.parse(txtEdate.getText()));

        } catch (ParseException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(this,"日期格式不对");
            return;

        }

        mdb.updateSubject(subject);    }

        private void tabbedPane1StateChanged(ChangeEvent e) {



        }

        private void button7ActionPerformed(ActionEvent e) {
            // 增加关联

            Photopic photopic=new Photopic();

            if (listTeaName.getSelectedIndex() != -1 && listSub.getSelectedIndex() != -1)

            {
                int i = listTeaName.getSelectedIndex();
                Teacher author = teachers.get(i);
                if (listSub.getSelectedIndex() == 0) {

                    //选择无主题
                    System.out.print("无主题");


                }

                else
                {
                    //选择主题

                    Subject subject=subjects.get(listSub.getSelectedIndex()-1);
                    photopic.setBelongToSubject(subject.get_id());

                    System.out.println(subject.getSubjectName());

                }

                photopic.setPhotoauthor(author.getName());
                photopic.setPhotoauthorid(author.get_id());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    photopic.setPhotodate(sdf.parse(txtPicDateTime.getText()));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                photopic.setPhotomemo(txtPicMemo.getText());

                mdb.addFakePhoto(photopic,timelabel);


            }
        }

        private void button8ActionPerformed(ActionEvent e) {
            // 选择本地目录
            JFileChooser chooser = new JFileChooser("D:\\");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showOpenDialog(null);
            String fname = chooser.getName(chooser.getSelectedFile());
            if (result == JFileChooser.APPROVE_OPTION) {

                filePath = chooser.getSelectedFile().getPath();
                System.out.println(filePath);


            }
        }

        private void panel3PropertyChange(PropertyChangeEvent e) {
            // TODO add your code here

            // 加载专题和老师姓名

            // 图片编辑切换
            String timeStamp = new SimpleDateFormat("yyMMdd_HHmmssSSS").format(new Date());
            txtPicDateTime.setText(timeStamp);


            //装载teaname

            teachers=mdb.getTeas();
            for(Teacher tea:teachers)
            {
                teaname.add(tea.getName());
            }
            listTeaName.setListData(teaname);

            subjects=mdb.getSubjects();
            subname.clear();
            subname.add("无专题");
            for(Subject sub:subjects)
            {
                subname.add(sub.getSubjectName());
            }
            listSub.setListData(subname);
        }

        private void button9ActionPerformed(ActionEvent e) {
            // 学生账号导出excel
            ArrayList <Student>stus=mdb.getStus();
            File file=new File("stuout.xls");

            try {
                WritableWorkbook workbook=Workbook.createWorkbook(file);
                WritableSheet sheet=workbook.createSheet("学生名单",0);
                jxl.write.Label label=new Label(0,0,"班级");

                sheet.addCell(label);
                label=new Label(1,0,"姓名");
                sheet.addCell(label);
                label=new Label(2,0,"密码");
                sheet.addCell(label);
                int i=1;
                for (Student stu:stus)
                {
                    label=new Label(0,i,stu.getGradeclass());
                    sheet.addCell(label);
                    label=new Label(1,i,stu.getName());
                    sheet.addCell(label);
                    label=new Label(2,i,stu.getPwd());
                    sheet.addCell(label);

                    i=i+1;

                }
                workbook.write();
                workbook.close();


            } catch (IOException e1) {

                e1.printStackTrace();
            } catch (RowsExceededException e1) {
                e1.printStackTrace();
            } catch (WriteException e1) {
                e1.printStackTrace();
            }



        }

        private void button10ActionPerformed(ActionEvent e) {
            // 教师账号导出EXCEL

            ArrayList <Teacher>teas=mdb.getTeas();
            File file=new File("teaout.xls");

            try {
                WritableWorkbook workbook=Workbook.createWorkbook(file);
                WritableSheet sheet=workbook.createSheet("教师名单",0);
                jxl.write.Label label=new Label(0,0,"班级");

                sheet.addCell(label);
                label=new Label(1,0,"姓名");
                sheet.addCell(label);
                label=new Label(2,0,"登录名");
                sheet.addCell(label);
                label=new Label(3,0,"密码");
                sheet.addCell(label);
                int i=1;
                for (Teacher tea:teas)
                {
                    label=new Label(0,i,tea.getOnDutyGradeClassName());
                    sheet.addCell(label);
                    label=new Label(1,i,tea.getName());
                    sheet.addCell(label);
                    label=new Label(2,i,tea.getTid());
                    sheet.addCell(label);
                    label=new Label(3,i,tea.getPwd());
                    sheet.addCell(label);

                    i=i+1;

                }
                workbook.write();
                workbook.close();


            } catch (IOException e1) {

                e1.printStackTrace();
            } catch (RowsExceededException e1) {
                e1.printStackTrace();
            } catch (WriteException e1) {
                e1.printStackTrace();
            }

        }

        private void button11ActionPerformed(ActionEvent e) {
            // TODO add your code here 罗立新 t37  赵艳然 t38
            Teacher luotea=new Teacher("罗立新");
            luotea.setDutytitle("校长");
            luotea.setOnDutyGradeClassId(UUID.fromString("980c1244-ebe9-4740-8a3b-959148e2e8f3"));
            luotea.setOnDutyGradeClassName("八1班");
            luotea.setPwd("123123");
            mdb.addTea(luotea);

            Teacher zhaotea=new Teacher("赵艳然");
            zhaotea.setDutytitle("书记");
            zhaotea.setOnDutyGradeClassId(UUID.fromString("980c1244-ebe9-4740-8a3b-959148e2e8f3"));
            zhaotea.setOnDutyGradeClassName("八1班");
            zhaotea.setPwd("123123");

            mdb.addTea(zhaotea);
        }

        private void button12ActionPerformed(ActionEvent e) {
            // 输出统计xls
            MySqlTools mySqlTools=new MySqlTools();
            mySqlTools.getConn();
            List<Log> logs=mySqlTools.queryLogTea();
            List<LogAction> logActions=mySqlTools.queryLogAction();
            FileNameExtensionFilter filter=new FileNameExtensionFilter("*.xls","xls");
            JFileChooser chooser=new JFileChooser();
            SimpleDateFormat df=new SimpleDateFormat("YYMMdd");
            String date=df.format(new Date());
            chooser.setSelectedFile(new File("情况输出"+date+".xls"));
            chooser.setFileFilter(filter);
            chooser.setMultiSelectionEnabled(false);

            int result=chooser.showSaveDialog(this);
            if(result==JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
        //    File file=new File("情况输出"+date+".xls");
                if (!file.getPath().endsWith(".xls")) {
                    file = new File(file.getPath() + ".xls");
                }

                WritableWorkbook workbook = null;
                try {
                    workbook = Workbook.createWorkbook(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                WritableSheet sheet = workbook.createSheet("教师登录软件情况表", 0);
                jxl.write.Label label = new Label(0, 0, "账号");
                try {
                    sheet.addCell(label);
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }
                label = new Label(1, 0, "登录时间");
                try {
                    sheet.addCell(label);
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }
                label = new Label(2, 0, "结束时间");
                try {
                    sheet.addCell(label);
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }
                label = new Label(3, 0, "时长（秒）");
                try {
                    sheet.addCell(label);
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }
                label = new Label(4, 0, "姓名");
                try {
                    sheet.addCell(label);
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }
                label = new Label(5, 0, "班级");
                try {
                    sheet.addCell(label);
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }
                int i = 1;
                for (Log log : logs) {
                    label = new Label(0, i, log.getLid());
                    try {
                        sheet.addCell(label);

                    label = new Label(1, i, log.getStime().toString());

                        sheet.addCell(label);

                    label = new Label(2, i, log.getEtime().toString());

                        sheet.addCell(label);

                    label = new Label(3, i, (String.valueOf(log.getDtime() / 1000)));

                        sheet.addCell(label);

                    label = new Label(4, i, log.getLname());
                    sheet.addCell(label);
                    label = new Label(5, i, log.getgradeClassName());
                    sheet.addCell(label);
                }
catch (WriteException e1) {
                        e1.printStackTrace();
                    }
                    i = i + 1;

                }

                WritableSheet sheet1 = workbook.createSheet("教师使用软件情况表", 1);
                jxl.write.Label label1 = new Label(0, 0, "账号");
                try {
                    sheet1.addCell(label1);
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }
                label1 = new Label(1, 0, "姓名");
                try {
                    sheet1.addCell(label1);

                label1 = new Label(2, 0, "行为内容");
                sheet1.addCell(label1);
                label1 = new Label(3, 0, "行为时间");
                sheet1.addCell(label1);
                label1 = new Label(4, 0, "班级");
                sheet1.addCell(label1);
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }
                int ii = 1;
                for (LogAction log :logActions ) {
                    label1 = new Label(0, ii, log.getActionpeopleid());
                    try {
                        sheet1.addCell(label1);

                    label1 = new Label(1, ii, log.getActionpeoplename());
                    sheet1.addCell(label1);
                    label1 = new Label(2, ii, log.getActionname());
                    sheet1.addCell(label1);
                    label1 = new Label(3, ii,log.getActiontime().toString());
                    sheet1.addCell(label1);
                    label1 = new Label(4, ii, log.getActiongradeclassname());
                    sheet1.addCell(label1);

                    } catch (WriteException e1) {
                        e1.printStackTrace();
                    }
                    ii = ii + 1;

                }

                try {
                    workbook.write();
                    workbook.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (WriteException e1) {
                    e1.printStackTrace();
                }


            }}

            private void tstActionPerformed(ActionEvent e) {
                // TODO add your code here
                List<GradeClass> gradeClassList=mdb.getGradeClassesIsActive();
                GradeClass gradeClass=mdb.getGradeClassIsActiveByName("九1班");

                Student stu=new Student("常文静");


            }

            private void LoadStuActionPerformed(ActionEvent e) {
                // 从服务器读取学生名单

              students= (ArrayList<Student>) mdb.getStus();gradeClasses=mdb.getGradeClassesIsActive();
                stulist.clear();
                for(Student stu:students){
                    stulist.add(stu.getName()+"  " + stu.getStatus() + " " + stu.get_id().toString());
                }
                stunamelist.setListData(stulist);



                gclist.clear();

                for(GradeClass gc:gradeClasses){
                    gclist.add(gc.getName());
                }
                classlist.setListData(gclist);

            }

            private void addgradeclassActionPerformed(ActionEvent e) {



            }

            private void classlistValueChanged(ListSelectionEvent e) {
                // TODO add your code here
                GradeClass gc=gradeClasses.get(classlist.getSelectedIndex());
                gcstudents= (ArrayList<Student>) gc.getStus();
                gcstulist.clear();
                for (Student stu:gcstudents){
                    gcstulist.add(stu.getName());
                }
                listgcstu.setListData(gcstulist);
                  }

                  private void addstudentsActionPerformed(ActionEvent e) {
                      // TODO add your code here

                      // 从服务器读取当前的数据库内容
                  //   if(students.size()>0) students.clear();
                    //  if(gradeClasses.size()>0) gradeClasses.clear();


                      students = mdb.getStus();
                      gradeClasses = mdb.getGradeClassesIsActive();

                      String path = "";
                      JFileChooser chooser = new JFileChooser("D:\\1newbote\\lizhi");
                      FileNameExtensionFilter filter = new FileNameExtensionFilter(
                              "2003~2007 Excel files", "xls");
                      chooser.setFileFilter(filter);
                      int ii = chooser.showOpenDialog(null);
                      if (ii == chooser.APPROVE_OPTION) {
                          path = chooser.getSelectedFile().getAbsolutePath();


                          try {
                              Workbook workbook = Workbook.getWorkbook(new File(path));
                              Sheet sheet = workbook.getSheet(0);
                                int colss=sheet.getColumns();
                              for (int cols = 1; cols < sheet.getColumns(); cols++) {
                                  Cell[] cells = sheet.getColumn(cols);

                                  //新建导入的班级

                                  GradeClass gradeClass = new GradeClass(cells[0].getContents());
                                  ArrayList<Student> classstues=new ArrayList<>();

                                  for (int j = 1; j < cells.length; j++) {
                                      //    Cell cell1 = sheet.getCell(i, j);
                                      Student stu=null;
                                      boolean isnothave = true;

                                      for (int i = 0; i < students.size(); i++) {

                                          if (students.get(i).getName().equals( cells[j].getContents())) {
                                              // 学生在老名单里面
                                              stu=students.get(i);
                                             stu.setStatus("在读");
                                              //赋予新班级的信息
                                              stu.setGradeclass(gradeClass.getName());
                                              stu.setGradeclassid(gradeClass.get_id());

                                              students.set(i,stu);
                                              isnothave = false;
                                              break;
                                          }
                                      }

                                      if (isnothave) {
                                          stu = new Student(cells[j].getContents());
                                          stu.setGradeclass(gradeClass.getName());
                                          stu.setGradeclassid(gradeClass.get_id());
                                          stu.setPwd("123456");
                                          stu.setStatus("在读");
                                          students.add(stu);
                                      }

                                      classstues.add(stu);
                                  }
                                  gradeClass.setStus(classstues);
                                  gradeClasses.add(gradeClass);
                              }
                          }
                          catch (IOException e1) {
                              e1.printStackTrace();
                          } catch (BiffException e1) {
                              e1.printStackTrace();
                          }

                          System.out.print("");

                      }

                      stulist.clear();
                      for(Student stu:students){
                          stulist.add(stu.getName()+"  " + stu.getStatus() + " " + stu.get_id().toString());
                      }
                      stunamelist.setListData(stulist);



                      gclist.clear();

                      for(GradeClass gc:gradeClasses){
                          gclist.add(gc.getName());
                      }
                      classlist.setListData(gclist);


                  }

                  private void saveClassAndStuActionPerformed(ActionEvent e) {
                      // TODO add your code here
                      mdb.saveGradeClasses(gradeClasses);
                      mdb.saveStudents(students);

                  }

                  private void LoadFromServerTeaActionPerformed(ActionEvent e) {
                      // TODO add your code here
                    //  teachers=mdb.getTeas();
                      //gradeClasses=mdb.getGradeClassesIsActive();
                     // mdb.setIsActiveFalseGradeClass();


                  }

                  private void ImportFromFileTeaActionPerformed(ActionEvent e) {
                      // TODO add your code here
                      teachers = mdb.getTeas(); //读取旧教师名单那
                      gradeClasses = mdb.getGradeClassesIsActive();
                      //班级已经OK，所以班级不新建

                      String path = "";
                      ArrayList<Integer> tids=new ArrayList<>();

                      int itid=10;  //tid 的计算起点


                      for(Teacher tea: teachers) {
                          tids.add(Integer.parseInt(tea.getTid().substring(1)));
                      }

                      JFileChooser chooser = new JFileChooser("D:\\1newbote\\lizhi");
                      FileNameExtensionFilter filter = new FileNameExtensionFilter(
                              "2003~2007 Excel files", "xls");
                      chooser.setFileFilter(filter);
                      int ii = chooser.showOpenDialog(null);
                      if (ii == chooser.APPROVE_OPTION) {
                          path = chooser.getSelectedFile().getAbsolutePath();


                          try {
                              Workbook workbook = Workbook.getWorkbook(new File(path));
                              Sheet sheet = workbook.getSheet(0);
                              int colss = sheet.getColumns();
                              for (int cols = 1; cols < sheet.getColumns(); cols++) {
                                  Cell[] cells = sheet.getColumn(cols);
                                  //获得班级对象
                                  GradeClass gradeClass = mdb.getGradeClassIsActiveByName(cells[0].getContents());

                                  for (int j = 1; j < cells.length; j++) {

                                      Teacher tea = null;
                                      boolean isnothave = true;

                                      for (int i = 0; i < teachers.size(); i++) {

                                          if (teachers.get(i).getName().equals(cells[j].getContents())) {
                                              // 教师在老名单里面
                                              tea = teachers.get(i);
                                              tea.setStatus("在职");
                                              //赋予新班级的信息
                                              tea.setOnDutyGradeClassName(gradeClass.getName());
                                              tea.setOnDutyGradeClassId(gradeClass.get_id());

                                              teachers.set(i, tea);
                                              isnothave = false;
                                              break;
                                          }
                                      }

                                      if (isnothave) {

                                          //建立新老师对象

                                          tea = new Teacher(cells[j].getContents());
                                          tea.setOnDutyGradeClassName(gradeClass.getName());
                                          tea.setOnDutyGradeClassId(gradeClass.get_id());
                                          tea.setPwd("123456");
                                          tea.setStatus("在职");

                                          //计算tid
                                          boolean isNotStop = true;

                                          while (isNotStop) {

                                              itid++;

                                              Boolean result = tids.contains(itid);

                                              if (!result) {
                                                  isNotStop = false;
                                              }
                                          }
                                          tea.setTid("t" + Integer.toString(itid));

                                          tids.add(itid);
                                          teachers.add(tea);

                                      }


                                  }
                              }
                          }




                          catch (IOException e1) {
                              e1.printStackTrace();
                          } catch (BiffException e1) {
                              e1.printStackTrace();
                          }

                          System.out.print("");

                      }

                      if (teaname.size()>0) teaname.clear();
                      for(Teacher tea:teachers){
                          teaname.add(tea.getName()+"  " + tea.getStatus() + " " + tea.getTid().toString() + " " +tea.getOnDutyGradeClassName());
                      }
                      listteanameForClass.setListData(teaname);
System.out.print("");
                  }

                  private void btnSaveTeaclasstoServerActionPerformed(ActionEvent e) {
                      // TODO add your code here
                      //mdb.saveTeachers(teachers);

                      if (teaname.size()>0) teaname.clear();
                      for(Teacher tea:teachers){
                          teaname.add(tea.getName()+"  " + tea.getStatus() + " " + tea.getTid().toString() + " " +tea.getOnDutyGradeClassName());
                      }
                      listteanameForClass.setListData(teaname);

                  }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        button2 = new JButton();
        button3 = new JButton();
        label1 = new JLabel();
        button9 = new JButton();
        button10 = new JButton();
        button11 = new JButton();
        LoadStu = new JButton();
        scrollPane9 = new JScrollPane();
        stunamelist = new JList();
        scrollPane10 = new JScrollPane();
        classlist = new JList();
        scrollPane11 = new JScrollPane();
        listgcstu = new JList();
        addstudents = new JButton();
        textField1 = new JTextField();
        textField2 = new JTextField();
        labelsid = new JLabel();
        saveClassAndStu = new JButton();
        checkupyear = new JCheckBox();
        panel2 = new JPanel();
        scrollPane12 = new JScrollPane();
        listteanameForClass = new JList();
        LoadFromServerTea = new JButton();
        ImportFromFileTea = new JButton();
        btnSaveTeaclasstoServer = new JButton();
        panel3 = new JPanel();
        button1 = new JButton();
        scrollPane2 = new JScrollPane();
        list2 = new JList();
        scrollPane3 = new JScrollPane();
        labelimage = new JLabel();
        scrollPane6 = new JScrollPane();
        listSub = new JList();
        scrollPane7 = new JScrollPane();
        listTeaName = new JList();
        txtPicDateTime = new JTextField();
        scrollPane8 = new JScrollPane();
        txtPicMemo = new JTextPane();
        button7 = new JButton();
        button8 = new JButton();
        panel4 = new JPanel();
        button4 = new JButton();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        panel5 = new JPanel();
        button5 = new JButton();
        panel6 = new JPanel();
        scrollPane4 = new JScrollPane();
        list3 = new JList();
        button6 = new JButton();
        txtSubName = new JTextField();
        txtSdate = new JTextField();
        txtEdate = new JTextField();
        addSubject = new JButton();
        txt_id = new JLabel();
        modSub = new JButton();
        scrollPane5 = new JScrollPane();
        txtSubInfo = new JTextPane();
        panel7 = new JPanel();
        out = new JButton();
        tst = new JButton();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        Container contentPane = getContentPane();

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                //---- button2 ----
                button2.setText("1\u3001\u5bfc\u5165\u5b66\u751f\u6570\u636e");
                button2.setEnabled(false);
                button2.addActionListener(e -> button2ActionPerformed(e));

                //---- button3 ----
                button3.setText("2\u3001\u5bfc\u5165\u6559\u5e08\u6570\u636e");
                button3.setEnabled(false);
                button3.addActionListener(e -> button3ActionPerformed(e));

                //---- label1 ----
                label1.setText("--\u300b");

                //---- button9 ----
                button9.setText("\u5bfc\u51fa\u5b66\u751f\u540d\u5355\u8d26\u53f7");
                button9.addActionListener(e -> button9ActionPerformed(e));

                //---- button10 ----
                button10.setText("\u5bfc\u51fa\u6559\u5e08\u540d\u5355\u8d26\u53f7");
                button10.addActionListener(e -> button10ActionPerformed(e));

                //---- button11 ----
                button11.setText("addtea");
                button11.setEnabled(false);
                button11.addActionListener(e -> button11ActionPerformed(e));

                //---- LoadStu ----
                LoadStu.setText("\u4ece\u670d\u52a1\u5668\u8bfb\u53d6");
                LoadStu.addActionListener(e -> LoadStuActionPerformed(e));

                //======== scrollPane9 ========
                {
                    scrollPane9.setViewportView(stunamelist);
                }

                //======== scrollPane10 ========
                {

                    //---- classlist ----
                    classlist.addListSelectionListener(e -> classlistValueChanged(e));
                    scrollPane10.setViewportView(classlist);
                }

                //======== scrollPane11 ========
                {
                    scrollPane11.setViewportView(listgcstu);
                }

                //---- addstudents ----
                addstudents.setText("\u5bfc\u5165\u5b66\u751f");
                addstudents.addActionListener(e -> addstudentsActionPerformed(e));

                //---- labelsid ----
                labelsid.setText("text");

                //---- saveClassAndStu ----
                saveClassAndStu.setText("\u4fdd\u5b58\u5b66\u751f\u548c\u73ed\u7ea7\u8bbe\u7f6e");
                saveClassAndStu.addActionListener(e -> saveClassAndStuActionPerformed(e));

                //---- checkupyear ----
                checkupyear.setText("\u5b66\u5e74\u5207\u6362\uff08\u539f\u73ed\u7ea7\u4f5c\u5e9f\uff09");

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addComponent(LoadStu)
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addComponent(button2)
                                            .addGap(5, 5, 5)
                                            .addComponent(label1)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(button3)
                                            .addGap(18, 18, 18)
                                            .addComponent(button9)
                                            .addGap(18, 18, 18)
                                            .addComponent(button10)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(button11)))
                                    .addContainerGap(318, Short.MAX_VALUE))
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                            .addComponent(saveClassAndStu, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
                                            .addGap(73, 73, 73))
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addGroup(panel1Layout.createParallelGroup()
                                                .addGroup(panel1Layout.createSequentialGroup()
                                                    .addComponent(addstudents)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(checkupyear))
                                                .addComponent(scrollPane10, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                    .addComponent(scrollPane11, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(labelsid, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                        .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                                    .addGap(41, 41, 41)
                                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                                    .addComponent(scrollPane9, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
                                    .addGap(15, 15, 15))))
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(button3)
                                    .addComponent(button2)
                                    .addComponent(label1))
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(button9)
                                        .addComponent(button10)
                                        .addComponent(button11))))
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(LoadStu)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addComponent(scrollPane9, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                                            .addContainerGap())
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addGroup(panel1Layout.createParallelGroup()
                                                .addComponent(scrollPane11, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(panel1Layout.createSequentialGroup()
                                                    .addComponent(scrollPane10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(addstudents)
                                                        .addComponent(checkupyear))))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                                            .addComponent(saveClassAndStu, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                            .addGap(32, 32, 32))))
                                .addGroup(GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                                    .addGap(78, 78, 78)
                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(labelsid)
                                    .addContainerGap())))
                );
            }
            tabbedPane1.addTab("\u4eba\u5458\u73ed\u7ea7", panel1);

            //======== panel2 ========
            {

                //======== scrollPane12 ========
                {
                    scrollPane12.setViewportView(listteanameForClass);
                }

                //---- LoadFromServerTea ----
                LoadFromServerTea.setText("\u4ece\u670d\u52a1\u5668\u8bfb\u53d6");
                LoadFromServerTea.addActionListener(e -> LoadFromServerTeaActionPerformed(e));

                //---- ImportFromFileTea ----
                ImportFromFileTea.setText("\u4ece\u6587\u4ef6\u5bfc\u5165");
                ImportFromFileTea.addActionListener(e -> ImportFromFileTeaActionPerformed(e));

                //---- btnSaveTeaclasstoServer ----
                btnSaveTeaclasstoServer.setText("\u4fdd\u5b58\u5230\u670d\u52a1\u5668\uff08\u5148\u64cd\u4f5c\u6570\u636e\u4e4b\u540e\uff09");
                btnSaveTeaclasstoServer.addActionListener(e -> btnSaveTeaclasstoServerActionPerformed(e));

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addGap(27, 27, 27)
                            .addGroup(panel2Layout.createParallelGroup()
                                .addComponent(btnSaveTeaclasstoServer)
                                .addComponent(scrollPane12, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addComponent(LoadFromServerTea, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(ImportFromFileTea, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(702, Short.MAX_VALUE))
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(LoadFromServerTea)
                                .addComponent(ImportFromFileTea))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane12, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSaveTeaclasstoServer)
                            .addContainerGap(17, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("\u6559\u5e08\u914d\u7f6e", panel2);

            //======== panel3 ========
            {
                panel3.addPropertyChangeListener(e -> panel3PropertyChange(e));

                //---- button1 ----
                button1.setText("\u52a0\u8f7d\u56fe\u7247");
                button1.addActionListener(e -> button1ActionPerformed(e));

                //======== scrollPane2 ========
                {

                    //---- list2 ----
                    list2.addListSelectionListener(e -> list2ValueChanged(e));
                    scrollPane2.setViewportView(list2);
                }

                //======== scrollPane3 ========
                {

                    //---- labelimage ----
                    labelimage.setFocusable(false);
                    scrollPane3.setViewportView(labelimage);
                }

                //======== scrollPane6 ========
                {
                    scrollPane6.setViewportView(listSub);
                }

                //======== scrollPane7 ========
                {
                    scrollPane7.setViewportView(listTeaName);
                }

                //======== scrollPane8 ========
                {
                    scrollPane8.setViewportView(txtPicMemo);
                }

                //---- button7 ----
                button7.setText("\u589e\u52a0\u5173\u8054");
                button7.addActionListener(e -> button7ActionPerformed(e));

                //---- button8 ----
                button8.setText("\u9009\u62e9\u672c\u5730\u76ee\u5f55");
                button8.addActionListener(e -> button8ActionPerformed(e));

                GroupLayout panel3Layout = new GroupLayout(panel3);
                panel3.setLayout(panel3Layout);
                panel3Layout.setHorizontalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addComponent(button1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(txtPicDateTime, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
                                    .addGap(132, 132, 132)
                                    .addComponent(button7, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                                .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel3Layout.createParallelGroup()
                                .addComponent(scrollPane7, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                .addComponent(scrollPane6, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                .addComponent(scrollPane8, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
                );
                panel3Layout.setVerticalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addGroup(panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPane7)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPane8, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel3Layout.createParallelGroup()
                                        .addGroup(panel3Layout.createSequentialGroup()
                                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(button1)
                                                .addComponent(button8))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE))
                                        .addGroup(panel3Layout.createSequentialGroup()
                                            .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                                            .addGap(16, 16, 16)
                                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(button7)
                                                .addComponent(txtPicDateTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("\u7167\u7247\u7f16\u8f91", panel3);

            //======== panel4 ========
            {
                panel4.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentShown(ComponentEvent e) {
                        panel4ComponentShown(e);
                    }
                });

                //---- button4 ----
                button4.setText("text");
                button4.addActionListener(e -> button4ActionPerformed(e));

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(list1);
                }

                GroupLayout panel4Layout = new GroupLayout(panel4);
                panel4.setLayout(panel4Layout);
                panel4Layout.setHorizontalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel4Layout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
                                .addComponent(button4, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(810, Short.MAX_VALUE))
                );
                panel4Layout.setVerticalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(button4)
                            .addGap(18, 18, 18)
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("\u884c\u4e3a\u5206\u6570", panel4);

            //======== panel5 ========
            {

                //---- button5 ----
                button5.setText("text");
                button5.addActionListener(e -> button5ActionPerformed(e));

                GroupLayout panel5Layout = new GroupLayout(panel5);
                panel5.setLayout(panel5Layout);
                panel5Layout.setHorizontalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addComponent(button5)
                            .addContainerGap(920, Short.MAX_VALUE))
                );
                panel5Layout.setVerticalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(button5)
                            .addContainerGap(552, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("\u56fe\u7247\u884c\u4e3a", panel5);

            //======== panel6 ========
            {

                //======== scrollPane4 ========
                {

                    //---- list3 ----
                    list3.addListSelectionListener(e -> list3ValueChanged(e));
                    scrollPane4.setViewportView(list3);
                }

                //---- button6 ----
                button6.setText("\u4ece\u670d\u52a1\u5668\u52a0\u8f7d");
                button6.addActionListener(e -> button6ActionPerformed(e));

                //---- addSubject ----
                addSubject.setText("\u589e\u52a0\u4e13\u9898");
                addSubject.addActionListener(e -> addSubjectActionPerformed(e));

                //---- modSub ----
                modSub.setText("\u4fee\u6539\u4e13\u9898");
                modSub.addActionListener(e -> modSubActionPerformed(e));

                //======== scrollPane5 ========
                {
                    scrollPane5.setViewportView(txtSubInfo);
                }

                GroupLayout panel6Layout = new GroupLayout(panel6);
                panel6.setLayout(panel6Layout);
                panel6Layout.setHorizontalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(panel6Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                .addComponent(button6, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                            .addGroup(panel6Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_id, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel6Layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addGroup(panel6Layout.createParallelGroup()
                                        .addGroup(panel6Layout.createSequentialGroup()
                                            .addGroup(panel6Layout.createParallelGroup()
                                                .addGroup(panel6Layout.createSequentialGroup()
                                                    .addComponent(addSubject, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(panel6Layout.createSequentialGroup()
                                                    .addComponent(txtSdate, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)))
                                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtEdate, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(panel6Layout.createSequentialGroup()
                                                    .addGap(1, 1, 1)
                                                    .addComponent(modSub, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addComponent(txtSubName, GroupLayout.Alignment.TRAILING)
                                        .addComponent(scrollPane5, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))))
                            .addGap(387, 387, 387))
                );
                panel6Layout.setVerticalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(panel6Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button6)
                                .addComponent(txtSubName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel6Layout.createParallelGroup()
                                .addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                                .addGroup(panel6Layout.createSequentialGroup()
                                    .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtEdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_id)
                                    .addGap(7, 7, 7)
                                    .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(addSubject)
                                        .addComponent(modSub))
                                    .addGap(0, 383, Short.MAX_VALUE)))
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("\u4e13\u9898\u7f16\u8f91", panel6);

            //======== panel7 ========
            {

                //---- out ----
                out.setText("\u5bfc\u51fa\u6570\u636e");
                out.addActionListener(e -> button12ActionPerformed(e));

                //---- tst ----
                tst.setText("text");
                tst.addActionListener(e -> tstActionPerformed(e));

                GroupLayout panel7Layout = new GroupLayout(panel7);
                panel7.setLayout(panel7Layout);
                panel7Layout.setHorizontalGroup(
                    panel7Layout.createParallelGroup()
                        .addGroup(panel7Layout.createSequentialGroup()
                            .addGap(51, 51, 51)
                            .addComponent(out)
                            .addGap(61, 61, 61)
                            .addComponent(tst)
                            .addContainerGap(741, Short.MAX_VALUE))
                );
                panel7Layout.setVerticalGroup(
                    panel7Layout.createParallelGroup()
                        .addGroup(panel7Layout.createSequentialGroup()
                            .addGap(39, 39, 39)
                            .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(out)
                                .addComponent(tst))
                            .addContainerGap(519, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("\u4f7f\u7528\u7edf\u8ba1", panel7);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane1)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton button2;
    private JButton button3;
    private JLabel label1;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton LoadStu;
    private JScrollPane scrollPane9;
    private JList stunamelist;
    private JScrollPane scrollPane10;
    private JList classlist;
    private JScrollPane scrollPane11;
    private JList listgcstu;
    private JButton addstudents;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel labelsid;
    private JButton saveClassAndStu;
    private JCheckBox checkupyear;
    private JPanel panel2;
    private JScrollPane scrollPane12;
    private JList listteanameForClass;
    private JButton LoadFromServerTea;
    private JButton ImportFromFileTea;
    private JButton btnSaveTeaclasstoServer;
    private JPanel panel3;
    private JButton button1;
    private JScrollPane scrollPane2;
    private JList list2;
    private JScrollPane scrollPane3;
    private JLabel labelimage;
    private JScrollPane scrollPane6;
    private JList listSub;
    private JScrollPane scrollPane7;
    private JList listTeaName;
    private JTextField txtPicDateTime;
    private JScrollPane scrollPane8;
    private JTextPane txtPicMemo;
    private JButton button7;
    private JButton button8;
    private JPanel panel4;
    private JButton button4;
    private JScrollPane scrollPane1;
    private JList list1;
    private JPanel panel5;
    private JButton button5;
    private JPanel panel6;
    private JScrollPane scrollPane4;
    private JList list3;
    private JButton button6;
    private JTextField txtSubName;
    private JTextField txtSdate;
    private JTextField txtEdate;
    private JButton addSubject;
    private JLabel txt_id;
    private JButton modSub;
    private JScrollPane scrollPane5;
    private JTextPane txtSubInfo;
    private JPanel panel7;
    private JButton out;
    private JButton tst;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

              SwingMain swingMain=  new SwingMain();
              swingMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                swingMain.setVisible(true);
            }

        });
    }
}

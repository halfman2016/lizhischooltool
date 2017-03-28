/*
 * Created by JFormDesigner on Mon Dec 26 10:38:31 CST 2016
 */

package main.view;

import javax.swing.event.*;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import main.Module.*;
import main.util.MDBTools;
import main.util.Utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
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
    ArrayList<Subject> subjects;
    ArrayList<Teacher> teachers;

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

                for (int i = 0; i < sheet.getColumns(); i++) {
                    Cell[] cells = sheet.getColumn(i);
                    GradeClass gradeClass = new GradeClass(cells[0].getContents());
                    java.util.List<Student> stus = new ArrayList<Student>();
                    System.out.print("cells has " + cells.length);

                    for (int j = 1; j < cells.length; j++) {
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
            icon=new ImageIcon("D:\\1newbote\\lizhi\\serverpic"+File.separator+listname.get(list2.getSelectedIndex())+".jpeg");
            labelimage.setIcon(icon);

        txtPicDateTime.setText(listname.get(list2.getSelectedIndex()).toString());



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
        }

        mdb.updateSubject(subject);    }

        private void tabbedPane1StateChanged(ChangeEvent e) {
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

            subname.add("无专题");
            for(Subject sub:subjects)
            {
                subname.add(sub.getSubjectName());
            }
            listSub.setListData(subname);



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
                SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd_HHmmssSSS");
                try {
                    photopic.setPhotodate(sdf.parse(txtPicDateTime.getText()));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                photopic.setPhotomemo(txtPicMemo.getText());

                mdb.addFakePhoto(photopic,txtPicDateTime.getText());


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




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        button2 = new JButton();
        button3 = new JButton();
        label1 = new JLabel();
        panel2 = new JPanel();
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
            tabbedPane1.addChangeListener(e -> tabbedPane1StateChanged(e));

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

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(button2)
                            .addGap(5, 5, 5)
                            .addComponent(label1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button3)
                            .addContainerGap(721, Short.MAX_VALUE))
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button3)
                                .addComponent(button2)
                                .addComponent(label1))
                            .addGap(0, 473, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("\u4eba\u5458\u73ed\u7ea7", panel1);

            //======== panel2 ========
            {

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGap(0, 1002, Short.MAX_VALUE)
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGap(0, 506, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("\u6559\u5e08\u914d\u7f6e", panel2);

            //======== panel3 ========
            {

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
                                    .addComponent(button7, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                                .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel3Layout.createParallelGroup()
                                .addComponent(scrollPane7, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                .addComponent(scrollPane6, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                .addComponent(scrollPane8, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
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
                                    .addComponent(scrollPane8, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel3Layout.createParallelGroup()
                                        .addGroup(panel3Layout.createSequentialGroup()
                                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(button1)
                                                .addComponent(button8))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                                        .addGroup(panel3Layout.createSequentialGroup()
                                            .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
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
                            .addContainerGap(822, Short.MAX_VALUE))
                );
                panel4Layout.setVerticalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(button4)
                            .addGap(18, 18, 18)
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
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
                            .addContainerGap(936, Short.MAX_VALUE))
                );
                panel5Layout.setVerticalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(button5)
                            .addContainerGap(467, Short.MAX_VALUE))
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
                                        .addComponent(scrollPane5, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))))
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
                                .addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
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
                                    .addGap(0, 290, Short.MAX_VALUE)))
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("\u4e13\u9898\u7f16\u8f91", panel6);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1)
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
    private JPanel panel2;
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

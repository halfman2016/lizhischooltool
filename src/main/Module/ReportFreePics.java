package main.Module;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import main.util.MDBTools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportFreePics {
    private List<String> titles=new ArrayList<>();
    private List<String> pssjs=new ArrayList<>();
    private List<String> psrs=new ArrayList<>();
    private List<String> memos=new ArrayList<>();
    private List<String> picnames=new ArrayList<>();
    private List<String> comments=new ArrayList<>();
    private int count;
    private int index;
    public ReportFreePics(List <Photopic> photopics) {
        titles.add("拍摄时间");
        titles.add("拍摄人");
        titles.add("拍摄说明");
        titles.add("拍摄文件名");
        titles.add("评论");
        //初始化
        String pssj;
        String psr;
        String subjectname;
        String memo;
        String picname;
        String comment;
        index=0;
        MDBTools mdb=new MDBTools();
        for(Photopic photopic:photopics)
        {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            pssj=sdf.format(photopic.getPhotodate());
            psr=photopic.getPhotoauthor();
            memo=photopic.getPhotomemo();
            picname=photopic.getPicname();
            comment="";
            if (photopic.getCommentList()!=null) {
                for (Comment comment1 : photopic.getCommentList()) {
                    comment = comment + comment1.getCommentpeoplename() + " :  " + comment1.getCommentbody() + "\n";

                }
            }
            pssjs.add(pssj);
            psrs.add(psr);
            memos.add(memo);
            picnames.add(picname);
            comments.add(comment);
            index=index+1;
        }


    }

    public void saveToFile()
    {
        //初始化之后调用
        File file=new File("自由照片导出.xls");

        try {
            WritableWorkbook workbook= Workbook.createWorkbook(file);
            WritableSheet sheet=workbook.createSheet("自由照片导出",0);

            int colcount=0;
            for(String title:titles)
            {
                jxl.write.Label label=new Label(colcount,0,title);
                sheet.addCell(label);
                colcount=colcount+1;
            }



            for (int i=0;i<index;i++)
            {
                jxl.write.Label label=new Label(0,i+1,pssjs.get(i));
                sheet.addCell(label);
                label=new Label(1,i+1,psrs.get(i));
                sheet.addCell(label);
                label=new Label(2,i+1,memos.get(i));
                sheet.addCell(label);
                label=new Label(3,i+1,picnames.get(i));
                sheet.addCell(label);
                label=new Label(4,i+1,comments.get(i));
                sheet.addCell(label);
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


}

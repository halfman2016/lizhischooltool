package main.Module;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportDayChecked {
    private List<String> titles=new ArrayList<>();
    private List<String> banjis=new ArrayList<>();
    private List<String> jcrqs=new ArrayList<>();
    private List<String> lbs=new ArrayList<>();
    private List<String> jcrs=new ArrayList<>();
    private List<String> stunames=new ArrayList<>();
    private List<String> stuacions=new ArrayList<>();
    private List<String> stuscores=new ArrayList<>();
    private int count;
    private int index;
    public ReportDayChecked(List <DayCheckRec> dayCheckRecs) {
        titles.add("班级");
        titles.add("检查日期");
        titles.add("类别");
        titles.add("检查人");
        titles.add("学生姓名");
        titles.add("学生行为");
        titles.add("学生分数");
        //初始化
        String gc;
        String jcrq;
        String lb;
        String jcr;
        String stuname;
        String stuaction;
        String stuscore;
        index=0;
        for(DayCheckRec dayCheckRec:dayCheckRecs)
        {
            gc=dayCheckRec.getGradeClass().getName();
            jcrq=dayCheckRec.getStrdate();
            lb=dayCheckRec.getTypename();
            jcr=dayCheckRec.getCheckedteachername();



            for(DayCommonAction dayCommonAction:dayCheckRec.getDayCommonActions())
            {

                banjis.add(gc);
                jcrqs.add(jcrq);
                lbs.add(lb);
                jcrs.add(jcr);

                stuscore=String.valueOf(dayCommonAction.getActionValue());
                stuaction=dayCommonAction.getActionName();
                stuname=dayCommonAction.getRelativeStus().get(0).getName();
                stuscores.add(stuscore);
                stuacions.add(stuaction);
                stunames.add(stuname);

                index=index+1;
          }
        }


    }

    public void saveToFile()
    {
        //初始化之后调用
        File file=new File("教师检查.xls");

        try {
            WritableWorkbook workbook= Workbook.createWorkbook(file);
            WritableSheet sheet=workbook.createSheet("检查清单",0);

            int colcount=0;
            for(String title:titles)
            {
                jxl.write.Label label=new Label(colcount,0,title);
                sheet.addCell(label);
                colcount=colcount+1;
            }



            for (int i=0;i<index;i++)
            {

              jxl.write.Label label=new Label(0,i+1,banjis.get(i));
              sheet.addCell(label);
                 label=new Label(1,i+1,jcrqs.get(i));
                sheet.addCell(label);
                 label=new Label(2,i+1,lbs.get(i));
                sheet.addCell(label);
                 label=new Label(3,i+1,jcrs.get(i));
                sheet.addCell(label);
                 label=new Label(4,i+1,stunames.get(i));
                sheet.addCell(label);
                 label=new Label(5,i+1,stuacions.get(i));
                sheet.addCell(label);
                label=new Label(6,i+1,stuscores.get(i));
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

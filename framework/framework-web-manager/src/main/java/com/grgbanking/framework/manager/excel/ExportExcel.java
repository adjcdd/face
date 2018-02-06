package com.grgbanking.framework.manager.excel;

import com.grgbanking.framework.domains.report.employee.monitoringEmployee.pojo.MonitoringEmployeePojo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangweihua on 2018/2/1.
 */
public class ExportExcel {


    public static void exportMonitoringEmployeeExcel(List<MonitoringEmployeePojo> monitoringEmployeePojoList, HttpServletRequest request, HttpServletResponse response)
    {
        try{
            HSSFWorkbook workbook=new HSSFWorkbook();
            CellRangeAddress cellRangeAddress= new CellRangeAddress(0,0,0,0);
            HSSFCellStyle headStyle=createCellStyle(workbook,(short)16);
            HSSFCellStyle colStyle=createCellStyle(workbook,(short)10);
            HSSFSheet sheet=workbook.createSheet("个人实时考勤报表");
            sheet.addMergedRegion(cellRangeAddress);
            //sheet.setDefaultColumnWidth(25);
            HSSFRow row=sheet.createRow(0);
            HSSFCell cell=row.createCell(0);
            cell.setCellStyle(headStyle);
            cell.setCellValue("个人实时考勤报表");
            HSSFRow row2=sheet.createRow(1);
            String[] titles={"姓名","部门","开始日期","结束日期","考勤日期","考勤结果","迟到次数","早退次数","正常次数","应出勤天数","实出勤天数","迟到时长","早退时长","上班无考勤次数","下班无考勤次数"};
            for(int i=0;i<titles.length;i++){
                HSSFCell cell2=row2.createCell(i);
                cell2.setCellStyle(colStyle);
                cell2.setCellValue(titles[i]);
            }
            if(monitoringEmployeePojoList!=null){
                for(int j=0;j<monitoringEmployeePojoList.size();j++){
                    HSSFRow row3=sheet.createRow(j+2);
                    HSSFCell cell1 = row3.createCell(0);
                    cell1.setCellValue(monitoringEmployeePojoList.get(j).getName());
                    HSSFCell cell2 = row3.createCell(1);
                    cell2.setCellValue(monitoringEmployeePojoList.get(j).getDept());
                    HSSFCell cell3 = row3.createCell(2);
                    cell3.setCellValue(monitoringEmployeePojoList.get(j).getStart_time());
                    HSSFCell cell4 = row3.createCell(3);
                    cell4.setCellValue(monitoringEmployeePojoList.get(j).getEnd_time());
                    HSSFCell cell5 = row3.createCell(4);
                    cell5.setCellValue(monitoringEmployeePojoList.get(j).getDaily_time());
                    HSSFCell cell6 = row3.createCell(5);
                    cell6.setCellValue(monitoringEmployeePojoList.get(j).getDaily_result());
                    HSSFCell cell7 = row3.createCell(6);
                    cell7.setCellValue(monitoringEmployeePojoList.get(j).getLater_times());
                    HSSFCell cell8 = row3.createCell(7);
                    cell8.setCellValue(monitoringEmployeePojoList.get(j).getEarly_times());
                    HSSFCell cell9 = row3.createCell(8);
                    cell9.setCellValue(monitoringEmployeePojoList.get(j).getNormal_times());
                    HSSFCell cell10 = row3.createCell(9);
                    cell10.setCellValue(monitoringEmployeePojoList.get(j).getAttendance_days());
                    HSSFCell cell11 = row3.createCell(10);
                    cell11.setCellValue(monitoringEmployeePojoList.get(j).getAttendanced_days());
                    HSSFCell cell12 = row3.createCell(11);
                    cell12.setCellValue(monitoringEmployeePojoList.get(j).getTotal_latertime());
                    HSSFCell cell13 = row3.createCell(12);
                    cell13.setCellValue(monitoringEmployeePojoList.get(j).getTotal_earlytime());
                    HSSFCell cell14 = row3.createCell(13);
                    cell14.setCellValue(monitoringEmployeePojoList.get(j).getNo_check_in());
                    HSSFCell cell15 = row3.createCell(14);
                    cell15.setCellValue(monitoringEmployeePojoList.get(j).getNo_check_out());
                }
                OutputStream output;
                try {
                    output=response.getOutputStream();
                    response.reset();
                    response.setHeader("Content-disposition", "attachment;filename=details.xls");
                    response.setContentType("application/msexcel");
                    workbook.write(output);
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // workbook.write(out);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //创建字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints(fontsize);
        //加载字体
        style.setFont(font);
        return style;
    }


    /**
     *   通用导出
     * @param headers
     * @param datalist
     * @param response
     */
    public void exportExcel(String title,String[] headers,List<?> datalist,HttpServletResponse response){
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        // 设置表格默认列宽度为12个字节
        sheet.setDefaultColumnWidth((short) 12);
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //遍历集合数据，产生数据行
        Iterator it = datalist.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Object t =  it.next();
            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    String textValue = null;
                    if(value!=null){
                            if (value instanceof Date)
                            {
                                Date date = (Date) value;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                textValue = sdf.format(date);
                            }
                            else
                            {
                                //其它数据类型都当作字符串简单处理
                                textValue = value.toString();
                            }
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        HSSFFont font3 = workbook.createFont();
                        font3.setColor(HSSFColor.BLACK.index);//定义Excel数据颜色
                        richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        //response.setContentType("application/octet-stream");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");// // 指定文件的保存类型。
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(title, "UTF-8")+".xls");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // response.setHeader("Content-disposition", "attachment;filename="+title+".xls");//默认Excel名称
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //显示的导出表的标题
    //导出表的列名
    /*public void export(String title,String[] rowName,List<Object[]>  dataList,HttpServletResponse  response) throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表

            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);

            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook);                    //单元格样式对象

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);                // 在索引2的位置创建行(最顶端的行开始的第二行)

            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){
                HSSFCell  cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
            }

            //将查询出的数据设置到sheet对应的单元格中
            for(int i=0;i<dataList.size();i++){

                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i+3);//创建所需的行数

                for(int j=0; j<obj.length; j++){
                    HSSFCell  cell = null;   //设置单元格的数据类型
                    if(j == 0){
                        cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i+1);
                    }else{
                        cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                        if(!"".equals(obj[j]) && obj[j] != null){
                            cell.setCellValue(obj[j].toString());                        //设置单元格的值
                        }
                    }
                    cell.setCellStyle(style);                                    //设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if(colNum == 0){
                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                }else{
                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                }
            }

            if(workbook !=null){
                try
                {
                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                    String headStr = "attachment; filename=\"" + fileName + "\"";
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    response.setHeader("Content-Disposition", headStr);
                    OutputStream out = response.getOutputStream();
                    workbook.write(out);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
*/
    /*
     * 列头单元格样式
     */
 /*   public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }*/

    /*
   * 列数据信息单元格样式
   */
   /* public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }*/
}

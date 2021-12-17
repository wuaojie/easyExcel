import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class TypeWriteHandler implements CellWriteHandler {


    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }
    public static String resolveExplicitConstraint(ExplicitConstraint explicitConstraint){
        return explicitConstraint.source();
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        // 区间设置 第一列第一行和第二行的数据。由于第一行是头，所以第一、二行的数据实际上是第二三行
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 1000, 0, 0);
        DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();
        DataValidationConstraint constraint = helper.createExplicitListConstraint(new String[] {"测试1", "测试2"});
        DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
        writeSheetHolder.getSheet().addValidationData(dataValidation);
        // 获取需要设置单元格类型的注解，并将FieldName和注解内容存入map中
        HashMap<Integer, String> typMap = new HashMap<>();
        Class clazz = writeSheetHolder.getClazz();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            //解析注解信息
            ExplicitConstraint explicitConstraint = field.getAnnotation(ExplicitConstraint.class);
            if(null != explicitConstraint){
                String type = resolveExplicitConstraint(explicitConstraint);
                String name = field.getName();
                typMap.put(i,type);
            }
        }
        typMap.forEach((k, v) -> {
            if(k==cell.getColumnIndex()){
                //设置单元格格式为注解内容
                Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
                CellStyle cellStyle = workbook.createCellStyle();
                DataFormat dataFormat = workbook.createDataFormat();
                cellStyle.setDataFormat(dataFormat.getFormat(v));
                cell.setCellStyle(cellStyle);
            }
        });
    }
}

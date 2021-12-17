import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

public class User {
    @ExcelProperty("名称")
    private String name;
    @ExcelProperty("电话号码")
    @ColumnWidth(50)
    @ExplicitConstraint(source = "0.00")
    private Double iphoneNo;
    @ExcelProperty("地址")
    private String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getIphoneNo() {
        return iphoneNo;
    }

    public void setIphoneNo(Double iphoneNo) {
        this.iphoneNo = iphoneNo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}

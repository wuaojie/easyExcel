import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;

public class easyExcelTest {
    /**
     * 最简单的写
     * <p>2. 直接写即可
     */
    public static void main(String[] args) {
        int arr[] = new int[]{1,2,3};
        // 写法1
        String fileName = "D:/" + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        ArrayList<User> objects = new ArrayList<>();
        User user = new User();
        user.setAddr("安徽省");
        user.setIphoneNo(18860434877d);
        user.setName("吴奥杰");
        User user1 = new User();
        user1.setAddr("浙江省");
        user1.setIphoneNo(123456789d);
        user1.setName("吴奥杰1");
        User user2 = new User();
        user2.setAddr("安徽省");
        user2.setIphoneNo(188244434877d);
        user2.setName("吴奥杰");
        objects.add(user);
        objects.add(user1);
        objects.add(user2);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        long start = System.currentTimeMillis();
        EasyExcel.write(fileName, User.class) .registerWriteHandler(new TypeWriteHandler())
              .sheet("模板").doWrite(objects);
        System.out.println(System.currentTimeMillis()-start);

    }
}

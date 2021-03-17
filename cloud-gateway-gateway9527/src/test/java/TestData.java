import org.junit.Test;

import java.time.ZonedDateTime;

public class TestData {

    /*-+
        断言获取 After 的时间
     */
    @Test
    public void test(){
        ZonedDateTime zonedDateTime =ZonedDateTime.now();
        System.out.println(zonedDateTime);
    }
}

import com.lanwei.haq.comm.jdbc.MyJedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @description：
 * @author：liuxinyun
 * @date：2017/12/19 22:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class RedisTest {

    @Resource
    private MyJedisService statisJedisService;

    @Test
    public void getKey(){
        String hget = statisJedisService.hget("web_news.163.com", "1501516800000");
        System.out.println(hget);
    }
}

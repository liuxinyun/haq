import com.alibaba.fastjson.JSONObject;
import com.lanwei.haq.comm.entity.ImgPath;
import com.lanwei.haq.comm.jdbc.MyJedisService;
import com.lanwei.haq.comm.thread.DownloadImage;
import com.lanwei.haq.comm.util.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @description：
 * @author：liuxinyun
 * @date：2017/12/19 22:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml",
    "classpath:spring-redis.xml"})
public class RedisTest {

    @Resource
    private MyJedisService statisJedisService;
    @Autowired
    private DownloadImage downloadImage;

    @Test
    public void getKey(){
        String hget = statisJedisService.hget("web_news.163.com", "1501516800000");
        System.out.println(hget);
    }

    @Test
    public void downloadImg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadImage.download();
            }
        }).start();
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ImgPath imgPath = new ImgPath("测试", "网址", "本地路径");
        statisJedisService.lpush(Constant.REDIS_IMG_INFO, JSONObject.toJSONString(imgPath));
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

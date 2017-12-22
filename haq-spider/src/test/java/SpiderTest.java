import com.lanwei.haq.comm.util.Constant;
import com.lanwei.haq.comm.util.DownPicUtil;
import com.lanwei.haq.comm.util.PropertiesUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * @作者：刘新运
 * @日期：2017/8/12 20:19
 * @描述：类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class SpiderTest {

    @Test
    public void spider(){
        try {
            Document document = Jsoup.connect("https://cn.nytimes.com/china/20170929/taiwan-autonomous-bus-test/")
                    .proxy(Constant.PROXY)
                    .ignoreContentType(false)//解析响应是忽略文档类型
                    .ignoreHttpErrors(false)  //响应时是否忽略错误，404等
                    .validateTLSCertificates(false)//关闭证书验证
                    .timeout(10000).get();
            System.out.println(document.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void regex(){
        String image1 = "<img alt=\"解放军军机5天内3次绕台 台媒:展现不容分裂决心\" src=\"http://cms-bucket.nosdn.127.net/4e9ecef273cd4496bd98ad08a09a27bf20170814091205.jpeg\" style=\"border-width: 0px; border-style: initial; vertical-align: middle; max-width: 640px; display: block; margin: 0px auto;\">";
        String image2 = "<img style=\"max-width:500px\" src=\"http://n.sinaimg.cn/translate/20170809/ibtW-fyitpmh5638890.jpg\" img-size=\"540,303\">";
        String html = image1+"<p>ahoaoiiuiu</p>"+image2;
        List<String> imageUrl = DownPicUtil.getImageUrl(html);
        for (String s : imageUrl) {
            System.out.println(s);
        }
        List<String> imageSrc = DownPicUtil.getImageSrc(imageUrl);
        for (String s : imageSrc) {
            System.out.println(s);
        }
    }

}

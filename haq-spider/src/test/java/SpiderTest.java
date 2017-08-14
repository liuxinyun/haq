import com.alibaba.fastjson.JSONObject;
import com.lanwei.haq.comm.entity.ImgPath;
import com.lanwei.haq.comm.util.DownPicUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @作者：刘新运
 * @日期：2017/8/12 20:19
 * @描述：类
 */
public class SpiderTest {

    @Test
    public void spider(){
        try {
            Document document = Jsoup.connect("http://sports.163.com/17/0812/20/CRLRLGSF00058780.html").timeout(3000).get();
            String content = document.select("div#endText").html();
            Map<String, String> map = DownPicUtil.htmlToFtp(content);
            //System.out.println(map.get("img"));
            //System.out.println(map.get("html"));
            String img_path = map.get("img");
            System.out.println(img_path);
            List<ImgPath> imgPaths = JSONObject.parseArray(img_path, ImgPath.class);
            for (ImgPath imgPath : imgPaths) {
                System.out.println(imgPath.toString());
            }

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

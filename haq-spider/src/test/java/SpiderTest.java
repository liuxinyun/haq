import com.alibaba.fastjson.JSONObject;
import com.lanwei.haq.comm.entity.ImgPath;
import com.lanwei.haq.comm.util.DownPicUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
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

}

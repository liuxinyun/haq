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
            System.out.println(map.get("img"));
            System.out.println(map.get("html"));
            /*List<String> imageUrl = DownPicUtil.getImageUrl(content);
            for (String s : imageUrl) {
                System.out.println(s);
            }
            List<String> imageSrc = DownPicUtil.getImageSrc(imageUrl);
            List<ImgPath> imgPaths = new ArrayList<>();
            for (String s : imageSrc) {
                System.out.println(s);
                String imageName = s.substring(s.lastIndexOf("/") + 1, s.length());
                System.out.println(imageName);
                ImgPath imgPath = new ImgPath();
                imgPath.setSource(s);
                imgPath.setLocal(imageName);
                imgPaths.add(imgPath);
            }
            String img_path = JSONObject.toJSONString(imgPaths);
            System.out.println(img_path);
            List<ImgPath> imgPath1 = JSONObject.parseArray(img_path, ImgPath.class);
            for (ImgPath imgPath : imgPath1) {
                System.out.println(imgPath.toString());
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

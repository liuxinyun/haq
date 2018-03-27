import com.lanwei.haq.comm.util.Constant;
import com.lanwei.haq.comm.util.PropertiesUtil;
import org.junit.Test;

/**
 * @作者：刘新运
 * @日期：2017/9/30 0:01
 * @描述：类
 */
public class PropertyTest extends BaseTest{

    @Test
    public void hostPort(){
        System.out.println(Constant.PROXY.toString());
        System.out.println(PropertiesUtil.get("proxy.host"));
        System.out.println(PropertiesUtil.getInt("proxy.port"));
    }

}

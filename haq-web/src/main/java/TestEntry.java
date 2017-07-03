import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @作者：刘新运
 * @日期：2017/6/25 17:39
 * @描述：类
 */

public class TestEntry {

    private static final String PRIVATE_KEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA6Jv7w+6HDFXLip+R0vpFUsrQXSBWWHnWU+bqxiqfY3DRsSAvVEtmDETHRLCalgmYj+5bU6EtJSkNWjYRoHH+8wIDAQABAkEA3/lR8tc6MiZqVpBPWVaiaZXgC8uXWJn4RdGTPjzMPHIPI3rEUcKplJgCBQbOeFmEAo6yO3u/c/0vB0+8Sh+AAQIhAP4l/3QlMJCspJ03ICGlEuDwIjA9ta5Qz56BhIrh9GaBAiEA6k3QV7FpyAmuF8LA8e8+ZCrizVDuHMDj75tstV+cc3MCIEo9P0nmMzGPvOZP3Ar39XPPsaz12cR5xqw+mUEYkBQBAiBZYc/t9C8/cXnpdJE4eiUZ/0ZAPvRnIaqSmH8y/cNoQwIhAOXTihgIigAMrrXFPiovVK7SIXSwnWgGlf8iyX3j0jDd";

    private static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOib+8PuhwxVy4qfkdL6RVLK0F0gVlh51lPm6sYqn2Nw0bEgL1RLZgxEx0SwmpYJmI/uW1OhLSUpDVo2EaBx/vMCAwEAAQ==";

    public static void main(String[] args) {
        ConfigTools ct = new ConfigTools();
        try {
            String c = ct.encrypt(PRIVATE_KEY,"root");
            System.out.println(c);
            String p = ct.decrypt(PUBLIC_KEY,c);
            System.out.println(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

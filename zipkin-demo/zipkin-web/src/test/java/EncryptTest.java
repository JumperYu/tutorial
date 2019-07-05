import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version: 1.0    2019/7/5
 */
public class EncryptTest {
    public static void main(String[] args) {
        System.out.println(RandomStringUtils.random(16, true, true).toUpperCase());
    }
}

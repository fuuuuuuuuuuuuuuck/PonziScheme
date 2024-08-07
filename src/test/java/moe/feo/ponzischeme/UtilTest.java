package moe.feo.ponzischeme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class UtilTest {

    private static final Logger logger = LogManager.getLogger(UtilTest.class.getSimpleName());

    @Test
    public void testConvertToMilliseconds() {
        Assert.assertEquals(3900000, Util.convertToMilliseconds("1h5m"));
        Assert.assertEquals(340000, Util.convertToMilliseconds("5m40s"));
        logger.info("时长转化测试完成.");
    }

}

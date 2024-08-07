package moe.feo.ponzischeme;

import cn.hutool.json.JSONUtil;
import moe.feo.ponzischeme.bilibili.BilibiliMember;
import moe.feo.ponzischeme.bilibili.BilibiliVideoStatus;
import moe.feo.ponzischeme.flarum.FlarumPost;
import moe.feo.ponzischeme.flarum.FlarumUser;
import org.junit.Assert;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrawlerTest {

    private static final Logger logger = LogManager.getLogger(CrawlerTest.class.getSimpleName());

    @Test
    public void testGetBilibiliMember() {
        int mid = 28853775;
        BilibiliMember member = Crawler.getBilibiliMember(mid);
        Assert.assertEquals(member.getMid(), mid);
        String result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(member));
        logger.info("B站用户信息获取测试中得到的信息: ");
        logger.info(result);
    }

    @Test
    public void testGetBilibiliVideoStatus() {
        String bvid = "BV1puvUeREap";
        int avid = -1392374345;
        BilibiliVideoStatus videoStatus = Crawler.getBilibiliVideoStatus(bvid);
        Assert.assertEquals(videoStatus.getAid(), avid);
        String result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(videoStatus));
        logger.info("B站视频信息获取测试中得到的信息: ");
        logger.info(result);
    }

    @Test
    public void getFlarumUserByUserId() {
        String flarum = "https://bbs.yinwurealm.org";
        int userId = 4;
        FlarumUser user = Crawler.getFlarumUserByUserId(flarum, userId);
        Assert.assertEquals(user.getId(), userId);
        String result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(user));
        logger.info("Flarum通过ID获取用户测试中得到的信息: ");
        logger.info(result);
    }

    @Test
    public void getFlarumUserByUsername() {
        String flarum = "https://bbs.yinwurealm.org";
        String username = "Fengshuai";
        FlarumUser user = Crawler.getFlarumUserByUsername(flarum, username);
        Assert.assertEquals(user.getAttributes().getSlug(), username);
        String result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(user));
        logger.info("Flarum通过用户名获取用户测试中得到的信息: ");
        logger.info(result);
    }

    @Test
    public void testGetFlarumActivateByUsername() {
        String flarum = "https://bbs.yinwurealm.org";
        String user = "Fengshuai";
        logger.info("Flarum活跃信息获取测试中得到的信息: ");
        for (FlarumPost flarumPost : Crawler.getFlarumActivateByUsername(flarum, user)) {
            logger.info(flarumPost.getId() + ": " + flarumPost.getAttributes().getCreatedAt());
        }
    }


}

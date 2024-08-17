package moe.feo.ponzischeme;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import moe.feo.ponzischeme.bilibili.BilibiliMember;
import moe.feo.ponzischeme.bilibili.BilibiliVideoStatus;
import moe.feo.ponzischeme.flarum.FlarumPost;
import moe.feo.ponzischeme.flarum.FlarumUser;

import java.util.ArrayList;
import java.util.HashMap;

public class Crawler {

    public static final String bilibiliMemberUrl = "https://api.bilibili.com/x/polymer/pc-electron/v1/user/cards";
    public static final String bilibiliVideoStatusUrl = "https://api.bilibili.com/x/web-interface/view";
    public static final String flarumUserUrl = "%flarum%/api/users/";
    public static final String flarumActivateUrl = "%flarum%/api/posts";

    public static BilibiliMember getBilibiliMember(int mid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("uids", mid);
        String content = HttpUtil.get(bilibiliMemberUrl, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(content);
        BilibiliMember member = jsonObject.getByPath("$.data." + mid, BilibiliMember.class);
        return member;
    }

    public static BilibiliVideoStatus getBilibiliVideoStatus(String bvid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("bvid", bvid);
        String content = HttpUtil.get(bilibiliVideoStatusUrl, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(content);
        BilibiliVideoStatus videoStatus = jsonObject.getByPath("$.data.stat", BilibiliVideoStatus.class);
        return videoStatus;
    }

    public static FlarumUser getFlarumUserByUsername(String website, String username) {
        String api = flarumUserUrl.replaceAll("%flarum%", website);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("bySlug", true);
        String content = HttpUtil.get(api + username, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(content);
        FlarumUser user = jsonObject.getByPath("data", FlarumUser.class);
        return user;
    }

    public static FlarumUser getFlarumUserByUserId(String website, int userId) {
        String api = flarumUserUrl.replaceAll("%flarum%", website);
        String content = HttpUtil.get(api + Integer.toString(userId));
        JSONObject jsonObject = JSONUtil.parseObj(content);
        FlarumUser user = jsonObject.getByPath("data", FlarumUser.class);
        return user;
    }

    public static ArrayList<FlarumPost> getFlarumActivateByUsername(String website, String username) {
        String api = flarumActivateUrl.replaceAll("%flarum%", website);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("filter[author]", username);
        paramMap.put("sort", "-createdAt");
        String content = HttpUtil.get(api, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(content);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        ArrayList activate = new ArrayList<FlarumPost>();
        for (int i = 0; i < jsonArray.size(); i++) {
            FlarumPost flarumPost = jsonArray.getJSONObject(i).toBean(FlarumPost.class);
            activate.add(flarumPost);
        }
        return activate;
    }

}

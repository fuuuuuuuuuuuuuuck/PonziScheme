package moe.feo.ponzischeme.bilibili;

public class BilibiliVideoStatus {

    private int aid; // 稿件avid
    private int view; // 正常：num; 屏蔽：str | 正常：播放次数屏蔽："--"
    private int danmaku; // 弹幕条数
    private int reply; // 评论条数
    private int favorite; // 收藏人数
    private int coin; // 投币枚数
    private int share; // 分享次数
    private int now_rank; // 0 作用尚不明确
    private int his_rank; // 历史最高排行
    private int like; // 获赞次数
    private int dislike; // 0 作用尚不明确
    private int no_reprint; // 禁止转载标志 | 0：无; 1：禁止
    private int copyright; // 版权标志 | 1：自制; 2：转载

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(int danmaku) {
        this.danmaku = danmaku;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getNow_rank() {
        return now_rank;
    }

    public void setNow_rank(int now_rank) {
        this.now_rank = now_rank;
    }

    public int getHis_rank() {
        return his_rank;
    }

    public void setHis_rank(int his_rank) {
        this.his_rank = his_rank;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getNo_reprint() {
        return no_reprint;
    }

    public void setNo_reprint(int no_reprint) {
        this.no_reprint = no_reprint;
    }

    public int getCopyright() {
        return copyright;
    }

    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }
}

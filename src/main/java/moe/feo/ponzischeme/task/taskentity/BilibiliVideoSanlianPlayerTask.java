package moe.feo.ponzischeme.task.taskentity;

import moe.feo.ponzischeme.task.TaskType;

public class BilibiliVideoSanlianPlayerTask extends BasePlayerTask {

    private String bvid;
    private int like;
    private int coin;
    private int favor;

    public BilibiliVideoSanlianPlayerTask() {
        super();
        super.setTaskType(TaskType.BILIBILI_VIDEO_SANLIAN);
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getFavor() {
        return favor;
    }

    public void setFavor(int favor) {
        this.favor = favor;
    }
}

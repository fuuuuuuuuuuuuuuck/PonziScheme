package moe.feo.ponzischeme.task.taskentity;

import moe.feo.ponzischeme.task.TaskType;

public class BilibiliVideoSanlianPlayerTask extends BasePlayerTask {

    private String bvid;
    private Integer like;
    private Integer coin;
    private Integer favor;

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

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getFavor() {
        return favor;
    }

    public void setFavor(Integer favor) {
        this.favor = favor;
    }
}

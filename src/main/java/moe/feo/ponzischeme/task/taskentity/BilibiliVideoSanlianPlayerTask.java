package moe.feo.ponzischeme.task.taskentity;

import moe.feo.ponzischeme.task.TaskType;

public class BilibiliVideoSanlianPlayerTask extends BasePlayerTask {

    private String bvid;

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
}

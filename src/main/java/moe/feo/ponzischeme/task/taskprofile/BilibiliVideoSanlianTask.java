package moe.feo.ponzischeme.task.taskprofile;

import moe.feo.ponzischeme.task.TaskType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BilibiliVideoSanlianTask extends BaseTask {

    private String bvid = null;
    private String timeLimit = null;
    private List<String> condition = null;

    public BilibiliVideoSanlianTask() {
        this.setTaskType(TaskType.BILIBILI_VIDEO_SANLIAN);
    }

    @Override
    public String getTaskType() {
        return super.getTaskType();
    }

    @Override
    public void setTaskType(String taskType) {
        super.setTaskType(taskType);
    }

    @Override
    public String getTaskName() {
        return super.getTaskName();
    }

    @Override
    public void setTaskName(String taskName) {
        super.setTaskName(taskName);
    }

    @Override
    public ItemStack getIcon() {
        return super.getIcon();
    }

    @Override
    public void setIcon(ItemStack icon) {
        super.setIcon(icon);
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public List<String> getCondition() {
        return condition;
    }

    public void setCondition(List<String> condition) {
        this.condition = condition;
    }
}

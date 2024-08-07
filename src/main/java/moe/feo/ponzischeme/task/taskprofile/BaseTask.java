package moe.feo.ponzischeme.task.taskprofile;

import moe.feo.ponzischeme.task.Rewards;
import org.bukkit.inventory.ItemStack;

public class BaseTask implements TaskImpl {

    private String taskType = null;
    private String taskName = null;
    private ItemStack icon = null;
    private Rewards rewards = null;

    @Override
    public String getTaskType() {
        return taskType;
    }

    @Override
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public ItemStack getIcon() {
        return icon;
    }

    @Override
    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    @Override
    public Rewards getRewards() {
        return rewards;
    }

    @Override
    public void setRewards(Rewards rewards) {
        this.rewards = rewards;
    }
}

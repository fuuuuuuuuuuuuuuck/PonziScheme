package moe.feo.ponzischeme.task.taskprofile;

import moe.feo.ponzischeme.task.Rewards;
import org.bukkit.inventory.ItemStack;

public interface TaskImpl {
    String getTaskId();
    void setTaskId(String taskId);
    String getTaskName();
    void setTaskName(String taskName);
    String getTaskType();
    void setTaskType(String taskType);
    ItemStack getIcon();
    void setIcon(ItemStack icon);
    Rewards getRewards();
    void setRewards(Rewards rewards);
}

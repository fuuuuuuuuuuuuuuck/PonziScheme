package moe.feo.ponzischeme.task.taskprofile;

import moe.feo.ponzischeme.task.Rewards;
import org.bukkit.inventory.ItemStack;

public interface TaskImpl {
    String getTaskType();
    void setTaskType(String taskType);
    String getTaskName();
    void setTaskName(String taskName);
    ItemStack getIcon();
    void setIcon(ItemStack icon);
    Rewards getRewards();
    void setRewards(Rewards rewards);
}

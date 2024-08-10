package moe.feo.ponzischeme.task.taskprofile;

import moe.feo.ponzischeme.task.TaskType;
import moe.feo.ponzischeme.task.taskentity.Condition;
import org.bukkit.inventory.ItemStack;

public class FlarumPostActivateTask extends BaseTask{

    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public FlarumPostActivateTask() {
        this.setTaskType(TaskType.FLARUM_POST_ACTIVATE);
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


}

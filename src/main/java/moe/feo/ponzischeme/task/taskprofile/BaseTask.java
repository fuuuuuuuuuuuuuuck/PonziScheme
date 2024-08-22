package moe.feo.ponzischeme.task.taskprofile;

import moe.feo.ponzischeme.task.Rewards;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BaseTask implements TaskImpl {

    private String taskId = null;
    private String taskName = null;
    private String taskType = null;
    private ItemStack icon = null;
    private Rewards rewards = null;

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
    public String getTaskType() {
        return taskType;
    }

    @Override
    public void setTaskType(String taskType) {
        this.taskType = taskType;
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

    @Override
    public void giveReward(Player player) {
        List<ItemStack> items = getRewards().getItems();
        HashMap<Integer,ItemStack> notAdded =player.getInventory().addItem(items.toArray(new ItemStack[0]));
        if (!notAdded.isEmpty()){
            for (Integer i:notAdded.keySet()){
                player.getWorld().dropItem(player.getLocation(),notAdded.get(i));    
            }
            
        }
        
        List<String> commands = getRewards().getCommands();
        for (String command : commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%PLAYER%", player.getName()));
        }
    }
}

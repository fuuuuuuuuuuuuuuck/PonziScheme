package moe.feo.ponzischeme.task;

import moe.feo.ponzischeme.config.ConfigUtil;
import moe.feo.ponzischeme.task.taskentity.Condition;
import moe.feo.ponzischeme.task.taskprofile.BaseTask;
import moe.feo.ponzischeme.task.taskprofile.BilibiliVideoSanlianTask;
import moe.feo.ponzischeme.task.taskprofile.FlarumPostActivateTask;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<BaseTask> tasks;
    private static TaskManager instance;

    public static synchronized TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public List<BaseTask> getTasks() {
        return tasks;
    }

    public void saveDefault() {
        ConfigUtil.saveDefault("tasks.yml");
    }

    public void load() {
        tasks = new ArrayList<>();
        YamlConfiguration taskFile = (YamlConfiguration) ConfigUtil.load("tasks.yml");
        for (String key : taskFile.getKeys(false)) {
            BaseTask task = null;
            String taskId = taskFile.getString(key);
            String taskName = taskFile.getString(key + ".name");
            String taskType = taskFile.getString(key + ".type");
            ItemStack icon = taskFile.getItemStack(key + ".icon");
            Rewards rewards = new Rewards();
            rewards.setItems(new ArrayList<>());
            ConfigurationSection rewardItemSection = taskFile.getConfigurationSection(key + ".reward.items");
            for (String itemKey : rewardItemSection.getKeys(false)) {
                rewards.getItems().add(taskFile.getItemStack(key + ".reward.items." + itemKey));
            }
            rewards.setCommands(taskFile.getStringList(key + ".reward.commands"));
            if (taskType.equals("flarum_post_activate")) {
                task = new FlarumPostActivateTask();
                Condition condition = new Condition();
                condition.setRepeat(taskFile.getString(key + ".condition.repeat"));
                condition.setCount(taskFile.getInt(key + ".condition.count"));
                ((FlarumPostActivateTask) task).setCondition(condition);
            } else if (taskType.equals("bilibili_video_sanlian")) {
                task = new BilibiliVideoSanlianTask();
                ((BilibiliVideoSanlianTask) task).setBvid(taskFile.getString(key + ".bvid"));
                ((BilibiliVideoSanlianTask) task).setTimeLimit(taskFile.getString(key + ".timeLimit"));
            }
            if (task != null) {
                task.setTaskId(taskId);
                task.setTaskName(taskName);
                task.setTaskType(taskType);
                task.setIcon(icon);
                task.setRewards(rewards);
                tasks.add(task);
            }
        }
    }
}

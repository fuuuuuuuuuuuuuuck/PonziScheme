package moe.feo.ponzischeme.task.taskentity;

import moe.feo.ponzischeme.Crawler;
import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.Util;
import moe.feo.ponzischeme.bilibili.BilibiliVideoStatus;
import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.sql.DatabaseManager;
import moe.feo.ponzischeme.task.TaskManager;
import moe.feo.ponzischeme.task.taskprofile.BilibiliVideoSanlianTask;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BilibiliSanlianTimer {

    public static List<BilibiliSanlianTimer> timers = new ArrayList<>();

    private BilibiliVideoSanlianPlayerTask task;
    private long time; // 时长
    private boolean cancel = false;

    private BilibiliSanlianTimer(BilibiliVideoSanlianPlayerTask task, long time) {
        this.task = task;
        this.time = time;
    }

    public static BilibiliSanlianTimer newTimer(BilibiliVideoSanlianPlayerTask task, long time) {
        BilibiliSanlianTimer timer = new BilibiliSanlianTimer(task, time);
        return timer;
    }

    public static void load() {
        List<BilibiliVideoSanlianPlayerTask> tasks = DatabaseManager.dao.loadBilibiliVideoSanlianTasks();
        if (tasks == null) {
            for (BilibiliVideoSanlianPlayerTask task : tasks) {
                if (!task.getTaskStatus().equals(PlayerTaskStatus.RUNNING)) { // 跳过已经结束的任务
                    continue;
                }
                if (find(task.getUuid(), task.getTaskId()) == null) { // 如果该任务未加载
                    BilibiliVideoSanlianTask taskProfile = (BilibiliVideoSanlianTask) TaskManager.getInstance().getTasks().get(task.getTaskId());
                    long timeLimit = Util.convertToMilliseconds(taskProfile.getTimeLimit());
                    long startTime = task.getTaskStartTime();
                    if (System.currentTimeMillis() > startTime + timeLimit) { // 任务已过期
                        task.setTaskStatus(PlayerTaskStatus.CANCELED);
                        task.setTaskEndTime(startTime + timeLimit);
                        DatabaseManager.dao.updateBilibiliVideoSanlianTask(task);
                    } else { // 未过期, 恢复任务, 并检测领取奖励条件
                        long newTimeLimit = (startTime + timeLimit) - System.currentTimeMillis();
                        BilibiliSanlianTimer timer = newTimer(task, task.getTaskEndTime() - System.currentTimeMillis());
                        timer = BilibiliSanlianTimer.newTimer(task, newTimeLimit);
                        timer.start();
                        timer.check();
                    }
                }
            }
        }
    }

    /**
     * 初始化任务, 涉及网络io, 当异步进行
     */
    public void initialize() {
        BilibiliVideoStatus status = Crawler.getBilibiliVideoStatus(task.getBvid());
        task.setLike(status.getLike());
        task.setCoin(status.getCoin());
        task.setFavor(status.getFavorite());
    }

    /*
     * 启动计时器, 将在指定时间后进行最后一次检查
     */
    public void start() {
        timers.add(this);
        BukkitRunnable runnableLater = new BukkitRunnable() {
            @Override
            public void run() {
                if (cancel) {
                    return;
                }
                check();
                // 最后一次检查时, 如果还未完成, 将任务标记取消
                if (task.getTaskStatus().equals(PlayerTaskStatus.RUNNING)) {
                    task.setTaskStatus(PlayerTaskStatus.CANCELED);
                    task.setTaskEndTime(System.currentTimeMillis());
                    DatabaseManager.dao.updateBilibiliVideoSanlianTask(task);
                    Player player = PonziScheme.getInstance().getServer().getPlayer(task.getUuid());
                    if (player != null && player.isOnline()) {
                        player.sendMessage(Language.PREFIX.getString() + Language.TASKCANCELED.getString());
                    }
                }
                // 最后一次检查时, 无论如何移除计时器
                cancel = true;
                timers.remove(BilibiliSanlianTimer.this);
            }
        };
        Util.runTaskLaterAsynchronously(runnableLater, time);
    }

    public void check() {
        Player player = PonziScheme.getInstance().getServer().getPlayer(task.getUuid());
        // 玩家不在线时跳过检查
        if (player == null || !player.isOnline()) {
            return;
        }
        if (!cancel) { // 已经取消的计时器不检查
            BilibiliVideoStatus status = Crawler.getBilibiliVideoStatus(task.getBvid());
            int likeNow = status.getLike();
            int coinNow = status.getCoin();
            int favorNow = status.getFavorite();
            if (likeNow > task.getLike() || coinNow > task.getCoin() || favorNow > task.getFavor()) {
                Runnable rewardRunnable = new Runnable() {
                    @Override
                    public void run() {
                        TaskManager.getInstance().getTasks().get(task.getTaskId()).giveReward(player);
                    }
                };
                Util.runTask(rewardRunnable, player);
                task.setTaskStatus(PlayerTaskStatus.COMPLETED);
                BilibiliVideoSanlianPlayerTask param = new BilibiliVideoSanlianPlayerTask();
                param.setUuid(task.getUuid());
                param.setTaskId(task.getTaskId());
                param.setTaskStatus(task.getTaskStatus());
                if (DatabaseManager.dao.getBilibiliVideoSanlianTask(param) != null) {
                    DatabaseManager.dao.updateBilibiliVideoSanlianTask(task);
                } else {
                    DatabaseManager.dao.addBilibiliVideoSanlianTask(task);
                }
                player.sendMessage(Language.PREFIX.getString() + Language.REWARDGIVED.getString());
            } else {
                player.sendMessage(Language.PREFIX.getString() + Language.TASKONGOING.getString());
            }
        }
    }

    public static BilibiliSanlianTimer find(String uuid, String taskId) {
        for (BilibiliSanlianTimer timer : timers) {
            if (timer.task.getUuid().equals(uuid) && timer.task.getTaskId().equals(taskId)) {
                return timer;
            }
        }
        return null;
    }
}

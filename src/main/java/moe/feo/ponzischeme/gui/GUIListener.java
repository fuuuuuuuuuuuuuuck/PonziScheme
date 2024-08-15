package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.Crawler;
import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.Util;
import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.flarum.FlarumPost;
import moe.feo.ponzischeme.player.BindListener;
import moe.feo.ponzischeme.player.PlayerProfile;
import moe.feo.ponzischeme.sql.DatabaseManager;
import moe.feo.ponzischeme.task.TaskManager;
import moe.feo.ponzischeme.task.taskentity.BilibiliSanlianTimer;
import moe.feo.ponzischeme.task.taskentity.BilibiliVideoSanlianPlayerTask;
import moe.feo.ponzischeme.task.taskentity.FlarumPostActivatePlayerTask;
import moe.feo.ponzischeme.task.taskentity.PlayerTaskStatus;
import moe.feo.ponzischeme.task.taskprofile.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GUIListener implements Listener {

    private static GUIListener instance;

    public static synchronized GUIListener getInstance() {
        if (instance == null)
            instance = new GUIListener();
        return instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;// 如果不是玩家操作的，返回
        Player player = (Player) event.getWhoClicked();
        InventoryHolder holder = event.getInventory().getHolder();
        //mainpage逻辑
        if (holder instanceof MainPage.MainPageGUIHolder) {
            MainPage page = ((MainPage.MainPageGUIHolder) holder).getPage();
            event.setCancelled(true);

            // B站账号信息
            if (event.getRawSlot() == 39) {
                player.closeInventory();
                UUID uid = player.getUniqueId();
                synchronized (BindListener.lock) { // 线程锁防止异步错位修改
                    BindListener rglistener = BindListener.map.get(uid);
                    // 如果这个玩家没有一个监听器
                    if (rglistener == null) {
                        new BindListener(player.getUniqueId(), BindListener.TYPE_BILIBILI).register();// 为此玩家创建一个监听器
                        String keywords = Arrays.toString(Config.CANCELKEYWORDS.getStringList().toArray());
                        player.sendMessage(Language.PREFIX.getString() + Language.GUI_BINDBILIBILI.getString().replaceAll("%KEYWORD%", keywords));
                    }
                }
            }
            // Flarum论坛账号信息
            if (event.getRawSlot() == 41){
                player.closeInventory();
                UUID uid = player.getUniqueId();
                synchronized (BindListener.lock) { // 线程锁防止异步错位修改
                    BindListener rglistener = BindListener.map.get(uid);
                    // 如果这个玩家没有一个监听器
                    if (rglistener == null) {
                        new BindListener(player.getUniqueId(), BindListener.TYPE_FLARUM).register();// 为此玩家创建一个监听器
                        String keywords = Arrays.toString(Config.CANCELKEYWORDS.getStringList().toArray());
                        player.sendMessage(Language.PREFIX.getString() + Language.GUI_BINDFLARUM.getString().replaceAll("%KEYWORD%", keywords));
                    }
                }
            }

            //任务
            if (((10 <= event.getRawSlot() && event.getRawSlot() <= 16) || (19 <= event.getRawSlot() && event.getRawSlot() <= 25)) && event.getCurrentItem() != null) {
                int index = page.calculateIndexFromSlot(event.getRawSlot());
                BaseTask task = TaskManager.getInstance().getTasks().get(index);
                if (event.isLeftClick()) {
                    taskClicked(player, task);
                } else if (event.isRightClick()) {
                    TaskPage taskPage = new TaskPage(task);
                    taskPage.openGui(player);
                }
            }
        }

        if (holder instanceof TaskPage.TaskPageGUIHolder){
            event.setCancelled(true);
            TaskPage.TaskPageGUIHolder taskPageGUIHolder = (TaskPage.TaskPageGUIHolder) holder;
            // 翻页
            if (event.getRawSlot() == 46) { // 上一页
                taskPageGUIHolder.getGui().prev();
            } else if (event.getRawSlot() == 50) { // 下一页
                taskPageGUIHolder.getGui().next();
            } else if (event.getRawSlot() == 53) { // 返回
                new MainPage().openGui(player);
            }

            if (event.getRawSlot() == 16 || event.getRawSlot() == 25 || event.getRawSlot() == 34 || event.getRawSlot() == 43){
                TaskImpl task = taskPageGUIHolder.getGui().getTask();
                taskClicked(player, task);
            }
        }
    }

    public void taskClicked(Player player, TaskImpl task) {
        BukkitRunnable runnable = new  BukkitRunnable(){
            @Override
            public void run() {
                PlayerProfile profile = DatabaseManager.dao.getPlayerProfile(player.getUniqueId().toString());
                if (task instanceof FlarumPostActivateTask) {
                    if (profile == null || profile.getFlarumId() == 0) {
                        player.sendMessage(Language.PREFIX.getString() + Language.NOTBOUNDFLARUM.getString());
                        return;
                    }
                    FlarumPostActivatePlayerTask param = new FlarumPostActivatePlayerTask();
                    param.setUuid(player.getUniqueId().toString());
                    param.setTaskId(task.getTaskId());
                    param.setTaskType(task.getTaskType());
                    param.setTaskStatus(PlayerTaskStatus.RUNNING);
                    FlarumPostActivatePlayerTask ongoing = DatabaseManager.dao.getFlarumPostActivateTask(param);
                    // 更新玩家Flarum用户名
                    PlayerProfile playerProfile = DatabaseManager.dao.getPlayerProfile(player.getUniqueId().toString());
                    String playerFlarumName = Crawler.getFlarumUserByUserId(Config.FLARUMURL.getString(), playerProfile.getFlarumId()).getAttributes().getSlug();
                    playerProfile.setFlarumName(playerFlarumName);
                    DatabaseManager.dao.updatePlayerProfile(playerProfile);
                    if (ongoing != null) { // 有正在进行的相同任务
                        List<FlarumPost> posts = Crawler.getFlarumActivateByUsername(Config.FLARUMURL.getString(), playerFlarumName);
                        if (posts.size() > 0) {
                            OffsetDateTime offsetDateTime = OffsetDateTime.parse(posts.getFirst().getAttributes().getCreatedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                            long lastPostTime = offsetDateTime.toInstant().toEpochMilli();
                            long startTime = 0; // 任务期限开始的时间
                            long endTime = 0;
                            switch (((FlarumPostActivateTask) task).getCondition().getRepeat()) {
                                case Condition.REPEAT_DAYS : {
                                    startTime = Util.getStartOfDay(ongoing.getTaskStartTime()); // 从今天开始的时间
                                    endTime = Util.getEndOfDay(ongoing.getTaskStartTime());
                                    break;
                                }
                                case Condition.REPEAT_WEEKS : {
                                    startTime = Util.getStartOfWeek(ongoing.getTaskStartTime()); // 从本周开始的时间
                                    endTime = Util.getEndOfWeek(ongoing.getTaskStartTime());
                                    break;
                                }
                            }
                            // 注意: 由于获取的帖子数量限制为20, 所以将不计算20次发帖之前的发帖
                            boolean isFinished = false;
                            for (FlarumPost post : posts) {
                                if (lastPostTime >= startTime && lastPostTime <= endTime) {
                                    isFinished = true;
                                }
                            }
                            if (isFinished) { // 玩家此次发帖时间大于任务重置时间, 发奖励
                                BukkitRunnable runnable = new  BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        task.giveReward(player);
                                    }
                                };
                                Util.runTask(runnable, player);
                                ongoing.setTaskStatus(PlayerTaskStatus.COMPLETED);
                                DatabaseManager.dao.updateFlarumPostActivateTask(ongoing);
                                player.sendMessage(Language.PREFIX.getString() + Language.REWARDGIVED.getString()
                                        .replaceAll("%TASK%", task.getTaskName()));
                                PonziScheme.getInstance().getServer().broadcastMessage(Language.REWARDBROADCAST.getString()
                                        .replaceAll("%PLAYER%", player.getName()).replaceAll("%TASK%", task.getTaskName()));
                            } else if (System.currentTimeMillis() > endTime) { // 任务已过期
                                ongoing.setTaskStatus(PlayerTaskStatus.CANCELED);
                                ongoing.setTaskEndTime(endTime);
                                DatabaseManager.dao.updateFlarumPostActivateTask(ongoing);
                                player.sendMessage(Language.PREFIX.getString() + Language.TASKCANCELED.getString());
                            } else { // 任务还未完成
                                player.sendMessage(Language.PREFIX.getString() + Language.TASKONGOING.getString());
                            }
                        }
                    } else { // 没有正在进行的相同任务
                        long resetTime = 0; // 任务重置的时间
                        switch (((FlarumPostActivateTask) task).getCondition().getRepeat()) {
                            case Condition.REPEAT_DAYS : {
                                resetTime = Util.getStartOfDay(System.currentTimeMillis()); // 从今天开始的时间
                                break;
                            }
                            case Condition.REPEAT_WEEKS : {
                                resetTime = Util.getStartOfWeek(System.currentTimeMillis()); // 从本周开始的时间
                                break;
                            }
                        }
                        param.setTaskStatus(null);
                        param.setTaskStartTime(resetTime);
                        FlarumPostActivatePlayerTask last = DatabaseManager.dao.getFlarumPostActivateTask(param);
                        if (last != null) { // 任务还在冷却中
                            player.sendMessage(Language.PREFIX.getString() + Language.TASKCOOLDOWN.getString()
                                    .replaceAll("%COOLDOWN%", Util.calculateTimeDifference(resetTime)));
                            return;
                        }
                        // 为玩家创建任务
                        FlarumPostActivatePlayerTask newTask = new FlarumPostActivatePlayerTask();
                        newTask.setUuid(player.getUniqueId().toString());
                        newTask.setTaskId(task.getTaskId());
                        newTask.setTaskType(task.getTaskType());
                        newTask.setTaskName(task.getTaskName());
                        newTask.setTaskStartTime(System.currentTimeMillis());
                        newTask.setTaskStatus(PlayerTaskStatus.RUNNING);
                        DatabaseManager.dao.addFlarumPostActivateTask(newTask);
                        player.sendMessage(Language.PREFIX.getString() + Language.TASKRECIVED.getString());
                    }
                } else if (task instanceof BilibiliVideoSanlianTask) {
                    if (profile == null || profile.getBilibiliId() == 0) {
                        player.sendMessage(Language.PREFIX.getString() + Language.NOTBOUNDBILIBILI.getString());
                        return;
                    }
                    BilibiliVideoSanlianPlayerTask param = new BilibiliVideoSanlianPlayerTask();
                    param.setUuid(player.getUniqueId().toString());
                    param.setTaskId(task.getTaskId());
                    param.setTaskType(task.getTaskType());
                    // 进行中的任务
                    param.setTaskStatus(PlayerTaskStatus.RUNNING);
                    BilibiliVideoSanlianPlayerTask ongoing = DatabaseManager.dao.getBilibiliVideoSanlianTask(param);
                    // 已完成的任务
                    param.setTaskStatus(PlayerTaskStatus.COMPLETED);
                    BilibiliVideoSanlianPlayerTask completed = DatabaseManager.dao.getBilibiliVideoSanlianTask(param);
                    // 恢复进行中的任务
                    BilibiliSanlianTimer timer = BilibiliSanlianTimer.find(player.getUniqueId().toString(), task.getTaskId());
                    if (ongoing != null) { // 有正在进行的相同任务
                        if (timer == null) { // 未完成且没加载任务
                            long timeLimit = Util.convertToMilliseconds(((BilibiliVideoSanlianTask) task).getTimeLimit());
                            long startTime = ongoing.getTaskStartTime();
                            if (System.currentTimeMillis() > startTime + timeLimit) { // 任务已过期
                                ongoing.setTaskStatus(PlayerTaskStatus.CANCELED);
                                ongoing.setTaskEndTime(startTime + timeLimit);
                                DatabaseManager.dao.updateBilibiliVideoSanlianTask(ongoing);
                            } else { // 未过期, 恢复任务, 并检测领取奖励条件
                                long newTimeLimit = (startTime + timeLimit) - System.currentTimeMillis();
                                timer = BilibiliSanlianTimer.newTimer(ongoing, newTimeLimit);
                                timer.start();
                                timer.check();
                            }
                        }
                    }
                    // 为玩家创建任务
                    if (timer == null) {
                        BilibiliVideoSanlianPlayerTask newTask = new BilibiliVideoSanlianPlayerTask();
                        newTask.setUuid(player.getUniqueId().toString());
                        newTask.setTaskId(task.getTaskId());
                        newTask.setTaskType(task.getTaskType());
                        newTask.setTaskName(task.getTaskName());
                        newTask.setTaskStartTime(System.currentTimeMillis());
                        newTask.setTaskStatus(PlayerTaskStatus.RUNNING);
                        newTask.setBvid(((BilibiliVideoSanlianTask) task).getBvid());
                        DatabaseManager.dao.addBilibiliVideoSanlianTask(newTask);
                        long timeLimit = Util.convertToMilliseconds(((BilibiliVideoSanlianTask) task).getTimeLimit());
                        timer = BilibiliSanlianTimer.newTimer(newTask, timeLimit);
                        timer.initialize();
                        timer.start();
                        player.sendMessage(Language.PREFIX.getString() + Language.TASKRECIVED.getString());
                    }
                }
            }
        };
        Util.runTaskAsynchronously(runnable, player);
    }
}

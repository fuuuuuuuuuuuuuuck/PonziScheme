package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.player.PlayerProfile;
import moe.feo.ponzischeme.sql.DatabaseManager;
import moe.feo.ponzischeme.task.TaskManager;
import moe.feo.ponzischeme.task.taskprofile.BaseTask;
import moe.feo.ponzischeme.task.taskprofile.BilibiliVideoSanlianTask;
import moe.feo.ponzischeme.task.taskprofile.FlarumPostActivateTask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

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
        PlayerProfile profile = DatabaseManager.dao.getPlayerProfile(player.getUniqueId().toString());
        //mainpage逻辑
        if (holder instanceof MainPage.MainPageGUIHolder) {
            MainPage page = ((MainPage.MainPageGUIHolder) holder).getPage();
            event.setCancelled(true);
            // TODO 主GUI点击相关逻辑

            // B站账号信息
            if (event.getRawSlot() == 39) {
                // TODO 绑定或重新绑定B站账号
            }
            // Flarum论坛账号信息
            if (event.getRawSlot() == 41){
                // TODO 绑定或重新绑定Flarum论坛账号
            }

            //任务
            if (((10 <= event.getRawSlot() && event.getRawSlot() <= 16) || (19 <= event.getRawSlot() && event.getRawSlot() <= 25)) && event.getCurrentItem() != null) {
                int index = page.calculateIndexFromSlot(event.getRawSlot());
                BaseTask task = TaskManager.getInstance().getTasks().get(index);
                if (event.isLeftClick()) {
                    if (task instanceof FlarumPostActivateTask) {
                        if (profile == null || profile.getFlarmumId() == 0) {
                            player.sendMessage(Language.PREFIX.getString() + Language.NOTBOUNDFLARUM.getString());
                            return;
                        }
                    } else if (task instanceof BilibiliVideoSanlianTask) {
                        if (profile == null || profile.getBilibiliId() == 0) {
                            player.sendMessage(Language.PREFIX.getString() + Language.NOTBOUNDBILIBILI.getString());
                            return;
                        }
                    }
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
                //TODO 点击领取奖励的逻辑
            }
        }
    }
}

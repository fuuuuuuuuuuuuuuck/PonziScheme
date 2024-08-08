package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.player.PlayerProfile;
import moe.feo.ponzischeme.sql.DatabaseManager;
import moe.feo.ponzischeme.task.taskprofile.BilibiliVideoSanlianTask;
import moe.feo.ponzischeme.task.taskprofile.FlarumPostActivateTask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;// 如果不是玩家操作的，返回
        Player player = (Player) event.getWhoClicked();
        InventoryHolder holder = player.getOpenInventory().getTopInventory().getHolder();
        PlayerProfile profile = DatabaseManager.dao.getPlayerProfile(player.getUniqueId().toString());
        //mainpage逻辑
        if (holder instanceof MainPage.MainPageGUIHolder) {
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

            //下界之星Flarum论坛账号任务
            if (event.getCurrentItem().getType()== Material.NETHER_STAR){
                if (profile == null || profile.getFlarmumId() == 0) {
                    player.sendMessage(Language.PREFIX.getString() + Language.NOTBOUNDFLARUM.getString());
                    return;
                }
                TaskPage taskPage = new TaskPage(new FlarumPostActivateTask());
                taskPage.openGui(player);
            }

            //书与笔 b站视频三连
            if (event.getCurrentItem().getType()== Material.WRITABLE_BOOK){
                if (profile == null || profile.getBilibiliId() == 0) {
                    player.sendMessage(Language.PREFIX.getString() + Language.NOTBOUNDBILIBILI.getString());
                    return;
                }
                TaskPage taskPage = new TaskPage(new BilibiliVideoSanlianTask());
                taskPage.openGui(player);
            }
        }

        if (holder instanceof TaskPage.TaskPageGUIHolder){
            TaskPage.TaskPageGUIHolder taskPageGUIHolder = (TaskPage.TaskPageGUIHolder) holder;
            event.setCancelled(true);
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

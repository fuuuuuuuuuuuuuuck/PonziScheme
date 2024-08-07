package moe.feo.ponzischeme.gui;

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
        if (holder instanceof MainPage.PonziSchemeGUIHolder) {
            event.setCancelled(true);
            // TODO GUI点击相关逻辑
        }
    }
}

package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.config.Language;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainPage {

    private Inventory inv;
    public static final Lock lock = new ReentrantLock();

    class MainPageGUIHolder implements InventoryHolder {// 定义一个Holder用于识别此插件的GUI
        @Override
        public Inventory getInventory() {
            return getGui();
        }
    }

    public Inventory getGui() {
        return inv;
    }

    public void setGui(Inventory inv) {
        this.inv = inv;
    }

    public void createGui(Player player) {
        lock.lock();
        try {
            InventoryHolder holder = new MainPageGUIHolder();
            this.setGui(Bukkit.createInventory(holder, InventoryType.CHEST, "Title"));
            for (int i = 0; i < inv.getSize(); i++) {// 设置边框
                // 中间掏空
                if (
                        (i > 9 && i < 17)
                                || (i > 18 && i < 26)
                                || (i > 36 && i < 44)
                                || (i == 46)
                                || (i == 52)
                ) {
                    continue;
                }
                inv.setItem(i, GuiUtil.getRandomPane());
            }
            ItemStack bilibili = new ItemStack(Material.TROPICAL_FISH, 1);
            bilibili.getItemMeta().setDisplayName(Language.GUI_BILIBILI.getString());
            inv.setItem(39, bilibili);
            ItemStack flarum = new ItemStack(Material.POISONOUS_POTATO, 1);
            flarum.getItemMeta().setDisplayName(Language.GUI_FLARUM.getString());
            inv.setItem(41, flarum);
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
            skullmeta.setDisplayName(Language.GUI_SKULL.getString());
            skullmeta.setOwningPlayer(player);
        } finally {
            lock.unlock();
        }
    }

    public void openGui(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    if (inv == null) {
                        createGui(player);
                    }
                    player.openInventory(inv);
                } finally {
                    lock.unlock();
                }
            }
        }.runTaskAsynchronously(PonziScheme.getInstance());
    }
}

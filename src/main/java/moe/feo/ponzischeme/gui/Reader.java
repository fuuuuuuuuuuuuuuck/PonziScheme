package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.config.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Reader implements Listener {

    private static Reader instance;
    private Inventory inventory;
    private Lock lock = new ReentrantLock();

    private Reader() {
    }

    public static synchronized Reader getInstance() {
        if (instance == null) {
            instance = new Reader();
        }
        return instance;
    }

    class ReaderInventoryHolder implements InventoryHolder {
        @Override
        public Inventory getInventory() {
            return inventory;
        }
    }

    public void saveDefault() {
        ConfigUtil.saveDefault("reader.yml");
    }

    /**
     * 保存 Inventory 中的所有物品到 YamlConfiguration 中
     */
    public void save() {
        if (inventory == null) {
            return;
        }
        YamlConfiguration config = new YamlConfiguration();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && !item.getType().isAir()) {
                config.set(String.valueOf(i), item);
            }
        }
        ConfigUtil.save(config, "reader.yml");
    }

    /**
     * 从 YamlConfiguration 中加载物品到 Inventory
     */
    @SuppressWarnings("deprecation")
    public void load() {
        inventory = Bukkit.createInventory(new ReaderInventoryHolder(), 54, "Reader");
        YamlConfiguration config = (YamlConfiguration) ConfigUtil.load("reader.yml");
        for (int i = 0; i < inventory.getSize(); i++) {
            Object item = config.get(String.valueOf(i));
            if (item != null) {
                inventory.setItem(i, (ItemStack) item);
            }
        }
    }

    /**
     * 为玩家打开 Inventory
     * @param player 要打开 Inventory 的玩家
     */
    public void openForPlayer(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    if (inventory == null) {
                        load();
                    }
                } finally {
                    lock.unlock();
                }
                player.openInventory(inventory);
            }
        }.runTaskAsynchronously(PonziScheme.getInstance());
    }

    /**
     * 当玩家关闭物品栏时进行处理
     * @param event InventoryCloseEvent事件，表示玩家关闭了物品栏
     */
    @EventHandler
    public void onPlayerClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        if (holder instanceof ReaderInventoryHolder) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        if (event.getViewers().size() <= 1) {
                            save();
                            inventory = null;
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            }.runTaskAsynchronously(PonziScheme.getInstance());
        }
    }
}

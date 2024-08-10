package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.Util;
import moe.feo.ponzischeme.task.TaskManager;
import moe.feo.ponzischeme.task.taskprofile.BaseTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainPage {

    private Inventory inv;
    private int currentPage = 0;
    public static final Lock lock = new ReentrantLock();

    class MainPageGUIHolder implements InventoryHolder {// 定义一个Holder用于识别此插件的GUI
        MainPage page;

        @Override
        public Inventory getInventory() {
            return getGui();
        }

        public MainPage getPage() {
            return page;
        }

        public void setPage(MainPage page) {
            this.page = page;
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
            MainPageGUIHolder holder = new MainPageGUIHolder();
            holder.setPage(this);
            this.setGui(Bukkit.createInventory(holder, 54, Language.GUI_TITLE.getString().replaceAll("%PREFIX%", Language.PREFIX.getString())));
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
            ItemMeta bilibiliMeta = bilibili.getItemMeta();
            bilibiliMeta.setDisplayName(Language.GUI_BILIBILI.getString());
            bilibili.setItemMeta(bilibiliMeta);
            inv.setItem(39, bilibili);
            ItemStack flarum = new ItemStack(Material.POISONOUS_POTATO, 1);
            ItemMeta flarumMeta = flarum.getItemMeta();
            flarumMeta.setDisplayName(Language.GUI_FLARUM.getString());
            flarum.setItemMeta(flarumMeta);
            inv.setItem(41, flarum);
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
            skullmeta.setDisplayName(Language.GUI_SKULL.getString().replaceAll("%PLAYER%", player.getName()));
            skullmeta.setOwningPlayer(player);
            skull.setItemMeta(skullmeta);
            inv.setItem(40, skull);
            ItemStack prev = GuiUtil.getPrevItem();
            inv.setItem(46, prev);
            ItemStack next = GuiUtil.getNextItem();
            inv.setItem(52, next);
            List<ItemStack> icons = getTaskIcons();
            fillRewardSlot(icons);
        } finally {
            lock.unlock();
        }
    }

    public void openGui(Player player) {
        Runnable runnable = new Runnable() {
            @Override

            public void run() {
                lock.lock();
                try {
                    if (inv == null) {
                        createGui(player);
                    }
                    Runnable runnableOpen = new Runnable() {
                        @Override
                        public void run() {
                            player.openInventory(inv);
                        }
                    };
                    Util.runTask(runnableOpen, player);
                } finally {
                    lock.unlock();
                }
            }
        };
        Util.runTaskAsynchronously(runnable, player);
    }

    /**
     * 获取所有任务的图标
     */
    private List<ItemStack> getTaskIcons() {
        List<BaseTask> tasks = TaskManager.getInstance().getTasks();
        List<ItemStack> icons = new ArrayList<>();
        for (BaseTask task : tasks) {
            ItemMeta meta = task.getIcon().getItemMeta();
            meta.setDisplayName(task.getTaskName());
            task.getIcon().setItemMeta(meta);
            icons.add(task.getIcon());
        }
        return icons;
    }

    /**
     * 从GUI的槽位和当前页码反向计算物品在列表中的原始索引
     *
     * @param slot  GUI中的槽位
     * @return 物品在列表中的原始索引
     */
    public int calculateIndexFromSlot(int slot) {
        final int ITEMS_PER_PAGE = 14; // 每页14个物品
        int start = currentPage * ITEMS_PER_PAGE;
        int index = -1; // 默认值，表示未找到对应的物品索引

        // 反向查找物品的原始索引
        if (slot >= 10 && slot <= 16) { // 第一行
            index = (slot - 10) + (0 * 7);
        } else if (slot >= 19 && slot <= 25) { // 第二行
            index = (slot - 19) + (1 * 7);
        }

        // 如果找到了有效的索引，则加上起始位置
        if (index != -1) {
            index += start;
        }

        return index;
    }

    /**
     * 填充物品
     *
     * @param items  物品
     */
    private void fillRewardSlot(List<ItemStack> items) {
        final int ITEMS_PER_PAGE = 14;
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, items.size());
        for (int i = start; i < end; i++) {
            ItemStack item = items.get(i);
            int slot = calculateSlot(i - start);
            inv.setItem(slot, item);
        }
    }

    /**
     * 计算物品在gui中显示的格子
     *
     * @param index 物品索引
     * @return 格子
     */
    private int calculateSlot(int index) {
        int row = index / 7;
        int col = index % 7;

        if (row == 0) {
            return col + 10;
        } else if (row == 1) {
            return col + 19;
        } else {
            return -1; // 不应该到达这里
        }
    }
}

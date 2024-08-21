package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.Util;
import moe.feo.ponzischeme.player.PlayerProfile;
import moe.feo.ponzischeme.sql.DatabaseManager;
import moe.feo.ponzischeme.task.TaskManager;
import moe.feo.ponzischeme.task.TaskType;
import moe.feo.ponzischeme.task.taskentity.BilibiliVideoSanlianPlayerTask;
import moe.feo.ponzischeme.task.taskentity.FlarumPostActivatePlayerTask;
import moe.feo.ponzischeme.task.taskentity.PlayerTaskStatus;
import moe.feo.ponzischeme.task.taskprofile.BaseTask;
import moe.feo.ponzischeme.task.taskprofile.BilibiliVideoSanlianTask;
import moe.feo.ponzischeme.task.taskprofile.FlarumCondition;
import moe.feo.ponzischeme.task.taskprofile.FlarumPostActivateTask;
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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainPage {

    private UUID uuid;
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
        uuid = player.getUniqueId();
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
            PlayerProfile profile = DatabaseManager.getDao().getPlayerProfile(player.getUniqueId().toString());
            if (profile == null) {
                profile = new PlayerProfile();
            }
            // Flarum
            ItemStack flarum = new ItemStack(Material.POISONOUS_POTATO, 1);
            ItemMeta flarumMeta = flarum.getItemMeta();
            flarumMeta.setDisplayName(Language.GUI_FLARUM.getString());
            List<String> flarumLore = flarumMeta.getLore();
            if (flarumLore == null) {// 防止下面的语句报NPE
                flarumLore = new ArrayList<>();
            }
            flarumLore.addAll(Language.GUI_FLARUMLORE.getStringList());
            List<String> flarumLoreNew = new ArrayList<>();
            for (String lore : flarumLore) {
                flarumLoreNew.add(lore.replaceAll("%ID%", String.valueOf(profile.getFlarumId()))
                        .replaceAll("%USERNAME%", String.valueOf(profile.getFlarumName())));
            }
            flarumLoreNew.add(Language.GUI_CLICKBIND.getString());
            flarumMeta.setLore(flarumLoreNew);
            flarum.setItemMeta(flarumMeta);
            inv.setItem(39, flarum);
            // Bilibili
            ItemStack bilibili = new ItemStack(Material.TROPICAL_FISH, 1);
            ItemMeta bilibiliMeta = bilibili.getItemMeta();
            bilibiliMeta.setDisplayName(Language.GUI_BILIBILI.getString());
            List<String> bilibiliLore = bilibiliMeta.getLore();
            if (bilibiliLore == null) {
                bilibiliLore = new ArrayList<>();
            }
            bilibiliLore.addAll(Language.GUI_BILIBILILORE.getStringList());
            List<String> bilibiliLoreNew = new ArrayList<>();
            for (String lore : bilibiliLore) {
                bilibiliLoreNew.add(lore.replaceAll("%ID%", String.valueOf(profile.getBilibiliId()))
                        .replaceAll("%USERNAME%", String.valueOf(profile.getBilibiliName())));
            }
            bilibiliLoreNew.add(Language.GUI_CLICKBIND.getString());
            bilibiliMeta.setLore(bilibiliLoreNew);
            bilibili.setItemMeta(bilibiliMeta);
            // 头颅
            inv.setItem(41, bilibili);
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
            skullmeta.setDisplayName(Language.GUI_SKULL.getString().replaceAll("%PLAYER%", player.getName()));
            skullmeta.setOwningPlayer(player);
            List<String> skullLore = skullmeta.getLore();
            // 绑定状态
            String flarumStatus = Language.GUI_SKULLUNBINDED.getString();
            String bilibiliStatus = Language.GUI_SKULLUNBINDED.getString();
            if (profile.getFlarumId() != null) {
                flarumStatus = Language.GUI_SKULLBINDED.getString();
            }
            if (profile.getBilibiliId() != null) {
                bilibiliStatus = Language.GUI_SKULLBINDED.getString();
            }
            if (skullLore == null) {
                skullLore = new ArrayList<>();
            }
            skullLore.addAll(Language.GUI_SKULLLORE.getStringList());
            List<String> skullLoreNew = new ArrayList<>();
            for (String lore : skullLore) {
                skullLoreNew.add(lore.replaceAll("%FLARUMSTATUS%", flarumStatus).replaceAll("%BILIBILISTATUS%", bilibiliStatus));
            }
            skullmeta.setLore(skullLoreNew);
            skull.setItemMeta(skullmeta);
            inv.setItem(40, skull);
            ItemStack prev = GuiUtil.getPrevItem();
            inv.setItem(46, prev);
            ItemStack next = GuiUtil.getNextItem();
            inv.setItem(52, next);
            updateGui();
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
        Player player = PonziScheme.getInstance().getServer().getPlayer(uuid);
        Map<String, BaseTask> tasks = TaskManager.getInstance().getTasks();
        List<ItemStack> icons = new ArrayList<>();
        for (Map.Entry<String, BaseTask> entry : tasks.entrySet()) {
            BaseTask task = entry.getValue();
            ItemStack taskIcon = task.getIcon().clone();
            ItemMeta meta = taskIcon.getItemMeta();
            meta.setDisplayName(task.getTaskName());
            List<String> lores = meta.getLore();
            if (lores == null) {
                lores = new ArrayList<>();
            }
            List<String> loresNew = new ArrayList<>();
            if (task instanceof FlarumPostActivateTask) {
                lores.addAll(Language.GUI_TASKLOREFLARUM.getStringList());
                for (String lore : lores) {
                    loresNew.add(lore.replaceAll("%URL%", Config.FLARUMURL.getString())
                            .replaceAll("%REPEAT%", ((FlarumPostActivateTask) task).getCondition().getRepeat()));
                }
            }
            if (task instanceof BilibiliVideoSanlianTask) {
                lores.addAll(Language.GUI_TASKLOREBILIBILI.getStringList());
                for (String lore : lores) {
                    loresNew.add(lore.replaceAll("%BVID%", ((BilibiliVideoSanlianTask) task).getBvid()));
                }
            }
            // 检查任务状态
            if (task.getTaskType().equals(TaskType.BILIBILI_VIDEO_SANLIAN)) {
                BilibiliVideoSanlianPlayerTask param = new BilibiliVideoSanlianPlayerTask();
                param.setUuid(player.getUniqueId().toString());
                param.setTaskId(task.getTaskId());
                param.setTaskType(task.getTaskType());
                // 已完成的任务
                param.setTaskStatus(PlayerTaskStatus.COMPLETED);
                BilibiliVideoSanlianPlayerTask completed = DatabaseManager.getDao().getBilibiliVideoSanlianTask(param);
                // 进行中的任务
                param.setTaskStatus(PlayerTaskStatus.RUNNING);
                BilibiliVideoSanlianPlayerTask ongoing = DatabaseManager.getDao().getBilibiliVideoSanlianTask(param);
                if (ongoing != null) {// 正在进行的任务
                    loresNew.add(Language.GUI_TASKSTATUS.getString().replaceAll("%STATUS%", Language.GUI_STATUSRUNNING.getString()));
                } else if (completed != null) {// B站三连任务只能接取一次
                    loresNew.add(Language.GUI_TASKSTATUS.getString().replaceAll("%STATUS%", Language.GUI_STATUSCOMPLETED.getString()));
                } else {
                    loresNew.add(Language.GUI_TASKSTATUS.getString().replaceAll("%STATUS%", Language.GUI_STATUSAVAILABLE.getString()));
                }
            } else if (task.getTaskType().equals(TaskType.FLARUM_POST_ACTIVATE)) {
                FlarumPostActivatePlayerTask param = new FlarumPostActivatePlayerTask();
                param.setUuid(player.getUniqueId().toString());
                param.setTaskId(task.getTaskId());
                param.setTaskType(task.getTaskType());
                param.setTaskStatus(PlayerTaskStatus.COMPLETED);
                FlarumPostActivatePlayerTask completed = DatabaseManager.getDao().getFlarumPostActivateTask(param);
                // 进行中的任务
                param.setTaskStatus(PlayerTaskStatus.RUNNING);
                FlarumPostActivatePlayerTask ongoing = DatabaseManager.getDao().getFlarumPostActivateTask(param);
                if (ongoing != null) {// 正在进行的任务
                    loresNew.add(Language.GUI_TASKSTATUS.getString().replaceAll("%STATUS%", Language.GUI_STATUSRUNNING.getString()));
                } else {// 没有正在进行的任务
                    long resetTime = 0; // 任务重置的时间
                    switch (((FlarumPostActivateTask) task).getCondition().getRepeat()) {
                        case FlarumCondition.REPEAT_DAYS : {
                            resetTime = Util.getStartOfDay(System.currentTimeMillis()); // 从今天开始的时间
                            break;
                        }
                        case FlarumCondition.REPEAT_WEEKS : {
                            resetTime = Util.getStartOfWeek(System.currentTimeMillis()); // 从本周开始的时间
                            break;
                        }
                    }
                    param.setTaskStatus(null);
                    param.setTaskStartTime(resetTime);
                    FlarumPostActivatePlayerTask last = DatabaseManager.getDao().getFlarumPostActivateTask(param);
                    if (last != null) { // 任务还在冷却中
                        loresNew.add(Language.GUI_TASKSTATUS.getString().replaceAll("%STATUS%", Language.GUI_STATUSCOOLDOWN.getString()));
                    } else {
                        loresNew.add(Language.GUI_TASKSTATUS.getString().replaceAll("%STATUS%", Language.GUI_STATUSAVAILABLE.getString()));
                    }
                }
            }
            loresNew.add(Language.GUI_LEFTCLICK.getString());
            loresNew.add(Language.GUI_RIGHTCLICK.getString());
            meta.setLore(loresNew);
            taskIcon.setItemMeta(meta);
            icons.add(taskIcon);
        }
        return icons;
    }

    public void updateGui(){
        lock.lock();
        try {
            List<ItemStack> icons = getTaskIcons();
            fillRewardSlot(icons);
        } finally {
            lock.unlock();
        }
    }

    public void prev() {
        if (currentPage > 0) {
            currentPage--;
            updateGui();
        }
    }

    public void next() {
        int max = (int) Math.floor(getTaskIcons().size() / 14.0);
        if (currentPage < max) {
            currentPage++;
            updateGui();
        }
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

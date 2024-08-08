package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.Crawler;
import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.bilibili.BilibiliVideoStatus;
import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.flarum.FlarumPost;
import moe.feo.ponzischeme.sql.DatabaseManager;
import moe.feo.ponzischeme.task.TaskType;
import moe.feo.ponzischeme.task.taskprofile.BilibiliVideoSanlianTask;
import moe.feo.ponzischeme.task.taskprofile.FlarumPostActivateTask;
import moe.feo.ponzischeme.task.taskprofile.TaskImpl;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskPage {

    private Inventory inv;
    private TaskImpl task;
    private TaskPage page;
    private int currentPage = 0;

    public static final Lock lock = new ReentrantLock();

    TaskPage(TaskImpl task) {
        this.task = task;
        this.page = this;
    }

    class TaskPageGUIHolder implements InventoryHolder {// 定义一个Holder用于识别此插件的GUI
        @Override
        public Inventory getInventory() {
            return inv;
        }

        public TaskPage getGui() {
            return page;
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
            InventoryHolder holder = new TaskPageGUIHolder();
            this.setGui(Bukkit.createInventory(holder, 54, Language.GUI_TITLE.getString())); // 54个格子
            for (int i = 0; i < inv.getSize(); i++) {
                if (
                        (i > 0 && i < 15)
                                || (i > 18 && i < 24)
                                || (i > 27 && i < 33)
                                || (i > 36 && i < 42)
                                || i == 16 || i == 25 || i == 34 || i == 43
                                || i == 46 || i == 50 || i == 53
                ) {
                    continue;
                }
                inv.setItem(i, GuiUtil.getRandomPane());
                // 设置边框
            }
            inv.setItem(46, GuiUtil.getPrevItem());
            inv.setItem(50, GuiUtil.getNextItem());
            inv.setItem(53, GuiUtil.getBackItem());
            // B站视频三连
            if (task.getTaskType().equals(TaskType.BILIBILI_VIDEO_SANLIAN)) {
                BilibiliVideoSanlianTask bilibiliVideoSanlianTask = (BilibiliVideoSanlianTask) task;
                String bvid = bilibiliVideoSanlianTask.getBvid();
                BilibiliVideoStatus status = Crawler.getBilibiliVideoStatus(bvid);

                // BiliBili 视频详情
                ItemStack video = new ItemStack(Material.TARGET, 1);
                ItemMeta videoMeta = video.getItemMeta();
                videoMeta.setDisplayName(Language.GUI_BILIBILI_VIDEO.getString());
                ArrayList<String> videoLore = new ArrayList<>();
                videoLore.add(Language.GUI_BILIBILI_VIDEO_LORE.getString().replace("%bvid%", bvid));
                videoLore.add(Language.GUI_CLAIMREWARD.getString());
                // TODO 判断玩家是否领取该任务和任务状态
                videoMeta.setLore(Arrays.asList(Language.GUI_BILIBILI_VIDEO_LORE.getString()
                        .replace("%bvid%", bvid)));
                video.setItemMeta(videoMeta);
                inv.setItem(16, video);

                // BiliBili 点赞
                ItemStack like = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1);
                ItemMeta likeMeta = like.getItemMeta();
                likeMeta.setDisplayName(Language.GUI_BILIBILI_LIKE.getString());
                likeMeta.setLore(Arrays.asList(Language.GUI_BILIBILI_LIKE_LORE.getString()
                        .replace("%like%", String.valueOf(status.getLike()))));
                like.setItemMeta(likeMeta);
                inv.setItem(25, like);

                // BiliBili 投币
                ItemStack coin = new ItemStack(Material.SUNFLOWER, 1);
                ItemMeta coinMeta = coin.getItemMeta();
                coinMeta.setDisplayName(Language.GUI_BILIBILI_COIN.getString());
                coinMeta.setLore(Arrays.asList(Language.GUI_BILIBILI_COIN_LORE.getString()
                        .replace("%coin%", String.valueOf(status.getCoin()))));
                coin.setItemMeta(coinMeta);
                inv.setItem(34, coin);

                // BiliBili 收藏
                ItemStack chest = new ItemStack(Material.CHEST_MINECART, 1);
                ItemMeta chestMeta = chest.getItemMeta();
                chestMeta.setDisplayName(Language.GUI_BILIBILI_LIKE.getString());
                chestMeta.setLore(Arrays.asList(Language.GUI_BILIBILI_LIKE_LORE.getString()
                        .replace("%favor%", String.valueOf(status.getFavorite()))));
                chest.setItemMeta(chestMeta);
                inv.setItem(43, chest);
            }
            // Flarum论坛活跃
            else if (task.getTaskType().equals(TaskType.FLARUM_POST_ACTIVATE)) {
                // 论坛数据
                FlarumPostActivateTask flarumPostActivateTask = (FlarumPostActivateTask) task;
                int flarumUserId = DatabaseManager.dao.getPlayerProfile(player.getUniqueId().toString()).getFlarmumId();
                String flarumUserName = Crawler.getFlarumUserByUserId(Config.DATABASE_URL.getString(), flarumUserId).getAttributes().getSlug();
                ArrayList<FlarumPost> flarumPosts = Crawler.getFlarumActivateByUsername(Config.DATABASE_URL.getString(), flarumUserName);
                String lastPostTime = "-";
                if (flarumPosts.size() != 0) {
                    lastPostTime = flarumPosts.get(0).getAttributes().getCreatedAt();
                }
                ItemStack flarum = new ItemStack(Material.TARGET, 1);
                ItemMeta flarumMeta = flarum.getItemMeta();
                flarumMeta.setDisplayName(Language.GUI_FLARUMSITE_LORE.getString());
                ArrayList<String> siteLore = new ArrayList<>();
                siteLore.add(Language.GUI_FLARUMSITE_LORE.getString().replace("%time%", lastPostTime));
                siteLore.add(Language.GUI_CLAIMREWARD.getString());
                // TODO 判断玩家是否领取该任务和任务状态
                flarumMeta.setLore(siteLore);
                flarum.setItemMeta(flarumMeta);
                inv.setItem(16, flarum);
            }
            // 显示奖励
            List<ItemStack> itemRewards = new ArrayList<>();
            itemRewards.addAll(task.getRewards().getItems());
            List<String> commandRewards = new ArrayList<>();
            commandRewards.addAll(task.getRewards().getCommands());
            ItemStack commandsItem = new ItemStack(Material.COMMAND_BLOCK, 1);
            ItemMeta commandsMeta = commandsItem.getItemMeta();
            commandsMeta.setDisplayName(Language.GUI_REWARDCOMMANDS.getString());
            commandsMeta.setLore(commandRewards);
            commandsItem.setItemMeta(commandsMeta);
            itemRewards.add(commandsItem);
            fillRewardSlot(itemRewards);
        } finally {
            lock.unlock();
        }
    }

    public void prev() {
        if (currentPage > 1) {
            currentPage--;
            fillRewardSlot(task.getRewards().getItems(), currentPage);
        }
    }

    public void next() {
        int max = (int) Math.ceil(task.getRewards().getItems().size() / 20.0);
        if (currentPage < max) {
            currentPage++;
            fillRewardSlot(task.getRewards().getItems(), currentPage);
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

    private void fillRewardSlot(List<ItemStack> items, int page) {
        final int ITEMS_PER_PAGE = 20;
        int start = (page - 1) * ITEMS_PER_PAGE;
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
        int row = index / 5;
        int col = index % 5;

        if (row == 0) {
            return col + 10;
        } else if (row == 1) {
            return col + 19;
        } else if (row == 2) {
            return col + 28;
        } else if (row == 3) {
            return col + 37;
        } else {
            return -1; // 不应该到达这里
        }
    }

}

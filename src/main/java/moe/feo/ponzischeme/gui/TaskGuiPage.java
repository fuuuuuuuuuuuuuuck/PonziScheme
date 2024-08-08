package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.config.Language;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskGuiPage {

    private Inventory inv;
    public static final Lock lock = new ReentrantLock();
    String type,BilitaskBV,BBStaskid;
    String BiliLike,BiliCoin,BiliFavorite,BBSreply,BiliALL,BBSALL;
    TaskGuiPage(String type) {
        //输入bili为bilibili三连任务
        //输入bbs为论坛任务
        this.type = type;
    }


    class PonziSchemeGUIHolder implements InventoryHolder {// 定义一个Holder用于识别此插件的GUI
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
        if (type.equals("bili")) {

            lock.lock();
            try {
                InventoryHolder holder = new PonziSchemeGUIHolder();
                this.setGui(Bukkit.createInventory(holder, 54, "Title")); // 54个格子
                for (int i = 0; i < inv.getSize(); i++) {
                    // 设置边框
                    if ((i >= 9 && i <= 44) && (i % 9 != 0 && i % 9 != 8)) { // 掏空中间区域
                        continue;
                    }
                    if (i == 1 + 9 * 4) { // (1, 5) 处放置火把花
                        ItemStack torchflower = new ItemStack(Material.TORCHFLOWER, 1);
                        ItemMeta torchflowerMeta = torchflower.getItemMeta();
                        torchflowerMeta.setDisplayName("BiliBili 任务");
                        torchflowerMeta.setLore(Arrays.asList("BV:"+BilitaskBV,
                        "当前状态："+BiliALL
                        ));
                        torchflower.setItemMeta(torchflowerMeta);
                        inv.setItem(i, torchflower);
                    } else if (i == 37) { // (4, 4) 处放置下界合金升级锻造模板
                        ItemStack netheriteUpgradeSmithingTemplate = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1);
                        ItemMeta netheriteUpgradeSmithingTemplateMeta = netheriteUpgradeSmithingTemplate.getItemMeta();
                        netheriteUpgradeSmithingTemplateMeta.setDisplayName("点赞");
                        netheriteUpgradeSmithingTemplateMeta.setLore(Arrays.asList("点赞状态："+BiliLike
                        ));
                        netheriteUpgradeSmithingTemplate.setItemMeta(netheriteUpgradeSmithingTemplateMeta);
                        inv.setItem(i, netheriteUpgradeSmithingTemplate);
                    } else if (i == 46) { // (4, 5) 处放置海洋之心
                        ItemStack heartOfTheSea = new ItemStack(Material.HEART_OF_THE_SEA, 1);
                        ItemMeta heartOfTheSeaMeta = heartOfTheSea.getItemMeta();
                        heartOfTheSeaMeta.setDisplayName("投币");
                        heartOfTheSeaMeta.setLore(Arrays.asList("投币状态："+BiliCoin));
                        heartOfTheSea.setItemMeta(heartOfTheSeaMeta);
                        inv.setItem(i, heartOfTheSea);
                    } else if (i == 55) { // (4, 6) 处放置箱子
                        ItemStack chest = new ItemStack(Material.CHEST, 1);
                        ItemMeta chestMeta = chest.getItemMeta();
                        chestMeta.setDisplayName("收藏");
                        chestMeta.setLore(Arrays.asList("收藏状态："+BiliFavorite));
                        chest.setItemMeta(chestMeta);
                        inv.setItem(i, chest);
                    } else { // 其他位置放置黑色染色玻璃板
                        ItemStack blackGlassPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
                        ItemMeta blackGlassPaneMeta = blackGlassPane.getItemMeta();
                        blackGlassPaneMeta.setDisplayName("");
                        blackGlassPane.setItemMeta(blackGlassPaneMeta);
                        inv.setItem(i, blackGlassPane);
                    }
                }
            } finally {
                lock.unlock();
            }
        }//bilibili任务
         else if (type.equals("bbs")) {

            lock.lock();
            try {
                InventoryHolder holder = new PonziSchemeGUIHolder();
                this.setGui(Bukkit.createInventory(holder, 54, "Title")); // 54个格子
                for (int i = 0; i < inv.getSize(); i++) {
                    // 设置边框
                    if ((i >= 9 && i <= 44) && (i % 9 != 0 && i % 9 != 8)) { // 掏空中间区域
                        continue;
                    }
                    if (i == 1 + 9 * 4) { // (1, 5) 处放置火把花
                        ItemStack torchflower = new ItemStack(Material.TORCHFLOWER, 1);
                        ItemMeta torchflowerMeta = torchflower.getItemMeta();
                        torchflowerMeta.setDisplayName("BBS 任务");
                        torchflowerMeta.setLore(Arrays.asList("帖子ID："+BBStaskid,
                                "当前状态："+BBSALL,
                                "如果完成了该任务,点击此处领取奖励"));
                        torchflower.setItemMeta(torchflowerMeta);
                        inv.setItem(i, torchflower);
                    }  else if (i == 46) { // (4, 5) 处放置海洋之心
                        ItemStack heartOfTheSea = new ItemStack(Material.PAPER, 1);
                        ItemMeta heartOfTheSeaMeta = heartOfTheSea.getItemMeta();
                        heartOfTheSeaMeta.setDisplayName("帖子");
                        heartOfTheSeaMeta.setLore(Arrays.asList("帖子回复状态："+BBSreply,
                                "关闭gui后刷新"));
                        heartOfTheSea.setItemMeta(heartOfTheSeaMeta);
                        inv.setItem(i, heartOfTheSea);
                    }  else { // 其他位置放置黑色染色玻璃板
                        ItemStack blackGlassPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
                        ItemMeta blackGlassPaneMeta = blackGlassPane.getItemMeta();
                        blackGlassPaneMeta.setDisplayName("");
                        blackGlassPane.setItemMeta(blackGlassPaneMeta);
                        inv.setItem(i, blackGlassPane);
                    }
                }
            } finally {
                lock.unlock();
            }
        } //bbs任务

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

    public ItemStack getRandomPane() {// 获取随机一种颜色的玻璃板
        short data = (short)(Math.random()* 16);// 这会随机取出0-15的数据值
        while (data == 8) {// 8号亮灰色染色玻璃板根本没有颜色
            data = (short)(Math.random()* 16);
        }
        String[] glasspanes = {"WHITE_STAINED_GLASS_PANE", "ORANGE_STAINED_GLASS_PANE", "MAGENTA_STAINED_GLASS_PANE",
                "LIGHT_BLUE_STAINED_GLASS_PANE", "YELLOW_STAINED_GLASS_PANE", "LIME_STAINED_GLASS_PANE", "PINK_STAINED_GLASS_PANE",
                "GRAY_STAINED_GLASS_PANE", "LIGHT_GRAY_STAINED_GLASS_PANE", "CYAN_STAINED_GLASS_PANE", "PURPLE_STAINED_GLASS_PANE",
                "BLUE_STAINED_GLASS_PANE", "BROWN_STAINED_GLASS_PANE", "GREEN_STAINED_GLASS_PANE", "RED_STAINED_GLASS_PANE",
                "BLACK_STAINED_GLASS_PANE"};
        ItemStack frame = new ItemStack(Material.getMaterial(glasspanes[data]), 1);
        ItemMeta framemeta = frame.getItemMeta();
        framemeta.setDisplayName(Language.GUI_FRAME.getString());
        frame.setItemMeta(framemeta);
        return frame;
    }
}

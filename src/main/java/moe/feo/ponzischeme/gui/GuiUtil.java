package moe.feo.ponzischeme.gui;

import moe.feo.ponzischeme.config.Language;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtil {
    public static ItemStack getRandomPane() {// 获取随机一种颜色的玻璃板
        short data = (short)(Math.random()* 16);// 这会随机取出0-15的数据值
        while (data == 8) {// 8号亮灰色染色玻璃板根本没有颜色
            data = (short)(Math.random()* 16);
        }
        ItemStack frame;
        try {
            frame = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, data);
        } catch (NoSuchFieldError e) {// 某些高版本服务端不兼容旧版写法
            String[] glasspanes = {"WHITE_STAINED_GLASS_PANE", "ORANGE_STAINED_GLASS_PANE", "MAGENTA_STAINED_GLASS_PANE",
                    "LIGHT_BLUE_STAINED_GLASS_PANE", "YELLOW_STAINED_GLASS_PANE", "LIME_STAINED_GLASS_PANE", "PINK_STAINED_GLASS_PANE",
                    "GRAY_STAINED_GLASS_PANE", "LIGHT_GRAY_STAINED_GLASS_PANE", "CYAN_STAINED_GLASS_PANE", "PURPLE_STAINED_GLASS_PANE",
                    "BLUE_STAINED_GLASS_PANE", "BROWN_STAINED_GLASS_PANE", "GREEN_STAINED_GLASS_PANE", "RED_STAINED_GLASS_PANE",
                    "BLACK_STAINED_GLASS_PANE"};
            frame = new ItemStack(Material.getMaterial(glasspanes[data]), 1);
        }
        ItemMeta framemeta = frame.getItemMeta();
        framemeta.setDisplayName(Language.GUI_FRAME.getString());
        frame.setItemMeta(framemeta);
        return frame;
    }

    public static ItemStack getPrevItem() {
        ItemStack prev = new ItemStack(Material.OAK_WALL_HANGING_SIGN, 1);
        ItemMeta prevmeta = prev.getItemMeta();
        prevmeta.setDisplayName(Language.GUI_PREV.getString());
        prev.setItemMeta(prevmeta);
        return prev;
    }

    public static ItemStack getNextItem() {
        ItemStack next = new ItemStack(Material.OAK_WALL_HANGING_SIGN, 1);
        ItemMeta nextmeta = next.getItemMeta();
        nextmeta.setDisplayName(Language.GUI_NEXT.getString());
        next.setItemMeta(nextmeta);
        return next;
    }

    public static ItemStack getBackItem() {
        ItemStack back = new ItemStack(Material.OAK_WALL_HANGING_SIGN, 1);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName(Language.GUI_BACK.getString());
        back.setItemMeta(backmeta);
        return back;
    }
}

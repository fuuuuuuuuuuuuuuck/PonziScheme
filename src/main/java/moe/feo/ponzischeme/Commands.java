package moe.feo.ponzischeme;

import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.gui.MainPage;
import moe.feo.ponzischeme.gui.Reader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 命令执行器
 */
public class Commands implements TabExecutor {

    private static final Commands executor = new Commands();

    private Commands() {
    }

    public static Commands getInstance() {
        return executor;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            String main = args[0].toLowerCase();
            if ("help".startsWith(main)) list.add("help");
            if ("bind".startsWith(main) && sender.hasPermission("ponzischeme.bind")) {
                list.add("bind");
            }
            if ("reader".startsWith(main) && sender.hasPermission("ponzischeme.reader")) {
                list.add("reader");
            }
            if ("reload".startsWith(main) && sender.hasPermission("ponzischeme.reload")) {
                list.add("reload");
            }
            if (list.size() > 0) {
                return list;
            } else {
                return null;
            }
        }
        if (args.length == 2) {
            List<String> list = new ArrayList<>();
            String main = args[0];
            String sub1 = args[1];
            if ("bind".equals(main) && sender.hasPermission("ponzischeme.bind")) {
                if ("flarum".startsWith(sub1)) {
                    list.add("flarum");
                }
                if ("bilibili".startsWith(sub1)) {
                    list.add("bilibili");
                }
            }
            if (list.size() > 0) {
                return list;
            } else {
                return null;
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {// 没有带参数
            if (sender instanceof Player) {
                Player player = (Player) sender;
                MainPage mainPage = new MainPage();
                mainPage.openGui(player);
            } else {
                String[] helpArgs = { "help" };
                onCommand(sender, cmd, label, helpArgs);
            }
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help": {
                sender.sendMessage(Language.PREFIX.getString() + Language.HELP_TITLE.getString());
                sender.sendMessage(Language.PREFIX.getString() + Language.HELP_HELP.getString());
                if (sender.hasPermission("ponzischeme.reader") && sender instanceof Player) {
                    sender.sendMessage(Language.PREFIX.getString() + Language.HELP_READER.getString());
                }
                if (sender.hasPermission("ponzischeme.reload")) {
                    sender.sendMessage(Language.PREFIX.getString() + Language.HELP_RELOAD.getString());
                }
                break;
            }
            case "bind": {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Language.PREFIX.getString() + Language.PLAYERCMD.getString());
                    sender.sendMessage(Language.PREFIX.getString() + Language.HELP_HELP.getString());
                    break;
                }
                if (!sender.hasPermission("ponzischeme.bind")) {
                    sender.sendMessage(Language.PREFIX.getString() + Language.NOPERMISSION.getString());
                    break;
                }
                if (args.length != 3) {
                    sender.sendMessage(Language.PREFIX.getString() + Language.INVALID.getString());
                    sender.sendMessage(Language.PREFIX.getString() + Language.HELP_BINDING.getString());
                    break;
                }
                String type = args[1];
                String player = args[2];
                UUID uuid = Bukkit.getOfflinePlayer(player).getUniqueId();
                switch (type) {
                    case "flarum": {
                        //TODO 绑定flarum账号, 需让用户确认两遍
                        break;
                    }
                    case "bilibili": {
                        //TODO 绑定bilibili账号, 需让用户确认两遍
                        break;
                    }
                }
                break;
            }
            case "reader": {
                if (!sender.hasPermission("ponzischeme.reader")) {
                    sender.sendMessage(Language.PREFIX.getString() + Language.NOPERMISSION.getString());
                    break;
                }
                if (sender instanceof Player) {
                    Reader.getInstance().openForPlayer((Player) sender);
                } else {
                    sender.sendMessage(Language.PREFIX.getString() + Language.PLAYERCMD.getString());
                }
                break;
            }
            case "reload": {
                if (!sender.hasPermission("ponzischeme.reload")) {
                    sender.sendMessage(Language.PREFIX.getString() + Language.NOPERMISSION.getString());
                    break;
                }
                //TODO 重载插件
                break;
            }
            default: {
                sender.sendMessage(Language.PREFIX.getString() + Language.INVALID.getString());
            }
        }
        return true;
    }
}
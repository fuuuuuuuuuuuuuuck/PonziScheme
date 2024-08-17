package moe.feo.ponzischeme;

import moe.feo.ponzischeme.bilibili.BilibiliMember;
import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.flarum.FlarumUser;
import moe.feo.ponzischeme.gui.GUIListener;
import moe.feo.ponzischeme.gui.MainPage;
import moe.feo.ponzischeme.gui.Reader;
import moe.feo.ponzischeme.player.BindListener;
import moe.feo.ponzischeme.player.PlayerProfile;
import moe.feo.ponzischeme.sql.BaseDao;
import moe.feo.ponzischeme.sql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * 命令执行器
 */
public class Commands implements TabExecutor {

    private static final Commands executor = new Commands();
    private Map<UUID, String> cache = new HashMap<>();

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
                String id = args[2];
                UUID uuid = ((Player) sender).getUniqueId();
                PlayerProfile profile = DatabaseManager.dao.getPlayerProfile(uuid.toString());
                long binddate = 0;
                int cooldownDays = 0;
                boolean isrecording = true;
                if (profile != null) {
                    switch (type) {
                        case BaseDao.TYPE_FLARUM: {
                            binddate = profile.getFlarumBinddate();
                            cooldownDays = Config.BINDCOOLDOWN_FLARUM.getInt();
                            break;
                        }
                        case BaseDao.TYPE_BILIBILI: {
                            binddate = profile.getBilibiliBinddate();
                            cooldownDays = Config.BINDCOOLDOWN_BILIBILI.getInt();
                            break;
                        }
                    }
                    long cd = System.currentTimeMillis() - binddate;// 已经过了的cd
                    long settedcd = cooldownDays * (long) 86400000;// 设置的cd
                    if (cd < settedcd) {// 如果还在cd那么直接return;
                        long leftcd = settedcd - cd;// 剩下的cd
                        long leftcdtodays = leftcd / 86400000;
                        sender.sendMessage(Language.PREFIX.getString() + Language.BINDINCOOLDOWN.getString()
                                .replaceAll("%COOLDOWN%", String.valueOf(leftcdtodays)));
                        BindListener.unregister(sender);
                        return true;
                    }
                } else {
                    profile = new PlayerProfile();
                    isrecording = false;
                }
                PlayerProfile check = DatabaseManager.dao.checkPlayerProfile(type, Integer.parseInt(args[2]));
                if (check == null) {// 没有人绑定过这个id
                    if (cache.get(uuid) != null && cache.get(uuid).equals(type + ": " + args[2])) {
                        profile.setUuid(uuid.toString());
                        profile.setName(sender.getName());
                        boolean succeed = false;
                        switch (type) {
                            case BaseDao.TYPE_FLARUM: {
                                profile.setFlarumId(Integer.parseInt(args[2]));
                                FlarumUser flarumUser = Crawler.getFlarumUserByUserId(Config.FLARUMURL.getString(), Integer.parseInt(args[2]));
                                if (flarumUser != null) {
                                    profile.setFlarumName(flarumUser.getAttributes().getUsername());
                                    profile.setFlarumBinddate(System.currentTimeMillis());
                                    succeed = true;
                                }
                                break;
                            }
                            case BaseDao.TYPE_BILIBILI: {
                                profile.setBilibiliId(Integer.parseInt(args[2]));
                                BilibiliMember bilibiliMember = Crawler.getBilibiliMember(Integer.parseInt(args[2]));
                                if (bilibiliMember != null) {
                                    profile.setBlibiliName(bilibiliMember.getName());
                                    profile.setBilibiliBinddate(System.currentTimeMillis());
                                    succeed = true;
                                }
                                break;
                           }
                        }
                        if (isrecording) {
                            DatabaseManager.dao.updatePlayerProfile(profile);
                        } else {
                            DatabaseManager.dao.addPlayerProfile(profile);
                        }
                        cache.put(uuid, null);// 绑定结束, 清理这个键
                        BindListener.unregister(sender);
                        if (succeed) {
                            sender.sendMessage(Language.PREFIX.getString() + Language.BINDSUCCESS.getString());
                        } else {
                            sender.sendMessage(Language.PREFIX.getString() + Language.ACCOUNTNOTFOUND.getString());
                        }
                    } else if (cache.get(uuid) == null) {
                        cache.put(uuid, type + ": " + args[2]);
                        sender.sendMessage(Language.PREFIX.getString() + Language.REPEAT.getString());
                    } else {
                        sender.sendMessage(Language.PREFIX.getString() + Language.NOTSAME.getString());
                        cache.put(uuid, null);
                        BindListener.unregister(sender);
                    }
                    return true;
                } else if (check.getUuid().equals(uuid.toString())) {// 自己绑定了这个论坛id
                    sender.sendMessage(Language.PREFIX.getString() + Language.OWNSAMEBIND.getString());
                    BindListener.unregister(sender);
                    return true;
                } else {
                    sender.sendMessage(Language.PREFIX.getString() + Language.SAMEBIND.getString());
                    BindListener.unregister(sender);
                    return true;
                }
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

    public Map<UUID, String> getCache() {
        return cache;
    }

    public void setCache(Map<UUID, String> cache) {
        this.cache = cache;
    }
}
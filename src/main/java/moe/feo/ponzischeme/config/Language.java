package moe.feo.ponzischeme.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public enum Language {

    PREFIX("prefix"),
    ENABLE("enable"),
    RELOAD("reload"),
    FAILEDCONNECTSQL("failedconnectsql"),
    REWARDGIVED("rewardgived"),
    REWARDBROADCAST("rewardbroadcast"),
    TASKCOOLDOWN("taskcooldown"),
    NOTBOUNDFLARUM("notboundflarum"),
    NOTBOUNDBILIBILI("notboundbilibili"),
    BILIBILINOTSUPPORTNAMEBIND("bilibilinotsupportnamebind"),
    BINDINCOOLDOWN("bindincooldown"),
    TASKRECIVED("taskreceived"),
    TASKCANCELED("taskcanceled"),
    TASKFINISHED("taskfinished"),
    TASKONGOING("taskongoing"),
    FLARUMURL("flarumurl"),
    BILIBILIURL("bilibiliurl"),
    BINDSUCCESS("bindsuccess"),
    BINDCANCELED("bindcanceled"),
    REPEAT("repeat"),
    NOTSAME("notsame"),
    SAMEBIND("samebind"),
    OWNSAMEBIND("ownsamebind"),
    ACCOUNTNOTFOUND("accountnotfound"),
    BINDBILIBILI("bindbilibili"),
    BINDFLARUM("bindflarum"),
    NOPERMISSION("nopermission"),
    INVALID("invalid"),
    PLAYERCMD("playercmd"),
    FAILEDUNINSTALLMO("faileduninstallmo"),
    GUI_TITLE("gui.title"),
    GUI_FRAME("gui.frame"),
    GUI_PREV("gui.prev"),
    GUI_NEXT("gui.next"),
    GUI_BACK("gui.back"),
    GUI_FLARUM("gui.flarum"),
    GUI_FLARUMLORE("gui.flarumlore"),
    GUI_SKULL("gui.skull"),
    GUI_SKULLLORE("gui.skulllore"),
    GUI_SKULLBINDED("gui.skullbinded"),
    GUI_SKULLUNBINDED("gui.skullunbinded"),
    GUI_BILIBILI("gui.bilibili"),
    GUI_BILIBILILORE("gui.bilibililore"),
    GUI_CLICKBIND("gui.clickbind"),
    GUI_TASKLOREFLARUM("gui.taskloreflarum"),
    GUI_TASKLOREBILIBILI("gui.tasklorebilibili"),
    GUI_TASKSTATUS("gui.taskstatus"),
    GUI_STATUSRUNNING("gui.statusrunning"),
    GUI_STATUSCOMPLETE("gui.statuscomplete"),
    GUI_STATUSAVAILABLE("gui.statusavailable"),
    GUI_STATUSCOOLDOWN("gui.statuscooldown"),
    GUI_LEFTCLICK("gui.leftclick"),
    GUI_RIGHTCLICK("gui.rightclick"),
    GUI_RIGHTCLICKURL("gui.rightclickurl"),
    GUI_BILIBILIERROR("gui.bilibilierror"),
    GUI_TITLETASK("gui.titletask"),
    GUI_BILIBILIVIDEO("gui.bilibilivideo"),
    GUI_BILIBILIVIDEOLORE("gui.bilibilivideolore"),
    GUI_BILIBILILIKE("gui.bilibililike"),
    GUI_BILIBILILIKELORE("gui.bilibililikelore"),
    GUI_BILIBILICOIN("gui.bilibilicoin"),
    GUI_BILIBILICOINLORE("gui.bilibilicoinlore"),
    GUI_BILIBILIFAVOR("gui.bilibilifavor"),
    GUI_BILIBILIFAVORLORE("gui.bilibilifavolorer"),
    GUI_FLARUMSITE("gui.flarumsite"),
    GUI_FLARUMSITE_LORE("gui.flarumsitelore"),
    GUI_REWARDCOMMANDS("gui.rewardcommands"),
    HELP_TITLE("help.title"),
    HELP_HELP("help.help"),
    HELP_BINDING("help.binding"),
    HELP_READER("help.reader"),
    HELP_RELOAD("help.reload");

    private static FileConfiguration config;
    private final String path;

    /**
     * @param path
     * 此项语言的路径
     */
    Language(String path) {
        this.path = path;
    }

    /**
     * 加载语言文件
     */
    public static void load() {
        String lang = Config.LANG.getString();
        String fileName = "lang/" + lang + ".yml";
        config = ConfigUtil.load(fileName);
    }

    /**
     * 保存默认的语言文件
     */
    public static void saveDefault() {
        ConfigUtil.saveDefault("lang/zh_cn.yml");
    }

    /**
     * 获取字符串，并转换样式代码
     * @return 最终的字符串
     */
    public String getString() {
        String string = config.getString(path);
        if (string != null){
            string = ChatColor.translateAlternateColorCodes('&', string);
        }
        return string;
    }

    /**
     * 获取字符串list，并转换每个元素的样式代码
     * @return 最终的字符串list
     */
    public List<String> getStringList() {
        List<String> list = new ArrayList<>();
        List<String> source = config.getStringList(path);
        for (String string : source) {
            list.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        return list;
    }
}

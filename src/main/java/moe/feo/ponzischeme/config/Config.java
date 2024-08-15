package moe.feo.ponzischeme.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public enum Config {

    DATABASE_PREFIX("database.prefix"),
    DATABASE_URL("database.url"),
    DATABASE_USER("database.user"),
    DATABASE_PASSWORD("database.password"),
    LANG("lang"),
    FLARUMURL("flarumurl"),
    CANCELKEYWORDS("cancelkeywords"),
    BINDCOOLDOWN_FLARUM("bindcooldown.flarum"),
    BINDCOOLDOWN_BILIBILI("bindcooldown.bilibili"),
    ;

    /**
     * 加载配置文件
     */
    public static void load() {
        config = ConfigUtil.load("config.yml");
    }

    public int getInt() {
        return config.getInt(path);
    }

    public double getDouble() {
        return config.getDouble(path);
    }

    public boolean getBoolean() {
        return config.getBoolean(path);
    }

    public String getString() {
        return config.getString(path);
    }

    public List<String> getStringList() {
        return config.getStringList(path);
    }

    private static FileConfiguration config;
    private final String path;

    /**
     * @param path
     * 此设置的路径
     */
    Config(String path) {
        this.path = path;
    }
}

package moe.feo.ponzischeme.config;

import moe.feo.ponzischeme.PonziScheme;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * 配置文件工具类
 */
public class ConfigUtil {

    /**
     * 从指定路径加载配置文件
     * @param filename
     * 文件名
     * @return 配置文件
     */
    public static FileConfiguration load(String filename) {
        JavaPlugin plugin = PonziScheme.getPlugin(PonziScheme.class);
        File file = new File(plugin.getDataFolder(), filename);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);// 用这个方法加载配置可以解决编码问题
        InputStream input = plugin.getResource(filename);
        if (input != null) {
            try (Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {// 读取默认配置
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
                config.setDefaults(defConfig);// 设置默认
            } catch (IOException ioe) {
                plugin.getLogger().log(Level.SEVERE, "Error when reading default config!", ioe);
            }
        }
        return config;
    }

    /**
     * 保存默认配置文件
     * @param filename
     * 文件名
     */
    public static void saveDefault(String filename) {
        JavaPlugin plugin = PonziScheme.getPlugin(PonziScheme.class);
        File dir = new File(plugin.getDataFolder(), filename).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        plugin.saveResource(filename, false);
    }

    /**
     * 保存YamlConfiguration到指定的文件中
     *
     * @param config 要保存的YamlConfiguration对象
     * @param filename 要保存到的文件名（包括路径）
     */
    public static void save(YamlConfiguration config, String filename) {
        try {
            // 将配置对象保存到指定文件
            config.save(new File(PonziScheme.getInstance().getDataFolder(), filename));
        } catch (IOException ioe) {
            PonziScheme.getInstance().getLogger().log(Level.SEVERE, "Error when saving config!", ioe);
        }
    }

}
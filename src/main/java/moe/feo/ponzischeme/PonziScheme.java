package moe.feo.ponzischeme;

import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.gui.GUIListener;
import moe.feo.ponzischeme.gui.Reader;
import moe.feo.ponzischeme.sql.DatabaseManager;
import moe.feo.ponzischeme.task.TaskManager;
import moe.feo.ponzischeme.task.taskentity.BilibiliSanlianTimer;
import org.bukkit.plugin.java.JavaPlugin;

public class PonziScheme extends JavaPlugin {

    private static PonziScheme ponziScheme;

    /**
     * 插件主类
     */
    @Override
    public void onEnable() {
        this.ponziScheme = this;
        saveDefaultConfig();
        Config.load();
        DatabaseManager.saveDefaultFile();
        DatabaseManager.initialize();
        Language.saveDefault();
        Language.load();
        TaskManager.getInstance().saveDefault();
        TaskManager.getInstance().load();
        Reader.getInstance().saveDefault();
        BilibiliSanlianTimer.load();
        getServer().getPluginManager().registerEvents(Reader.getInstance(), this);
        getServer().getPluginManager().registerEvents(GUIListener.getInstance(), this);
        this.getCommand("ponzischeme").setExecutor(Commands.getInstance());
        this.getCommand("ponzischeme").setTabCompleter(Commands.getInstance());
        this.getLogger().info(Language.ENABLE.getString());
    }

    @Override
    public void onDisable() {
        DatabaseManager.closeDatabase();
    }

    public static PonziScheme getInstance() {
        return ponziScheme;
    }
}

package moe.feo.ponzischeme;

import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.gui.Reader;
import moe.feo.ponzischeme.sql.DatabaseManager;
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
        getServer().getPluginManager().registerEvents(Reader.getInstance(), this);
        this.getCommand("ponzischeme").setExecutor(Commands.getInstance());
        //this.getCommand("shootexp").setTabCompleter(Commands.getInstance());
    }

    @Override
    public void onDisable() {

    }

    public static PonziScheme getInstance() {
        return ponziScheme;
    }
}

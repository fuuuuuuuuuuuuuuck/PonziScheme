package moe.feo.ponzischeme.sql;

import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.ConfigUtil;


public class DatabaseManager {
    public static BaseDao dao;

    public static void initialize() {// 初始化或重载数据库
        BaseDao.writelock.lock();
        try {
            if (dao != null) {
                dao.closeSessionFactory();// 此方法会在已经建立过连接的情况下关闭连接
            }
            if (Config.DATABASE_URL.getString().toLowerCase().contains("jdbc:mysql:")) {
                dao = MysqlDao.getInstance();
            } else if (Config.DATABASE_URL.getString().toLowerCase().contains("jdbc:sqlite:")) {
                dao = SqliteDao.getInstance();
            }
            dao.load();
            //CLI.setSQLer(dao);
            //GUI.setSQLer(dao);
            //Crawler.setSQLer(dao);
            //Poster.setSQLer(dao);
            //Reminder.setSQLer(dao);
            //if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            //    PAPIExpansion.setSQLer(dao);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.writelock.unlock();
        }
    }

    public static void closeDatabase() {// 关闭数据库
        dao.closeSessionFactory();
        dao = null;
    }

    public static void saveDefaultFile() {
        String mysqlConfig = "mybatis/config.mysql.xml";
        String sqliteConfig = "mybatis/config.sqlite.xml";
        String mapperPath = "mybatis/mapper.xml";
        ConfigUtil.saveDefault(mysqlConfig);
        ConfigUtil.saveDefault(sqliteConfig);
        ConfigUtil.saveDefault(mapperPath);
    }
}

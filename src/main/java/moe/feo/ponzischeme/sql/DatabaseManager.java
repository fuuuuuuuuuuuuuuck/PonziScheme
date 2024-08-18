package moe.feo.ponzischeme.sql;

import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.ConfigUtil;
import moe.feo.ponzischeme.config.Language;


public class DatabaseManager {
    private static BaseDao dao;

    public static void initialize() {// 初始化或重载数据库
        BaseDao.writelock.lock();
        try {
            if (getDao() != null) {
                getDao().closeSessionFactory();// 此方法会在已经建立过连接的情况下关闭连接
            }
            if (Config.DATABASE_URL.getString().toLowerCase().contains("jdbc:mysql:")) {
                setDao(MysqlDao.getInstance());
            } else if (Config.DATABASE_URL.getString().toLowerCase().contains("jdbc:sqlite:")) {
                setDao(SqliteDao.getInstance());
            }
            getDao().load();
        } catch (Exception e) {
            e.printStackTrace();
            PonziScheme.getInstance().getLogger().severe(Language.FAILEDCONNECTSQL.getString());
        } finally {
            BaseDao.writelock.unlock();
        }
    }

    public static void closeDatabase() {// 关闭数据库
        BaseDao.writelock.lock();
        try {
            getDao().closeSessionFactory();
            setDao(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.writelock.unlock();
        }
    }

    public static BaseDao getDao() {
        return dao;
    }

    public static void saveDefaultFile() {
        String mysqlConfig = "mybatis/config.mysql.xml";
        String sqliteConfig = "mybatis/config.sqlite.xml";
        String mapperPath = "mybatis/mapper.xml";
        ConfigUtil.saveDefault(mysqlConfig);
        ConfigUtil.saveDefault(sqliteConfig);
        ConfigUtil.saveDefault(mapperPath);
    }

    public static void setDao(BaseDao dao) {
        DatabaseManager.dao = dao;
    }
}

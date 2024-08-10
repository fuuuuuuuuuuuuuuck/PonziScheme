package moe.feo.ponzischeme.sql;

import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.player.PlayerProfile;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.bukkit.Bukkit;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class BaseDao implements DaoImpl {

    private SqlSessionFactory ssf = null;
    public static final ReadWriteLock lock = new ReentrantReadWriteLock();
    // sql本身是线程安全的, 读写锁仅在重载数据库时起作用
    public static final Lock readlock = lock.readLock();
    public static final Lock writelock = lock.writeLock();

    protected abstract void load();

    protected abstract void createPlayerProfileTable();

    protected abstract void createBilibiliVideoSanlianTaskDataTable();

    protected abstract void createFlarumPostActivateTaskDataTable();

    public String getTableName(String name) {// 获取数据表应有的名字
        return Config.DATABASE_PREFIX.getString() + name;
    }

    public void createSessionFactory() {
        Path path = null;
        if (Config.DATABASE_URL.getString().toLowerCase().contains("jdbc:mysql:")) {
            path = Paths.get(PonziScheme.getInstance().getDataFolder().getAbsolutePath(), "mybatis", "config.mysql.xml");
        } else if (Config.DATABASE_URL.getString().toLowerCase().contains("jdbc:sqlite:")) {
            path = Paths.get(PonziScheme.getInstance().getDataFolder().getAbsolutePath(), "mybatis", "config.sqlite.xml");
        } else {
            //TODO 禁用插件
        }
        SqlSessionFactory sqlSessionFactory = null;
        try {
            String config = Files.readString(path);
            config = config.replace("${url}", Config.DATABASE_URL.getString().replace("&", "&amp;"))
                    .replace("${username}", Config.DATABASE_USER.getString())
                    .replace("${password}", Config.DATABASE_PASSWORD.getString())
                    .replace("${dir}", PonziScheme.getInstance().getDataFolder().getAbsolutePath());
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(new StringReader(config));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.ssf = sqlSessionFactory;
    }

    public SqlSessionFactory getSessionFactory() {
        return ssf;
    }

    public void closeSessionFactory() {
        DataSource source = ssf.getConfiguration().getEnvironment().getDataSource();
        if (source instanceof PooledDataSource) {
            ((PooledDataSource) source).forceCloseAll();
        }
        this.ssf = null;
    }

    public PlayerProfile getPlayerProfile(String uuid) {
        SqlSession session = this.getSessionFactory().openSession();
        PlayerProfile param = new PlayerProfile();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        param.setUuid(uuid);
        readlock.lock();
        try {
            PlayerProfile profile = session.selectOne("moe.feo.ponzischeme.Mapper.getPlayerProfile", param);
            if (profile == null) {
                profile = new PlayerProfile();
                profile.setUuid(uuid);
                profile.setName(Bukkit.getPlayer(UUID.fromString(uuid)).getName());
            }
            return profile;
        } finally {
            session.commit();
            readlock.unlock();
            session.close();
        }
    }

    public void addPlayerProfile(PlayerProfile profile) {
        SqlSession session = this.getSessionFactory().openSession();
        profile.setTablePrefix(Config.DATABASE_PREFIX.getString());
        writelock.lock();
        try {
            session.insert("moe.feo.ponzischeme.Mapper.addPlayerProfile", profile);
        } finally {
            session.commit();
            writelock.unlock();
            session.close();
        }
    }

    public void updatePlayerProfile(PlayerProfile profile) {
        SqlSession session = this.getSessionFactory().openSession();
        profile.setTablePrefix(Config.DATABASE_PREFIX.getString());
        writelock.lock();
        try {
            session.update("moe.feo.ponzischeme.Mapper.updatePlayerProfile", profile);
        } finally {
            session.commit();
            writelock.unlock();
            session.close();
        }
    }

    public void deletePlayerProfile(String uuid) {
        SqlSession session = this.getSessionFactory().openSession();
        PlayerProfile param = new PlayerProfile();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        param.setUuid(uuid);
        writelock.lock();
        try {
            session.delete("moe.feo.ponzischeme.Mapper.deletePlayerProfile", param);
        } finally {
            session.commit();
            writelock.unlock();
            session.close();
        }
    }
}

package moe.feo.ponzischeme.sql;

import moe.feo.ponzischeme.PonziScheme;
import moe.feo.ponzischeme.config.Config;
import moe.feo.ponzischeme.config.Language;
import moe.feo.ponzischeme.player.PlayerProfile;
import moe.feo.ponzischeme.task.taskentity.BilibiliVideoSanlianPlayerTask;
import moe.feo.ponzischeme.task.taskentity.FlarumPostActivatePlayerTask;
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
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

public abstract class BaseDao {

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
        String driver = "";
        if (Config.DATABASE_URL.getString().toLowerCase().contains("jdbc:mysql:")) {
            path = Paths.get(PonziScheme.getInstance().getDataFolder().getAbsolutePath(), "mybatis", "config.mysql.xml");
        } else if (Config.DATABASE_URL.getString().toLowerCase().contains("jdbc:sqlite:")) {
            path = Paths.get(PonziScheme.getInstance().getDataFolder().getAbsolutePath(), "mybatis", "config.sqlite.xml");
        }
        SqlSessionFactory sqlSessionFactory;
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
        PlayerProfile profile;
        try {
            profile = session.selectOne("moe.feo.ponzischeme.Mapper.getPlayerProfile", param);
        } finally {
            session.commit();
            readlock.unlock();
            session.close();
        }
        return profile;
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

    public static final String TYPE_BILIBILI = "bilibili";
    public static final String TYPE_FLARUM = "flarum";

    public PlayerProfile checkPlayerProfile(String type, int id) {
        SqlSession session = this.getSessionFactory().openSession();
        PlayerProfile param = new PlayerProfile();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        switch (type) {
            case TYPE_BILIBILI:
                param.setBilibiliId(id);
                break;
            case TYPE_FLARUM:
                param.setFlarumId(id);
                break;
            default:
                return null;
        }
        readlock.lock();
        PlayerProfile profile = null;
        try {
            List<PlayerProfile> profiles = session.selectList("moe.feo.ponzischeme.Mapper.checkPlayerProfile", param);
            if (profiles != null && !profiles.isEmpty()) {
                profile = profiles.get(0);
            }
        } finally {
            session.commit();
            readlock.unlock();
            session.close();
        }
        return profile;
    }

    public FlarumPostActivatePlayerTask getFlarumPostActivateTask(FlarumPostActivatePlayerTask param) {
        SqlSession session = this.getSessionFactory().openSession();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        readlock.lock();
        FlarumPostActivatePlayerTask task = null;
        try {
            List<FlarumPostActivatePlayerTask> tasks = session.selectList("moe.feo.ponzischeme.Mapper.getFlarumPostActivateTask", param);
            if (tasks != null && !tasks.isEmpty()) {
                task = tasks.get(0);
            }
        } finally {
            session.commit();
            readlock.unlock();
            session.close();
        }
        return task;
    }

    public void addFlarumPostActivateTask(FlarumPostActivatePlayerTask param) {
        SqlSession session = this.getSessionFactory().openSession();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        writelock.lock();
        try {
            session.insert("moe.feo.ponzischeme.Mapper.addFlarumPostActivateTask", param);
        } finally {
            session.commit();
            writelock.unlock();
            session.close();
        }
    }

    public void updateFlarumPostActivateTask(FlarumPostActivatePlayerTask param) {
        SqlSession session = this.getSessionFactory().openSession();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        writelock.lock();
        try {
            session.update("moe.feo.ponzischeme.Mapper.updateFlarumPostActivateTask", param);
        } finally {
            session.commit();
            writelock.unlock();
            session.close();
        }
    }

    public List<BilibiliVideoSanlianPlayerTask> loadBilibiliVideoSanlianTasks() {
        SqlSession session = this.getSessionFactory().openSession();
        BilibiliVideoSanlianPlayerTask param = new BilibiliVideoSanlianPlayerTask();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        param.getTaskId();
        readlock.lock();
        List<BilibiliVideoSanlianPlayerTask> tasks;
        try {
            tasks = session.selectList("moe.feo.ponzischeme.Mapper.getBilibiliVideoSanlianTask", param);
        } finally {
            session.commit();
            readlock.unlock();
            session.close();
        }
        return tasks;
    }

    public BilibiliVideoSanlianPlayerTask getBilibiliVideoSanlianTask(BilibiliVideoSanlianPlayerTask param) {
        SqlSession session = this.getSessionFactory().openSession();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        readlock.lock();
        BilibiliVideoSanlianPlayerTask task = null;
        try {
            List<BilibiliVideoSanlianPlayerTask> tasks = session.selectList("moe.feo.ponzischeme.Mapper.getBilibiliVideoSanlianTask", param);
            if (tasks != null && !tasks.isEmpty()) {
                task = tasks.get(0);
            }
        } finally {
            session.commit();
            readlock.unlock();
            session.close();
        }
        return task;
    }

    public void addBilibiliVideoSanlianTask(BilibiliVideoSanlianPlayerTask param) {
        SqlSession session = this.getSessionFactory().openSession();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        writelock.lock();
        try {
            session.insert("moe.feo.ponzischeme.Mapper.addBilibiliVideoSanlianTask", param);
        } finally {
            session.commit();
            writelock.unlock();
            session.close();
        }
    }

    public void updateBilibiliVideoSanlianTask(BilibiliVideoSanlianPlayerTask param) {
        SqlSession session = this.getSessionFactory().openSession();
        param.setTablePrefix(Config.DATABASE_PREFIX.getString());
        writelock.lock();
        try {
            session.update("moe.feo.ponzischeme.Mapper.updateBilibiliVideoSanlianTask", param);
        } finally {
            session.commit();
            writelock.unlock();
            session.close();
        }
    }
}

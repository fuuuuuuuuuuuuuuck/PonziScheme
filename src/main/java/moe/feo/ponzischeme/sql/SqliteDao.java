package moe.feo.ponzischeme.sql;

import moe.feo.ponzischeme.config.Config;
import org.apache.ibatis.session.SqlSession;

public class SqliteDao extends BaseDao{

    private final static SqliteDao dao = new SqliteDao();

    public static SqliteDao getInstance() {
        return dao;
    }

    @Override
    protected void load() {
        super.createSessionFactory();
        createPlayerProfileTable();
        createFlarumPostActivateTaskDataTable();
        createBilibiliVideoSanlianTaskDataTable();
    }

    @Override
    protected void createPlayerProfileTable() {
        SqlSession session = super.getSessionFactory().openSession();
        try {
            session.update("moe.feo.ponzischeme.Mapper.createPlayerProfileTableSqlite",
                    Config.DATABASE_PREFIX.getString());
        } finally {
            session.commit();
            session.close();
        }
    }

    @Override
    protected void createBilibiliVideoSanlianTaskDataTable() {
        SqlSession session = super.getSessionFactory().openSession();
        try {
            session.update("moe.feo.ponzischeme.Mapper.createBilibiliVideoSanlianTaskTableSqlite",
                    Config.DATABASE_PREFIX.getString());
        } finally {
            session.commit();
            session.close();
        }
    }

    @Override
    protected void createFlarumPostActivateTaskDataTable() {
        SqlSession session = super.getSessionFactory().openSession();
        try {
            session.update("moe.feo.ponzischeme.Mapper.createFlarumPostActivateTaskTableSqlite",
                    Config.DATABASE_PREFIX.getString());
        } finally {
            session.commit();
            session.close();
        }
    }

}

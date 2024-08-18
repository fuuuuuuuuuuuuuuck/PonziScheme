package moe.feo.ponzischeme.sql;

import moe.feo.ponzischeme.config.Config;
import org.apache.ibatis.session.SqlSession;

public class MysqlDao extends BaseDao{


    private final static MysqlDao dao = new MysqlDao();

    public static MysqlDao getInstance() {
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
            session.update("moe.feo.ponzischeme.Mapper.createPlayerProfileTableMysql",
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
            session.update("moe.feo.ponzischeme.Mapper.createBilibiliVideoSanlianTaskTableMysql",
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
            session.update("moe.feo.ponzischeme.Mapper.createFlarumPostActivateTaskTableMysql",
                    Config.DATABASE_PREFIX.getString());
        } finally {
            session.commit();
            session.close();
        }
    }
}

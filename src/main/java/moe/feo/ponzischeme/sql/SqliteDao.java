package moe.feo.ponzischeme.sql;

import moe.feo.ponzischeme.config.Config;

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
        super.getSessionFactory().openSession().update(
                "moe.feo.ponzischeme.Mapper.createPlayerProfileTableSqlite",
                Config.DATABASE_PREFIX.getString());
    }

    @Override
    protected void createBilibiliVideoSanlianTaskDataTable() {
        super.getSessionFactory().openSession().update(
                "moe.feo.ponzischeme.Mapper.createBilibiliVideoSanlianTaskTableSqlite",
                Config.DATABASE_PREFIX.getString());
    }

    @Override
    protected void createFlarumPostActivateTaskDataTable() {
        super.getSessionFactory().openSession().update(
                "moe.feo.ponzischeme.Mapper.createFlarumPostActivateTaskTableSqlite",
                Config.DATABASE_PREFIX.getString());
    }

}

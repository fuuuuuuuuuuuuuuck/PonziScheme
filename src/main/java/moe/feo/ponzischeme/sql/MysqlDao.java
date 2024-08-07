package moe.feo.ponzischeme.sql;

import moe.feo.ponzischeme.config.Config;

public class MysqlDao extends BaseDao{


    private final static MysqlDao dao = new MysqlDao();

    public static MysqlDao getInstance() {
        return dao;
    }

    @Override
    protected void load() {
        super.createSessionFactory();
        createPlayerProfileTable();
    }

    @Override
    protected void createPlayerProfileTable() {
        super.getSessionFactory().openSession().update(
                "moe.feo.ponzischeme.Mapper.createPlayerProfileTableMysql",
                Config.DATABASE_PREFIX.getString());
    }

    @Override
    protected void createBilibiliVideoSanlianTaskDataTable() {
        super.getSessionFactory().openSession().update(
                "moe.feo.ponzischeme.Mapper.createBilibiliVideoSanlianTaskTableMysql",
                Config.DATABASE_PREFIX.getString());
    }

    @Override
    protected void createFlarumPostActivateTaskDataTable() {
        super.getSessionFactory().openSession().update(
                "moe.feo.ponzischeme.Mapper.createFlarumPostActivateTaskTableMysql",
                Config.DATABASE_PREFIX.getString());
    }

}

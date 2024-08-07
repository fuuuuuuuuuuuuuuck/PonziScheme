package moe.feo.ponzischeme.sql;

public class SqliteDao extends BaseDao{

    private final static SqliteDao dao = new SqliteDao();

    public static SqliteDao getInstance() {
        return dao;
    }

    @Override
    protected void load() {

    }

    @Override
    protected void createPlayerProfileTable() {

    }

    @Override
    protected void createBilibiliVideoSanlianTaskDataTable() {

    }

    @Override
    protected void createFlarumPostActivateTaskDataTable() {

    }

}

package moe.feo.ponzischeme.player;

import moe.feo.ponzischeme.sql.PrefixedTable;

public class PlayerProfile extends PrefixedTable {

    private String uuid;
    private String name;
    private int flarumId;
    private String flarumName;
    private long flarumBinddate;
    private int bilibiliId;
    private String blibiliName;
    private long bilibiliBinddate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getFlarumId() {
        return flarumId;
    }

    public void setFlarumId(int flarumId) {
        this.flarumId = flarumId;
    }

    public String getFlarumName() {
        return flarumName;
    }

    public void setFlarumName(String flarumName) {
        this.flarumName = flarumName;
    }

    public long getFlarumBinddate() {
        return flarumBinddate;
    }

    public void setFlarumBinddate(long flarumBinddate) {
        this.flarumBinddate = flarumBinddate;
    }

    public int getBilibiliId() {
        return bilibiliId;
    }

    public void setBilibiliId(int bilibiliId) {
        this.bilibiliId = bilibiliId;
    }

    public String getBlibiliName() {
        return blibiliName;
    }

    public void setBlibiliName(String blibiliName) {
        this.blibiliName = blibiliName;
    }

    public long getBilibiliBinddate() {
        return bilibiliBinddate;
    }

    public void setBilibiliBinddate(long bilibiliBinddate) {
        this.bilibiliBinddate = bilibiliBinddate;
    }
}

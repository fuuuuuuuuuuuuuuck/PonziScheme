package moe.feo.ponzischeme.player;

import moe.feo.ponzischeme.sql.PrefixedTable;

public class PlayerProfile extends PrefixedTable {

    private String uuid;
    private String name;
    private int flarmumId;
    private String flarumName;
    private int bilibiliId;
    private String blibiliName;

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

    public int getFlarmumId() {
        return flarmumId;
    }

    public void setFlarmumId(int flarmumId) {
        this.flarmumId = flarmumId;
    }

    public String getFlarumName() {
        return flarumName;
    }

    public void setFlarumName(String flarumName) {
        this.flarumName = flarumName;
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
}

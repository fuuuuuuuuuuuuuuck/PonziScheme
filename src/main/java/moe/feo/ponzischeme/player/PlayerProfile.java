package moe.feo.ponzischeme.player;

import moe.feo.ponzischeme.sql.PrefixedTable;

public class PlayerProfile extends PrefixedTable {

    private String uuid;
    private String name;
    private Integer flarumId;
    private String flarumName;
    private Long flarumBinddate;
    private Integer bilibiliId;
    private String bilibiliName;
    private Long bilibiliBinddate;

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

    public Integer getFlarumId() {
        return flarumId;
    }

    public void setFlarumId(Integer flarumId) {
        this.flarumId = flarumId;
    }

    public String getFlarumName() {
        return flarumName;
    }

    public void setFlarumName(String flarumName) {
        this.flarumName = flarumName;
    }

    public Long getFlarumBinddate() {
        return flarumBinddate;
    }

    public void setFlarumBinddate(Long flarumBinddate) {
        this.flarumBinddate = flarumBinddate;
    }

    public Integer getBilibiliId() {
        return bilibiliId;
    }

    public void setBilibiliId(Integer bilibiliId) {
        this.bilibiliId = bilibiliId;
    }

    public String getBilibiliName() {
        return bilibiliName;
    }

    public void setBilibiliName(String bilibiliName) {
        this.bilibiliName = bilibiliName;
    }

    public Long getBilibiliBinddate() {
        return bilibiliBinddate;
    }

    public void setBilibiliBinddate(Long bilibiliBinddate) {
        this.bilibiliBinddate = bilibiliBinddate;
    }
}

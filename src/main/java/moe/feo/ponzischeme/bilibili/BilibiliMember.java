package moe.feo.ponzischeme.bilibili;

public class BilibiliMember {

    private String face;
    private byte face_nft; // 是否为 NFT 头像
    private byte face_nft_new; // 是否为新版 NFT 头像
    private int mid;
    private String name; // 昵称
    private Object name_render; // 有效时：obj; 无效时：null | 昵称渲染信息
    private Object nameplate; // 有效时：obj; 无效时：null | 勋章信息, 基本同「用户空间详细信息」中的 data.nameplate 对象
    private Object official; // 认证信息, 基本同「用户空间详细信息」中的 data.official 对象
    private Object pendant; // 有效时：obj; 无效时：null | 头像框信息, 基本同「用户空间详细信息」中的 data.pendant 对象，其中有些类型为 num 的字段在本接口中类型为 str
    private Object vip; // 会员信息, 基本同「用户空间详细信息」中的 data.vip 对象，其中有些类型为 num 的字段在本接口中类型为 str

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public byte getFace_nft() {
        return face_nft;
    }

    public void setFace_nft(byte face_nft) {
        this.face_nft = face_nft;
    }

    public byte getFace_nft_new() {
        return face_nft_new;
    }

    public void setFace_nft_new(byte face_nft_new) {
        this.face_nft_new = face_nft_new;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getName_render() {
        return name_render;
    }

    public void setName_render(Object name_render) {
        this.name_render = name_render;
    }

    public Object getNameplate() {
        return nameplate;
    }

    public void setNameplate(Object nameplate) {
        this.nameplate = nameplate;
    }

    public Object getOfficial() {
        return official;
    }

    public void setOfficial(Object official) {
        this.official = official;
    }

    public Object getPendant() {
        return pendant;
    }

    public void setPendant(Object pendant) {
        this.pendant = pendant;
    }

    public Object getVip() {
        return vip;
    }

    public void setVip(Object vip) {
        this.vip = vip;
    }
}

package moe.feo.ponzischeme.logic;

import moe.feo.ponzischeme.sql.MysqlDao;
import org.bukkit.entity.Player;

public class Bilibilitask {

    public static boolean PlayerISBindBiliID(Player player) {
        String FlarmumName = new MysqlDao().getPlayerProfile(player.getUniqueId().toString()).getFlarumName();
        if (FlarmumName == null) return false;else return true;
    }






}

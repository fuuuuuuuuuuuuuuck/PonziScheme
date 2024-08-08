package moe.feo.ponzischeme.logic;

import moe.feo.ponzischeme.Crawler;
import moe.feo.ponzischeme.flarum.FlarumPost;
import moe.feo.ponzischeme.sql.MysqlDao;
import moe.feo.ponzischeme.task.taskprofile.FlarumPostActivateTask;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BBStask {



    /**
     * 检查玩家是否已绑定BBS账号
     * 通过查询数据库中玩家的Flarum名称来判断玩家是否已经完成绑定
     * @param player 玩家，通过玩家来判断是否已绑定BBS账号
     * @return 如果玩家已绑定BBS账号，则返回true；否则返回false
     */
    public static boolean PlayerISBindBBSid(Player player) {
        String FlarmumName = new MysqlDao().getPlayerProfile(player.getUniqueId().toString()).getFlarumName();
        if (FlarmumName == null) return false;else return true;
    }

    /**
     * 判断玩家是否完成特定的顶贴任务
     * 本方法通过爬取指定论坛（Flarum）上玩家的活跃帖子来判断是否完成了特定的任务
     *
     * @param ID 帖子的ID，用于判断玩家是否完成了对应的任务
     * @param player 玩家，通过玩家来获取玩家的Flarum名称，用于爬取对应的帖子
     * @return 如果玩家完成了指定的任务，返回true；否则返回false
     */
    public static boolean PlayerISFinishBBSTask(int ID,Player player) {
        //todo 填充网址
        int findid = 0;
        ArrayList<FlarumPost> flarumPosts=Crawler.getFlarumActivateByUsername("flarum",new MysqlDao().getPlayerProfile(player.getUniqueId().toString()).getFlarumName());
        for (int i = 0; i < flarumPosts.size(); i++) {
            if (flarumPosts.get(i).getId() == ID) {
                findid=i;
            }
        }
        if(flarumPosts.get(findid).getId()==ID){
            return true;
        }else return false;

    }





}

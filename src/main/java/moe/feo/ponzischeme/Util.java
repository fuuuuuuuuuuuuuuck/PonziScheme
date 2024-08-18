package moe.feo.ponzischeme;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Util {

    private static ArrayList<Integer> runningtaskidlist = new ArrayList<Integer>();

    public static void addRunningTaskID(int i) {
        if (!runningtaskidlist.contains(i))
            runningtaskidlist.add(i);
    }

    public static void removeRunningTaskID(int i) {
        if (runningtaskidlist.contains(i))
            runningtaskidlist.remove((Integer) i);
    }

    /**
     * 将时间字符串转换为毫秒数。
     *
     * @param timeStr 时间字符串，例如 "1h30m"。
     * @return 对应的时间毫秒数。
     */
    public static long convertToMilliseconds(String timeStr) {
        Pattern pattern = Pattern.compile("(\\d+)([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(timeStr);

        long totalMilliseconds = 0;

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit.toLowerCase()) {
                case "s":
                    totalMilliseconds += value * 1000L; // 秒
                    break;
                case "m":
                    totalMilliseconds += value * 60 * 1000L; // 分钟
                    break;
                case "h":
                    totalMilliseconds += value * 60 * 60 * 1000L; // 小时
                    break;
                case "d":
                    totalMilliseconds += value * 24 * 60 * 60 * 1000L; // 天
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported time unit: " + unit);
            }
        }

        return totalMilliseconds;
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.ScheduledTask");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isPaper() {
        String versionInfo = Bukkit.getVersion();
        if (versionInfo.contains("Paper")) {
            return true;
        } else {
            return false;
        }
    }

    public static void runTaskAsynchronously(Runnable runnable, Player player) {
        Object scheduler;
        if (Util.isFolia()) {
            scheduler = player.getScheduler();
        } else {
            scheduler = Bukkit.getScheduler();
        }
        if (Util.isFolia()) {
            ((EntityScheduler) scheduler).execute(PonziScheme.getInstance(), runnable, runnable, 0);
        } else {
            ((BukkitScheduler) scheduler).runTaskAsynchronously(PonziScheme.getInstance(), runnable);
        }
    }

    public static void runTask(Runnable runnable, Player player) {
        Object scheduler;
        if (Util.isFolia()) {
            scheduler = player.getScheduler();
        } else {
            scheduler = Bukkit.getScheduler();
        }
        if (Util.isFolia()) {
            ((EntityScheduler) scheduler).execute(PonziScheme.getInstance(), runnable, runnable, 0);
        } else {
            ((BukkitScheduler) scheduler).runTask(PonziScheme.getInstance(), runnable);
        }
    }

    public static void runTaskGlobally(Runnable runnable) {
        Object scheduler;
        if (Util.isFolia()) {
            scheduler = PonziScheme.getInstance().getServer().getGlobalRegionScheduler();
        } else {
            scheduler = Bukkit.getScheduler();
        }
        if (Util.isFolia()) {
            Consumer<ScheduledTask> consumer = new Consumer<ScheduledTask>() {
                @Override
                public void accept(ScheduledTask scheduledTask) {
                    runnable.run();
                }
            };
            ((GlobalRegionScheduler) scheduler).run(PonziScheme.getInstance(), consumer);
        } else {
            ((BukkitScheduler) scheduler).runTask(PonziScheme.getInstance(), runnable);
        }
    }

    public static void runTaskLaterAsynchronously(Runnable runnable, long delay) {
        Object scheduler;
        if (Util.isFolia()) {
            scheduler = PonziScheme.getInstance().getServer().getAsyncScheduler();
        } else {
            scheduler = Bukkit.getScheduler();
        }
        if (Util.isFolia()) {
            // Folia 的脱裤子放屁的狗屎, Runnable能解决的非要搞个傻逼Consumer, 傻逼函数式, 狗都不用
            Consumer<ScheduledTask> consumer = new Consumer<ScheduledTask>() {
                @Override
                public void accept(ScheduledTask scheduledTask) {
                    runnable.run();
                }
            };
            ((AsyncScheduler) scheduler).runDelayed(PonziScheme.getInstance(), consumer, delay, TimeUnit.MILLISECONDS);
        } else {
            ((BukkitScheduler) scheduler).runTaskLaterAsynchronously(PonziScheme.getInstance(), runnable, delay / 50);
        }
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        Object scheduler;
        if (Util.isFolia()) {
            scheduler = PonziScheme.getInstance().getServer().getGlobalRegionScheduler();
        } else {
            scheduler = Bukkit.getScheduler();
        }
        if (Util.isFolia()) {
            Consumer<ScheduledTask> consumer = new Consumer<ScheduledTask>() {
                @Override
                public void accept(ScheduledTask scheduledTask) {
                    runnable.run();
                }
            };
            ((GlobalRegionScheduler) scheduler).runDelayed(PonziScheme.getInstance(), consumer, (delay));
        } else {
            ((BukkitScheduler) scheduler).runTaskLater(PonziScheme.getInstance(), runnable, delay);
        }
    }

    public static long getStartOfDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getStartOfWeek(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为周一
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int offset = dayOfWeek - Calendar.MONDAY;
        if (offset < 0) {
            offset += 7; // 如果已经是周一，则不需要调整
        }
        cal.add(Calendar.DAY_OF_MONTH, -offset);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getEndOfDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    public static long getEndOfWeek(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为周一
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int offset = dayOfWeek - Calendar.MONDAY;
        if (offset < 0) {
            offset += 7; // 如果已经是周一，则不需要调整
        }
        cal.add(Calendar.DAY_OF_MONTH, (7 - offset) % 7); // 调整到周日
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    /**
     * 计算给定时间与当前时间之间的差异，并以天、小时和分钟的形式返回。
     * 如果时间差不足一天，则不显示天数部分。
     *
     * @param pastTime 给定的过去时间（毫秒）
     * @return 时间差的描述字符串
     */
    public static String calculateTimeDifference(long pastTime) {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        LocalDateTime past = LocalDateTime.ofEpochSecond(pastTime / 1000, (int) (pastTime % 1000 * 1000000), zonedDateTime.getZone().getRules().getOffset(now));

        Duration duration = Duration.between(past, now);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" ").append(days == 1 ? "day" : "days").append(", ");
        }
        if (hours > 0 || days > 0) {
            result.append(hours).append(" ").append(hours == 1 ? "hour" : "hours").append(", ");
        }
        if (minutes > 0 || (hours > 0 || days > 0)) {
            result.append(minutes).append(" ").append(minutes == 1 ? "minute" : "minutes");
        }

        return result.toString();
    }
}

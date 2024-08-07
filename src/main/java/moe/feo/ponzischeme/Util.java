package moe.feo.ponzischeme;

import java.util.ArrayList;
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
}

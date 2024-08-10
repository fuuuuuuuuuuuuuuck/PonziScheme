package moe.feo.ponzischeme.task.taskentity;

public class Condition {

    public static final String REPEAT_DAYS = "days";
    public static final String REPEAT_WEEKS = "weeks";

    private String repeat;
    private int count;

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

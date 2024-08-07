package moe.feo.ponzischeme.task.taskentity;

public class BasePlayerTask implements PlayerTaskImpl{

    private String uuid;
    private String taskType;
    private String taskName;
    private String taskStatus;
    private String taskStartTime;
    private String taskEndTime;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getTaskType() {
        return taskType;
    }

    @Override
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String getTaskStatus() {
        return taskStatus;
    }

    @Override
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String getTaskStartTime() {
        return taskStartTime;
    }

    @Override
    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    @Override
    public String getTaskEndTime() {
        return taskEndTime;
    }

    @Override
    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }
}

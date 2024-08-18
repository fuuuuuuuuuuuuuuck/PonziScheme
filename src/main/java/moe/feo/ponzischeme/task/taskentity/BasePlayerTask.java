package moe.feo.ponzischeme.task.taskentity;

import moe.feo.ponzischeme.sql.PrefixedTable;

public class BasePlayerTask extends PrefixedTable implements PlayerTaskImpl{

    private String uuid;
    private String taskType;
    private String taskId;
    private String taskName;
    private String taskStatus;
    private Long taskStartTime;
    private Long taskEndTime;

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
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
    public Long getTaskStartTime() {
        return taskStartTime;
    }

    @Override
    public void setTaskStartTime(Long taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    @Override
    public Long getTaskEndTime() {
        return taskEndTime;
    }

    @Override
    public void setTaskEndTime(Long taskEndTime) {
        this.taskEndTime = taskEndTime;
    }
}

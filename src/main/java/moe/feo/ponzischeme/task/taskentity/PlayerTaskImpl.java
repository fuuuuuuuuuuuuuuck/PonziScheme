package moe.feo.ponzischeme.task.taskentity;

public interface PlayerTaskImpl {

    String getUuid();
    void setUuid(String uuid);
    String getTaskType();
    void setTaskType(String taskType);
    String getTaskId();
    void setTaskId(String taskId);
    String getTaskName();
    void setTaskName(String taskName);
    String getTaskStatus();
    void setTaskStatus(String taskStatus);
    long getTaskStartTime();
    void setTaskStartTime(long taskStartTime);
    long getTaskEndTime();
    void setTaskEndTime(long taskEndTime);
}

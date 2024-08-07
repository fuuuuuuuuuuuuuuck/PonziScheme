package moe.feo.ponzischeme.task.taskentity;

public interface PlayerTaskImpl {

    String getUuid();
    void setUuid(String uuid);
    String getTaskType();
    void setTaskType(String taskType);
    String getTaskName();
    void setTaskName(String taskName);
    String getTaskStatus();
    void setTaskStatus(String taskStatus);
    String getTaskStartTime();
    void setTaskStartTime(String taskStartTime);
    String getTaskEndTime();
    void setTaskEndTime(String taskEndTime);
}

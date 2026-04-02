package ilya.tsimerman.service;

import ilya.tsimerman.domain.data.dto.*;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponse getTaskById(Long id);

    PageResponse<TaskResponse> getAllTasks(Pageable pageable);

    void addTask(CreateTaskRequest request);

    void assignTask(Long taskId, AssignTaskRequest userIdDto);

    void updateTaskStatus(Long id, UpdateTaskStatusRequest request);
}

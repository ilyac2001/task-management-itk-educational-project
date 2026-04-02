package ilya.tsimerman.service.impl;

import ilya.tsimerman.domain.data.common.TaskStatus;
import ilya.tsimerman.domain.data.dto.*;
import ilya.tsimerman.domain.data.mapper.PageMapper;
import ilya.tsimerman.domain.data.mapper.TaskMapper;
import ilya.tsimerman.domain.data.model.Task;
import ilya.tsimerman.domain.data.model.User;
import ilya.tsimerman.domain.event.TaskEvent;
import ilya.tsimerman.domain.event.TaskEventType;
import ilya.tsimerman.domain.exception.EntityNotFoundException;
import ilya.tsimerman.domain.repository.TaskRepository;
import ilya.tsimerman.domain.repository.UserRepository;
import ilya.tsimerman.service.TaskService;
import ilya.tsimerman.service.kafka.TaskEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final PageMapper pageMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskEventPublisher taskEventPublisher;

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        return taskMapper.toDto(taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена %s".formatted(id))));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TaskResponse> getAllTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return pageMapper.toPageResponse(tasks, taskMapper::toDto);
    }

    @Override
    @Transactional
    public void addTask(CreateTaskRequest request) {
        Task task = taskMapper.toEntity(request);

        Task savedTask = taskRepository.save(task);

        taskEventPublisher.publish(TaskEvent.builder()
                .taskId(savedTask.getId())
                .eventType(TaskEventType.CREATED)
                .build()
        );
    }

    @Override
    @Transactional
    public void assignTask(Long taskId, AssignTaskRequest userIdDto) {
        Long userId = userIdDto.assigneeId();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена %s".formatted(taskId)));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден %s".formatted(userId)));

        task.setAssignee(user);
        task.setStatus(TaskStatus.IN_PROGRESS);

        taskEventPublisher.publish(TaskEvent.builder()
                .taskId(task.getId())
                .eventType(TaskEventType.ASSIGNED)
                .assignee(task.getAssignee().getId())
                .build()
        );
    }

    @Override
    @Transactional
    public void updateTaskStatus(Long id, UpdateTaskStatusRequest request) {
        TaskStatus newStatus = request.status();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена %s".formatted(id)));
        task.setStatus(newStatus);
    }
}

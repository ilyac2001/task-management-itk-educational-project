package ilya.tsimerman.controller;

import ilya.tsimerman.domain.data.dto.*;
import ilya.tsimerman.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable
            @Positive(message = "ID задачи должен быть положительным числом.")
            Long id
    ) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    public PageResponse<TaskResponse> getAllTasks(Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid CreateTaskRequest request) {
        taskService.addTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Новая задача добавлена");
    }

    @PatchMapping("/{id}/assignee")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignTask(
            @PathVariable
            @Positive(message = "ID задачи должен быть положительным числом.")
            Long id,
            @Valid @RequestBody AssignTaskRequest request
    ) {
        taskService.assignTask(id, request);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTaskStatus(
            @PathVariable
            @Positive(message = "ID задачи должен быть положительным числом.")
            Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        taskService.updateTaskStatus(id, request);
    }
}
package ilya.tsimerman.taskservice.domain.repository;

import ilya.tsimerman.taskservice.domain.data.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

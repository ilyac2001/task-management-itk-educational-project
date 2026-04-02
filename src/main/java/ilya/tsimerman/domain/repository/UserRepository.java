package ilya.tsimerman.domain.repository;

import ilya.tsimerman.domain.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}

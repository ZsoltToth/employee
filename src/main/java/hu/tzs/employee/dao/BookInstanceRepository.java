package hu.tzs.employee.dao;

import hu.tzs.employee.dao.entity.BookInstanceEntity;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInstanceRepository extends JpaRepository<BookInstanceEntity, String> {

    Collection<BookInstanceEntity> findAllByBook(String isbn);

}

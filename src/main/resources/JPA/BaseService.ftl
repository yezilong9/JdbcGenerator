package ${package}.base;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BaseService<T>{

  T create(T entity);
  T update(T entity);
  T get(int id);
  void delete(int id);
  Page<T> findAll(Pageable page);
  Page<T> findAll(Specification<T> specification,
      Pageable pageable);
  List<T> findAll(Specification<T> specification);
}

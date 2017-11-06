package ${package}.base;

import java.util.Date;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

  public abstract BaseRepository<T, Long> getRepository();

  public Page<T> findAll(Pageable page) {
    return getRepository().findAll(page);
  }

  @Override
  public T create(T entity) {
    if (entity.getId() != null) {
      return update(entity);
    }
    return getRepository().save(entity);
  }

  @Override
  public T update(T entity) {
    return getRepository().save(entity);
  }

  @Override
  public T get(int id) {
    return getRepository().findOne(id);
  }

  @Override
  public void delete(int id) {
    try {
      getRepository().delete(id);
    } catch (Exception e) {
      throw new EmptyResultDataAccessException(id);
    }
  }

  public Page<T> findAll(Specification<T> specification,
      Pageable pageable) {
    return getRepository().findAll(specification, pageable);
  }

  public List<T> findAll(Specification<T> specification) {
    return getRepository().findAll(specification);
  }
}

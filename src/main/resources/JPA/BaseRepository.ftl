package ${package}.base;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends CrudRepository<T, ID>,PagingAndSortingRepository<T, ID> {

    /**
     * 分页相关
     * @param pageable
     * @return
     */
    public Page<T> findAll(Pageable pageable);
    public int count(Specification<T> specification);

    public Page<T> findAll(Specification<T> specification,
        Pageable pageable);
    public List<T> findAll(Specification<T> specification);

}

package ${package}.service.impl;

import com.ubbcc.module.base.BaseRepository;
import com.ubbcc.module.base.BaseServiceImpl;
import ${package}.entity.${entityName};
import ${package}.repository.${entityName}Repository;
import ${package}.service.${entityName}Service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}> implements ${entityName}Service{

    @Autowired
    private ${entityName}Repository repository;

    @Override
    public BaseRepository<${entityName}, Long> getRepository() {
      return repository;
    }
}
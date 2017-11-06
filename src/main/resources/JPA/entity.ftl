package ${package}.entity;

import java.util.Date;
import ${package}.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="${tableName}")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
public class ${entityName} extends BaseEntity {

    <#list parameters as parameter>
    ${parameter.annotation}
    private ${parameter.columnType} ${parameter.columnName};
    </#list>


    <#list parameters as parameter>
    public void set${parameter.methodName}(${parameter.columnType} ${parameter.columnName}){
        this.${parameter.columnName} = ${parameter.columnName};
    }

    public ${parameter.columnType} get${parameter.methodName}(){
        return ${parameter.columnName};
    }
    </#list>

}
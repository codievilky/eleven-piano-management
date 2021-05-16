package idv.codievilky.august.eleven.pinao.store.repository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.sql.DataSource;


/**
 * @auther Codievilky August
 * @since 2021/5/16
 */
@Slf4j
public class BaseCustomizedRepository {
  @Autowired
  protected DataSource dataSource;

  // 由外界保证 increasedColumn1是数值类型的
  protected <T> int insertOnDupIncreaseEntity(T entity, String increasedColumn1Name) {
    Table tableType = entity.getClass().getAnnotation(Table.class);
    String tableName = null;
    if (tableType != null) {
      tableName = tableType.value();
    }

    String insertOnDupIncreaseTemplate =
        "INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE `%s` = `%s` + VALUES(`%s`)";
    List<String> insertColumns = new ArrayList<>();
    List<String> insertOriginalColumns = new ArrayList<>();

    parseInsertFieldFromEntity(entity, insertColumns, null, insertOriginalColumns, null, null);
    String columns = insertColumns.stream()
        .map(o -> String.format("`%s`", o))
        .collect(Collectors.joining(", "));
    String values = insertOriginalColumns.stream()
        .map(o -> String.format(":%s", o))
        .collect(Collectors.joining(", "));
    String insertOnDupIncreaseSql = String.format(insertOnDupIncreaseTemplate,
        tableName, columns, values, increasedColumn1Name, increasedColumn1Name, increasedColumn1Name);
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(entity);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    new NamedParameterJdbcTemplate(this.dataSource).update(
        insertOnDupIncreaseSql,
        parameters,
        keyHolder,
        new String[] {"ID"}
    );
    return Integer.parseInt(keyHolder.getKeyList().get(0).getOrDefault("GENERATED_KEY", "1").toString());
  }

  protected <T> int insertOnDupUpdateEntity(T entity, String updatedColumn1Name) {
    Table tableType = entity.getClass().getAnnotation(Table.class);
    String tableName = null;
    if (tableType != null) {
      tableName = tableType.value();
    }

    String insertOnDupUpdateTemplate = "INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE `%s` = VALUES(`%s`)";
    List<String> insertColumns = new ArrayList<>();
    List<String> insertOriginalColumns = new ArrayList<>();

    parseInsertFieldFromEntity(entity, insertColumns, null, insertOriginalColumns, null, null);
    String columns = insertColumns.stream()
        .map(o -> String.format("`%s`", o))
        .collect(Collectors.joining(", "));
    String values = insertOriginalColumns.stream()
        .map(o -> String.format(":%s", o))
        .collect(Collectors.joining(", "));
    String insertOnDupUpdateSql = String.format(insertOnDupUpdateTemplate,
        tableName, columns, values, updatedColumn1Name, updatedColumn1Name);
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(entity);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    new NamedParameterJdbcTemplate(this.dataSource).update(
        insertOnDupUpdateSql,
        parameters,
        keyHolder,
        new String[] {"ID"}
    );
    if (CollectionUtils.isEmpty(keyHolder.getKeyList())) {
      return 1;
    } else {
      return Integer.parseInt(keyHolder.getKeyList().get(0).getOrDefault("GENERATED_KEY", "1").toString());
    }
  }

  protected <T> void insertOnDupIncreaseAllEntities(List<T> entities, String increasedColumn1Name) {
    if (entities.isEmpty()) {
      return;
    }
    Table tableType = entities.get(0).getClass().getAnnotation(Table.class);
    String tableName = null;
    if (tableType != null) {
      tableName = tableType.value();
    }

    String insertOnDupIncreaseTemplate =
        "INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE `%s` = `%s` + VALUES(`%s`)";

    String columns;
    String values;
    T entity = entities.get(0);
    List<String> insertColumns = new ArrayList<>();
    List<String> insertOriginalColumns = new ArrayList<>();
    parseInsertFieldFromEntity(entity, insertColumns, null, insertOriginalColumns, null, null);
    columns = insertColumns.stream()
        .map(o -> String.format("`%s`", o))
        .collect(Collectors.joining(", "));
    values = insertOriginalColumns.stream()
        .map(o -> String.format(":%s", o))
        .collect(Collectors.joining(", "));

    String insertOnDupIncreaseSql = String.format(insertOnDupIncreaseTemplate,
        tableName, columns, values, increasedColumn1Name, increasedColumn1Name, increasedColumn1Name);
    SqlParameterSource[] parameterSources = new SqlParameterSource[entities.size()];
    for (int i = 0; i < entities.size(); ++i) {
      parameterSources[i] = new BeanPropertySqlParameterSource(entities.get(i));
    }
    new NamedParameterJdbcTemplate(this.dataSource).batchUpdate(
        insertOnDupIncreaseSql, parameterSources);
  }

  protected <T> int replaceEntity(T entity) {
    Table tableType = entity.getClass().getAnnotation(Table.class);
    String tableName = null;
    if (tableType != null) {
      tableName = tableType.value();
    }

    String replaceTemplate = "REPLACE INTO %s (%s) VALUES (%s)";
    List<String> replaceColumns = new ArrayList<>();
    List<String> replaceOriginalColumns = new ArrayList<>();

    parseInsertFieldFromEntity(entity, replaceColumns, null, replaceOriginalColumns, null, null);
    String columns = replaceColumns.stream()
        .map(o -> String.format("`%s`", o))
        .collect(Collectors.joining(", "));
    String values = replaceOriginalColumns.stream()
        .map(o -> String.format(":%s", o))
        .collect(Collectors.joining(", "));
    String replaceSql = String.format(replaceTemplate, tableName, columns, values);
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(entity);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    new NamedParameterJdbcTemplate(this.dataSource).update(
        replaceSql,
        parameters,
        keyHolder,
        new String[] {"ID"}
    );
    return Integer.parseInt(keyHolder.getKeyList().get(0).getOrDefault("GENERATED_KEY", "1").toString());
  }

  protected <T> int updateEntityNotNullFieldsById(T entity) {
    Table tableType = entity.getClass().getAnnotation(Table.class);
    String tableName = null;
    if (tableType != null) {
      tableName = tableType.value();
    }
    String updateTemplate = "UPDATE %s SET ";
    final String[] idColumn = {null};
    List<String> updateColumns = new ArrayList<>();
    List<Object> updateValues = new ArrayList<>();
    final Object[] idValue = {null};

    parseUpdateFieldFromEntity(entity, updateColumns, updateValues, null, idColumn, idValue);
    if (updateColumns.isEmpty()) {
      return 0;
    }

    updateTemplate += updateColumns.stream()//
        .map(n -> String.format("`%s` = ?", n))//
        .collect(Collectors.joining(", "));

    updateTemplate += " WHERE `%s` = ?";
    updateValues.add(idValue[0]);
    return new JdbcTemplate(this.dataSource)
        .update(String.format(updateTemplate, tableName, idColumn[0]), updateValues.toArray());
  }


  private <T> void parseUpdateFieldFromEntity(T entity, final List<String> columns, final List<Object> values,
      final List<String> originalColumns, final String[] idColumn, final Object[] idValue) {
    parseFieldFromEntity(entity, columns, values, originalColumns, idColumn, idValue, true);
  }

  private <T> void parseInsertFieldFromEntity(T entity, final List<String> columns, final List<Object> values,
      final List<String> originalColumns, final String[] idColumn, final Object[] idValue) {
    parseFieldFromEntity(entity, columns, values, originalColumns, idColumn, idValue, false);
  }

  private <T> void parseFieldFromEntity(T entity, final List<String> columns, final List<Object> values,
      final List<String> originalColumns, final String[] idColumn, final Object[] idValue, final boolean insertOnly) {
    ReflectionUtils.doWithFields(entity.getClass(), (Field field) -> {
      if (field.isAnnotationPresent(Transient.class)
          || (insertOnly && field.isAnnotationPresent(ReadOnlyProperty.class))
          || Modifier.isStatic(field.getModifiers())) {
        return;
      }
      Column column = field.getAnnotation(Column.class);
      String columnName;
      // 和 Spring Jdbc 一样，支持 Column 注解的定义方式，如果没用就直接转下划线
      if (column == null) {
        columnName = underscoreName(field.getName());
      } else {
        columnName = column.value();
      }
      ReflectionUtils.makeAccessible(field);
      Object fieldValue = ReflectionUtils.getField(field, entity);
      Id id = field.getAnnotation(Id.class);
      if (idColumn != null && idValue != null && id != null) {
        idColumn[0] = columnName;
        idValue[0] = fieldValue;
      } else if (fieldValue != null) {
        columns.add(columnName);
        if (values != null) {
          values.add(fieldValue);
        }
        if (originalColumns != null) {
          originalColumns.add(field.getName());
        }
      }
    });
  }

  private String underscoreName(String name) {
    if (!StringUtils.hasLength(name)) {
      return "";
    }
    StringBuilder result = new StringBuilder();
    result.append(lowerCaseName(name.substring(0, 1)));
    for (int i = 1; i < name.length(); i++) {
      String s = name.substring(i, i + 1);
      String slc = lowerCaseName(s);
      if (!s.equals(slc)) {
        result.append("_").append(slc);
      } else {
        result.append(s);
      }
    }
    return result.toString();
  }

  private String lowerCaseName(String name) {
    return name.toLowerCase(Locale.US);
  }

}

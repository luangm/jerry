package io.luan.jerry.category.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BasePropertyMapper {

    @DeleteProvider(type = PropertySqlProvider.class, method = "delete")
    int delete(BasePropertyDO property);

    @SelectProvider(type = PropertySqlProvider.class, method = "findAll")
    @ResultMap("propertyResult")
    List<BasePropertyDO> findAll();

    @SelectProvider(type = PropertySqlProvider.class, method = "findById")
    @Results(id = "propertyResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "status", property = "status"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    BasePropertyDO findById(Long id);

    @InsertProvider(type = PropertySqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(BasePropertyDO property);

    @DeleteProvider(type = PropertySqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = PropertySqlProvider.class, method = "update")
    void update(BasePropertyDO property);

    class PropertySqlProvider {

        private static final String TABLE_PROPERTY = "`base_property`";

        public static String delete(final BasePropertyDO property) {
            return new SQL() {{
                DELETE_FROM(TABLE_PROPERTY);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PROPERTY);
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PROPERTY);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String insert(final BasePropertyDO property) {
            return new SQL() {{
                INSERT_INTO(TABLE_PROPERTY);
                VALUES("name", "#{name}");
                VALUES("status", "#{status}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_PROPERTY);
            }}.toString();
        }

        public static String update(final BasePropertyDO property) {
            return new SQL() {{
                UPDATE(TABLE_PROPERTY);
                SET("name = #{name}");
                SET("status = #{status}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

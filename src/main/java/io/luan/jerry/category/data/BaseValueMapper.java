package io.luan.jerry.category.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BaseValueMapper {

    @DeleteProvider(type = ValueSqlProvider.class, method = "delete")
    int delete(BaseValueDO value);

    @SelectProvider(type = ValueSqlProvider.class, method = "findAll")
    @ResultMap("valueResult")
    List<BaseValueDO> findAll();

    @SelectProvider(type = ValueSqlProvider.class, method = "findById")
    @Results(id = "valueResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "value", property = "value"),
            @Result(column = "status", property = "status"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    BaseValueDO findById(Long id);

    @InsertProvider(type = ValueSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(BaseValueDO value);

    @DeleteProvider(type = ValueSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = ValueSqlProvider.class, method = "update")
    void update(BaseValueDO value);

    class ValueSqlProvider {

        private static final String TABLE_VALUE = "`base_value`";

        public static String delete(final BaseValueDO value) {
            return new SQL() {{
                DELETE_FROM(TABLE_VALUE);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_VALUE);
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_VALUE);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String insert(final BaseValueDO value) {
            return new SQL() {{
                INSERT_INTO(TABLE_VALUE);
                VALUES("value", "#{value}");
                VALUES("status", "#{status}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_VALUE);
            }}.toString();
        }

        public static String update(final BaseValueDO value) {
            return new SQL() {{
                UPDATE(TABLE_VALUE);
                SET("value = #{value}");
                SET("status = #{status}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

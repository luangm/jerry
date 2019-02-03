package io.luan.jerry.category.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CategoryPropertyValueMapper {

    @DeleteProvider(type = CategoryPropertyValueValueSqlProvider.class, method = "delete")
    int delete(CategoryPropertyValueDO property);

    @SelectProvider(type = CategoryPropertyValueValueSqlProvider.class, method = "findAllByCategoryId")
    @Results(id = "cpResult", value = {
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "property_id", property = "propertyId"),
            @Result(column = "value_id", property = "valueId"),
            @Result(column = "alias", property = "alias"),
            @Result(column = "sort_order", property = "sortOrder"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    List<CategoryPropertyValueDO> findAllByCategoryId(Long categoryId);

    @InsertProvider(type = CategoryPropertyValueValueSqlProvider.class, method = "insert")
    void insert(CategoryPropertyValueDO property);

    @DeleteProvider(type = CategoryPropertyValueValueSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = CategoryPropertyValueValueSqlProvider.class, method = "update")
    void update(CategoryPropertyValueDO property);

    class CategoryPropertyValueValueSqlProvider {

        private static final String TABLE_CATEGORY_PROPERTY_VALUE = "`category_property_value`";

        public static String delete(final CategoryPropertyValueDO cpv) {
            return new SQL() {{
                DELETE_FROM(TABLE_CATEGORY_PROPERTY_VALUE);
                WHERE("category_id = #{categoryId}");
                WHERE("property_id = #{propertyId}");
                WHERE("value_id = #{valueId}");
            }}.toString();
        }

        public static String findAllByCategoryId() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_CATEGORY_PROPERTY_VALUE);
                WHERE("category_id = #{categoryId}");
            }}.toString();
        }

        public static String insert(final CategoryPropertyValueDO cpv) {
            return new SQL() {{
                INSERT_INTO(TABLE_CATEGORY_PROPERTY_VALUE);
                VALUES("category_id", "#{categoryId}");
                VALUES("property_id", "#{propertyId}");
                VALUES("value_id", "#{valueId}");
                VALUES("alias", "#{alias}");
                VALUES("sort_order", "#{sortOrder}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_CATEGORY_PROPERTY_VALUE);
            }}.toString();
        }

        public static String update(final CategoryPropertyValueDO cpv) {
            return new SQL() {{
                UPDATE(TABLE_CATEGORY_PROPERTY_VALUE);
                SET("alias = #{alias}");
                SET("sort_order = #{sortOrder}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("category_id = #{categoryId}");
                WHERE("property_id = #{propertyId}");
                WHERE("value_id = #{valueId}");
            }}.toString();
        }
    }
}

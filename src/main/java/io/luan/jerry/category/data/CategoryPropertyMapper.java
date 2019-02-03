package io.luan.jerry.category.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CategoryPropertyMapper {

    @DeleteProvider(type = CategoryPropertySqlProvider.class, method = "delete")
    int delete(CategoryPropertyDO property);

    @SelectProvider(type = CategoryPropertySqlProvider.class, method = "findAllByCategoryId")
    @Results(id = "cpResult", value = {
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "property_id", property = "propertyId"),
            @Result(column = "sort_order", property = "sortOrder"),
            @Result(column = "alias", property = "alias"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    List<CategoryPropertyDO> findAllByCategoryId(Long categoryId);

    @InsertProvider(type = CategoryPropertySqlProvider.class, method = "insert")
    void insert(CategoryPropertyDO property);

    @DeleteProvider(type = CategoryPropertySqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = CategoryPropertySqlProvider.class, method = "update")
    void update(CategoryPropertyDO property);

    class CategoryPropertySqlProvider {

        private static final String TABLE_CATEGORY_PROPERTY = "`category_property`";

        public static String delete(final CategoryPropertyDO cp) {
            return new SQL() {{
                DELETE_FROM(TABLE_CATEGORY_PROPERTY);
                WHERE("category_id = #{categoryId}");
                WHERE("property_id = #{propertyId}");
            }}.toString();
        }

        public static String findAllByCategoryId() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_CATEGORY_PROPERTY);
                WHERE("category_id = #{categoryId}");
            }}.toString();
        }

        public static String insert(final CategoryPropertyDO cp) {
            return new SQL() {{
                INSERT_INTO(TABLE_CATEGORY_PROPERTY);
                VALUES("category_id", "#{categoryId}");
                VALUES("property_id", "#{propertyId}");
                VALUES("alias", "#{alias}");
                VALUES("sort_order", "#{sortOrder}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_CATEGORY_PROPERTY);
            }}.toString();
        }

        public static String update(final CategoryPropertyDO cp) {
            return new SQL() {{
                UPDATE(TABLE_CATEGORY_PROPERTY);
                SET("alias = #{alias}");
                SET("sort_order = #{sortOrder}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("category_id = #{categoryId}");
                WHERE("property_id = #{propertyId}");
            }}.toString();
        }
    }
}

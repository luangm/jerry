package io.luan.jerry.category.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CategoryMapper {

    @DeleteProvider(type = CategorySqlProvider.class, method = "delete")
    int delete(CategoryDO category);

    @SelectProvider(type = CategorySqlProvider.class, method = "findAll")
    @ResultMap("categoryResult")
    List<CategoryDO> findAll();

    @SelectProvider(type = CategorySqlProvider.class, method = "findById")
    @Results(id = "categoryResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name"),
            @Result(column = "is_leaf", property = "isLeaf"),
            @Result(column = "status", property = "status"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    CategoryDO findById(Long id);

    @InsertProvider(type = CategorySqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(CategoryDO category);

    @DeleteProvider(type = CategorySqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = CategorySqlProvider.class, method = "update")
    void update(CategoryDO category);

    class CategorySqlProvider {

        private static final String TABLE_CATEGORY = "`category`";

        public static String delete(final CategoryDO category) {
            return new SQL() {{
                DELETE_FROM(TABLE_CATEGORY);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_CATEGORY);
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_CATEGORY);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String insert(final CategoryDO category) {
            return new SQL() {{
                INSERT_INTO(TABLE_CATEGORY);
                VALUES("parent_id", "#{parentId}");
                VALUES("name", "#{name}");
                VALUES("is_leaf", "#{isLeaf}");
                VALUES("status", "#{status}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_CATEGORY);
            }}.toString();
        }

        public static String update(final CategoryDO category) {
            return new SQL() {{
                UPDATE(TABLE_CATEGORY);
                SET("parent_id = #{parentId}");
                SET("name = #{name}");
                SET("is_leaf = #{isLeaf}");
                SET("status = #{status}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

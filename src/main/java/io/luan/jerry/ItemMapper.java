package io.luan.jerry;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ItemMapper {

    @SelectProvider(type = ItemSqlProvider.class, method = "findAll")
    @ResultMap("itemResult")
    List<Item> findAll();

    @SelectProvider(type = ItemSqlProvider.class, method = "findById")
    @Results(id = "itemResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    Item findById(@Param("id") long id);

    @InsertProvider(type = ItemSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(Item item);

    class ItemSqlProvider {

        private static final String TABLE_ITEM = "`item`";

        public static String findById(final long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_ITEM);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_ITEM);
            }}.toString();
        }

        public static String insert(final Item item) {
            return new SQL() {{
                INSERT_INTO(TABLE_ITEM);
                VALUES("title", "#{title}");
                if (item.getGmtCreate() != null) {
                    VALUES("gmt_create", "#{gmtCreate}");
                }
                if (item.getGmtModified() != null) {
                    VALUES("gmt_modified", "#{gmtModified}");
                }
            }}.toString();
        }
    }
}

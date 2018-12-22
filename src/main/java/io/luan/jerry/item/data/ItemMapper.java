package io.luan.jerry.item.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ItemMapper {

    @DeleteProvider(type = ItemSqlProvider.class, method = "delete")
    int delete(ItemDO item);

    @SelectProvider(type = ItemSqlProvider.class, method = "findAll")
    @ResultMap("itemResult")
    List<ItemDO> findAll();

    @SelectProvider(type = ItemSqlProvider.class, method = "findById")
    @Results(id = "itemResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "price", property = "price"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    ItemDO findById(Long id);

    @InsertProvider(type = ItemSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(ItemDO item);

    @DeleteProvider(type = ItemSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    class ItemSqlProvider {

        private static final String TABLE_ITEM = "`item`";

        public static String delete(final ItemDO item) {
            return new SQL() {{
                DELETE_FROM(TABLE_ITEM);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_ITEM);
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_ITEM);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String insert(final ItemDO item) {
            return new SQL() {{
                INSERT_INTO(TABLE_ITEM);
                VALUES("title", "#{title}");
                VALUES("price", "#{price}");
                if (item.getGmtCreate() != null) {
                    VALUES("gmt_create", "#{gmtCreate}");
                }
                if (item.getGmtModified() != null) {
                    VALUES("gmt_modified", "#{gmtModified}");
                }
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_ITEM);
            }}.toString();
        }
    }
}

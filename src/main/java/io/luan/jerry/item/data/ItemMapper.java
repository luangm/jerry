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
            @Result(column = "user_id", property = "userId"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "inventory_id", property = "inventoryId"),
            @Result(column = "title", property = "title"),
            @Result(column = "img_url", property = "imgUrl"),
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

    @UpdateProvider(type = ItemSqlProvider.class, method = "update")
    void update(ItemDO item);

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
                VALUES("user_id", "#{userId}");
                VALUES("category_id", "#{categoryId}");
                VALUES("inventory_id", "#{inventoryId}");
                VALUES("title", "#{title}");
                VALUES("img_url", "#{imgUrl}");
                VALUES("price", "#{price}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_ITEM);
            }}.toString();
        }

        public static String update(final ItemDO item) {
            return new SQL() {{
                UPDATE(TABLE_ITEM);
                SET("inventory_id = #{inventoryId}");
                SET("title = #{title}");
                SET("img_url = #{imgUrl}");
                SET("price = #{price}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

package io.luan.jerry.item.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SkuMapper {

    @DeleteProvider(type = SkuSqlProvider.class, method = "delete")
    int delete(SkuDO sku);

    @SelectProvider(type = SkuSqlProvider.class, method = "findAll")
    @ResultMap("skuResult")
    List<SkuDO> findAll();

    @SelectProvider(type = SkuSqlProvider.class, method = "findAllByItemId")
    @ResultMap("skuResult")
    List<SkuDO> findAllByItemId(Long id);

    @SelectProvider(type = SkuSqlProvider.class, method = "findById")
    @Results(id = "skuResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "item_id", property = "itemId"),
            @Result(column = "img_url", property = "imgUrl"),
            @Result(column = "price", property = "price"),
            @Result(column = "inventory_id", property = "inventoryId"),
            @Result(column = "properties", property = "properties"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    SkuDO findById(Long id);

    @InsertProvider(type = SkuSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(SkuDO sku);

    @DeleteProvider(type = SkuSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = SkuSqlProvider.class, method = "update")
    void update(SkuDO sku);

    class SkuSqlProvider {

        private static final String TABLE_SKU = "`sku`";

        public static String delete(final SkuDO sku) {
            return new SQL() {{
                DELETE_FROM(TABLE_SKU);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_SKU);
            }}.toString();
        }

        public static String findAllByItemId(final Long itemId) {
            return new SQL() {{
                SELECT("*");
                WHERE("item_id = #{itemId}");
                FROM(TABLE_SKU);
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_SKU);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String insert(final SkuDO sku) {
            return new SQL() {{
                INSERT_INTO(TABLE_SKU);
                VALUES("item_id", "#{itemId}");
                VALUES("img_url", "#{imgUrl}");
                VALUES("price", "#{price}");
                VALUES("inventory_id", "#{inventoryId}");
                VALUES("properties", "#{properties}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_SKU);
            }}.toString();
        }

        public static String update(final SkuDO sku) {
            return new SQL() {{
                UPDATE(TABLE_SKU);
                SET("img_url = #{imgUrl}");
                SET("price = #{price}");
                SET("inventory_id = #{inventoryId}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

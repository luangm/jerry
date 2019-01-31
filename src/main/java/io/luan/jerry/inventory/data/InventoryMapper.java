package io.luan.jerry.inventory.data;

import io.luan.jerry.common.utils.ArrayUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface InventoryMapper {

    @DeleteProvider(type = InventorySqlProvider.class, method = "delete")
    int delete(InventoryDO inventory);

    @SelectProvider(type = InventorySqlProvider.class, method = "findAll")
    @ResultMap("inventoryResult")
    List<InventoryDO> findAll();

    @SelectProvider(type = InventorySqlProvider.class, method = "findBatch")
    @ResultMap("inventoryResult")
    List<InventoryDO> findBatch(@Param("ids") List<Long> ids);

    @SelectProvider(type = InventorySqlProvider.class, method = "findById")
    @Results(id = "inventoryResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "item_id", property = "itemId"),
            @Result(column = "available", property = "available"),
            @Result(column = "withheld", property = "withheld"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "version", property = "version"),
    })
    InventoryDO findById(Long id);

    @InsertProvider(type = InventorySqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    int insert(InventoryDO inventory);

    @DeleteProvider(type = InventorySqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = InventorySqlProvider.class, method = "update")
    int update(InventoryDO inventory);

    class InventorySqlProvider {

        private static final String TABLE_INVENTORY = "`inventory`";

        public static String delete(final InventoryDO inventory) {
            return new SQL() {{
                DELETE_FROM(TABLE_INVENTORY);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_INVENTORY);
            }}.toString();
        }

        public static String findBatch(final List<Long> ids) {
            var idList = ArrayUtils.arrayToString(ids, ",");
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_INVENTORY);
                WHERE("id IN (" + idList + ")");
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_INVENTORY);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String insert(final InventoryDO inventory) {
            return new SQL() {{
                INSERT_INTO(TABLE_INVENTORY);
                VALUES("item_id", "#{itemId}");
                VALUES("available", "#{available}");
                VALUES("withheld", "#{withheld}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
                VALUES("version", "1");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_INVENTORY);
            }}.toString();
        }

        public static String update(final InventoryDO inventory) {
            return new SQL() {{
                UPDATE(TABLE_INVENTORY);
                SET("available = #{available}");
                SET("withheld = #{withheld}");
                SET("gmt_modified = #{gmtModified}");
                SET("version = version + 1");
                WHERE("id = #{id}");
                WHERE("version = #{version}");
            }}.toString();
        }
    }
}

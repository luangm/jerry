package io.luan.jerry.order.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {

    @DeleteProvider(type = OrderSqlProvider.class, method = "delete")
    int delete(OrderDO orderDO);


    @SelectProvider(type = OrderSqlProvider.class, method = "findAll")
    @ResultMap("orderResult")
    List<OrderDO> findAll();

    @SelectProvider(type = OrderSqlProvider.class, method = "findAllByParentId")
    @ResultMap("orderResult")
    List<OrderDO> findAllByParentId(Long parentId);

    @SelectProvider(type = OrderSqlProvider.class, method = "findById")
    @Results(id = "orderResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "is_main", property = "isMain"),
            @Result(column = "is_sub", property = "isSub"),
            @Result(column = "buyer_id", property = "buyerId"),
            @Result(column = "seller_id", property = "sellerId"),
            @Result(column = "item_id", property = "itemId"),
            @Result(column = "quantity", property = "quantity"),
            @Result(column = "total_fee", property = "totalFee"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    OrderDO findById(Long id);

    @InsertProvider(type = OrderSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(OrderDO order);

    @DeleteProvider(type = OrderSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = OrderSqlProvider.class, method = "update")
    void update(OrderDO order);

    class OrderSqlProvider {

        private static final String TABLE_ORDER = "`order`";

        public static String delete(final OrderDO order) {
            return new SQL() {{
                DELETE_FROM(TABLE_ORDER);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_ORDER);
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_ORDER);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAllByParentId(final Long parentId) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_ORDER);
                WHERE("parent_id = #{parentId}");
            }}.toString();
        }

        public static String insert(final OrderDO order) {
            return new SQL() {{
                INSERT_INTO(TABLE_ORDER);
                VALUES("parent_id", "#{parentId}");
                VALUES("is_main", "#{isMain}");
                VALUES("is_sub", "#{isSub}");
                VALUES("buyer_id", "#{buyerId}");
                VALUES("seller_id", "#{sellerId}");
                VALUES("item_id", "#{itemId}");
                VALUES("quantity", "#{quantity}");
                VALUES("total_fee", "#{totalFee}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_ORDER);
            }}.toString();
        }

        public static String update(final OrderDO order) {
            return new SQL() {{
                UPDATE(TABLE_ORDER);
                SET("parent_id = #{parentId}");
                SET("is_main = #{isMain}");
                SET("is_sub = #{isSub}");
                SET("buyer_id = #{buyerId}");
                SET("seller_id = #{sellerId}");
                SET("item_id = #{itemId}");
                SET("quantity = #{quantity}");
                SET("total_fee = #{totalFee}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

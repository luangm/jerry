package io.luan.jerry.cart.data;

import io.luan.jerry.cart.domain.CartItemState;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CartItemMapper {

    //    @DeleteProvider(type = DeliveryAddressSqlProvider.class, method = "delete")
//    int delete(ItemDO item);
//
    @SelectProvider(type = CartSqlProvider.class, method = "findAllByUserId")
    @ResultMap("cartResult")
    List<CartItemDO> findAllByUserId(Long userId);

    @SelectProvider(type = CartSqlProvider.class, method = "findById")
    @Results(id = "cartResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "item_id", property = "itemId"),
            @Result(column = "quantity", property = "quantity"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "status", property = "status")
    })
    CartItemDO findById(Long id);

    @InsertProvider(type = CartSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(CartItemDO cartItemDO);

//    @DeleteProvider(type = io.luan.jerry.item.data.ItemMapper.ItemSqlProvider.class, method = "unsafeDeleteAll")
//    int unsafeDeleteAll();
//
    @UpdateProvider(type = CartItemMapper.CartSqlProvider.class, method = "update")
    void update(CartItemDO cartItemDO);

    class CartSqlProvider {

        private static final String TABLE_CART = "`cart`";

//        public static String delete(final ItemDO item) {
//            return new SQL() {{
//                DELETE_FROM(TABLE_CART);
//                WHERE("id = #{id}");
//            }}.toString();
//        }

        public static String findAllByUserId(final Long userId) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_CART);
                WHERE("user_id = #{userId}");
                WHERE("status = " + CartItemState.Normal.getValue());
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_CART);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String insert(final CartItemDO cartItemDO) {
            return new SQL() {{
                INSERT_INTO(TABLE_CART);
                VALUES("user_id", "#{userId}");
                VALUES("item_id", "#{itemId}");
                VALUES("quantity", "#{quantity}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
                VALUES("status", "#{status}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_CART);
            }}.toString();
        }

        public static String update(final CartItemDO cartItemDO) {
            return new SQL() {{
                UPDATE(TABLE_CART);
                SET("quantity = #{quantity}");
                SET("status = #{status}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

package io.luan.jerry;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper {

    @SelectProvider(type = OrderSqlProvider.class, method = "findById")
    @Results(id = "orderResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "item_id", property = "itemId"),
            @Result(column = "total_fee", property = "totalFee"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    Order findById(@Param("id") long id);

    @InsertProvider(type = OrderSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(Order order);

    class OrderSqlProvider {

        private static final String TABLE_ORDER = "`order`";

        public static String findById(final long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_ORDER);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String insert(final Order order) {
            return new SQL() {{
                INSERT_INTO(TABLE_ORDER);
                VALUES("user_id", "#{userId}");
                VALUES("item_id", "#{itemId}");
                VALUES("total_fee", "#{totalFee}");
                if (order.getGmtCreate() != null) {
                    VALUES("gmt_create", "#{gmtCreate}");
                }
                if (order.getGmtModified() != null) {
                    VALUES("gmt_modified", "#{gmtModified}");
                }
            }}.toString();
        }
    }
}

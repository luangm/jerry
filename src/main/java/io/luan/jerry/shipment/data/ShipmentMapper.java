package io.luan.jerry.shipment.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ShipmentMapper {

    @DeleteProvider(type = ShipmentSqlProvider.class, method = "delete")
    int delete(ShipmentDO shipmentDO);

    @SelectProvider(type = ShipmentSqlProvider.class, method = "findAll")
    @ResultMap("shipmentResult")
    List<ShipmentDO> findAll();

    @SelectProvider(type = ShipmentSqlProvider.class, method = "findById")
    @Results(id = "shipmentResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "order_id", property = "orderId"),
            @Result(column = "buyer_id", property = "buyerId"),
            @Result(column = "seller_id", property = "sellerId"),
            @Result(column = "method", property = "method"),
            @Result(column = "address_address", property = "addressAddress"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "status", property = "status")
    })
    ShipmentDO findById(Long id);

    @SelectProvider(type = ShipmentSqlProvider.class, method = "findByOrderId")
    @ResultMap("shipmentResult")
    ShipmentDO findByOrderId(Long orderId);

    @InsertProvider(type = ShipmentSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(ShipmentDO shipmentDO);

    @DeleteProvider(type = ShipmentSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = ShipmentSqlProvider.class, method = "update")
    void update(ShipmentDO shipmentDO);

    class ShipmentSqlProvider {

        private static final String TABLE_SHIPMENT = "`shipment`";

        public static String delete(final ShipmentDO shipmentDO) {
            return new SQL() {{
                DELETE_FROM(TABLE_SHIPMENT);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_SHIPMENT);
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_SHIPMENT);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findByOrderId(final Long orderId) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_SHIPMENT);
                WHERE("order_id = #{orderId}");
            }}.toString();
        }

        public static String insert(final ShipmentDO shipmentDO) {
            return new SQL() {{
                INSERT_INTO(TABLE_SHIPMENT);
                VALUES("order_id", "#{orderId}");
                VALUES("buyer_id", "#{buyerId}");
                VALUES("seller_id", "#{sellerId}");
                VALUES("method", "#{method}");
                VALUES("address_address", "#{addressAddress}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
                VALUES("status", "#{status}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_SHIPMENT);
            }}.toString();
        }

        public static String update(final ShipmentDO shipmentDO) {
            return new SQL() {{
                UPDATE(TABLE_SHIPMENT);
                SET("gmt_modified = #{gmtModified}");
                SET("status = #{status}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

package io.luan.jerry.address.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DeliveryAddressMapper {

    @UpdateProvider(type = DeliveryAddressSqlProvider.class, method = "delete")
    int delete(DeliveryAddressDO deliveryAddressDO);

    @SelectProvider(type = DeliveryAddressSqlProvider.class, method = "findAllByUserId")
    @ResultMap("deliveryAddressResult")
    List<DeliveryAddressDO> findAllByUserId(Long userId);

    @SelectProvider(type = DeliveryAddressSqlProvider.class, method = "findById")
    @Results(id = "deliveryAddressResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "receiver", property = "receiver"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "address_address", property = "addressAddress"),
            @Result(column = "is_default", property = "isDefault"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "is_deleted", property = "isDeleted")
    })
    DeliveryAddressDO findById(Long id);

    @SelectProvider(type = DeliveryAddressSqlProvider.class, method = "findDefaultByUserId")
    @ResultMap("deliveryAddressResult")
    DeliveryAddressDO findDefaultByUserId(Long userId);

    @InsertProvider(type = DeliveryAddressSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(DeliveryAddressDO deliveryAddressDO);

    @DeleteProvider(type = DeliveryAddressSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = DeliveryAddressSqlProvider.class, method = "update")
    void update(DeliveryAddressDO deliveryAddressItemDO);

    class DeliveryAddressSqlProvider {

        private static final String TABLE_DELIVERY_ADDRESS = "`delivery_address`";

        public static String delete(final DeliveryAddressDO deliveryAddressDO) {
            return new SQL() {{
                UPDATE(TABLE_DELIVERY_ADDRESS);
                SET("is_deleted = 1");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAllByUserId(final Long userId) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_DELIVERY_ADDRESS);
                WHERE("user_id = #{userId}");
                WHERE("is_deleted = 0");
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_DELIVERY_ADDRESS);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findDefaultByUserId(final Long userId) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_DELIVERY_ADDRESS);
                WHERE("is_default = 1");
                ORDER_BY("gmt_modified desc");
            }}.toString() + " LIMIT 1";
        }

        public static String insert(final DeliveryAddressDO deliveryAddressDO) {
            return new SQL() {{
                INSERT_INTO(TABLE_DELIVERY_ADDRESS);
                VALUES("user_id", "#{userId}");
                VALUES("receiver", "#{receiver}");
                VALUES("phone", "#{phone}");
                VALUES("address_address", "#{addressAddress}");
                VALUES("is_default", "#{isDefault}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
                VALUES("is_deleted", "#{isDeleted}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_DELIVERY_ADDRESS);
            }}.toString();
        }

        public static String update(final DeliveryAddressDO deliveryAddressDO) {
            return new SQL() {{
                UPDATE(TABLE_DELIVERY_ADDRESS);
                SET("receiver = #{receiver}");
                SET("phone = #{phone}");
                SET("address_address = #{addressAddress}");
                SET("is_default = #{isDefault}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

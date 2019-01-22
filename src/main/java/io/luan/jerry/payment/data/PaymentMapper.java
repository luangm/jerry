package io.luan.jerry.payment.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PaymentMapper {

    @DeleteProvider(type = PaymentSqlProvider.class, method = "delete")
    int delete(PaymentDO paymentDO);

    @SelectProvider(type = PaymentSqlProvider.class, method = "findAll")
    @ResultMap("paymentResult")
    List<PaymentDO> findAll();

    @SelectProvider(type = PaymentSqlProvider.class, method = "findById")
    @Results(id = "paymentResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "order_id", property = "orderId"),
            @Result(column = "payer_id", property = "payerId"),
            @Result(column = "payee_id", property = "payeeId"),
            @Result(column = "channel_id", property = "channelId"),
            @Result(column = "total_fee", property = "totalFee"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "status", property = "status")
    })
    PaymentDO findById(Long id);

    @SelectProvider(type = PaymentSqlProvider.class, method = "findByOrderId")
    @ResultMap("paymentResult")
    PaymentDO findByOrderId(Long orderId);

    @InsertProvider(type = PaymentSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(PaymentDO paymentDO);

    @DeleteProvider(type = PaymentSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = PaymentSqlProvider.class, method = "update")
    void update(PaymentDO paymentDO);

    class PaymentSqlProvider {

        private static final String TABLE_PAYMENT = "`payment`";

        public static String delete(final PaymentDO paymentDO) {
            return new SQL() {{
                DELETE_FROM(TABLE_PAYMENT);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PAYMENT);
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PAYMENT);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findByOrderId(final Long orderId) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PAYMENT);
                WHERE("order_id = #{orderId}");
            }}.toString();
        }

        public static String insert(final PaymentDO paymentDO) {
            return new SQL() {{
                INSERT_INTO(TABLE_PAYMENT);
                VALUES("order_id", "#{orderId}");
                VALUES("payer_id", "#{payerId}");
                VALUES("payee_id", "#{payeeId}");
                VALUES("channel_id", "#{channelId}");
                VALUES("total_fee", "#{totalFee}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
                VALUES("status", "#{status}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_PAYMENT);
            }}.toString();
        }

        public static String update(final PaymentDO paymentDO) {
            return new SQL() {{
                UPDATE(TABLE_PAYMENT);
                SET("gmt_modified = #{gmtModified}");
                SET("status = #{status}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

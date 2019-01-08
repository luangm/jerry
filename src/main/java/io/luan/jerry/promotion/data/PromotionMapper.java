package io.luan.jerry.promotion.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Component
public interface PromotionMapper {

    @DeleteProvider(type = PromotionSqlProvider.class, method = "delete")
    int delete(PromotionDO promotion);


    @SelectProvider(type = PromotionSqlProvider.class, method = "findAll")
    @ResultMap("promotionResult")
    List<PromotionDO> findAll();

    @SelectProvider(type = PromotionSqlProvider.class, method = "findAllByItemIdAndTime")
    @ResultMap("promotionResult")
    List<PromotionDO> findAllByItemIdAndTime(Long itemId, LocalDateTime time);

    @SelectProvider(type = PromotionSqlProvider.class, method = "findById")
    @Results(id = "promotionResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "type", property = "type"),
            @Result(column = "item_id", property = "itemId"),
            @Result(column = "new_price", property = "newPrice"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    PromotionDO findById(Long id);

    @SelectProvider(type = PromotionSqlProvider.class, method = "findByItemIds")
    @ResultMap("promotionResult")
    List<PromotionDO> findByItemIds(List<Long> itemIds, LocalDateTime time);

    @InsertProvider(type = PromotionSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(PromotionDO promotion);

    @DeleteProvider(type = PromotionSqlProvider.class, method = "unsafeDeleteAll")
    int unsafeDeleteAll();

    @UpdateProvider(type = PromotionSqlProvider.class, method = "update")
    void update(PromotionDO promotion);

    class PromotionSqlProvider {

        private static final String TABLE_PROMOTION = "`promotion`";

        public static String delete(final PromotionDO promotion) {
            return new SQL() {{
                DELETE_FROM(TABLE_PROMOTION);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findAll() {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PROMOTION);
            }}.toString();
        }

        public static String findAllByItemIdAndTime(final Long itemId, final LocalDateTime time) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PROMOTION);
                WHERE("item_id = #{itemId}");
                WHERE("start_time <= #{time}");
                WHERE("end_time > #{time}");
            }}.toString();
        }

        public static String findById(final Long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PROMOTION);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findByItemIds(final List<Long> itemIds, final LocalDateTime time) {
            var sb = new StringBuilder();
            for (int i = 0; i < itemIds.size(); i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append("#{itemIds[");
                sb.append(i);
                sb.append("]}");
            }

            return new SQL() {{
                SELECT("*");
                FROM(TABLE_PROMOTION);
                WHERE("start_time <= #{time}");
                WHERE("end_time > #{time}");
                WHERE("item_id IN (" + sb.toString() + ")");
            }}.toString();
        }

        public static String insert(final PromotionDO promotion) {
            return new SQL() {{
                INSERT_INTO(TABLE_PROMOTION);
                VALUES("type", "#{type}");
                VALUES("item_id", "#{itemId}");
                VALUES("new_price", "#{newPrice}");
                VALUES("start_time", "#{startTime}");
                VALUES("end_time", "#{endTime}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }

        public static String unsafeDeleteAll() {
            return new SQL() {{
                DELETE_FROM(TABLE_PROMOTION);
            }}.toString();
        }

        public static String update(final PromotionDO promotion) {
            return new SQL() {{
                UPDATE(TABLE_PROMOTION);
                SET("new_price = #{newPrice}");
                SET("start_time = #{startTime}");
                SET("end_time = #{endTime}");
                SET("gmt_modified = #{gmtModified}");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}

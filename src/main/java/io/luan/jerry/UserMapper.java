package io.luan.jerry;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @SelectProvider(type = UserSqlProvider.class, method = "findById")
    @Results(id = "userResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "nick", property = "nick"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    User findById(@Param("id") long id);

    @SelectProvider(type = UserSqlProvider.class, method = "findByNick")
    @ResultMap("userResult")
    User findByNick(@Param("nick") String nick);

    @InsertProvider(type = UserSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(User user);

    class UserSqlProvider {

        private static final String TABLE_USER = "`user`";

        public static String findById(final long id) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_USER);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findByNick(final String nick) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_USER);
                WHERE("nick = #{nick}");
            }}.toString();
        }

        public static String insert(final User user) {
            return new SQL() {{
                INSERT_INTO(TABLE_USER);
                VALUES("nick", "#{nick}");
                if (user.getGmtCreate() != null) {
                    VALUES("gmt_create", "#{gmtCreate}");
                }
                if (user.getGmtModified() != null) {
                    VALUES("gmt_modified", "#{gmtModified}");
                }
            }}.toString();
        }
    }
}

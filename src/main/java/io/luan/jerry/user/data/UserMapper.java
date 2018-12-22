package io.luan.jerry.user.data;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @DeleteProvider(type = UserSqlProvider.class, method = "delete")
    int delete(UserDO user);

    @SelectProvider(type = UserSqlProvider.class, method = "findById")
    @Results(id = "userResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "nick", property = "nick"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    UserDO findById(Long id);

    @SelectProvider(type = UserSqlProvider.class, method = "findByNick")
    @ResultMap("userResult")
    UserDO findByNick(String nick);

    @InsertProvider(type = UserSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(UserDO user);

    class UserSqlProvider {

        private static final String TABLE_USER = "`user`";

        public static String delete(final UserDO user) {
            return new SQL() {{
                DELETE_FROM(TABLE_USER);
                WHERE("id = #{id}");
            }}.toString();
        }

        public static String findById(final Long id) {
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

        public static String insert(final UserDO user) {
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

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
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified")
    })
    UserDO findById(Long id);

    @SelectProvider(type = UserSqlProvider.class, method = "findByUsername")
    @ResultMap("userResult")
    UserDO findByUsername(String username);

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

        public static String findByUsername(final String username) {
            return new SQL() {{
                SELECT("*");
                FROM(TABLE_USER);
                WHERE("username = #{username}");
            }}.toString();
        }

        public static String insert(final UserDO user) {
            return new SQL() {{
                INSERT_INTO(TABLE_USER);
                VALUES("username", "#{username}");
                VALUES("password", "#{password}");
                VALUES("gmt_create", "#{gmtCreate}");
                VALUES("gmt_modified", "#{gmtModified}");
            }}.toString();
        }
    }
}

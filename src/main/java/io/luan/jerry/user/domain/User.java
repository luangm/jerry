package io.luan.jerry.user.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Entity {

    static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    /**
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

    public void changePassword(String cipher) {
        var encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(cipher);
        this.gmtModified = LocalDateTime.now().withNano(0);
    }

    public boolean verifyPassword(String cipher) {
        if (StringUtils.isEmpty(cipher) || StringUtils.isEmpty(this.password)) {
            return false;
        }
        var encoder = new BCryptPasswordEncoder();
        return encoder.matches(cipher, this.password);
    }
}

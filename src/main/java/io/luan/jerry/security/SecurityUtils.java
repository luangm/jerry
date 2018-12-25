package io.luan.jerry.security;

import io.luan.jerry.user.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof UserPrincipal) {
            var principle = (UserPrincipal) auth.getPrincipal();
            return principle.getUser();
        }
        return null;
    }
}

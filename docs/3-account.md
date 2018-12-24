3 - account registration

***

In previous mvp, anyone can login as anyone as long as a valid nick (username) is known
Now lets implement a real username/password login system:
* first, password must not be stored in plain text, must be hashed with salt (to prevent from rainbow table attack).
* use BCrypt hash algorithm, no need to save salt separately

Use Spring Security, but implement a custom UserDetailsService to read data from UserService.


package com.boardv4.util;

import com.boardv4.exception.member.PasswordNotMatchException;
import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {
    public static String encrypt(String rawPassword) {
        return DigestUtils.sha256Hex(rawPassword);
    }

    public static void validatePassword(String inputPassword, String storedPassword) {
        if (!PasswordUtil.encrypt(inputPassword).equals(storedPassword)) {
            throw new PasswordNotMatchException();
        }
    }
}

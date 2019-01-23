package com.lxliner.ciklumtestproject;

import com.lxliner.ciklumtestproject.util.PasswordStrengthChecker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PasswordStrengthCheckerTest {

    private PasswordStrengthChecker passwordStrengthChecker;

    @Before
    public void init() {
        passwordStrengthChecker = new PasswordStrengthChecker();
    }

    @Test
    public void testGetPasswordStrength() {
        Assert.assertEquals("Password is empty", 0, passwordStrengthChecker.getPasswordStrength(""));
        Assert.assertEquals("Password is too short", 1, passwordStrengthChecker.getPasswordStrength("1111"));
        Assert.assertEquals("Password not contain small letters and/or capital letters and/or numbers", 2, passwordStrengthChecker.getPasswordStrength("11111111"));
        Assert.assertEquals("Password not contain special symbols", 3, passwordStrengthChecker.getPasswordStrength("aaaBBB111"));
        Assert.assertEquals("Password is strong enough", 4, passwordStrengthChecker.getPasswordStrength("qweASD123!@#"));
    }

    @Test
    public void testIsWeakPassword() {
        Assert.assertEquals("Password is weak", true, passwordStrengthChecker.isWeakPassword("qwerty"));
        Assert.assertEquals("Password is strong", false, passwordStrengthChecker.isWeakPassword("zsW5@f%s1"));
    }
}

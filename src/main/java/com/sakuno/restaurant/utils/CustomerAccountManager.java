package com.sakuno.restaurant.utils;

import com.sakuno.restaurant.json.CustomerFullInfo;
import com.sakuno.restaurant.json.CustomerLoginInfo;
import com.sakuno.restaurant.json.CustomerRegisterInfo;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Arrays;

public class CustomerAccountManager {

    private static CustomerAccountManager singleItem = null;

    private CustomerAccountManager() {
    }

    public static CustomerAccountManager getInstance() {
        if (singleItem == null)
            synchronized (CustomerAccountManager.class) {
                singleItem = new CustomerAccountManager();
            }
        return singleItem;
    }

    private OutputStream failReason = new ByteArrayOutputStream();

    private PrintStream errorOs = new PrintStream(failReason);

    public String popFailReason() {
        var result = "注册管理：" + failReason.toString();
        errorOs.close();
        failReason = new ByteArrayOutputStream();
        errorOs = new PrintStream(failReason);
        return result;
    }

    public boolean register(CustomerRegisterInfo info) {

        var username = info.getUsername();
        var password = info.getPassword();
        var phone = info.getPhone();

        if (!Character.isLetter(username.charAt(0)))
            return false;

        if (phone == null)
            phone = "";

        var id = (phone + username + System.currentTimeMillis()).hashCode();

        var db = DatabaseEntrance.getInstance();
        if (!db.isAvailable()) {
            errorOs.println("数据库不可用 2001");
            return false;
        }

        var prepareState = StateBuilder.Companion
                .insert()
                .into("CustomerAccounts")
                .forColumns("ID", "Username", "Password", "Phone")
                .withItems(id, username, password, phone)
                .build();

        return db.runStatement(prepareState);
    }

    public String login(CustomerLoginInfo info) {

        var account = info.getAccount();
        var password = info.getPassword();

        String accountType;
        var firstChar = account.charAt(0);
        if (Character.isLetter(firstChar)) accountType = "Username";
        else if (Character.isDigit(firstChar)) accountType = "Phone";
        else {
            errorOs.println("账号不合规 2003");
            return null;
        }

        var db = DatabaseEntrance.getInstance();
        if (!db.isAvailable()) {
            errorOs.println("数据库不可用 2004");
            return null;
        }

        var prepareState = StateBuilder.Companion
                .select()
                .from("CustomerAccount")
                .withCondition(accountType, account)
                .withCondition("Password", password)
                .build();

        var result = db.runStatementWithQuery(prepareState);
        if (result == null) {
            errorOs.println("数据库查询错误 2005");
            return null;
        }

        try {
            if (result.next()) {
                String prepareAuthCode;
                try {
                    var md5Builder = MessageDigest.getInstance("MD5");
                    prepareAuthCode = Arrays.toString(md5Builder.digest((account + System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8)));
                } catch (Exception e) {
                    errorOs.println("加密出错 2002");
                    return null;
                }
                if (db.runStatement(StateBuilder.Companion
                        .update()
                        .fromTable("CustomerAccounts")
                        .withCondition(accountType, account)
                        .change("SessionAuthCode", prepareAuthCode)
                        .build()
                )) return prepareAuthCode;
                else {
                    errorOs.println("认证码更新失败 2006");
                    return null;
                }
            }
        } catch (Exception ignore) {
            errorOs.println("数据库查询错误 2007");
            return null;
        }
        return null;
    }

    public CustomerFullInfo checkAuth(String authCode) {
        var db = DatabaseEntrance.getInstance();
        if (!db.isAvailable()) {
            errorOs.println("数据库不可用 2008");
            return null;
        }

        var result = db.runStatementWithQuery(
                StateBuilder.Companion
                        .select()
                        .from("CustomerAccounts")
                        .forColumns("ID", "Username", "Phone")
                        .withCondition("SessionAuthCode", authCode)
                        .build()
        );
        try {
            if (result.next()) {
                return new CustomerFullInfo(
                        result.getString(1),
                        result.getString(3),
                        authCode,
                        result.getString(2)
                );
            } else return null;
        } catch (SQLException e) {
            errorOs.println("数据库查询错误 2009");
            return null;
        }
    }
}

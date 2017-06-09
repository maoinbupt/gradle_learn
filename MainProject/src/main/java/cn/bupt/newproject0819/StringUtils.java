package cn.bupt.newproject0819;

import android.os.Build;
import android.util.Patterns;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GaoFeng on 2017.03.20.
 */
public class StringUtils {
    public StringUtils() {
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEquals(String actual, String expected) {
        if(actual != expected) {
            if(actual == null) {
                if(expected != null) {
                    return false;
                }
            } else if(!actual.equals(expected)) {
                return false;
            }
        }

        return true;
    }

    public static String nullStrToEmpty(String str) {
        return str == null?"":str;
    }

    public static String capitalizeFirstLetter(String str) {
        if(isEmpty(str)) {
            return str;
        } else {
            char c = str.charAt(0);
            return Character.isLetter(c) && !Character.isUpperCase(c)?(new StringBuilder(str.length())).append(Character.toUpperCase(c)).append(str.substring(1)).toString():str;
        }
    }

    public static String utf8Encode(String str) {
        if(!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", var2);
            }
        } else {
            return str;
        }
    }

    public static String utf8Encode(String str, String defultReturn) {
        if(!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException var3) {
                return defultReturn;
            }
        } else {
            return str;
        }
    }

    public static String getHrefInnerHtml(String href) {
        if(isEmpty(href)) {
            return "";
        } else {
            String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
            Pattern hrefPattern = Pattern.compile(hrefReg, 2);
            Matcher hrefMatcher = hrefPattern.matcher(href);
            return hrefMatcher.matches()?hrefMatcher.group(1):href;
        }
    }

    public static String htmlEscapeCharsToString(String source) {
        return isEmpty(source)?source:source.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    public static String fullWidthToHalfWidth(String s) {
        if(isEmpty(s)) {
            return s;
        } else {
            char[] source = s.toCharArray();

            for(int i = 0; i < source.length; ++i) {
                if(source[i] == 12288) {
                    source[i] = 32;
                } else if(source[i] >= '！' && source[i] <= '～') {
                    source[i] -= 'ﻠ';
                } else {
                    source[i] = source[i];
                }
            }

            return new String(source);
        }
    }

    public static String halfWidthToFullWidth(String s) {
        if(isEmpty(s)) {
            return s;
        } else {
            char[] source = s.toCharArray();

            for(int i = 0; i < source.length; ++i) {
                if(source[i] == 32) {
                    source[i] = 12288;
                } else if(source[i] >= 33 && source[i] <= 126) {
                    source[i] += 'ﻠ';
                } else {
                    source[i] = source[i];
                }
            }

            return new String(source);
        }
    }

    public static boolean isNumber(String num) {
        for(int i = 0; i < num.length(); ++i) {
            if(!Character.isDigit(num.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkCellPhone(String phoneNum) {
        String check = "^(1[0-9])\\d{9}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(phoneNum);
        return matcher.matches();
    }

    public static boolean checkNickNameIsAlphFigureChinese(String string) {
        String check = "[0-9a-zA-Z一-龥]*";
        Pattern regex = Pattern.compile(check, 2);
        Matcher matcher = regex.matcher(string);
        return matcher.matches();
    }

    public static boolean checkNickNameIsStartByStr(String string) {
        String check = "(weibo|官方|微博).*";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(string);
        return matcher.matches();
    }

    public static boolean checkNickNameIsContains(String string) {
        String check = "搜狐|搜狐微博|sohu|souhu";
        Pattern regex = Pattern.compile(check, 2);
        Matcher matcher = regex.matcher(string);
        return matcher.find();
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if(str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }

        return dest;
    }

    public static boolean checkFirstCharLower(String content) {
        if(isEmpty(content)) {
            return false;
        } else {
            String check = "[a-z](.)*";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(content);
            return matcher.matches();
        }
    }

    public static boolean checkEmailUserName(String email) {
        if(Build.VERSION.SDK_INT >= 8) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            String check = "^([a-z0-9A-Z-_]+[-|_|\\.]?)+[a-z0-9A-Z_-]@([a-z0-9A-Z_+]+(-[a-z0-9A-Z_+]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            return matcher.matches();
        }
    }

    public static boolean checkStringIsForbid(String string) {
        String check = "(Abuse|contact|help|info|jobs|owner|sales|staff|sales|support|www)?+(@sohu.com)";
        Pattern regex = Pattern.compile(check, 2);
        Matcher matcher = regex.matcher(string);
        return matcher.matches();
    }

    public static boolean checkStringIsContains(String string) {
        String check = "admin|master";
        Pattern regex = Pattern.compile(check, 2);
        Matcher matcher = regex.matcher(string);
        return matcher.find();
    }

    public static boolean checkPassportIsValid(String str) {
        return checkEmailUserName(str)?(checkStringIsContains(str)?false:!checkStringIsForbid(str)):false;
    }

    public static boolean checkPasswordIsValid(String string) {
        if(isEmpty(string)) {
            return false;
        } else {
            String check = "[0-9a-zA-Z~!@#$%^&*()\\-+_={}\\[\\];:\'\",.<>?/]*";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(string);
            return matcher.matches();
        }
    }

    public static int stringToInt(String str) {
        if(isEmpty(str)) {
            return 0;
        } else {
            int ret = 0;

            try {
                ret = Integer.parseInt(str);
            } catch (Exception var3) {
                ;
            }

            return ret;
        }
    }

    public static long stringToLong(String str) {
        if(isEmpty(str)) {
            return 0L;
        } else {
            long dest = 0L;

            try {
                dest = Long.parseLong(str);
            } catch (Exception var4) {
                ;
            }

            return dest;
        }
    }

    public static float stringToFloat(String str) {
        if(isEmpty(str)) {
            return 0.0F;
        } else {
            float dest = 0.0F;

            try {
                dest = Float.parseFloat(str);
            } catch (Exception var3) {
                ;
            }

            return dest;
        }
    }

    public static int compareVersion(String sourceVersion, String desVersion) {
        String[] source = sourceVersion.split(".");
        String[] des = desVersion.split(".");
        String tempSource = "";

        for(int tempDes = 0; tempDes < source.length; ++tempDes) {
            String i = "";
            if(source[tempDes].length() >= 8) {
                i = source[tempDes].substring(0, 8);
            } else {
                i = source[tempDes];

                for(int str = source[tempDes].length(); str < 8; ++str) {
                    i = "0" + i;
                }
            }

            tempSource = tempSource + i;
        }

        String var9 = "";

        for(int var10 = 0; var10 < des.length; ++var10) {
            String var11 = "";
            if(des[var10].length() >= 8) {
                var11 = des[var10].substring(0, 8);
            } else {
                var11 = des[var10];

                for(int j = des[var10].length(); j < 8; ++j) {
                    var11 = "0" + var11;
                }
            }

            var9 = var9 + var11;
        }

        return tempSource.compareTo(var9);
    }
}

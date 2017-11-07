package halo.com.moneytracker.utils;

/**
 * Created by HoVanLy on 8/8/2016.
 */
public final class StringUtil {
    private static StringUtil mStringUtil;

    public static StringUtil getInstance() {
        if (mStringUtil == null) {
            mStringUtil = new StringUtil();
        }
        return mStringUtil;
    }

    public String getStringMoney(long money) {
        String temp = String.valueOf(money);
        StringBuilder stringBuilder = new StringBuilder();
        int size = temp.length();
        int index = 0;
        for (int i = size - 1; i > -1; i--) {
            if (index > 2) {
                stringBuilder.append(",");
                stringBuilder.append(temp.charAt(i));
                index = 1;
            } else {
                stringBuilder.append(temp.charAt(i));
                index++;
            }
        }
        return stringBuilder.reverse().toString() + String.format(" %s","đ");
    }
}

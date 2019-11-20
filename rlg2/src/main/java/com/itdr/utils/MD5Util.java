package com.itdr.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: MD5Util
 * 日期: 2019/9/10 12:28
 *
 * @author Air张
 * @since JDK 1.8
 */
public class MD5Util {
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 返回大写MD5
     *
     * @param origin
     * @param charsetname
     * @return
     */
    private static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString.toUpperCase();
    }

    public static String MD5EncodeUtf8(String origin) {
        return MD5Encode(origin, "utf-8");
    }


    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static void main(String[] args) {
        System.out.println(MD5EncodeUtf8("47bbe2fbe7d92d3be07c59bb09b788fad01c37a987eb4bbe067b6fb74994d79aapiCodescs.openlink.sales.order.pullapiVersion1.0appKey974885f2dea9b0224ebba36c323742c10b2b0d1a2671f92d01ed97f07bc74a36appSecret47bbe2fbe7d92d3be07c59bb09b788fad01c37a987eb4bbe067b6fb74994d79atimestamp2019-10-30 15:56:27token1adf1caaa0203cc94338ff7288af76bd{\"params\":{\"gift\":\"N\",\"orderNo\":\"120000000472\"}}47bbe2fbe7d92d3be07c59bb09b788fad01c37a987eb4bbe067b6fb74994d79a"));
        System.out.println("26E1BB31BFCB967B689E0E57DF7272E5");
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sf.format(date));
    }
}

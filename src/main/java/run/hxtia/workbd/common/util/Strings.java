package run.hxtia.workbd.common.util;

import java.util.UUID;

/**
 * 字符串工具类
 */
public class Strings {

    /**
     * 获取UUID
     * @return ：去掉 "-" 后的 UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取指定长度的 UUID
     * @param len ：(0, 32]
     * @return ：获取 (0 , 32] 长度的UUID
     */
    public static String getUUID(int len) {
        if (len <= 0) return null;
        String uuid = getUUID();
        if (len > 32) return uuid;
        return uuid.substring(0, len);
    }

    /**
     * 将集合的元素用指定的分隔符连接起来
     * @param list ：集合
     * 例如：[1, 2, 3]
     * 分隔符：","
     * 返回："1,2,3"
     * @return ：连接后的字符串
     */
    public static String join(Iterable<?> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb.append(o).append(separator);
        }
        return sb.substring(0, sb.length() - separator.length());
    }

}

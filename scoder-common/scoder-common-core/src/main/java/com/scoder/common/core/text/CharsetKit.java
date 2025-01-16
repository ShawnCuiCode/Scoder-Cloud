package com.scoder.common.core.text;

import com.scoder.common.core.utils.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Charset Utility Class
 * <p>
 * Provides utilities for handling character sets, including common charset definitions,
 * charset conversions, and system default charset retrieval.
 *
 * @author Shawn Cui
 */
public class CharsetKit {

    /**
     * ISO-8859-1 Charset Name
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * UTF-8 Charset Name
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * GBK Charset Name
     */
    public static final String GBK = "GBK";

    /**
     * ISO-8859-1 Charset Object
     */
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);

    /**
     * UTF-8 Charset Object
     */
    public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);

    /**
     * GBK Charset Object
     */
    public static final Charset CHARSET_GBK = Charset.forName(GBK);

    /**
     * Converts a charset name into a Charset object.
     *
     * @param charset the name of the charset, or null to use the default charset.
     * @return the corresponding Charset object.
     */
    public static Charset charset(String charset) {
        return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
    }

    /**
     * Converts the encoding of a string from one charset to another.
     *
     * @param source      the source string to be converted.
     * @param srcCharset  the source charset, defaults to ISO-8859-1 if null.
     * @param destCharset the target charset, defaults to UTF-8 if null.
     * @return the string with the converted charset.
     */
    public static String convert(String source, String srcCharset, String destCharset) {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    /**
     * Converts the encoding of a string from one charset to another.
     *
     * @param source      the source string to be converted.
     * @param srcCharset  the source Charset object, defaults to ISO-8859-1 if null.
     * @param destCharset the target Charset object, defaults to UTF-8 if null.
     * @return the string with the converted charset.
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset) {
        if (srcCharset == null) {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (destCharset == null) {
            destCharset = StandardCharsets.UTF_8;
        }

        if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset)) {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }

    /**
     * Retrieves the system's default charset name.
     *
     * @return the name of the system's default charset.
     */
    public static String systemCharset() {
        return Charset.defaultCharset().name();
    }
}
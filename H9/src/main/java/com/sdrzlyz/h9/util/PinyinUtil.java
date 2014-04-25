package com.sdrzlyz.h9.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by sdrzlyz on 14-4-25.
 * TODO 没有考虑多音字的问题，可以用temp的长度来判断是否含多个注音。然后决定是否需要替换
 */
public class PinyinUtil {

    /**
     * 汉字转拼音，遇到数字、字母时爆出原来的形态，只对汉字进行转换  例如：Lai孙-》LaiSUN
     *
     * @param chineseStr
     * @return
     */
    public static String getPinYin(String chineseStr) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = chineseStr.trim().toCharArray();
        StringBuilder pysb = new StringBuilder();
        try {
            for (char c : input) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (temp != null) {
                    pysb.append(temp[0]);
                } else {
                    pysb.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pysb.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 只对汉字部分做转换，而且不会转换数字及字母部分   例如：Lai孙-》S
     *
     * @param chineseStr
     * @return
     */
    public static String getFirstLetter(String chineseStr) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = chineseStr.trim().toCharArray();
        StringBuilder pysb = new StringBuilder();
        try {
            for (char c : input) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (temp != null) {
                    pysb.append(temp[0].charAt(0));
                } else {
                    pysb.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pysb.toString().replaceAll("\\W", "").trim();
    }
}

package com.learning.poi;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author xuetao
 * @Description: 根据poi导出 word文档，解决富文本中的标签问题
 * @Date 2019-04-25
 * @Version 1.0
 */
public class ExportWord {

    public static void main(String[] args) throws Exception {
        ExportWord exportWord = new ExportWord();
        Map<String, Object> params = new HashMap<String, Object>();
        File file1 = new File("/Users/xuetao/Desktop/111.docx");
        File file2 = new File("/Users/xuetao/Desktop/222.docx");
        params.put("${title}", "会议测试");
        params.put("${startTime}", "2019-02-22");
        params.put("${address}", "五棵松");
        params.put("${totalNum}", "20");
        params.put("${signedNum}", "10");
        params.put("${signedPerson}", "张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。");
        params.put("${absenteePerson}", "张二，李1，王2。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。张珊，李四，王五。。。。");
        params.put("${meetingContents}", "<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:20.0000pt;mso-font-kerning:1.0000pt;\">农文旅党总支会议记录表</span><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">" +
                "<o:p></o:p></span></p><table class=\"MsoNormalTable\" style=\"border-collapse:collapse;width:405.2000pt;margin-left:6.7500pt;margin-right:6.7500pt;mso-table-layout-alt:fixed;mso-padding-alt:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;\"><tbody><tr style=\"height:16.1000pt;\">" +
                "<td width=\"107\" valign=\"top\" style=\"width:80.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid " +
                "windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">时间</span><span style=\"font-family:宋体;" +
                "font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"191\" valign=\"top\" colspan=\"2\" style=\"width:143.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid " +
                "windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">2018<font face=\"宋体\">年</font><font face=\"Calibri\">" +
                "3</font><font face=\"宋体\">月</font><font face=\"Calibri\">08</font><font face=\"宋体\">日</font></span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"61\" valign=\"top\" style=\"width:45.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt " +
                "solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">地点</span><span " +
                "style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"181\" valign=\"top\" colspan=\"2\" style=\"width:135.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid " +
                "windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">博物馆</span><span style=\"mso-spacerun:'yes';font-family:Calibri;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">" +
                "301会议室</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td></tr><tr style=\"height:54.4000pt;\"><td width=\"107\" valign=\"top\" style=\"width:80.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt " +
                "solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">参加人员</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p " +
                "class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">出勤情况</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"433\" valign=\"top\" colspan=\"5\" style=\"width:324.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt " +
                "solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">党总支委员：袁吉、万建国、丁惠明</span><span style=\"font-family:Calibri;font-size:16.0000pt;" +
                "mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">列席人员：李珺</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">" +
                "应到<font face=\"Calibri\">4</font><font face=\"宋体\">人，实到</font><font face=\"Calibri\">4</font><font face=\"宋体\">人，缺席</font><font face=\"Calibri\">0 </font><font face=\"宋体\">人</font></span><span style=\"font-family:宋体;font-size:18.0000pt;mso-font-kerning:1.0000pt;\">。</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td></tr><tr style=\"height:28.1000pt;\">" +
                "<td width=\"107\" valign=\"top\" style=\"width:80.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\">" +
                "<p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">会议议题</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"433\" valign=\"center\" colspan=\"5\" style=\"width:324.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt " +
                "solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">关于组织生活会和民主评议方案</span><span " +
                "style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">开展批评和自我批评</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td></tr><tr style=\"height:371.0000pt;\"><td width=\"107\" valign=\"top\" rowspan=\"2\" style=\"width:80.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;" +
                "border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:none;;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"> </span></p><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"> </span></p><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"> </span></p><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"> </span></p><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"> </span></p><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">主</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">要</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">内</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"center\" style=\"text-align:center;\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">容</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"433\" valign=\"top\" colspan=\"5\" style=\"width:324.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:31.8750pt none rgb(255,255,255);mso-border-bottom-alt:31.8750pt none rgb(255,255,255);\"><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">袁吉（党总支副书记）：根据2017年年组织生活会和民主评议党员工作方案，今天我们将开展批评和自我批评。首先，由我报告一年来党总支工作情况、检查党总支建设存在的问题。</span><span style=\"font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">..................</span><span style=\"font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">下面，开展批评和自我批评。发言具体步骤是：发言同志剖析自己存在的突出问题，提出今后整改措施；其他班子成员逐一对发言的同志提出批评意见和建议，发言的同志对照大家的批评意见再作表态。</span><span style=\"font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">下面，我先进行发言。</span><span style=\"font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">请同志们对我提出批评意见。</span><span style=\"font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">下面，请万建国同志发言</span><span style=\"font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">请同志们对万建国提出批评意见。</span><span style=\"font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">下面，请丁惠明同志发言</span><span style=\"font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" align=\"justify\" style=\"margin-right:0.0000pt;margin-left:0.0000pt;mso-para-margin-right:0.0000gd;mso-para-margin-left:0.0000gd;text-indent:24.0000pt;mso-char-indent-count:2.0000;text-autospace:ideograph-numeric;mso-pagination:none;text-align:justify;text-justify:inter-ideograph;line-height:150%;\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;mso-font-kerning:1.0000pt;\">请同志们提出批评意见。</span><span style=\"font-family:宋体;font-size:14.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td></tr><tr style=\"height:16.2000pt;\"><td width=\"433\" valign=\"top\" colspan=\"5\" style=\"width:324.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"> </span></p></td></tr><tr style=\"height:33.2500pt;\"><td width=\"107\" valign=\"top\" style=\"width:80.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">记录人员</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"153\" valign=\"top\" style=\"width:114.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">   李珺</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"144\" valign=\"top\" colspan=\"3\" style=\"width:108.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">" +
                "党总支副书记</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td><td width=\"136\" valign=\"top\" style=\"width:102.2000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid " +
                "windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;\"><p class=\"MsoNormal\"><span style=\"mso-spacerun:'yes';font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\">袁吉</span><span style=\"font-family:宋体;font-size:16.0000pt;mso-font-kerning:1.0000pt;\"><o:p></o:p></span></p></td></tr></tbody></table>");
        params.put("${summaryContents}", "<span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp; &nbsp; &nbsp; 1.关于组织开展演讲比赛事宜</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; " +
                "background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;副书记郑津京：工会已发通知，要求各科室报名。比赛开始前，想邀请培训老师给大家辅导一下。在比赛当天邀请评委，结合参赛人员具体情况，选拔参加上级宣讲比赛选手。时间定在5月12日“护士节”前完成。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书记陈宝军：建议先收集、审核报名人员的稿件，大概先筛一下，然后邀请老师培训，并有针对性地对重点人员进行指导。假如像内科这样职工多的科室1个名额，有的人员和事迹发现不了，可以深入科室去挖掘下，建议科室多报，增加选择的多样性。另外，郭欣把上级宣讲员的录音发给大家，供科室人员学习、借鉴。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支部委员衡东辉：建议邀请参加过北京市宣讲比赛和巡讲的老师来，因为经过系统培训，宣讲经验也很丰富。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书记陈宝军：可以。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.关于做好医药分开综合改革一周年成效宣传工作的通知</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;副书记郑津京：党群办印发的文件，要求重点做好医改一周年结构性变化宣传、创新服务举措宣传、医疗机构经验体会宣传。通知办公室李煊发布了一篇信息，计划在5月的“党员活动日”组织党员志愿宣传医改活动。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书记陈宝军：可以。建议要融入日常业务工作，把咱们的行业特点和工作成效体现出来。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.</span><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">传达关于开展“党徽印我心，清风伴我行”党建暨党风廉政建设知识竞赛的通知</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;副书记郑津京：区卫生计生委党群办将于5月中上旬，组织开展知识竞赛，4人组成1个队伍，分初赛、决赛。我看了下题库，题型丰富，大答题难度相对较大。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书记陈宝军：大家认真复习，积极努力。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp; &nbsp; &nbsp;&nbsp;</span><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">4.</span><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">关于邀请专家开展党的十九大专题讲座事宜</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;副书记郑津京：前期征求来的意见建议其中一项是建议邀请专家讲解党的十九大精神。现在已联系到首都师范大学的老师，预计时间是5月25日下午。半天两个多小时。这个机会难得，是不是不局限于党员、中层，不值班的职工也可以听一听？</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书记陈宝军：可以。宣传贯彻落实党的十九大精神也是今年党建的一项重点工作。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纪检委员张秀生：党的十九大报告里面有党风廉政建设等内容，在讲座开始前建议跟老师确认一下，她也讲部分廉政内容。这样可以与“六个一”廉政警示教育活动结合起来。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;郭欣：好的。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.关于廉政警示教育参观安排</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纪检委员张秀生：本周三上午9:00出发，第一批26人。参观后全体人员都返回医院。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书记陈宝军：可以。发通知时、上车签到完毕，把要求和注意事项再强调一下。注意做好影像资料留存，报送活动信息。</span><br data-filtered=\"filtered\" style=\"margin: 0px; padding: 0px; font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: Arial, Helvetica, sans-serif; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纪检委员张秀生：好的。</span>");

        XWPFDocument doc;
        InputStream is = new FileInputStream(file1);

        doc = new XWPFDocument(is);
        OutputStream os = new FileOutputStream(file2);
        exportWord.replaceInPara(doc, params);
        exportWord.replaceInTable(doc, params);


        doc.write(os);

        exportWord.close(os);
        exportWord.close(is);

        os.flush();
        os.close();
    }


    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params, true);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    public void replaceInPara(XWPFParagraph para, Map<String, Object> params, boolean isHead) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                stringBuffer.append(runText);
            }
            String text = stringBuffer.toString();
            int size = runs.size();

            for (int j = 0; j < size; j++) {
                para.removeRun(0);
            }
            for (String key : params.keySet()) {
                if (isHead) {
                    if (text.contains(key)) {
                        XWPFRun xwpfRun = para.createRun();
                        xwpfRun.setText(params.get(key) + "记录");
                        xwpfRun.setFontSize(20);
                        break;
                    }
                } else {
                    if (text.equals(key)) {
                        XWPFRun xwpfRun = para.createRun();


                        String content = (String) params.get(key);
                        if ("${summaryContents}".equals(text) || "${meetingContents}".equals(text)) {
                            String s = stripHtml(content);
                            String[] array = s.split("tabstr");
                            for (int i = 0; i < array.length; i++) {
                                System.out.println("array" + array[i]);
                                xwpfRun.setText((array[i]));
                                xwpfRun.addBreak();
                            }
                        } else {
                            xwpfRun.setText(content);
                        }
                        xwpfRun.setFontSize(16);

                        break;

                    }
                }
            }


        }
    }

    public String stripHtml(String content) {
//        content = content.replaceAll("&nbsp;", "  ");

        System.out.println(content);

        // <p>段落替换为换行
        content = content.replaceAll("</p>", "tabstr");
        // <br><br/>替换为换行
        content = content.replaceAll("<br/>", "tabstr");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        content = content.replaceAll("(&nbsp;){3,}", "   ");
        content = content.replaceAll("(&nbsp;){2,}", "   ");
        content = content.replaceAll("&nbsp;", " ");
        System.out.println(content);

        return content;
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params, false);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    public void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    public void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

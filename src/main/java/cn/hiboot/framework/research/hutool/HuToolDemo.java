package cn.hiboot.framework.research.hutool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/4/11 0:26
 */
public class HuToolDemo {

    @Test
    public void read() {
        String path = "C:\\Users\\DH\\Desktop\\quick\\kgms_new.xlsx";
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(path));
        //通过sheet编号获取
//        reader = ExcelUtil.getReader(FileUtil.file(path), 0);
        //通过sheet名获取
//        reader = ExcelUtil.getReader(FileUtil.file(path), "computer");
        List<List<Object>> readAll = reader.read();
        System.out.println(readAll);

        List<Map<String, Object>> maps = reader.readAll();
        System.out.println(maps);

        //读取大量数据
        ExcelUtil.readBySax(path, 0, (sheetIndex, rowIndex, rowList) -> Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowList));

    }

    @Test
    public void write() {

        List<String> row1 = Lists.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = Lists.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = Lists.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = Lists.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = Lists.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = Lists.newArrayList(row1, row2, row3, row4, row5);

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/writeTest.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        // 定义单元格背景色
        StyleSet style = writer.getStyleSet();
        // 第二个参数表示是否也设置头部单元格背景
        style.setBackgroundColor(IndexedColors.YELLOW, false);

        //设置内容字体
        Font font = writer.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setItalic(true);

        //第二个参数表示是否忽略头部样式
        style.setFont(font, true);

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容
        writer.write(rows);
        //关闭writer，释放内存
        writer.close();

    }

    @Test
    public void nerExcel() throws IOException {
        File dir = new File("G:\\res1");

        List<List<String>> rows = Lists.newArrayList();
        rows.add(Lists.newArrayList("标题", "地名", "人名"));
        for (File file : dir.listFiles()) {
            try {
                List<String> data = Files.readAllLines(Paths.get(file.getAbsolutePath()));
                List<String> row = Lists.newArrayList(file.getName().replace(".txt",""));
                for (String d : data) {
                    if(d.startsWith("人名")){
                        row.add(d.split(":")[1]);
                    }else if(d.startsWith("地名")){
                        row.add(d.split(":")[1]);
                    }
                }
                rows.add(row);
            } catch (Exception e) {
                System.out.println(file.getName());
            }
        }

        ExcelWriter writer = ExcelUtil.getWriter("d:/writeTest.xlsx");
        writer.write(rows);
        writer.close();

    }


    /**
     * 利用hutool封装的加密工具
     */
    @Test
    public void crypto() {
        String content = "test中文";

        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue()).getEncoded();

        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.DESede, key);

        //加密
        byte[] encrypt = aes.encrypt(content);
        //解密
        byte[] decrypt = aes.decrypt(encrypt);

        //加密为16进制表示
        String encryptHex = aes.encryptHex(content);

        //解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);

    }
}

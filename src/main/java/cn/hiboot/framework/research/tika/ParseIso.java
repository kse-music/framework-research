package cn.hiboot.framework.research.tika;

import org.apache.tika.exception.TikaException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ParseIso {


	public static void main(String[] args) throws FileNotFoundException, IOException, TikaException {
		String filePath = "G:\\戴朝波数据\\论文\\论文.iso";     
        boolean b = WinRarUtil.uncompress(filePath);        
        System.out.println(b);       
	}
}

package com.grgbanking.framework.util.images;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.*;

/**
 * Created by Administrator on 2017/6/7.
 */
public class MimeUtils {

    /**
     * 获取图片后缀
     * @param data
     * @return
     * @throws Exception
     */
    public static String getImageSuffix2(byte[] data) throws Exception{
        Magic parser = new Magic() ;
        // getMagicMatch accepts Files or byte[],
        // which is nice if you want to test streams
        MagicMatch match = parser.getMagicMatch(data);
//        String mimeType = match.getMimeType();
        return "." + match.getExtension();
    }

    public static String getImageSuffix(byte[] data) throws Exception{
        InputStream is = new ByteArrayInputStream(data);

        ContentHandler contentHandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, "");
        AutoDetectParser parser = new AutoDetectParser();
        parser.parse(is, contentHandler, metadata);
        String mimeType = metadata.get(Metadata.CONTENT_TYPE);
        String suffix = "";
        if(mimeType.contains("image") && mimeType.contains("bmp")){
            suffix = ".bmp";
        }else if(mimeType.contains("image") && mimeType.contains("jpeg")){
            suffix = ".jpg";
        }else if(mimeType.contains("image") && mimeType.contains("png")){
            suffix = ".png";
        }
        return suffix;

    }

    public static void main(String[] args) throws Exception{
        File file = new File("D:\\Lighthouse.jpg");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        byte[] buffer = bos.toByteArray();
        fis.close();
        bos.close();
        System.out.println(getImageSuffix(buffer));
//        System.out.println(getImageSuffix2(buffer));
    }

}

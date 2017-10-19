/**
 * 
 */
package auto.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@CommonsLog
public class ImageUtils {
    
    private static final Log logger = LogFactory.getLog(ImageUtils.class);
    
    
    private static final long GET_IMAGE_TIMEOUT = 10000L;
    
    
    private static ExecutorService threadPool = Executors.newFixedThreadPool(20);
    
    
    private static class GetImageTask implements Callable<BufferedImage> {
        
        private String url;
        
        private InputStream in;
        
        public GetImageTask(String url) {
            this.url = url;
        }
        
        @Override
        public BufferedImage call() throws Exception {
            in = new URL(url).openStream();
            BufferedImage image = ImageIO.read(in);
            in.close();
            return image;
        }
        
    }
    
    public static BufferedImage getImage(String url) {
        GetImageTask task = new GetImageTask(url);
        Future<BufferedImage> future = threadPool.submit(task);
        try {
            return future.get(GET_IMAGE_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error(e, e);
            try {
                future.cancel(true);
//                task.in.close();
            } catch(Exception e1){}
        }
        return null;
    }
    
    

    public static void cutImage(InputStream input, OutputStream out,
            String type, int x, int y, int width, int height)
            throws IOException {
        ImageInputStream imageStream = null;
        try {
            String imageType = (null == type || "".equals(type)) ? "jpg" : type;
            Iterator<ImageReader> readers = ImageIO
                    .getImageReadersByFormatName(imageType);
            ImageReader reader = readers.next();
            imageStream = ImageIO.createImageInputStream(input);
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, imageType, out);
        } finally {
            imageStream.close();
        }
    }
    
    public static String getSuffix(String url) {
        url = url.toLowerCase();
        if (url.endsWith(".bmp")) return "bmp";
        if (url.endsWith(".png")) return "png";
        if (url.endsWith(".gif")) return "gif";
        if (url.endsWith(".jpeg")) return "jpeg";
        return "jpg";
    }
    
    public static void cutImageRightMargin(String url, OutputStream out, float margin) {
        String suffix = getSuffix(url);
        BufferedImage img = null;
        ByteArrayOutputStream bs = null;
        try {
            img = getImage(url);
            int width = img.getWidth();
            int height = img.getHeight();
            bs =new ByteArrayOutputStream();
            ImageIO.write(img, suffix, ImageIO.createImageOutputStream(bs));
            cutImage(new ByteArrayInputStream(bs.toByteArray()), out, suffix, 0, 0, (int)((1 - margin) * width), height);
        } catch(Exception e) {
            logger.error(e, e);
        } finally {
            if (bs != null) {
                try {
                    bs.close();
                } catch (IOException e) {}
            }
        }
    }
    
    public static void cutImageButtomMargin(String url, OutputStream out, float margin) {
        String suffix = getSuffix(url);
        BufferedImage img = null;
        ByteArrayOutputStream bs = null;
        try {
            img = getImage(url);
            int width = img.getWidth();
            int height = img.getHeight();
            
            bs =new ByteArrayOutputStream();
            ImageIO.write(img, suffix, ImageIO.createImageOutputStream(bs));
            cutImage(new ByteArrayInputStream(bs.toByteArray()), out, suffix, 0, 0, width, (int)((1 - margin) * height));
        } catch(Exception e) {
            logger.error(e, e);
        } finally {
            if (bs != null) {
                try {
                    bs.close();
                } catch (IOException e) {}
            }
        }
    }
    
    public static void compress(String url, OutputStream out, int maxSize) throws Exception {
        String suffix = getSuffix(url);
        BufferedImage image = getImage(url);
        int width = image.getWidth();
        int height = image.getHeight();
        double ratio = Math.min(maxSize * 1.0 / Math.max(width, height), 1);
        int newWidth = (int)(ratio * width);
        int newHeight = (int)(ratio * height);
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
        ImageIO.write(newImage, suffix, out);
    }
    
    public static void main(String[] args) throws Exception {
        String url = "http://h107.qiniudn.com/20150327/20150327-173724-0_LOCAL58_21132711066390.jpg";
        FileOutputStream out = new FileOutputStream("/Users/wanglongtao/Desktop/tmp.jpg");
        ImageUtils.compress(url, out, 250);
        out.close();
        System.exit(0);
    }

}

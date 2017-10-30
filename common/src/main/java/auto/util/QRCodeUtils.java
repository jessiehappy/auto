package auto.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import auto.datamodel.controller.constants.JsonStatus;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author wangWentao
 *
 */
public class QRCodeUtils {
	
	private static final int QRCOLOR = 0xFF000000;   //default qrcolor is black 0xFF000000
	
    private static final int BGWHITE = 0xFFFFFFFF;   //default background is white 0xFFFFFFFF

    private static final int DEFAULT_QRCODE_WIDTH = 400; //the default width of the qrCode 

    private static final int DEFAULT_QRCODE_HEIGHT = 400; //the default height of the qrCode
    
    
    /**
     * 生成二维码bufferedImage图片
     * 
     * @param token
     * @param content
     * @param logoUrl
     * @return
     * @throws Exception
     */
    public static String getQRCodeBufferedImage(String token, String content, String logoUrl) throws Exception {
        BitMatrix bm = null;
        BufferedImage image = null;
//        if(content == null || "".equals(content)) return null; 
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
	            
	     try {
	     	bm = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, DEFAULT_QRCODE_WIDTH, DEFAULT_QRCODE_HEIGHT, getDecodeHintType());
	     } catch (Exception e) {
	     	// TODO Auto-generated catch block
	    	 throw new RuntimeException("Two-dimensional code generation failed",e);
	     }
        int w = bm.getWidth();
        int h = bm.getHeight();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
            }
        }
        //add logo for image
        return addLogoQRCode(image, logoUrl, new LogoConfig(),token);
    }
    
    /**
     * read logo image
     * @param logoUrl
     * @return
     */
    private static BufferedImage readLogo(String logoUrl) {
    	if(logoUrl == null) return null;
    	BufferedImage logo = null;
		try {
			logo = ImageIO.read(new File(logoUrl));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return logo;
    }
    /**
     * Set the width of the logo image according to the 2D code
     * @param image
     * @param logoPic
     * @return
     */
    private static Integer setLogoWidth (BufferedImage image, BufferedImage logo) {
    	if (image == null || logo == null) {
			return null;
		}
    	return logo.getWidth(null)>image.getWidth()*3/10?(image.getWidth()*3/10):logo.getWidth(null);
    }
    /**
     * Set the length of the logo image according to the 2D code
     * @param image
     * @param logoPic
     * @return
     */
    private static Integer setLogoHeight (BufferedImage image, BufferedImage logo) {
    	if (image == null || logo == null) {
			return null;
		}
    	return logo.getHeight(null)>image.getHeight()*3/10?(image.getHeight()*3/10):logo.getHeight(null);
    }
    
    /**
     * 给二维码图片添加Logo(没有logo则不添加)
     * @param bim two dimensional code
     * @param logoPic logoAddress
     * @param logoConfig setLogo
     * @param productName two dimensional code name
     * @return
     * @throws Exception 
     */
    private static String addLogoQRCode(BufferedImage image, String logoUrl, LogoConfig logoConfig, String token) throws Exception {
        if(image == null) return null;
    	//读取二维码图片，并构建绘图对象  
    	//Read the two-dimensional code image, and build the drawing object    
    	Graphics2D g = image.createGraphics();
        //读取Logo图片           
    	//Read the logo image
		BufferedImage logo = readLogo(logoUrl);
		if (logo != null) {
			//设置logo的大小,默认设置为二维码图片的20%,因为过大会盖掉二维码
            //Set the size of the logo. Default set the picture for the two-dimensional code 20%,
            //because the Congress cover the two-dimensional code
            int widthLogo = setLogoWidth(image, logo), heightLogo = setLogoHeight(image, logo);
            //logo放在中心
            //put logo in the center
	        int x = (image.getWidth() - widthLogo) / 2;
	        int y = (image.getHeight() - heightLogo) / 2;
//	        logo放在右下角
//	        int x = (image.getWidth() - widthLogo);
//	        int y = (image.getHeight() - heightLogo);
	        //开始绘制图片
	        //Start drawing pictures
	        g.drawImage(logo, x, y, widthLogo, heightLogo, null);
//		    g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
//		    g.setStroke(new BasicStroke(logoConfig.getBorder()));
//		    g.setColor(logoConfig.getBorderColor());
//		    g.drawRect(x, y, widthLogo, heightLogo);
	        Shape shape = new RoundRectangle2D.Float(x, y, widthLogo, heightLogo, 6, 6);
	        g.setStroke(new BasicStroke(3f));
	        g.draw(shape);
	        g.dispose();//bushu
	        logo.flush();
	        image.flush();
		} else {
			image.flush();
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.flush();
		ImageIO.write(image, "jpg", out);
        //二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
        //可以看到这个方法最终返回的是这个二维码的imageBase64字符串
        //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>  其中${imageBase64QRCode}对应二维码的imageBase64字符串
//        ImageIO.write(image, "png", new File("C:/d/other/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg")); //TODO  
//        ImageIO.write(image, "jpg", new File("C:/d/other/" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".jpg"));
//        String imageBase64QRCode =  Base64.encodeBase64URLSafeString(out.toByteArray());
		// upload image to image server
		String url = ImageUploadUtils.uploadQrcode(token, out.toByteArray(), getSuffix(logoUrl));
        try {
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
        return url;
    }
    
    private static String getSuffix(String logoUrl) {
    	logoUrl = logoUrl.toLowerCase();
    	if (logoUrl.endsWith(".bmp")) return "bmp";
    	if (logoUrl.endsWith(".png")) return "png";
        if (logoUrl.endsWith(".gif")) return "gif";
        if (logoUrl.endsWith(".jpeg")) return "jpeg";
		return "jpg";
	}

	/**
     * 给二维码添加说明
     * @param productName
     * @param image
     */
    /*private static void addQRCodeName(String productName,BufferedImage image) {
    	if (productName != null && ! productName.equals("")) {
	    	//新的图片，把带logo的二维码下面加上文字
	        BufferedImage outImage = new BufferedImage(DEFAULT_QRCODE_WIDTH, DEFAULT_QRCODE_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
	        Graphics2D outg = outImage.createGraphics();
	        //画二维码到新的面板
	        outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
	        //画文字到新的面板
	        outg.setColor(Color.BLACK); 
	        outg.setFont(new Font("宋体",Font.BOLD,30)); //字体、字型、字号 
	        int strWidth = outg.getFontMetrics().stringWidth(productName);
	        if (strWidth > 399) {
	//          //长度过长就截取前面部分
	//          outg.drawString(productName, 0, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 5 ); //画文字
	            //长度过长就换行
	            String productName1 = productName.substring(0, productName.length()/2);
	            String productName2 = productName.substring(productName.length()/2, productName.length());
	            int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
	            int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
	            outg.drawString(productName1, 200  - strWidth1/2, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 );
	            BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
	            Graphics2D outg2 = outImage2.createGraphics();
	            outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
	            outg2.setColor(Color.BLACK); 
	            outg2.setFont(new Font("宋体",Font.BOLD,30)); //字体、字型、字号 
	            outg2.drawString(productName2, 200  - strWidth2/2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 5 );
	            outg2.dispose(); 
	            outImage2.flush();
	            outImage = outImage2;
	        }else {
	            outg.drawString(productName, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 ); //画文字 
	        }
	        outg.dispose(); 
	        outImage.flush();
	        image = outImage;
    	}
    }*/
    /**
     * 设置二维码的格式参数
     *
     * @return
     */
	@SuppressWarnings("deprecation")
	public static Map<EncodeHintType, Object> getDecodeHintType(){
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, JsonStatus.CHARSET);//utf-8
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);
        return hints;
    }
	
	public static void main(String[] args) throws Exception {
		String content = "http://192.168.9.4:8080/img/qrcode/2017102517174913559.BMP";
		
		String token =  ImageUploadUtils.getImageToken();
		System.out.println(token);
		if (token == null) {
			System.out.println("授权失败");
		}else {
			String logoUrl = "C:/d/source/image/logo.BMP";
//		String content = "";
			try {
				String url =  QRCodeUtils.getQRCodeBufferedImage(token,content,logoUrl);
				System.out.println(url);
			} catch (RuntimeException rune){
				System.out.println(rune.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*if(token == null) {
			System.out.println("授权失败！");
		}else {
			File file = new File("C:/d/other/logo.jpg");
			String ext = ImageUtils.getSuffix(file.getName());
//			String url = uploadVerify(token, File2byte(file), ext);
//			System.out.println(url);
		}*/
		
		
	}
}
class LogoConfig {
	// logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public int DEFAULT_BORDER = 2;
    // logo大小默认为照片的1/5
    public static final int DEFAULT_LOGOPART = 5;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    /**
     * Creates a default config with on color {@link #BLACK} and off color
     * {@link #WHITE}, generating normal black-on-white barcodes.
     */
    public LogoConfig() {
    	this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }

    public LogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }
}
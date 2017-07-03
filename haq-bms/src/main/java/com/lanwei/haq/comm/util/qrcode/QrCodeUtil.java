package com.lanwei.haq.comm.util.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 * jar 包依赖  ：
 * <dependency>
 *		<groupId>com.google.zxing</groupId>
 *	 	<artifactId>core</artifactId>
 *	 	<version>2.2</version>
 * </dependency>
 * <dependency>
 *		<groupId>com.google.zxing</groupId>
 *		<artifactId>javase</artifactId>
 *		<version>2.2</version>
 * </dependency>
 *
 *
 * @author		liuxinyun
 * @date		2016年8月25日
 * @version		1.0
 * @copyright	neusoft
 */
public class QrCodeUtil {

	private final static Logger log = Logger.getLogger(QrCodeUtil.class);
	/* 默认字符集 */
	public static final String CHARACTER_SET = "utf-8";
	/* 默认二维码尺寸  */
	public static final int QRCODE_SIZE = 260;

	/**
	 * 生成二维码图片
	 *
	 * @author		liuxinyun
	 * @date		2016年8月25日
	 * @param 		content
	 * @throws 		WriterException
	 * @throws 		IOException
	 */
	public static BufferedImage create(String content) throws WriterException, IOException {
		return create(content, CHARACTER_SET, QRCODE_SIZE, QRCODE_SIZE);
	}

	/**
	 * 生成二维码，宽高相同
	 *
	 * @author		liuxinyun
	 * @date		2016年8月25日
	 * @param 		content
	 * @param 		size
	 * @throws 		WriterException
	 * @throws 		IOException
	 */
	public static BufferedImage create(String content, int size) throws WriterException, IOException {
		return create(content,CHARACTER_SET, size, size);
	}

	/**
	 * 生成二维码图片，字符集自定
	 *
	 * @author		liuxinyun
	 * @date		2016年8月25日
	 * @param 		content
	 * @throws 		WriterException
	 * @throws 		IOException
	 */
	public static BufferedImage create(String content,  String charset) throws WriterException, IOException {
		return create(content, charset, QRCODE_SIZE, QRCODE_SIZE);
	}

	/**
	 * 生成二维码图片，参数自定义
	 *
	 * @author		liuxinyun
	 * @date		2016年8月25日
	 * @param 		content
	 * @param 		charset
	 * @param 		width
	 * @param 		height
	 * @throws 		WriterException
	 * @throws 		IOException
	 */
	public static BufferedImage create(String content, String charset, int width, int height)
			throws WriterException, IOException {
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		hints.put(EncodeHintType.MARGIN, 0);
		// 生成矩阵
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		log.info("二维码生成成功！");
		return image;
	}

	/**
	 * 解析二维码内容，默认字符集
	 *
	 * @author		liuxinyun
	 * @date		2016年8月25日
	 * @param 		filePath
	 * @return		String
	 * @throws 		IOException
	 * @throws 		NotFoundException
	 */
	public static String parse(String filePath) throws IOException, NotFoundException {
		return parse(filePath, CHARACTER_SET);
	}

	/**
	 * 解析二维码内容
	 *
	 * @author		liuxinyun
	 * @date		2016年8月25日
	 * @param 		in
	 * @return		String
	 * @throws 		IOException
	 * @throws 		NotFoundException
	 */
	public static String parse(InputStream in) throws IOException, NotFoundException {
		return parse(in, CHARACTER_SET);
	}


	/**
	 * 解析二维码内容
	 *
	 * @author		liuxinyun
	 * @date		2016年8月25日
	 * @param 		filePath
	 * @param 		charset
	 * @return		String
	 * @throws 		IOException
	 * @throws 		NotFoundException
	 */
	public static String parse(String filePath, String charset) throws IOException, NotFoundException {
		return parse(new FileInputStream(filePath), charset);
	}

	/**
	 * 解析二维码内容
	 *
	 * @author		liuxinyun
	 * @date		2016年8月25日
	 * @param 		in
	 * @param 		charset
	 * @return		String
	 * @throws 		IOException
	 * @throws 		NotFoundException
	 */
	public static String parse(InputStream in, String charset) throws IOException, NotFoundException {
		// 解析参数
		Map<DecodeHintType, Object> hints = new HashMap<>();
		hints.put(DecodeHintType.CHARACTER_SET, charset);
		// 图片
		Binarizer binarizer = new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(in)));
		String str = new MultiFormatReader().decode(new BinaryBitmap(binarizer), hints).getText();
		log.info("二维码解析成功");
		return str;
	}
}
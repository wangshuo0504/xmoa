package com.zkxy.xmoa.pdf.factory;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;

/**
 * <b>Description</b><br>
 * ITextRenderer 对象工厂,提供性能,加载中文字体集(大小20M),故增加对象池
 * @author DyLanMJ
 */
public class ITextRendererObjectFactory extends BasePoolableObjectFactory {
	private static GenericObjectPool itextRendererObjectPool = null;

	@Override
	public Object makeObject() throws Exception {
		ITextRenderer renderer = createTextRenderer();
		return renderer;
	}
	/**
	 * <b>Description</b><br>
	 * 获取对象池,使用对象工厂 后提供性能,能够支持 500线程 迭代10
	 * @author DyLanMJ
	 */
	public static GenericObjectPool getObjectPool(){
		synchronized (ITextRendererObjectFactory.class) {
			if(itextRendererObjectPool==null){
				itextRendererObjectPool = new GenericObjectPool(new ITextRendererObjectFactory());
				GenericObjectPool.Config config = new GenericObjectPool.Config();
				config.lifo = false;
				config.maxActive = 15;
				config.maxIdle = 5;
				config.minIdle = 1;
				config.maxWait = 5 * 1000;
				itextRendererObjectPool.setConfig(config);
			}
		}
		
		return itextRendererObjectPool;
	}

	/**
	 *<b>Description</b><br>
	 *初始化
     *@author DyLanMJ
	 */
	public static synchronized ITextRenderer createTextRenderer() throws DocumentException, IOException {
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver();
		addFonts(fontResolver);
		return renderer;
	}

	/**
	 * <b>Description</b><br>
	 * 添加字体 <br>
	 * 默认文件夹为：src/main/resources/pdf/fonts
     * @author DyLanMJ
	 */
	public static ITextFontResolver addFonts(ITextFontResolver fontResolver)
			throws DocumentException, IOException {
		// Font fontChinese = null;
		// BaseFont bfChinese = BaseFont.createFont("STSong-Light",
		// "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		// fontChinese = new Font(bfChinese, 12, Font.NORMAL);
		File fontsDir = new File( Thread.currentThread().getContextClassLoader().getResource("pdf/fonts").getPath().replaceAll("%20", " ") );
		if (fontsDir != null && fontsDir.isDirectory() ) {
			File[] files = fontsDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f == null || f.isDirectory()) {
					break;
				}
				fontResolver.addFont(f.getAbsolutePath(), BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			}
		}
		return fontResolver;
	}
	
}

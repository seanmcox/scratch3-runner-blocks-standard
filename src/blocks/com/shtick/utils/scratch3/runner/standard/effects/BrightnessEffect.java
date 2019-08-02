/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.effects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.IndexColorModel;
import java.awt.image.RGBImageFilter;

import com.shtick.util.image.ImageUtils;
import com.shtick.utils.scratch3.runner.core.GraphicEffect;

/**
 * @author Sean
 *
 */
public class BrightnessEffect implements GraphicEffect {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.GraphicEffect#getName()
	 */
	@Override
	public String getName() {
		return "brightness";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.GraphicEffect#getAffectedImage(java.awt.image.BufferedImage, double)
	 */
	@Override
	public BufferedImage getAffectedImage(BufferedImage image, double value) {
		BrightnessFilter filter = new BrightnessFilter(value);
		switch(image.getType()){
		case BufferedImage.TYPE_BYTE_INDEXED:
			return new BufferedImage(filter.filterIndexColorModel((IndexColorModel)image.getColorModel()),image.getRaster(),image.isAlphaPremultiplied(),ImageUtils.getBufferedImageProperties(image));
		default:
	        ImageProducer ip = image.getSource();
	        ImageProducer fip = new FilteredImageSource(ip,filter);
	        Image         fi =(new Label()).createImage(fip);
	        image=new BufferedImage(image.getColorModel(),image.getRaster(),image.isAlphaPremultiplied(),ImageUtils.getBufferedImageProperties(image));
	        Graphics2D riContext = image.createGraphics();
	        riContext.drawImage(fi, 0, 0, null);
	        return image;
		}
	}
	
	private class BrightnessFilter extends RGBImageFilter {
		double brightness;
		
		/**
		 * @param brightness
		 */
		public BrightnessFilter(double brightness) {
			this.brightness = brightness/100;
		}

		@Override
		public int filterRGB(int x, int y, int rgb) {
			if(brightness>=1)
				return (rgb&0xFF000000)|0x0FFFFFF;
			if(brightness<=-1)
				return (rgb&0xFF000000);
			if(brightness==0)
				return rgb;
			
	        int blue=(rgb&0xFF);
	        int green=((rgb>>8)&0xFF);
	        int red=((rgb>>16)&0xFF);
	        red=brighten(red);
	        blue=brighten(blue);
	        green=brighten(green);
	        return (rgb&0xFF000000)+(red<<16)+(green<<8)+blue;
		}
		
		private int brighten(int value) {
			if(brightness<0)
				return (int)(value*(1+brightness));
			return (int)(value+(255-value)*brightness);
		}
	};

}

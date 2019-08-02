/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.effects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.shtick.util.image.ImageUtils;
import com.shtick.util.image.ImageUtils.DownsampleMethod;
import com.shtick.util.image.ImageUtils.UpsampleMethod;
import com.shtick.utils.scratch3.runner.core.GraphicEffect;

/**
 * @author sean.cox
 *
 */
public class MosaicEffect implements GraphicEffect {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.GraphicEffect#getName()
	 */
	@Override
	public String getName() {
		return "mosaic";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.GraphicEffect#getAffectedImage(java.awt.image.BufferedImage, double)
	 */
	@Override
	public BufferedImage getAffectedImage(BufferedImage image, double value) {
		int oldWidth = image.getWidth();
		int oldHeight = image.getHeight();

		int factor = (int)Math.round(Math.abs(value)/10)+1;
		int newWidth = Math.max(oldWidth/factor,1);
		int newHeight = Math.max(oldHeight/factor,1);
		BufferedImage tile = ImageUtils.resize(image, newWidth, newHeight, DownsampleMethod.AVERAGE_COLOR, UpsampleMethod.INTERPOLATE_1ST_ORDER);
		BufferedImage retval = new BufferedImage(oldWidth, oldHeight, image.getType());
		Graphics g = retval.getGraphics();
		int i,j;
		for(i=0;i<factor;i++)
			for(j=0;j<factor;j++)
				g.drawImage(tile, oldWidth*i/factor, oldHeight*j/factor, null);
		
		return retval;
	}
}

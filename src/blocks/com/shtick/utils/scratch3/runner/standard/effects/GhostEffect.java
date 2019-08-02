/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.effects;

import java.awt.image.BufferedImage;

import com.shtick.utils.scratch3.runner.core.GraphicEffect;

/**
 * @author sean.cox
 *
 */
public class GhostEffect implements GraphicEffect {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.GraphicEffect#getName()
	 */
	@Override
	public String getName() {
		return "ghost";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.GraphicEffect#getAffectedImage(java.awt.image.BufferedImage, double)
	 */
	@Override
	public BufferedImage getAffectedImage(BufferedImage image, double value) {
		int rgb;
		int alpha;
		for(int i=0;i<image.getWidth();i++) {
			for(int j=0;j<image.getHeight();j++) {
				rgb = image.getRGB(i, j);
				alpha = (int)((rgb>>24)*(1-value));
				image.setRGB(i, j, (rgb&0x00FFFFFF)|(alpha<<24));
			}
		}
		return image;
	}

}

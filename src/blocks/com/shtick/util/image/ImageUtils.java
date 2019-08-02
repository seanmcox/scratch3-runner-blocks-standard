/**
 * 
 */
package com.shtick.util.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

/**
 * @author sean.cox
 *
 */
public class ImageUtils {
	/**
	 * A set of image resizing methods that apply to downsampling, that can be applied along one axis separate from the other.
	 * @author sean.cox
	 */
	public enum DownsampleMethod {
		/**
		 * A method that maps takes a sample pixel from the old image to use for the new image's pixel.
		 * The sample is taken in a regular and ordered way.
		 */
		REPRESENTATIVE_PIXEL_ORDERED,
		/**
		 * A method that averages the colors in the old image to generate the pixel color for the new image.
		 * Cannot be used with an IndexedColorModel.
		 */
		AVERAGE_COLOR
	}
	
	/**
	 * A set of image resizing methods that apply to upsampling, that can be applied along one axis separate from the other.
	 * @author sean.cox
	 */
	public enum UpsampleMethod {
		/**
		 * A method that increases the size of the image by increasing the size of the pixels.
		 */
		GROW_PIXELS,
		/**
		 * A method that uses 1st order interpolation to derive pixel colors.
		 * Cannot be used with an IndexedColorModel.
		 */
		INTERPOLATE_1ST_ORDER
	}
	
	/**
	 * 
	 * @param oldImage
	 * @param newWidth
	 * @param newHeight
	 * @param downsampleMethod
	 * @param upsampleMethod
	 * @return The resized image.
	 */
	public static BufferedImage resize(BufferedImage oldImage, int newWidth, int newHeight, DownsampleMethod downsampleMethod, UpsampleMethod upsampleMethod) {
		int oldWidth = oldImage.getWidth();
		int oldHeight = oldImage.getHeight();

        WritableRaster oldRaster=oldImage.getRaster();
        if((newHeight<oldHeight)||(newWidth<oldWidth)) {
	    		if(downsampleMethod==DownsampleMethod.AVERAGE_COLOR) {
	    			if((oldImage.getType()==BufferedImage.TYPE_BYTE_INDEXED)||(oldImage.getType()==BufferedImage.TYPE_BYTE_BINARY)||(oldImage.getColorModel() instanceof IndexColorModel)) {
	    				throw new RuntimeException("Cannot downsample by average with images that use an indexed color model.");
	    			}
	    			if((oldImage.getType()==BufferedImage.TYPE_CUSTOM)&&(oldRaster.getNumBands()!=oldImage.getColorModel().getNumComponents())&&(oldRaster.getNumBands()!=1)) {
	    				throw new RuntimeException("Cannot downsample by average with custom sample model with an unusual correlation between raster data bands and color model components.");
	    			}
	    		}
	    }
        if((newHeight>oldHeight)||(newWidth>oldWidth)) {
	    		if(upsampleMethod==UpsampleMethod.INTERPOLATE_1ST_ORDER) {
	    			if((oldImage.getType()==BufferedImage.TYPE_BYTE_INDEXED)||(oldImage.getType()==BufferedImage.TYPE_BYTE_BINARY)||(oldImage.getColorModel() instanceof IndexColorModel)) {
	    				throw new RuntimeException("Cannot upsample by interpolation with images that use an indexed color model.");
	    			}
	    			if((oldImage.getType()==BufferedImage.TYPE_CUSTOM)&&(oldRaster.getNumBands()!=oldImage.getColorModel().getNumComponents())&&(oldRaster.getNumBands()!=1)) {
	    				throw new RuntimeException("Cannot upsample by interpolation with custom sample model with an unusual correlation between raster data bands and color model components.");
	    			}
	    		}
	    }
        
        int[] oldPixels=new int[oldWidth*oldRaster.getNumBands()];
        WritableRaster newRaster=oldRaster.createCompatibleWritableRaster(newWidth,newHeight);
        int[] newPixels=new int[newWidth*newHeight*newRaster.getNumBands()];
        int j,i,b,oldJTop,oldJBottom,oldILeft,oldIRight;
        double newOverOldHeight = newHeight/(double)oldHeight;
        double newOverOldWidth = newWidth/(double)oldWidth;
        double jOld, iOld;
		int[] bandSizes;
		if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) {
			bandSizes = newRaster.getSampleModel().getSampleSize();
		}
		else {
			bandSizes = oldImage.getColorModel().getComponentSize();
		}
		for(b=0;b<bandSizes.length;b++)
			bandSizes[b] = 1<<bandSizes[b];
        for(j=0;j<newHeight;j++){
            if((newOverOldHeight>1.0)&&(upsampleMethod==UpsampleMethod.INTERPOLATE_1ST_ORDER)) {
        		jOld = (j+0.5)/newOverOldHeight - 0.5;
        		oldJTop = (int)Math.floor(jOld);
        		oldJBottom = (int)Math.ceil(jOld);
        		if(oldJTop<0) {
        			jOld = -jOld;
            		int[] oldPixelsStageTop=new int[oldWidth*oldRaster.getNumBands()];
            		int[] oldPixelsStageBottom=new int[oldWidth*oldRaster.getNumBands()];
            		oldRaster.getPixels(0,0,oldWidth,1,oldPixelsStageTop);
            		oldRaster.getPixels(0,1,oldWidth,1,oldPixelsStageBottom);
            		for(int stagedX=0;stagedX<oldWidth;stagedX++) {
            			// Interpolate top to bottom pixels
            			if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) { // Assume raster stores all color components in separate bands
    	                		for(b=0;b<newRaster.getNumBands();b++) {
    	                			oldPixels[stagedX*newRaster.getNumBands()+b] = applyValueBounds((int)Math.round(
    	                					oldPixelsStageTop[stagedX*newRaster.getNumBands()+b]+jOld*(oldPixelsStageTop[stagedX*newRaster.getNumBands()+b]-oldPixelsStageBottom[stagedX*newRaster.getNumBands()+b])
    	                			),0,bandSizes[b]-1);
    	                		}
            			}
            			else {
            				ColorModel colorModel = oldImage.getColorModel();
            				int[] yT = new int[colorModel.getNumComponents()];
            				int[] ppTop = new int[colorModel.getNumComponents()];
            				int[] ppBottom = new int[colorModel.getNumComponents()];
            				colorModel.getComponents(oldPixelsStageTop[stagedX], ppTop, 0);
            				colorModel.getComponents(oldPixelsStageBottom[stagedX], ppBottom, 0);
            				for(int pi = 0;pi<yT.length;pi++)
            					yT[pi]=applyValueBounds((int)Math.round(ppTop[pi]+jOld*(ppTop[pi]-ppBottom[pi])),0,bandSizes[pi]-1);
            				oldPixels[stagedX] = colorModel.getDataElement(yT, 0);
            			}
            		}
        		}
        		else if(oldJBottom>=oldHeight) {
            		jOld -= oldJTop;
            		int[] oldPixelsStageTop=new int[oldWidth*oldRaster.getNumBands()];
            		int[] oldPixelsStageBottom=new int[oldWidth*oldRaster.getNumBands()];
            		oldRaster.getPixels(0,oldJTop-1,oldWidth,1,oldPixelsStageTop);
            		oldRaster.getPixels(0,oldJTop,oldWidth,1,oldPixelsStageBottom);
            		for(int stagedX=0;stagedX<oldWidth;stagedX++) {
            			// Interpolate top to bottom pixels
            			if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) { // Assume raster stores all color components in separate bands
    	                		for(b=0;b<newRaster.getNumBands();b++) {
    	                			oldPixels[stagedX*newRaster.getNumBands()+b] =
    	                					applyValueBounds((int)Math.round(
    	                							oldPixelsStageBottom[stagedX*newRaster.getNumBands()+b]
    	                							+jOld*(oldPixelsStageBottom[stagedX*newRaster.getNumBands()+b]-oldPixelsStageTop[stagedX*newRaster.getNumBands()+b])
    	                					),0,bandSizes[b]-1);
    	                		}
            			}
            			else {
            				ColorModel colorModel = oldImage.getColorModel();
            				int[] yT = new int[colorModel.getNumComponents()];
            				int[] ppTop = new int[colorModel.getNumComponents()];
            				int[] ppBottom = new int[colorModel.getNumComponents()];
            				colorModel.getComponents(oldPixelsStageTop[stagedX], ppTop, 0);
            				colorModel.getComponents(oldPixelsStageBottom[stagedX], ppBottom, 0);
            				for(int pi = 0;pi<yT.length;pi++)
            					yT[pi]=applyValueBounds((int)Math.round(ppBottom[pi]+jOld*(ppBottom[pi]-ppTop[pi])),0,bandSizes[pi]-1);
            				oldPixels[stagedX] = colorModel.getDataElement(yT, 0);
            			}
            		}
        		}
        		else {
            		jOld -= oldJTop;
            		int[] oldPixelsStageTop=new int[oldWidth*oldRaster.getNumBands()];
            		int[] oldPixelsStageBottom=new int[oldWidth*oldRaster.getNumBands()];
            		oldRaster.getPixels(0,oldJTop,oldWidth,1,oldPixelsStageTop);
            		oldRaster.getPixels(0,oldJBottom,oldWidth,1,oldPixelsStageBottom);
            		for(int stagedX=0;stagedX<oldWidth;stagedX++) {
            			// Interpolate top to bottom pixels
            			if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) { // Assume raster stores all color components in separate bands
    	                		for(b=0;b<newRaster.getNumBands();b++) {
    	                			oldPixels[stagedX*newRaster.getNumBands()+b] = (int)Math.round(jOld*oldPixelsStageBottom[stagedX*newRaster.getNumBands()+b]+(1.0-jOld)*oldPixelsStageTop[stagedX*newRaster.getNumBands()+b]);
    	                		}
            			}
            			else {
            				ColorModel colorModel = oldImage.getColorModel();
            				int[] yT = new int[colorModel.getNumComponents()];
            				int[] ppTop = new int[colorModel.getNumComponents()];
            				int[] ppBottom = new int[colorModel.getNumComponents()];
            				colorModel.getComponents(oldPixelsStageTop[stagedX], ppTop, 0);
            				colorModel.getComponents(oldPixelsStageBottom[stagedX], ppBottom, 0);
            				for(int pi = 0;pi<yT.length;pi++)
            					yT[pi]=(int)Math.round(jOld*ppBottom[pi]+(1-jOld)*ppTop[pi]);
            				oldPixels[stagedX] = colorModel.getDataElement(yT, 0);
            			}
            		}
        		}
            }
            else if((newOverOldHeight<1.0)&&(downsampleMethod==DownsampleMethod.AVERAGE_COLOR)) {
                oldJTop=j*oldHeight/newHeight; // Inclusive
                oldJBottom=(j+1)*oldHeight/newHeight; // Exclusive
            		int stagedHeight = (oldJBottom-oldJTop);
            		int[] oldPixelsStage=new int[oldWidth*stagedHeight*oldRaster.getNumBands()];
            		oldRaster.getPixels(0,oldJTop,oldWidth,stagedHeight,oldPixelsStage);
            		for(int stagedX=0;stagedX<oldWidth;stagedX++) {
            			// Average over pixels from top to bottom.
            			if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) { // Assume raster stores all color components in separate bands
	                		for(b=0;b<newRaster.getNumBands();b++) {
	                			int yT = 0;
		            			for(int stagedY=0;stagedY<stagedHeight;stagedY++) {
		            				yT+=oldPixelsStage[(stagedX+stagedY*oldWidth)*newRaster.getNumBands()+b];
		            			}
		            			oldPixels[stagedX*newRaster.getNumBands()+b] = yT/stagedHeight;
	                		}
            			}
            			else { // Only one band that needs colors unpacked.
            				ColorModel colorModel = oldImage.getColorModel();
            				int[] yT = new int[colorModel.getNumComponents()];
            				int[] pp = new int[colorModel.getNumComponents()];
            				int p;
	            			for(int stagedY=0;stagedY<stagedHeight;stagedY++) {
	            				p = oldPixelsStage[(stagedX+stagedY*oldWidth)];
	            				colorModel.getComponents(p, pp, 0);
	            				for(int pi = 0;pi<pp.length;pi++)
	            					yT[pi]+=pp[pi];
	            			}
            				for(int pi = 0;pi<yT.length;pi++)
            					yT[pi]/=stagedHeight;
	            			oldPixels[stagedX] = colorModel.getDataElement(yT, 0);
            			}
            		}
            }
            else {
                oldJTop=j*oldHeight/newHeight; // Inclusive
                oldRaster.getPixels(0,oldJTop,oldWidth,1,oldPixels);
            }
            for(i=0;i<newWidth;i++){
                oldILeft=i*oldWidth/newWidth;
                oldIRight=(i+1)*oldWidth/newWidth;
                if((newOverOldWidth>1.0)&&(oldWidth>1)&&(upsampleMethod==UpsampleMethod.INTERPOLATE_1ST_ORDER)) {
	            		iOld = (i+0.5)/newOverOldWidth - 0.5;
	            		oldILeft = (int)Math.floor(iOld);
	            		oldIRight = (int)Math.ceil(iOld);
	            		if(oldILeft<0) {
	            			iOld = -iOld;

                			if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) { // Assume raster stores all color components in separate bands
    	                		for(b=0;b<newRaster.getNumBands();b++) {
    	                			newPixels[(i+j*newWidth)*newRaster.getNumBands()+b] = applyValueBounds((int)Math.round(
    	                					oldPixels[b]+iOld*(oldPixels[b]-oldPixels[newRaster.getNumBands()+b])
    	                			),0,bandSizes[b]-1);
    	                		}
                			}
                			else {
                				ColorModel colorModel = oldImage.getColorModel();
                				int[] xT = new int[colorModel.getNumComponents()];
                				int[] ppLeft = new int[colorModel.getNumComponents()];
                				int[] ppRight = new int[colorModel.getNumComponents()];
                				colorModel.getComponents(oldPixels[0], ppLeft, 0);
                				colorModel.getComponents(oldPixels[1], ppRight, 0);
                				for(int pi = 0;pi<xT.length;pi++)
                					xT[pi]=applyValueBounds((int)Math.round(ppLeft[pi]+iOld*(ppLeft[pi]-ppRight[pi])),0,bandSizes[pi]-1);
                				newPixels[i+j*newWidth] = colorModel.getDataElement(xT, 0);
                			}
	            		}
	            		else if(oldIRight*newRaster.getNumBands()>=oldPixels.length) {
	            			int iOldInt = oldILeft;
	                		double iOldRemainder = iOld - iOldInt;
	                		
                			if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) { // Assume raster stores all color components in separate bands
    	                		for(b=0;b<newRaster.getNumBands();b++) {
    	                			newPixels[(i+j*newWidth)*newRaster.getNumBands()+b] =
    	                					applyValueBounds((int)Math.round(
    	                							oldPixels[iOldInt*newRaster.getNumBands()+b]
    	                							+iOldRemainder*(oldPixels[iOldInt*newRaster.getNumBands()+b] - oldPixels[(iOldInt-1)*newRaster.getNumBands()+b])
    	                									),0,bandSizes[b]-1);
    	                		}
                			}
                			else {
                				ColorModel colorModel = oldImage.getColorModel();
                				int[] xT = new int[colorModel.getNumComponents()];
                				int[] ppLeft = new int[colorModel.getNumComponents()];
                				int[] ppRight = new int[colorModel.getNumComponents()];
                				colorModel.getComponents(oldPixels[iOldInt-1], ppLeft, 0);
                				colorModel.getComponents(oldPixels[iOldInt], ppRight, 0);
                				for(int pi = 0;pi<xT.length;pi++)
                					xT[pi]=applyValueBounds((int)Math.round(ppRight[pi]+iOldRemainder*(ppRight[pi]-ppLeft[pi])),0,bandSizes[pi]-1);
                				newPixels[i+j*newWidth] = colorModel.getDataElement(xT, 0);
                			}
	            		}
	            		else {
	            			int iOldInt = oldILeft;
	                		double iOldRemainder = iOld - iOldInt;
	                		
                			if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) { // Assume raster stores all color components in separate bands
	    	                		for(b=0;b<newRaster.getNumBands();b++) {
	    	                			newPixels[(i+j*newWidth)*newRaster.getNumBands()+b] =
	    	                					(int)Math.round(
	    	                							(1.0-iOldRemainder)*oldPixels[iOldInt*newRaster.getNumBands()+b]
	    	                							+iOldRemainder*oldPixels[(iOldInt+1)*newRaster.getNumBands()+b]
	    	                					);
	    	                		}
                			}
                			else {
                				ColorModel colorModel = oldImage.getColorModel();
                				int[] xT = new int[colorModel.getNumComponents()];
                				int[] ppLeft = new int[colorModel.getNumComponents()];
                				int[] ppRight = new int[colorModel.getNumComponents()];
                				colorModel.getComponents(oldPixels[iOldInt], ppLeft, 0);
                				colorModel.getComponents(oldPixels[iOldInt+1], ppRight, 0);
                				for(int pi = 0;pi<xT.length;pi++)
                					xT[pi]=(int)Math.round(iOldRemainder*ppRight[pi]+(1-iOldRemainder)*ppLeft[pi]);
                				newPixels[i+j*newWidth] = colorModel.getDataElement(xT, 0);
                			}
	            		}
                }
                else if((oldIRight-oldILeft>1)&&(downsampleMethod==DownsampleMethod.AVERAGE_COLOR)) {
		    			// Average over pixels from left to right.
	        			if((newRaster.getNumBands()>1)||(oldImage.getColorModel().getNumComponents()==1)) { // Assume raster stores all color components in separate bands
			        		for(b=0;b<newRaster.getNumBands();b++) {
			        			int xT = 0;
			        			for(int stagedX=oldILeft;stagedX<oldIRight;stagedX++) {
			        				xT+=oldPixels[stagedX*newRaster.getNumBands()+b];
			        			}
			        			newPixels[(i+j*newWidth)*newRaster.getNumBands()+b] = xT/(oldIRight-oldILeft);
			        		}
	        			}
	        			else if(oldImage.getColorModel().getNumComponents()>1) { // Only one band that needs colors unpacked.
            				ColorModel colorModel = oldImage.getColorModel();
            				int[] xT = new int[colorModel.getNumComponents()];
            				int[] pp = new int[colorModel.getNumComponents()];
            				int p;
		        			for(int stagedX=oldILeft;stagedX<oldIRight;stagedX++) {
	            				p = oldPixels[stagedX];
	            				colorModel.getComponents(p, pp, 0);
	            				for(int pi = 0;pi<pp.length;pi++)
	            					xT[pi]+=pp[pi];
		        			}
		        			newPixels[i+j*newWidth] = colorModel.getDataElement(xT, 0);
	        			}
                }
                else {
	                for(b=0;b<newRaster.getNumBands();b++)
	                    newPixels[(i+j*newWidth)*newRaster.getNumBands()+b]=oldPixels[oldILeft*newRaster.getNumBands()+b];
                }
            }
        }
        newRaster.setPixels(0,0,newWidth,newHeight,newPixels);
        return new BufferedImage(oldImage.getColorModel(),newRaster,oldImage.isAlphaPremultiplied(),getBufferedImageProperties(oldImage));
	}
	
	private static int applyValueBounds(int value, int min, int max) {
		if(value<min)
			return min;
		if(value>max)
			return max;
		return value;
	}
	
	/**
	 * 
	 * @param image
	 * @return The properties obtained from the image.
	 */
	public static Hashtable<String,Object> getBufferedImageProperties(BufferedImage image){
        String[] propertyNames = image.getPropertyNames();
        if(propertyNames==null)
        		return new Hashtable<>(0);
        Hashtable<String, Object> properties = new Hashtable<>(propertyNames.length);
        for(String propertyName:propertyNames)
        		properties.put(propertyName, image.getProperty(propertyName));
        return properties;
	}
}

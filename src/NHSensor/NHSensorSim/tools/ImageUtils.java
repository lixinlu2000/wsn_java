package NHSensor.NHSensorSim.tools;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ImageUtils {

	/**
	 * @param args
	 */
	public static BufferedImage getResizePicture(BufferedImage originalPic,
			double bo) {
		// ���ԭʼͼƬ�Ŀ�ȡ�
		int originalImageWidth = originalPic.getWidth();
		// ���ԭʼͼƬ�ĸ߶ȡ�
		int originalImageHeight = originalPic.getHeight();
		
		// �������ű�����ô�����ͼƬ��ȡ�
		int changedImageWidth = (int) (originalImageWidth * bo);
		// �������ű�����ô�����ͼƬ�߶ȡ�
		int changedImageHeight = (int) (originalImageHeight * bo);

		// ���ɴ�����ͼƬ�洢�ռ䡣
		BufferedImage changedImage = new BufferedImage(changedImageWidth,
				changedImageHeight, BufferedImage.TYPE_3BYTE_BGR);

		// double widthBo = (double) yourWidth / originalImageWidth;
		// double heightBo = (double) yourHeightheight / originalImageHeight;
		// ������ű�����
		double widthBo = bo;
		// �߶����ű�����
		double heightBo = bo;

		AffineTransform transform = new AffineTransform();
		transform.setToScale(widthBo, heightBo);

		// ����ԭʼͼƬ���ɴ�����ͼƬ��
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		ato.filter(originalPic, changedImage);
		// ���ش�����ͼƬ
		return changedImage;
	}

	public static BufferedImage createImageByMaskColorEx(BufferedImage imageSrc, Color mask) {
        int x, y;
        x = imageSrc.getWidth(null);
        y = imageSrc.getHeight(null);
        Raster rasterSrc = imageSrc.getRaster();
        BufferedImage imageDes = new BufferedImage(x, y, BufferedImage.TYPE_4BYTE_ABGR);
        WritableRaster rasterDes = imageDes.getRaster();
        
        int[] src = null;
        int[] des = new int[4];

        ColorModel cm = imageSrc.getColorModel();
        Color cmask = (Color)mask;
        Object data = null;
        int maskR, maskG, maskB;
        maskR = cmask.getRed();
        maskG = cmask.getGreen();
        maskB = cmask.getBlue();
            while (--x >= 0)
                for (int j = 0; j < y; ++j) {
                data = rasterSrc.getDataElements(x, j, null);
                int rgb = cm.getRGB(data);
                int sr, sg, sb;
                sr = (rgb & 0xFF0000)>>16;
                sg = (rgb & 0xFF00)>>8;
                sb = rgb & 0xFF;
                if (sr == maskR && sg == maskG && sb == maskB)
                    des[3] = 0;
                else {
                    des[0] = sr;
                    des[1] = sg;
                    des[2] = sb;
                    des[3] = 255;
                }
                rasterDes.setPixel(x, j, des);
            }        
        return imageDes;    
    }

}

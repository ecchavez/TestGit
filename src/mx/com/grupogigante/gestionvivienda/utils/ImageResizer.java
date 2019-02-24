package mx.com.grupogigante.gestionvivienda.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;
 
public class ImageResizer {
    //Ancho máximo
    public static int MAX_WIDTH=756;
    //Alto máximo
    public static int MAX_HEIGHT=1890;
 
    /*Este método es el de la magia recibe la ruta al archivo original y la ruta donde vamos a guardar la copia
    copyImage("C:\\Users\\IngenioDS\\Desktop\\test.png","C:\\Users\\IngenioDS\\Desktop\\Copia\\test2.png");*/
 
    public void copyImage(File file, String copyPath) {
        BufferedImage bimage = null;
		try {
			bimage = ImageIO.read(file);
			if(bimage.getHeight()>bimage.getWidth()){
	            int heigt = (bimage.getHeight() * MAX_WIDTH) / bimage.getWidth();
	            bimage = resize(bimage, MAX_WIDTH, heigt);
	            int width = (bimage.getWidth() * MAX_HEIGHT) / bimage.getHeight();
	            bimage = resize(bimage, width, MAX_HEIGHT);
			}else{
	            int width = (bimage.getWidth() * MAX_HEIGHT) / bimage.getHeight();
	            bimage = resize(bimage, width, MAX_HEIGHT);
	            int heigt = (bimage.getHeight() * MAX_WIDTH) / bimage.getWidth();
	            bimage = resize(bimage, MAX_WIDTH, heigt);
			}
        saveImage(bimage, copyPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public void copyImageJPG(File file, String copyPath) {
        BufferedImage bimage = null;
		try {
			bimage = ImageIO.read(file);
			if(bimage.getHeight()>bimage.getWidth()){
	            int heigt = (bimage.getHeight() * 640) / bimage.getWidth();
	            bimage = resize(bimage, 640, heigt);
	            int width = (bimage.getWidth() * 480) / bimage.getHeight();
	            bimage = resize(bimage, width, 480);
			}else{
	            int width = (bimage.getWidth() * 480) / bimage.getHeight();
	            bimage = resize(bimage, width, 480);
	            int heigt = (bimage.getHeight() * 640) / bimage.getWidth();
	            bimage = resize(bimage, 640, heigt);
			}
        saveImage(bimage, copyPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
     
    /*
    Este método se utiliza para cargar la imagen de disco
    */
    public BufferedImage loadImage(String pathName) {
        BufferedImage bimage = null;
        try {
            bimage = ImageIO.read(new File(pathName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimage;
    }
 
    /*
    Este método se utiliza para almacenar la imagen en disco
    */
    public void saveImage(BufferedImage bufferedImage, String pathName) {
        try {
            String format = (pathName.endsWith(".png")) ? "png" : "jpg";
            File file =new File(pathName);
            file.getParentFile().mkdirs();
            ImageIO.write(bufferedImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    /*
    Este método se utiliza para redimensionar la imagen
    */
    public BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }
    
    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
    {
            File convFile = new File( multipart.getOriginalFilename());
            multipart.transferTo(convFile);
            return convFile;
    }
}
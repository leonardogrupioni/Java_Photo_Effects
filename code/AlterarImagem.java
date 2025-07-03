import java.awt.*;
import java.awt.color.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javafx.scene.effect.SepiaTone;

/**
 * Permiti modificar a imagem a partir de cada filtro
 * 
 * @author Joao Pedro Figols Neco, Julia Gachido Schmidt, Leonardo Fajardo Grupioni
 * @version 20231119
 */
public class AlterarImagem extends JFrame
{
    //matrizes que armazenam cada pixel da imagem alterada
    int[][][] img;
    int[][] imgPB;
    int[][] imgTC;
    int[] Hist;

    /**
     * Gera imagem de tons de cinza
     *
     * @param img - matriz de pixel da imagem original
     * @param l - largura da imagem
     * @param h - altura da imagem
     * @return imgTC - matriz de pixel com cada cor alterada
     */
    public int[][] transTonsC(int[][][] img, int l, int h){
        int[][] TC = new int[l][h];

        for(int i = 0; i < l; i++){
            for(int j = 0; j < h; j++){
                TC[i][j] = (img[i][j][0] + img[i][j][1] + img[i][j][2]) / 3;
            }
        }

        return TC;
    }

    /**
     * Gera imagem preto e branco
     *
     * @param img - matriz de pixel da imagem original
     * @param l - largura da imagem
     * @param h - altura da imagem
     * @return PB - matriz de pixels com cada cor alterada
     */
    public int[][] transPB(int[][][] img, int l, int h){
        int[][] PB = new int[l][h];
        int cor = 0;

        for(int i = 0; i < l; i++){
            for(int j = 0; j < h; j++){
                cor = (img[i][j][0] + img[i][j][1] + img[i][j][2]) / 3;
                if(cor > 128) PB[i][j] = 255;
                else PB[i][j] = 0;
            }
        }

        return PB;
    }
    
    /**
     * Gera imagem sepia
     *
     * @param img - matriz de pixel da imagem original
     * @param l - largura da imagem
     * @param h - altura da imagem
     * @return SP - matriz de pixels com cada cor alterada
     */
    public int[][][] transSP(int[][][] img, int l, int h){
        int[][][] SP = new int[l][h][3];

        for(int i = 0; i < l; i++){
            for(int j = 0; j < h; j++){
                SP[i][j][0] = (int)(0.393 * img[i][j][0] + 0.769 * img[i][j][1] + 0.189 * img[i][j][2]);
                SP[i][j][1] = (int)(0.349 * img[i][j][0] + 0.686 * img[i][j][1] + 0.168 * img[i][j][2]);
                SP[i][j][2] = (int)(0.272 * img[i][j][0] + 0.534 * img[i][j][1] + 0.131 * img[i][j][2]);
                
                if(SP[i][j][0] > 255) SP[i][j][0] = 255;
                if(SP[i][j][1] > 255) SP[i][j][1] = 255;
                if(SP[i][j][2] > 255) SP[i][j][2] = 255;
            }
        }

        return SP;
    }
    
    /**
     * Gera imagem com cores invertidas
     *
     * @param img - matriz de pixel da imagem original
     * @param l - largura da imagem
     * @param h - altura da imagem
     * @return IV - matriz de pixels com cada cor alterada
     */
    public int[][][] transInv(int[][][] img, int l, int h){
        int[][][] IV = new int[l][h][3];

        for(int i = 0; i < l; i++){
            for(int j = 0; j < h; j++){
                for(int k = 0; k < 3; k++){
                    if(img[i][j][k] > 128) IV[i][j][k] = 128 - (img[i][j][k] - 128);
                    else IV[i][j][k] = 128 + (128 - img[i][j][k]);
                }
            }
        }

        return IV;
    }

    /**
     * Gera as imagem a partir dos pixels
     *
     * @param img - matriz com pixels de imagens monocromaticas (preto, branco e cinza)
     * @param l - largura da imagem
     * @param h - altura da imagem
     * @return image - imagem gerada
     */
    public BufferedImage gerarImagemMonocromatica(int[][] img, int l, int h){
        BufferedImage image = new BufferedImage(l,h,BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        int[] cor = new int[3];

        for(int i = 0; i < l; i++){
            for(int j = 0; j < h; j++){
                cor[0] = img[i][j];
                cor[1] = img[i][j];
                cor[2] = img[i][j];
                raster.setPixel(i,j,cor);
            }
        }

        return image;
    }    
    
    /**
     * Gera as imagem a partir dos pixels
     *
     * @param img - matriz com pixels de imagens RGB (coloridas)
     * @param l - largura da imagem
     * @param h - altura da imagem
     * @return image - imagem gerada
     */
    public BufferedImage gerarImagemRGB(int[][][] img, int l, int h){
        BufferedImage image = new BufferedImage(l,h,BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        int[] cor = new int[3];

        for(int i = 0; i < l; i++){
            for(int j = 0; j < h; j++){
                cor[0] = img[i][j][0];
                cor[1] = img[i][j][1];
                cor[2] = img[i][j][2];
                raster.setPixel(i,j,cor);
            }
        }
        
        return image;
    }    
}

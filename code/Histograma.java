import java.awt.*;
import java.awt.color.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

/**
 * Constroi o histograma
 * 
 * @author Joao Pedro Figols Neco, Julia Gachido Schmidt, Leonardo Fajardo Grupioni
 * @version 20231119
 */
public class Histograma extends JFrame
{
    public int qtdPixel = 0;
    private static int lm, hm;  //largura e altura da imagem

    /**
     * Método gerarHistogramaTonsC
     *
     * @param img - matriz de pixels da imagem de tonalidades acinzentadas
     * @param l - largura da imagem
     * @param h - altura da imagem
     * @return H - matriz com a quantidade de cada intensidade de cinza
     */
    protected int[] gerarHistogramaTonsC(int[][] img, int l, int h){
        int H[] = new int[256];
        qtdPixel = l * h;
        lm = l;
        hm = h;

        for(int i = 0; i < l; i++){
            for(int j = 0; j < h; j++){
                H[(img[i][j])]++; 
            }
        }

        return H;
    }

    /**
     * Gera o gráfico de histograma
     *
     * @param H - matriz com a quantidade de cada tonalidade de cinza
     * @return M - matriz de pixels da imagem que representara o histograma
     */
    public int[][][] gerarGraficoH(int[] H){
        //tamanho da tela do histograma
        int l = 868;
        int h = 600;
        
        int[][][] M = new int[l][h][3];

        //variaveis de indice
        int k = 0;
        int s = 0;

        //loop para criar o fundo do histograma
        for(int i = 0; i < l; i++){
            for(int j = 0; j < h; j++){
                M[i][j][0] = 233;
                M[i][j][1] = 224;
                M[i][j][2] = 210;
            }
        }
        
        //loop para criar a barra que representa as tonalidades de cinza possiveis, ate o branco
        for(int i = 50; i < 818; i++){
            M[i][525][0] = 0;
            M[i][525][1] = 0;
            M[i][525][2] = 0;
            
            M[i][575][0] = 0;
            M[i][575][1] = 0;
            M[i][575][2] = 0;
            
            for(int j = 526; j < 575; j++){
                M[i][j][0] = k;
                M[i][j][1] = k;
                M[i][j][2] = k;
            }
            if(s == 0) s = 1;
            else if( s == 1) s = 2;
            else{
                k++;
                s = 0;
            }
        }
        
        double p = 0;   //porcentagem de cada tonalidade
        int j = 0;
        int pMax = 0;
        double max =  (lm * hm) * 1.0; //tamanho maximo da imagem
        
        //loop para desenhar o grafico
        for(int i = 52; i < 818; i = i + 3){
            p = H[j] / max;
            p = p * 10000;
            pMax = 523 - (int)p;
                
            for(int n = 523; n > pMax; n--){
                M[i][n][0] = 0;
                M[i][n][1] = 0;
                M[i][n][2] = 0;
            }
            j++;
        }

        return M;
    }

    JButton btn;

    /**
     * Constroi a tela que mostrara o histograma
     *
     * @param imagem - histograma criado a partir dos pixels
     */
    public void mostrarH(BufferedImage imagem){
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(imagem);
        JLabel imageLabel = new JLabel(icon);

        btn = new JButton("Voltar");
        btn.addActionListener(e -> {
                    setVisible(false);
                    dispose();
            }
        );

        JPanel painel = new JPanel();
        painel.setLayout(new FlowLayout());    
        painel.add(btn);

        c.add(new JScrollPane(imageLabel));
        
        c.add(painel, BorderLayout.SOUTH);
        
        setSize(900, 700);
        
        setLocationRelativeTo(null);

        setVisible(true);
    }   
}

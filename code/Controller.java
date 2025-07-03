import java.awt.*;
import java.awt.color.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Menu para realizar alterações na imagem
 * 
 * @author Joao Pedro Figols Neco, Julia Gachido Schmidt, Leonardo Fajardo Grupioni
 * @version 20231119
 */
public class Controller extends JFrame
{
    //arrays que representa cada pixel de cada imagem
    int[][][] img;
    int[][][] imgSP;
    int[][][] imgIV;
    int[][] imgPB;
    int[][] imgTC;
    int[][][] imgH;
    int[] H;
    
    int l, h;  //altura e largura da imagem

    AlterarImagem altImag = new AlterarImagem();
    Histograma histograma = new Histograma();

    BufferedImage imagemOriginal, imagemIV, imagemSP, imagemPB, imagemTC, imagemH;
    String nomeArq;

    JButton buttonPB, buttonTC, buttonFS, buttonFI, buttonOG, buttonS, buttonHM, buttonST;

    /**
     * Constroi o menu para aparecer a imagem, gera todas as imagens com filtros e salva os pixels
     *
     * @param imagem - escolhida pelo usuario
     * @param nomeArquivo - nome da imagem 
     */
    public Controller(BufferedImage imagem, String nomeArquivo){
        l = imagem.getWidth(null);
        h = imagem.getHeight(null);

        imagemOriginal = imagem;
        nomeArq = nomeArquivo;

        img = SalvarPixels(imagem, l ,h);
        gerarImagens(imagem, img, l, h);
        mostrarImagem(imagem);
    }

    /**
     * Salva cada pixel da imagem originalmente escolhida
     *
     * @param imagem - escolhida pelo usuario
     * @param l - largura da imagem
     * @param h - altura da imagem
     * @return img - uma matriz de 3 dimensoes, apresentando todos os pixels para cada RGB
     */
    protected int[][][] SalvarPixels(BufferedImage imagem, int l, int h){
        img = new int[l][h][3];

        Raster raster = imagem.getRaster();
        int[] pixel = new int[3];

        for(int i = 0; i < h; i++){
            for(int j = 0; j < l; j++){
                raster.getPixel(j,i,pixel);
                img[j][i][0] = pixel[0];
                img[j][i][1] = pixel[1];
                img[j][i][2] = pixel[2];
            }
        }

        return img;
    }

    /**
     * Mostra o menu juntamente com a imagem 
     *
     * @param imagem - escolhida pelo usuario
     */
    protected void mostrarImagem(BufferedImage imagem){
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(imagem);
        JLabel imageLabel = new JLabel(icon);

        buttonPB = new JButton("PretoBranco");
        buttonTC = new JButton("TonsCinza");
        buttonFS = new JButton("FiltroSepia");
        buttonFI = new JButton("FiltroInvert");
        buttonOG = new JButton("Original");
        buttonHM = new JButton("Histrograma");
        buttonST = new JButton("SalvarTudo");
        buttonS = new JButton("Sair");

        buttonPB.addActionListener(e -> {
                    mostrarImagem(imagemPB);
            }
        );

        buttonTC.addActionListener(e -> {
                    mostrarImagem(imagemTC);
            }
        );

        buttonFI.addActionListener(e -> {
                    mostrarImagem(imagemIV);
            }
        );

        buttonFS.addActionListener(e -> {
                    mostrarImagem(imagemSP);
            }
        );

        buttonOG.addActionListener(e -> {
                    mostrarImagem(imagemOriginal);
            }
        );

        buttonHM.addActionListener(e -> {
                    histograma.mostrarH(imagemH);
            }
        );

        buttonS.addActionListener(e -> {
                    System.exit(0);
            }
        );

        buttonST.addActionListener(e -> {
                    salvarTudo();
            }
        );

        JPanel painel = new JPanel();
        JPanel painel2 = new JPanel();

        painel.setLayout(new FlowLayout());
        painel.add(buttonPB);
        painel.add(buttonTC);
        painel.add(buttonFS);
        painel.add(buttonFI);

        painel2.setLayout(new FlowLayout());
        painel2.add(buttonOG);
        painel2.add(buttonHM);
        painel2.add(buttonST);
        painel2.add(buttonS);

        c.add(new JScrollPane(imageLabel));
    
        //largura e altura da imagem
        int lI = imagem.getWidth(null);
        int hI = imagem.getHeight(null);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();       

        //largura e altura da tela do computador
        int lM = d.width;
        int hM = d.height;

        if(lM < lI && hM < hI) setSize(lM, hM);
        else if(lM < lI && hM > hI)  setSize(lM,hI + 150);
        else if(lM > lI && hM < hI) setSize(lI + 400,hM);
        else setSize(lI + 400,hI + 150);       

        setLocationRelativeTo(null);

        c.add(painel, BorderLayout.NORTH);
        c.add(painel2, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Gera as imagens com filtros (Preto e branco, tons de cinza, sepia e cores invertidas), e ainda, gera o histograma de intensidade de cinza
     *
     * @param imagem - escolhida pelo usuario
     * @param img - matriz com pixels
     * @param l - largura da imagem
     * @param h - altura da imagem
     */
    public void gerarImagens(BufferedImage imagem, int img[][][], int l, int h){
        imgPB = altImag.transPB(img, l, h);    
        imagemPB = altImag.gerarImagemMonocromatica(imgPB, l, h);

        imgTC = altImag.transTonsC(img, l, h);
        imagemTC = altImag.gerarImagemMonocromatica(imgTC, l, h);

        imgIV = altImag.transInv(img, l, h);
        imagemIV = altImag.gerarImagemRGB(imgIV, l, h);

        imgSP = altImag.transSP(img, l, h);
        imagemSP = altImag.gerarImagemRGB(imgSP, l, h);

        H = histograma.gerarHistogramaTonsC(imgTC, l, h);
        imgH = histograma.gerarGraficoH(H);
        imagemH = altImag.gerarImagemRGB(imgH, 868, 600);
    }

    /**
     * Salva 5 arquivos, cada um corresponte com um filtro
     *
     */
    public void salvarTudo(){
        //permite escolher o diretorio em que deseja salvar
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try{
                File file = new File(chooser.getSelectedFile() + "\\" + nomeArq + "OG.jpg");
                ImageIO.write(imagemOriginal, "jpg", file);

                file = new File(chooser.getSelectedFile() + "\\" + nomeArq + "IV.jpg");
                ImageIO.write(imagemIV, "jpg", file);

                file = new File(chooser.getSelectedFile() + "\\" + nomeArq + "PB.jpg");
                ImageIO.write(imagemPB, "jpg", file);

                file = new File(chooser.getSelectedFile() + "\\" + nomeArq + "TC.jpg");
                ImageIO.write(imagemTC, "jpg", file);

                file = new File(chooser.getSelectedFile() + "\\" + nomeArq + "SP.jpg");
                ImageIO.write(imagemSP, "jpg", file);

                JOptionPane.showMessageDialog(null, "Salvo com sucesso!!");
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, "Nao foi possivel salvar um dos arquivos!!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nao selecionado!!");
        }
    } 
}

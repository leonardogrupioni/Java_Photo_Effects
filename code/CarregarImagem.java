import java.awt.*;
import java.awt.color.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Apresenta o menu de escolha da imagem
 * 
 * @author Joao Pedro Figols Neco, Julia Gachido Schmidt, Leonardo Fajardo Grupioni
 * @version 20231119
 */
public class CarregarImagem extends JFrame
{
    //menu com botão (escolhas)
    private BufferedImage imagem;   //representa a imagem escolhida
    
    //botoes do menu
    JButton btn1;
    JButton btn2;

    public CarregarImagem(){
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        btn1 = new JButton("Carregar Imagem");

        btn1.addActionListener(e -> {
                    //Permite escolher, a partir dos diretorios, qual imagem deseja utilizar
                    
                    JFileChooser fc = new JFileChooser();
                    fc.setFileFilter(new FileNameExtensionFilter("Image files", "jpg")); 
                    fc.setAcceptAllFileFilterUsed(false);
                    int res = fc.showOpenDialog(null);
                    
                    if(res == JFileChooser.APPROVE_OPTION){
                        File arquivo = fc.getSelectedFile();  

                        imagem = null;

                        try{
                            imagem = ImageIO.read(arquivo);
                        } catch(IOException exc){
                            JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem: " + exc.getMessage());
                        }

                        if(imagem != null){
                            String nomeArquivo = arquivo.getName();
                            nomeArquivo = nomeArquivo.replace(".jpg", ""); //retira o .jpg do nome do arquivo
                            
                            //desabilita a tela 
                            setVisible(false);
                            dispose();

                            Controller ctrller = new Controller(imagem, nomeArquivo);
                        }
                    }
            }
        );

        btn2 = new JButton("Sair");
        btn2.addActionListener(e -> {
                    System.exit(0);
            }
        );

        JPanel painel = new JPanel();
        painel.setLayout(new FlowLayout());    
        painel.add(btn1);
        painel.add(btn2);

        setLocation(((Toolkit.getDefaultToolkit().getScreenSize().width  / 2 - 125) - (this.getWidth() / 2)), 
            ((Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 36) - (this.getHeight() / 2)));

        c.add(painel, BorderLayout.CENTER);

        setSize(250, 73);
        setVisible(true);
    }
}
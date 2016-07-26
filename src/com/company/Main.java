package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class Main {
    static BufferedImage image;

    public static void main(String[] args) {

        Main main = new Main();

        main.go();

    }


    int red = 0, green = 0, blue = 0;
    public void go(){
        File file = null;
        BufferedImage bImage = null;

        final JFileChooser choose = new JFileChooser();//Окошко с выбором файла

        //Показываем диалоговое окно
        int returnVal = choose.showDialog(null, "Open file");
        if(returnVal == choose.APPROVE_OPTION){//При выборе файла...
            file = choose.getSelectedFile();//Получаем файл
            try{
                image = ImageIO.read(file);//Переделываем его в картинку
            }catch(IOException e){
            }

            //Создаем новую картинку с размерами прежней
             bImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

            //Пробегаемся и переделываем все пиксели прежней картинки
            for(int i = 0; i< image.getWidth(); i++){
                for(int j = 0; j < image.getHeight(); j++){
                    final int clr = image.getRGB(i, j);//Вот такое вот страшное получение цветов
                    red = (clr & 0x00ff0000) >> 16;
                    green = ( clr & 0x0000ff00) >> 8;
                    blue = clr & 0x000000ff;

                    int average = (red + green + blue)/3;//Для получения чб цвета нужно найти среднее значение между цветами

                    Color color = new Color(average, average, average);//Выставляем значения и получаем RGB
                    int rgb = color.getRGB();

                    bImage.setRGB(i, j, rgb);//Ставим новый пиксель на место
                }
            }
        }

        //Создаем и открываем окошко с получившейся картинкой
        ImageIcon icon = new ImageIcon(bImage);
        JLabel label = new JLabel(icon);
        JOptionPane.showMessageDialog(null, label);
        
    }
}

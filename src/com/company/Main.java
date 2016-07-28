package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Main main = new Main();

        main.go();

    }


    int red = 0, green = 0, blue = 0;
    public void go(){

        final JFileChooser choose = new JFileChooser();//Окошко с выбором файла
        choose.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                final String name = f.getName();
                return name.endsWith(".png") || name.endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return "*.png, *.jpg";
            }
        });
        choose.setMultiSelectionEnabled(true);

        //Показываем диалоговое окно
        int returnVal = choose.showDialog(null, "Open file");
        if(returnVal == choose.APPROVE_OPTION){//При выборе файла...

            File[] file = choose.getSelectedFiles();//Получаем файл

            BufferedImage[] image = new BufferedImage[file.length];
            BufferedImage[] bImage = new BufferedImage[file.length];

            try{
                for(int i = 0; i < file.length; i++)
                    image[i] = ImageIO.read(file[i]);//Переделываем его в картинку
            }catch(IOException e){
            }

            for(int l = 0; l < file.length; l++) {
                //Создаем новую картинку с размерами прежней
                try {
                    bImage[l] = new BufferedImage(image[l].getWidth(), image[l].getHeight(), BufferedImage.TYPE_INT_RGB);
                }catch (NullPointerException e){
                    System.out.println(e);
                }

                //Пробегаемся и переделываем все пиксели прежней картинки
                for (int i = 0; i < image[l].getWidth(); i++) {
                    for (int j = 0; j < image[l].getHeight(); j++) {
                        int clr = image[l].getRGB(i, j);//Вот такое вот страшное получение цветов
                        red = (clr & 0x00ff0000) >> 16;
                        green = (clr & 0x0000ff00) >> 8;
                        blue = clr & 0x000000ff;

                        int average = (red + green + blue) / 3;//Для получения чб цвета нужно найти среднее значение между цветами

                        Color color = new Color(average, average, average);//Выставляем значения и получаем RGB
                        int rgb = color.getRGB();

                        bImage[l].setRGB(i, j, rgb);//Ставим новый пиксель на место
                    }
                }

                //Создаем и открываем окошко с получившейся картинкой
                //ImageIcon icon = new ImageIcon(bImage[l]);
                //JLabel label = new JLabel(icon);
                //JOptionPane.showMessageDialog(null, label);

                try {
                    File outputfile = new File("grey-" + file[l].getName());

                    if(file[l].getName().endsWith(".png"))
                        ImageIO.write(bImage[l], "png", outputfile);
                    else if(file[l].getName().endsWith(".jpg"))
                        ImageIO.write(bImage[l], "jpg", outputfile);
                }catch (IOException e){

                }
            }
        }



    }
}

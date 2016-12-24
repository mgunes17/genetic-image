import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

       /* Main main = new Main();
        Scanner in = new Scanner(System.in);
        System.out.println("Resim yolu:");
        String path = in.next();

        Mat img = Imgcodecs.imread(path);
        Mat imageGray = new Mat();
        Mat imageCanny = new Mat();

        Imgproc.cvtColor(img, imageGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(imageGray, imageCanny, 150, 200, 3, false);
        Imgcodecs.imwrite("img.jpg", imageCanny);

        List<Coordinate> imgEdges = new ArrayList<>();

        for(int i = 0; i < imageCanny.rows(); i++){
            for(int j = 0; j < imageCanny.cols(); j++){
                if(imageCanny.get(i, j)[0] == 255.0){
                    imgEdges.add(new Coordinate(i, j));
                }
            }
        }

        Image image = main.toBufferedImage(imageCanny);
        ImageIcon imageIcon = new ImageIcon();
        imageIcon.setImage(image);

        Chromosome alpha = new Chromosome();
        alpha.setImg(imageCanny);
        alpha.setEdgeCoordinates(imgEdges);

        LifeForCircle lifeForCircle = new LifeForCircle(75, alpha, imageCanny.width(), imageCanny.height(), imageCanny.type());

        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        lifeForCircle.initialize();

        for (int i = 0; i < 100000; i++) {
            if(i % 100 == 1) {
                lifeForCircle.nextAge();
                image = main.toBufferedImage(lifeForCircle.findBestChromosome());
                imageIcon.setImage(image);
                frame.setSize(imageIcon.getIconWidth() + 10, imageIcon.getIconHeight() + 35);
                JLabel label1 = new JLabel(" ", imageIcon, JLabel.CENTER);
                frame.getContentPane().add(label1);
                frame.validate();
                frame.setVisible(true);
                //System.out.println("iteration: " + i);
            }
        }*/


        Scanner in = new Scanner(System.in);
        System.out.println("Resim yolu:");
        String path = in.next();

        Mat img = Imgcodecs.imread(path);
        Main main = new Main();

        KMeans kMeans = new KMeans(img, 5);
        kMeans.startKMeans();
        img = kMeans.getNewImg();

        Imgcodecs.imwrite("img.jpg", img);

        Image image = main.toBufferedImage(img);
        ImageIcon imageIcon = new ImageIcon();
        imageIcon.setImage(image);

        List<Pixel> pixelsInImage = main.findColorsInImage(img);
        System.out.println(pixelsInImage.size());

        Life life = new Life(500, img, img.width(), img.height(), img.type(), pixelsInImage);
        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        life.initialize();

        for (int i = 0; i < 100000; i++) {
            life.nextAge();
            image = main.toBufferedImage(life.findBestChromosome());
            imageIcon.setImage(image);
            frame.setSize(imageIcon.getIconWidth() + 10, imageIcon.getIconHeight() + 35);
            JLabel label1 = new JLabel(" ", imageIcon, JLabel.CENTER);
            frame.getContentPane().add(label1);
            frame.validate();
            frame.setVisible(true);
            System.out.println("iteration: " + i);
        }
    }

    public List<Pixel> findColorsInImage(Mat img) {
        Set<String> pixels = new HashSet<>();
        List<Pixel> pixelList = new ArrayList<>();
        String pixelString;
        double[] values;

        for (int i = 0; i < img.height(); i++) {
            for (int j = 0; j < img.width(); j++) {
                values = img.get(i, j);
                pixelString = values[0] + "-" + values[1] + "-" + values[2];
                pixels.add(pixelString);
            }
        }

        Iterator<String> iterator = pixels.iterator();
        while (iterator.hasNext()) {
            String[] tokens = iterator.next().split("-");
            Pixel pixel = new Pixel();
            pixel.setB(Double.parseDouble(tokens[0]));
            pixel.setG(Double.parseDouble(tokens[1]));
            pixel.setR(Double.parseDouble(tokens[2]));
            pixelList.add(pixel);
        }

        return pixelList;
    }



    public static Image toBufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;

    }


}
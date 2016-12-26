package main;

import org.opencv.core.Core;
import view.AppWindow;



public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        AppWindow appWindow = new AppWindow();

        /*AppWindow appWindow = new AppWindow();

        Scanner in = new Scanner(System.in);
        System.out.println("Resim yolu:");
        String path = in.next();
        Mat img = Imgcodecs.imread(path);

        while(img.height() == 0) {
            System.out.println("Resim bulunamadı.");
            path = in.next();
            img = Imgcodecs.imread(path);
        }

        Main main = new Main();

        System.out.println("Edge için 1, px için 2:");
        int select = in.nextInt();

        if(select == 1) {
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

            Chromosome alpha = new Chromosome();
            alpha.setImg(imageCanny);
            alpha.setEdgeCoordinates(imgEdges);

            LifeForEdge lifeForCircle = new LifeForEdge(75, alpha, imageCanny.width(), imageCanny.height(), imageCanny.type());
            main.run(lifeForCircle, imageCanny);
        } else if(select == 2){
            KMeans kMeans = new KMeans(img, 5);
            kMeans.startKMeans();
            img = kMeans.getNewImg();

            Imgcodecs.imwrite("img.jpg", img);

            List<Pixel> pixelsInImage = main.findColorsInImage(img);
            System.out.println(pixelsInImage.size());

            Chromosome alpha = new Chromosome();
            alpha.setImg(img);
            LifeForPixel life = new LifeForPixel(75, alpha, img.width(), img.height(), img.type(), pixelsInImage);
            main.run(life, img);
        } else {
            KMeans kMeans = new KMeans(img, 4);
            kMeans.startKMeans();
            img = kMeans.getNewImg();

            Imgcodecs.imwrite("img.jpg", img);

            List<Pixel> pixelsInImage = main.findColorsInImage(img);
            System.out.println(pixelsInImage.size());

            Chromosome alpha = new Chromosome();
            alpha.setImg(img);
            LifeForAlpha life = new LifeForAlpha(75, alpha, img.width(), img.height(), img.type(), pixelsInImage);
            main.run(life, img);
        }*/
    }



}
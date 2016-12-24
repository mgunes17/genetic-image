package kmeans;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 07.12.2016.
 */
public class KMeans {
    private Mat img;
    private int clusterNumber;
    private Mat newImg;
    private int rowsCount;
    private int colsCount;
    private List<KCenter> kcenter;
    private List<Pixel> newKcenter;
    private int epsilon = 10;

    public KMeans(Mat img, int clusterNumber) {
        this.img = img.clone();
        this.clusterNumber = clusterNumber;
        kcenter = new ArrayList<KCenter>();
        newKcenter = new ArrayList<>();
        newImg = img.clone();
        rowsCount = img.height();
        colsCount = img.width();
    }

    public void startKMeans() {
        initializeCenter();

        int sum = 0;
        boolean next = true;

        do {
            computePixelCluster();
            sum = computeNewCenters();

            //yeni bir iterasyon yapılacaksa merkez px değerlerini güncelle
            if (epsilon < sum) {
                for (int i = 0; i < clusterNumber; i++) {
                    kcenter.get(i).setCenter(newKcenter.get(i));
                    kcenter.get(i).setSumRed(0);
                    kcenter.get(i).setSumGreen(0);
                    kcenter.get(i).setSumBlue(0);
                    kcenter.get(i).setSize(0);
                }
            } else {
                next = false;
            }
        } while(next);
    }

    private int computeNewCenters() {
        List<Integer> majority = new ArrayList<>();
        newKcenter.clear();
        int sum = 0;

        for(int i = 0; i < clusterNumber; i++) {
            Pixel px = new Pixel();
            px.setB(kcenter.get(i).getSumBlue() / (kcenter.get(i).getSize() + 1));
            px.setG(kcenter.get(i).getSumGreen() / (kcenter.get(i).getSize() + 1));
            px.setR(kcenter.get(i).getSumRed() / (kcenter.get(i).getSize() + 1));

            newKcenter.add(px);

            majority.add(findEuclideanDistance(newKcenter.get(i), kcenter.get(i).getCenter()));
            sum += majority.get(i);
        }

        return sum;
    }

    private void computePixelCluster() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                double[] values = img.get(i, j);

                Pixel px = new Pixel();
                px.setB(values[0]);
                px.setG( values[1]);
                px.setR(values[2]);
                px.setRow(i);
                px.setColumn(j);

                int minDistance = 99999;
                int centerId = -1;

                //en yakın merkezi bul
                for(int p = 0; p < clusterNumber; p++) {
                    Pixel center = kcenter.get(p).getCenter();
                    int distance = findEuclideanDistance(px, center);
                    if(distance < minDistance) {
                        centerId = p;
                        minDistance = distance;
                    }
                }

                //o kümeye ait bilgileri güncelle
                px.setClusterID(centerId);
                kcenter.get(centerId).setSize(kcenter.get(centerId).getSize() + 1);
                kcenter.get(centerId).setSumBlue(kcenter.get(centerId).getSumBlue() + (int)px.getB());
                kcenter.get(centerId).setSumRed(kcenter.get(centerId).getSumRed() + (int)px.getR());
                kcenter.get(centerId).setSumGreen(kcenter.get(centerId).getSumGreen() + (int)px.getG());

                //px deki renk değerlerini, ait olduğu merkezinkilere set et
                double[] newValues = new double[3];
                newValues[0] = kcenter.get(centerId).getCenter().getB();
                newValues[1] = kcenter.get(centerId).getCenter().getG();
                newValues[2] = kcenter.get(centerId).getCenter().getR();

                newImg.put(i,j, newValues);

            }
        }
    }

    private void initializeCenter() {
        Random random = new Random();

        for(int i = 0; i < clusterNumber; i++) {
            int row = random.nextInt(rowsCount);
            int column = random.nextInt(colsCount);

            Pixel px = new Pixel();
            px.setRow(row);
            px.setColumn(column);

            double values[] = img.get(row, column);
            px.setB(values[0]);
            px.setG(values[1]);
            px.setR(values[2]);

            KCenter center = new KCenter();
            center.setCenter(px);
            center.setSize(0);
            center.setSumBlue(0);
            center.setSumGreen(0);
            center.setSumRed(0);

            kcenter.add(center);
        }
    }

    private int findEuclideanDistance(Pixel px1, Pixel px2) {
        double sum = 0.0;
        sum = Math.pow((px1.getB() - px2.getB()) ,2) +
                Math.pow((px1.getG() - px2.getG()), 2) +
                Math.pow((px1.getR() - px2.getR()), 2);

        return (int) Math.sqrt(sum);
    }

    public Mat getNewImg() {
        return newImg;
    }
}
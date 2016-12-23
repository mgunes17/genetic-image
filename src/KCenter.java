/**
 * Created by mgunes on 07.12.2016.
 */
public class KCenter {
    private Pixel center;
    private int size; //o kümeye ait px sayısı
    private int sumBlue;
    private int sumGreen;
    private int sumRed;

    public Pixel getCenter() {
        return center;
    }

    public void setCenter(Pixel center) {
        this.center = center;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSumBlue() {
        return sumBlue;
    }

    public void setSumBlue(int sumBlue) {
        this.sumBlue = sumBlue;
    }

    public int getSumGreen() {
        return sumGreen;
    }

    public void setSumGreen(int sumGreen) {
        this.sumGreen = sumGreen;
    }

    public int getSumRed() {
        return sumRed;
    }

    public void setSumRed(int sumRed) {
        this.sumRed = sumRed;
    }
}
package view;

import genetic.Chromosome;
import genetic.Coordinate;
import genetic.crossover.EdgeCrossover;
import genetic.crossover.RandomCrossover;
import genetic.crossover.UniformCrossover;
import genetic.distance.MeasureBetweenGrayPixels;
import genetic.distance.MeasureBetweenPixels;
import genetic.distance.MeasureWithEdgeMatching;
import genetic.distance.MeasureWithPixelMatching;
import genetic.life.Life;
import genetic.life.LifeForEdge;
import genetic.life.LifeForPixel;
import genetic.selection.OrderBasedSelection;
import genetic.selection.RouletteWheelSelection;
import kmeans.KMeans;
import kmeans.Pixel;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.*;
import java.util.List;


public class AppWindow extends JFrame implements ActionListener {
    private int crossoverType = 1;
    private int selectionType = 1;
    private int measureType = 1;

    private Mat img;

    private JButton reset;
    private JButton loadImage;
    private JButton startStopPixel;
    private JButton startStopEdge;

    private JLabel picture;
    private JLabel populationLabelForPixel;
    private JLabel mutationLabelForPixel;
    private JLabel populationLabelForEdge;
    private JLabel mutationLabelForEdge;
    private JLabel mutationCountLabelForPixel;
    private JLabel mutationCountLabelForEdge;
    private JLabel colorCountLabelForPixel;

    private JTextField populationForPixel;
    private JTextField mutationForPixel;
    private JTextField populationForEdge;
    private JTextField mutationForEdge;
    private JTextField mutationCountForPixel;
    private JTextField mutationCountForEdge;
    private JTextField colorCountForPixel;

    private JRadioButton e_EdgeCrossoverButton;
    private JRadioButton e_RandomCrossoverButton;
    private JRadioButton e_UniformCrossoverButton;
    ButtonGroup e_crossoverGroup;

    private JRadioButton p_RandomCrossoverButton;
    private JRadioButton p_UniformCrossoverButton;
    ButtonGroup p_crossoverGroup;

    private JRadioButton e_MeasureWithEdgeMatchingButton;
    private JRadioButton e_MeasureBetweenGrayPixelsButton;
    ButtonGroup e_distanceMeasureGroup;

    private JRadioButton p_MeasureWithPixelMatchingButton;
    private JRadioButton p_MeasureBetweenPixelsButton;
    ButtonGroup p_distanceMeasureGroup;

    private JRadioButton e_RouletteWheelSelectionButton;
    private JRadioButton e_OrderBasedSelectionButton;
    ButtonGroup e_selectionGroup;

    private JRadioButton p_RouletteWheelSelectionButton;
    private JRadioButton p_OrderBasedSelectionButton;
    ButtonGroup p_selectionGroup;

    public AppWindow(){
        super();
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();
        setBounds(100, 100, 900, 600);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String clicked = e.getActionCommand();

        switch (clicked){
            case "load"://load
                break;
            case "pixelStartStop": // life for pixel
                break;
            case "edgeStartStop": // life for edge
                break;
            case "e_edgeCo": crossoverType = 2;
                break;
            case "e_randomCo": crossoverType = 1;
                break;
            case "e_uniformCo": crossoverType = 3;
                break;
            case "p_randomCo": crossoverType = 1;
                break;
            case "p_uniformCo": crossoverType = 3;
                break;
            case "e_manhattan": measureType = 4;
                break;
            case "p_manhattan": measureType = 3;
                break;
            case "e_edge": measureType = 2;
                break;
            case "p_pixel": measureType = 1;
                break;
            case "e_order": selectionType = 2;
                break;
            case "p_order": selectionType = 2;
                break;
            case "e_rulet": selectionType = 1;
                break;
            case "p_rulet": selectionType = 1;
                break;
            default:
                break;
        }


        if(clicked.equals("load")){
            //resim yükle

            System.out.println("load img !");
        } else if(clicked.equals("pixelStartStop")){
            // pixel için life başlat
            String in1 = populationForPixel.getText();
            String in2 = mutationForPixel.getText();
            String in3 = mutationCountForPixel.getText();
            String in4 = colorCountForPixel.getText();

            int populationSize;
            int mutationRate;
            int mutationCount;
            int colorCount;

            if(measureType == 2)
                measureType = 1;

            if(measureType == 4)
                measureType = 3;

            try{
                populationSize = Integer.parseInt(in1);
                mutationRate = Integer.parseInt(in2);
                mutationCount = Integer.parseInt(in3);
                colorCount = Integer.parseInt(in4);

                if(crossoverType == 1) GlobalParameters.crossover = new RandomCrossover();
                if(crossoverType == 2) GlobalParameters.crossover = new EdgeCrossover();
                if(crossoverType == 3) GlobalParameters.crossover = new UniformCrossover(findColorsInImage(img));

                if(selectionType == 1) GlobalParameters.selection = new RouletteWheelSelection();
                if(selectionType == 2) GlobalParameters.selection = new OrderBasedSelection();

                if(measureType == 1) GlobalParameters.distanceMeasure = new MeasureWithPixelMatching();
                if(measureType == 2) GlobalParameters.distanceMeasure = new MeasureWithEdgeMatching();
                if(measureType == 3) GlobalParameters.distanceMeasure = new MeasureBetweenPixels();
                if(measureType == 4) GlobalParameters.distanceMeasure = new MeasureBetweenGrayPixels();

                GlobalParameters.colorCount = colorCount;
                GlobalParameters.populationSize = populationSize;
                GlobalParameters.mutationRate = mutationRate;
                GlobalParameters.mutationCount = mutationCount;

                System.out.println(crossoverType + " " + selectionType + " " + measureType);
                System.out.println("pixel -->>" + populationSize + " " + mutationRate + " " + mutationCount + " " + colorCount);

                Mat img = Imgcodecs.imread("mean5.png");
                KMeans kMeans = new KMeans(img, GlobalParameters.colorCount);
                kMeans.startKMeans();
                img = kMeans.getNewImg();
                List<Pixel> pixelsInImage = findColorsInImage(img);

                Chromosome alpha = new Chromosome();
                alpha.setImg(img);

                GlobalParameters.alpha = alpha;
                GlobalParameters.height = img.height();
                GlobalParameters.width = img.width();
                GlobalParameters.type = img.type();
                GlobalParameters.pixels = pixelsInImage;
                LifeForPixel life = new LifeForPixel(pixelsInImage);
                run(life, img);

            } catch (Exception ex){
                ex.printStackTrace();
                System.out.println("inputta hata var !! startStopPixel " +ex.getMessage());
            }

        } else if(clicked.equals("edgeStartStop")){
            // edge için life başlat

            String in1 = populationForEdge.getText();
            String in2 = mutationForEdge.getText();
            String in3 = mutationCountForEdge.getText();

            int populationSize;
            int mutationRate;
            int mutationCount;

            if(measureType == 1)
                measureType = 2;

            if(measureType == 3)
                measureType = 5;

            try{
                populationSize = Integer.parseInt(in1);
                mutationRate = Integer.parseInt(in2);
                mutationCount = Integer.parseInt(in3);

                GlobalParameters.crossover = new EdgeCrossover();

                if(selectionType == 1) GlobalParameters.selection = new RouletteWheelSelection();
                if(selectionType == 2) GlobalParameters.selection = new OrderBasedSelection();

                if(measureType == 2) GlobalParameters.distanceMeasure = new MeasureWithEdgeMatching();
                if(measureType == 4) GlobalParameters.distanceMeasure = new MeasureBetweenGrayPixels();

                GlobalParameters.populationSize = populationSize;
                GlobalParameters.mutationRate = mutationRate;
                GlobalParameters.mutationCount = mutationCount;

                System.out.println(crossoverType + " " + selectionType + " " + measureType);
                System.out.println("pixel -->>" + populationSize + " " + mutationRate + " " + mutationCount);

                Mat img = Imgcodecs.imread("mean5.png");
                Mat imageGray = new Mat();
                Mat imageCanny = new Mat();

                Imgproc.cvtColor(img, imageGray, Imgproc.COLOR_BGR2GRAY);
                Imgproc.Canny(imageGray, imageCanny, 150, 200, 3, false);

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
                GlobalParameters.alpha = alpha;
                GlobalParameters.height = imageCanny.height();
                GlobalParameters.width = imageCanny.width();
                GlobalParameters.type = imageCanny.type();

                LifeForEdge lifeForEdge = new LifeForEdge();
                run(lifeForEdge, imageCanny);
            } catch (Exception ex){
                ex.printStackTrace();
                System.out.println("inputta hata var !! startStopEdge " +ex.getMessage());
            }

        } else if(clicked.equals("reset")){
            //reset the system

            System.out.println("reset !");
        }
    }

    public void run(Life life, Mat img) {
        Image image = toBufferedImage(img);
        ImageIcon imageIcon = new ImageIcon();
        imageIcon.setImage(image);
        life.initialize();

        while(true) {
            life.nextAge();
            image = toBufferedImage(life.findBestChromosome());
            imageIcon.setImage(image);
            picture.setIcon(imageIcon);
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

    private void init(){
        GridBagConstraints c = new GridBagConstraints();
        reset = new JButton("Reset");
        reset.setActionCommand("reset");
        loadImage = new JButton("Resim Yükle");
        loadImage.setActionCommand("load");
        startStopPixel = new JButton("Başla/Dur");
        startStopPixel.setActionCommand("pixelStartStop");
        startStopEdge = new JButton("Başla/Dur");
        startStopEdge.setActionCommand("edgeStartStop");

        populationLabelForPixel = new JLabel("Popülasyon");
        mutationLabelForPixel = new JLabel("Mutasyon Oranı");
        populationForPixel = new JTextField();
        mutationForPixel = new JTextField();

        populationLabelForEdge = new JLabel("Popülasyon");
        mutationLabelForEdge = new JLabel("Mutasyon Oranı");
        populationForEdge = new JTextField();
        mutationForEdge = new JTextField();

        mutationCountLabelForPixel = new JLabel("Mutason Sayısı");
        mutationCountLabelForEdge = new JLabel("Mutason Sayısı");
        mutationCountForPixel = new JTextField();
        mutationCountForEdge = new JTextField();

        colorCountLabelForPixel = new JLabel("Renk Sayısı");
        colorCountForPixel = new JTextField();

        // edge için crossover butonlarının gruplanması ve panellerin oluşturulması
        e_EdgeCrossoverButton = new JRadioButton("Edge CO");
        e_EdgeCrossoverButton.setActionCommand("e_edgeCo");
        //e_RandomCrossoverButton = new JRadioButton("Random CO");
        //e_RandomCrossoverButton.setActionCommand("e_randomCo");
        //e_UniformCrossoverButton = new JRadioButton("Uniform CO");
        //e_UniformCrossoverButton.setActionCommand("e_uniformCo");
        e_EdgeCrossoverButton.setSelected(true);

        e_crossoverGroup = new ButtonGroup();
        e_crossoverGroup.add(e_EdgeCrossoverButton);
        //e_crossoverGroup.add(e_RandomCrossoverButton);
        //e_crossoverGroup.add(e_UniformCrossoverButton);

        JPanel e_crossoverPanel = new JPanel(new GridLayout(0, 1));
        e_crossoverPanel.add(new JLabel("Crossover Tipi"));
        e_crossoverPanel.add(e_EdgeCrossoverButton);
        //e_crossoverPanel.add(e_RandomCrossoverButton);
        //e_crossoverPanel.add(e_UniformCrossoverButton);

        // pixel için crossover butonlarının gruplanması ve panellerin oluşturulması
        p_RandomCrossoverButton = new JRadioButton("Random CO");
        p_RandomCrossoverButton.setActionCommand("p_randomCo");
        p_UniformCrossoverButton = new JRadioButton("Uniform CO");
        p_UniformCrossoverButton.setActionCommand("p_uniformCo");
        p_RandomCrossoverButton.setSelected(true);

        p_crossoverGroup = new ButtonGroup();
        p_crossoverGroup.add(p_RandomCrossoverButton);
        p_crossoverGroup.add(p_UniformCrossoverButton);

        JPanel p_crossoverPanel = new JPanel(new GridLayout(0, 1));
        p_crossoverPanel.add(new JLabel("Crossover tipi"));
        p_crossoverPanel.add(p_RandomCrossoverButton);
        p_crossoverPanel.add(p_UniformCrossoverButton);

        // edge için uzaklık butonlarının gruplanması ve panellerin oluşturulması
        e_MeasureWithEdgeMatchingButton = new JRadioButton("Edge Matching");
        e_MeasureWithEdgeMatchingButton.setActionCommand("e_edge");
        e_MeasureBetweenGrayPixelsButton = new JRadioButton("Manhattan");
        e_MeasureBetweenGrayPixelsButton.setActionCommand("e_manhattan");
        e_MeasureWithEdgeMatchingButton.setSelected(true);

        e_distanceMeasureGroup = new ButtonGroup();
        e_distanceMeasureGroup.add(e_MeasureWithEdgeMatchingButton);
        e_distanceMeasureGroup.add(e_MeasureBetweenGrayPixelsButton);

        JPanel e_distanceMeasurePanel = new JPanel(new GridLayout(0, 1));
        e_distanceMeasurePanel.add(new JLabel("Uzaklık Ölçütü"));
        e_distanceMeasurePanel.add(e_MeasureWithEdgeMatchingButton);
        e_distanceMeasurePanel.add(e_MeasureBetweenGrayPixelsButton);

        // pixel için uzaklık butonlarının gruplanması ve panellerin oluşturulması
        p_MeasureWithPixelMatchingButton = new JRadioButton("Pixel Matching");
        p_MeasureWithPixelMatchingButton.setActionCommand("p_pixel");
        p_MeasureBetweenPixelsButton = new JRadioButton("Manhattan");
        p_MeasureBetweenPixelsButton.setActionCommand("p_manhattan");
        p_MeasureWithPixelMatchingButton.setSelected(true);

        p_distanceMeasureGroup = new ButtonGroup();
        p_distanceMeasureGroup.add(p_MeasureWithPixelMatchingButton);
        p_distanceMeasureGroup.add(p_MeasureBetweenPixelsButton);

        JPanel p_distanceMeasurePanel = new JPanel(new GridLayout(0, 1));
        p_distanceMeasurePanel.add(new JLabel("Uzaklık Ölçütü"));
        p_distanceMeasurePanel.add(p_MeasureWithPixelMatchingButton);
        p_distanceMeasurePanel.add(p_MeasureBetweenPixelsButton);

        // edge için seçilim butonlarının gruplanması ve panellerin oluşturulması
        e_RouletteWheelSelectionButton = new JRadioButton("Roulette S");
        e_RouletteWheelSelectionButton.setActionCommand("e_rulet");
        e_OrderBasedSelectionButton = new JRadioButton("Ordered S");
        e_OrderBasedSelectionButton.setActionCommand("e_order");
        e_RouletteWheelSelectionButton.setSelected(true);

        e_selectionGroup = new ButtonGroup();
        e_selectionGroup.add(e_RouletteWheelSelectionButton);
        e_selectionGroup.add(e_OrderBasedSelectionButton);

        JPanel e_selectionPanel = new JPanel(new GridLayout(0, 1));
        e_selectionPanel.add(new JLabel("Seçilim Tipi"));
        e_selectionPanel.add(e_RouletteWheelSelectionButton);
        e_selectionPanel.add(e_OrderBasedSelectionButton);

        // pixel için seçilim butonlarının gruplanması ve panellerin oluşturulması
        p_RouletteWheelSelectionButton = new JRadioButton("Roulette S");
        p_RouletteWheelSelectionButton.setActionCommand("p_rulet");
        p_OrderBasedSelectionButton = new JRadioButton("Ordered S");
        p_OrderBasedSelectionButton.setActionCommand("p_order");
        p_RouletteWheelSelectionButton.setSelected(true);

        p_selectionGroup = new ButtonGroup();
        p_selectionGroup.add(p_RouletteWheelSelectionButton);
        p_selectionGroup.add(p_OrderBasedSelectionButton);

        JPanel p_selectionPanel = new JPanel(new GridLayout(0, 1));
        p_selectionPanel.add(new JLabel("Seçilim Tipi"));
        p_selectionPanel.add(p_RouletteWheelSelectionButton);
        p_selectionPanel.add(p_OrderBasedSelectionButton);

        // butonlara action listener eklenmesi
        reset.addActionListener(this);
        loadImage.addActionListener(this);
        startStopPixel.addActionListener(this);
        startStopEdge.addActionListener(this);
        e_EdgeCrossoverButton.addActionListener(this);
        //e_RandomCrossoverButton.addActionListener(this);
        //e_UniformCrossoverButton.addActionListener(this);
        p_RandomCrossoverButton.addActionListener(this);
        p_UniformCrossoverButton.addActionListener(this);
        e_MeasureWithEdgeMatchingButton.addActionListener(this);
        e_MeasureBetweenGrayPixelsButton.addActionListener(this);
        p_MeasureWithPixelMatchingButton.addActionListener(this);
        p_MeasureBetweenPixelsButton.addActionListener(this);
        e_RouletteWheelSelectionButton.addActionListener(this);
        e_OrderBasedSelectionButton.addActionListener(this);
        p_RouletteWheelSelectionButton.addActionListener(this);
        p_OrderBasedSelectionButton.addActionListener(this);

        // ----------------------------------------- EDGE İÇİN İNPUT VE PARAMETRELER
        // pixel için populasyon input
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 8;
        add(populationLabelForPixel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 9;
        add(populationForPixel, c);

        // pixel için mutasyon oranı input
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 10;
        add(mutationLabelForPixel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 11;
        add(mutationForPixel, c);

        // pixel için mutasyon sayısı input
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 12;
        add(mutationCountLabelForPixel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 13;
        add(mutationCountForPixel, c);

        // pixel için renk sayısı input
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 14;
        add(colorCountLabelForPixel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 15;
        add(colorCountForPixel, c);

        // pixel için parametreler
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(p_crossoverPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(p_selectionPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(p_distanceMeasurePanel, c);


        // RESİM YÜKLEME

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridwidth = 3;
        c.gridy = 0;
        add(loadImage, c);

        c.gridwidth = 1;

        // 1. RESİM AYARLARI
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;
        add(startStopPixel, c);

        ImageIcon image = new ImageIcon("/home/ercan/ideaprojects/Genetic_Images/ball2.png");
        picture = new JLabel(image);
        img = Imgcodecs.imread("mean5.png");

        ImageIcon image2 = new ImageIcon("/home/ercan/ideaprojects/Genetic_Images/ball1.jpeg");
        JLabel picture2 = new JLabel(image2);

        ImageIcon image3 = new ImageIcon("/home/ercan/ideaprojects/Genetic_Images/ball2.png");
        JLabel picture3 = new JLabel(image3);

        ImageIcon image4 = new ImageIcon("/home/ercan/ideaprojects/Genetic_Images/ball1.jpeg");
        JLabel picture4 = new JLabel(image4);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 2;
        add(picture, c);
        c.ipady = 0;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 3;
        add(picture2, c);
        c.ipady = 0;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 4;
        add(new JButton("Nesil:"), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 5;
        add(new JButton("Min:"), c);


        // 2. RESİM AYARLARI
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 1;
        add(startStopEdge, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 2;
        add(picture3, c);
        c.ipady = 0;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 3;
        add(picture4, c);
        c.ipady = 0;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 4;
        add(new JButton("Nesil:"), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 5;
        add(new JButton("Min:"), c);

        // ----------------------------------------- EDGE İÇİN İNPUT VE PARAMETRELER
        // edge için populasyon input
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 8;
        add(populationLabelForEdge, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 9;
        add(populationForEdge, c);

        // edge için mutasyon oranı input
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 10;
        add(mutationLabelForEdge, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 11;
        add(mutationForEdge, c);

        // edge için mutasyon sayısı input
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 12;
        add(mutationCountLabelForEdge, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 13;
        add(mutationCountForEdge, c);

        // edge için parametreler
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 1;
        add(e_crossoverPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 2;
        add(e_selectionPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 3;
        add(e_distanceMeasurePanel, c);
    }
}

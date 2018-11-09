/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChaserCalc;

import java.awt.Color;
import java.io.IOException;
import java.util.EnumSet;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import static ChaserCalc.CBoxEnum.*;

/**
 *
 * @author Toshiba
 */
public class ChaserCalc extends Application {

    Timeline animation;
    static boolean ANIME_FLAG = false;
    Image img1 = new Image("images/off555.png", 400, 400, false, false);
    Image img2 = new Image("images/on555.png", 402, 400, false, false);

    @FXML
    protected void calCulate(ActionEvent event) {
        if (rd555.isSelected()) handle555();
        else handle4017();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalcGui.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        final ToggleGroup timers = new ToggleGroup();
        rd555.setToggleGroup(timers);
        rd4017.setToggleGroup(timers);
         for (CBoxEnum res : EnumSet.range(OHM, MEG))
             cbR1.getItems().add(res.getSymbol());
         for (CBoxEnum res : EnumSet.range(OHM, MEG))
             cbR2.getItems().add(res.getSymbol());
         for (CBoxEnum res : EnumSet.range(NAN, MIL))
             cbC1.getItems().add(res.getSymbol());
        cbC1.valueProperty().setValue(CBoxEnum.MIC.getSymbol());
        cbR1.valueProperty().setValue(CBoxEnum.KIL.getSymbol());
        cbR2.valueProperty().setValue(CBoxEnum.KIL.getSymbol());
        rd555.setSelected(true);
        Image img = new Image("images/off555.png", 486, 512, true, true);
        imgs.setImage(img);
        cLed.setFill(Paint.valueOf("yellow"));  //348, 201
        
//        grid.backgroundProperty();
        Scene scene = new Scene(root);
//        scene.setb
        primaryStage.setTitle("LED CALCULATOR");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setAlwaysOnTop(true);
        primaryStage.resizableProperty().setValue(false);
//        primaryStage.set
//        boolean siza = primaryStage.resizableProperty().getValue();
//        System.out.println("siza: "+siza);
    }
    int count = 0;

    private void handle555() {
        double t1, t2, freq, time, duty, r1, r2, c1;
//        String ;
        try {
            stopAnimation();
            r1 = Double.valueOf(txR1.getText().toString()) * getMult(cbR1);
            r2 = Double.valueOf(txR2.getText().toString()) * getMult(cbR2);
            c1 = Double.valueOf(txC1.getText().toString()) * getMult(cbC1);
            t1 = 0.693 * c1 * (r1 + r2);
            t2 = 0.693 * r2 * c1;
            animate(sec2millis(t1), sec2millis(t2));
            time = shorten(t1 + t2);
            freq = shorten(1 / time);
            duty = shorten((100 * r2) / ((2 * r2) + r1));
            lbFreq.prefWidthProperty().setValue(80);
            lbDuty.prefWidthProperty().setValue(80);
            lbTime.prefWidthProperty().setValue(80);
            lbFreq.setText(String.valueOf(freq) + " Hertz");
            lbTime.setText(String.valueOf(time) + " Second"+((time==1)? "" : "s"));
            lbDuty.setText(String.valueOf(duty) + "%");
//            double2Int(freq);

        } catch (Exception e) {
            System.out.println("Error=" + e.toString());

        }
    }
    
    private int sec2millis(double d) {
        int x = (int) (d * 1000);
        if (x == 0) x = 1;
        return x;
    }

    long timer = 0;

    private void stopAnimation(){
            if(ANIME_FLAG) animation.stop();
            count = 0;
            timer = 0;
    }
    private void animate(int on, int off) {        
        animation = new Timeline();
        animation.getKeyFrames().add(new KeyFrame(
                Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer++;
                if (timer == on && count % 2 == 0) {
//                    imgs.setImage(img2);
                    cLed.setOpacity(1);
                    timer = 0;
                    count++;
                } else if (timer == off && count % 2 != 0) {
                    cLed.setOpacity(0);
//                    imgs.setImage(img1);
                    timer = 0;
                    count++;
                }
//                System.out.println("finito ");
            }
        }));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        ANIME_FLAG = true;
    }

    private double shorten(double d) {
        double res = Math.ceil(d * 10);
        return res / 10;
    }

    private double getMult(ChoiceBox cb){
        double result = 3;
        String st = (String) cb.valueProperty().getValue();        
//        CBoxEnum res[] = CBoxEnum.values();
        for(CBoxEnum res : CBoxEnum.values())
            if(res.getSymbol().equals(st))
                result = res.getValue();
        System.out.println("Symbol = "+st+"; Value = "+result);
        return result;
    }
    
    private double getMultiplier(ChoiceBox b) {
        double result = 0;
        String st = (String) b.valueProperty().getValue();
        switch (st) {
            case "Ω":
                result = 1;
                break;
            case "KΩ":
                result = 1000;
                break;
            case "MΩ":
                result = 1000000;
                break;
            case "mF":
                result = 0.001;
                break;
            case "uF":
                result = 0.000001;
                break;
            case "nF":
                result = 0.000000001;
                break;
        }
        return result;
    }

    private void handle4017() {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    Button btCalculate, btPreview, btClose;
    @FXML
    TextField txR1, txR2, txC1;
    @FXML
    Label lbFreq, lbTime, lbDuty;
    @FXML
    ImageView imgs;
    @FXML
    Circle cLed;
    @FXML
    RadioButton rd555, rd4017;
    @FXML
    ChoiceBox cbR1, cbR2, cbC1;
        @FXML GridPane grid;

}

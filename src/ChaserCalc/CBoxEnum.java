package ChaserCalc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Toshiba
 */
public enum CBoxEnum {
    OHM("Ω", 1),
    KIL("KΩ", 1000),
    MEG("MΩ", 1000000),
    NAN("nF", 0.000000001),
    MIC("uF", 0.000001),
    MIL("mF", 0.001);

    String symbol;
    double value;

    private CBoxEnum(String symbol, double value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getValue() {
        return value;
    }
//    public int getValue(int pos) {
//        int val=1;
//        CBoxEnum res[] = CBoxEnum.values();
//        CBoxEnum rs = res[pos];
//        val = rs.value;
//        System.out.println("Symbol = "+sym+"; Value = "+val);
//        return val;
//    }

//    public int getValue(String val) {
//        return value;
//    }
}

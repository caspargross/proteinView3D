package model;

/**
 * Created by Caspar on 31.01.2017.
 */
public final class aminoAcidTools {

    public static String transFormCode(String threeLetterCode){

        switch (threeLetterCode) {
            case "ALA" : return "A";
            case "CYS" : return "C";
            case "ASP" : return "D";
            case "GLU" : return "E";
            case "PHE" : return "F";
            case "GLY" : return "G";
            case "HIS" : return "H";
            case "ILE" : return "I";
            case "LYS" : return "K";
            case "LEU" : return "L";
            case "MET" : return "M";
            case "ASN" : return "N";
            case "PRO" : return "P";
            case "GLN" : return "Q";
            case "ARG" : return "R";
            case "SER" : return "S";
            case "THR" : return "T";
            case "VAL" : return "V";
            case "TRP" : return "W";
            case "TYR" : return "Y";

        }
        return "";
    }
}

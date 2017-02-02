package model;

/**
 * Created by Caspar on 31.01.2017.
 */
public final class AminoAcidTools {


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

    public static String getAminoAcidType(String threeLetterCode){
        switch (threeLetterCode) {
            case "A" : return "Hydrophobic";
            case "C" : return "Special";
            case "D" : return "Acidic";
            case "E" : return "Acidic";
            case "F" : return "Hydrophobic";
            case "G" : return "Special";
            case "H" : return "Basic";
            case "I" : return "Hydrophobic";
            case "K" : return "Basic";
            case "L" : return "Hydrophobic";
            case "M" : return "Hydrophobic";
            case "N" : return "Polarl";
            case "P" : return "Special";
            case "Q" : return "Polarl";
            case "R" : return "Basic";
            case "S" : return "Polarl";
            case "T" : return "Hydrophobic";
            case "V" : return "Hydrophobic";
            case "W" : return "Hydrophobic";
            case "Y" : return "Hydrophobic";

        }
        return null;
    }
}

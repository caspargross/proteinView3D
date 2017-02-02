package model;

/**
 * Created by Caspar on 31.01.2017.
 */
public final class AminoAcidTools {

    public enum AminoAcidType {Basic, Acidic, Polar, Special, Hydrophobic}

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

    public static AminoAcidType getAminoAcidType(String threeLetterCode){
        switch (threeLetterCode) {
            case "ALA" : return AminoAcidType.Hydrophobic;
            case "CYS" : return AminoAcidType.Special;
            case "ASP" : return AminoAcidType.Acidic;
            case "GLU" : return AminoAcidType.Acidic;
            case "PHE" : return AminoAcidType.Hydrophobic;
            case "GLY" : return AminoAcidType.Special;
            case "HIS" : return AminoAcidType.Basic;
            case "ILE" : return AminoAcidType.Hydrophobic;
            case "LYS" : return AminoAcidType.Basic;
            case "LEU" : return AminoAcidType.Hydrophobic;
            case "MET" : return AminoAcidType.Hydrophobic;
            case "ASN" : return AminoAcidType.Polar;
            case "PRO" : return AminoAcidType.Special;
            case "GLN" : return AminoAcidType.Polar;
            case "ARG" : return AminoAcidType.Basic;
            case "SER" : return AminoAcidType.Polar;
            case "THR" : return AminoAcidType.Hydrophobic;
            case "VAL" : return AminoAcidType.Hydrophobic;
            case "TRP" : return AminoAcidType.Hydrophobic;
            case "TYR" : return AminoAcidType.Hydrophobic;

        }
        return null;
    }
}

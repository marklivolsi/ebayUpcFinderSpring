package com.mcl.utilities;

import java.util.ArrayList;

public class UpcStringValidator {

    public ArrayList<String> removeInvalidUpcsFromArray(ArrayList<String> upcs) {
        ArrayList<String> results = new ArrayList<>();
        for (String upc : upcs) {
            if (isValid(upc)) {
                results.add(upc);
            }
        }
        return results;
    }

    public boolean isValid(String upc) {
        return new StringUtils().validate(upc, str -> (str.length() == 12 ||
                str.length() == 13) &&
                str.matches("\\d+"));
    }

}

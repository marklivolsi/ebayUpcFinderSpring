package com.mcl.ebayupcfinderspring;

import com.mcl.utilities.Serializer;
import com.mcl.utilities.UpcProductDataFetcher;
import com.mcl.utilities.UpcStringValidator;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @CrossOrigin
    @RequestMapping(value = "/{upc}", method = RequestMethod.GET)
    @ResponseBody
    public String fetchDataForUpc(@PathVariable String upc) {
        String jsonResponse;
        Serializer serializer = new Serializer();
        try {
            if (new UpcStringValidator().isValid(upc)) {
                UpcProductDataFetcher product = new UpcProductDataFetcher(upc);
                product.fetchAllItemDetails();
                jsonResponse = serializer.buildJsonResponse(product.getItemListings());
                return jsonResponse + "\n";
            } else {
                jsonResponse = serializer.buildEmptyJsonResponse();
                return jsonResponse;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            jsonResponse = serializer.buildEmptyJsonResponse();
            return jsonResponse;
        }

    }
}

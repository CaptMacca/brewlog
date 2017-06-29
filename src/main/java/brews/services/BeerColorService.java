package brews.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steve on 25/06/2017.
 */
@Service
public class BeerColorService {

    private static Map<Integer,String> srmMap;
    static {
        srmMap = new HashMap<Integer, String>();
        srmMap.put(1, "#F3F993");
        srmMap.put(2, "#F5F75C");
        srmMap.put(3, "#F6F513");
        srmMap.put(4, "#EAE615");
        srmMap.put(5, "#E0D01B");
        srmMap.put(6, "#D5BC26");
        srmMap.put(7, "#CDAA37");
        srmMap.put(8, "#C1963C");
        srmMap.put(9, "#BE8C3A");
        srmMap.put(10, "#BE823A");
        srmMap.put(11, "#C17A37");
        srmMap.put(12, "#BF7138");
        srmMap.put(13, "#BC6733");
        srmMap.put(14, "#B26033");
        srmMap.put(15, "#A85839");
        srmMap.put(16, "#985336");
        srmMap.put(17, "#8D4C32");
        srmMap.put(18, "#7C452D");
        srmMap.put(19, "#6B3A1E");
        srmMap.put(20, "#5D341A");
        srmMap.put(21, "#4E2A0C");
        srmMap.put(22, "#4A2727");
        srmMap.put(23, "#361F1B");
        srmMap.put(24, "#261716");
        srmMap.put(25, "#231716");
        srmMap.put(26, "#19100F");
        srmMap.put(27, "#16100F");
        srmMap.put(28, "#120D0C");
        srmMap.put(29, "#100B0A");
        srmMap.put(30, "#050B0A");
    }

    public String getColourForSRM(Integer srm) {
        if (srm > 30) return "#000000";
        return srmMap.get(srm);
    }
}

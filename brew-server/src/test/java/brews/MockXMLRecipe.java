package brews;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Returns a mock xml recipe file for unit tests
 */
public class MockXMLRecipe {

    public static InputStream getMockedXMLRecipe() {
        String recipe = new String("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><RECIPES>\n" +
                "<RECIPE>\n" +
                " <NAME>Sandman - Zombie Dust Clone</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>All Grain</TYPE>\n" +
                " <BREWER>Eric Knee</BREWER>\n" +
                " <ASST_BREWER></ASST_BREWER>\n" +
                " <BATCH_SIZE>21.0000000</BATCH_SIZE>\n" +
                " <BOIL_SIZE>27.1875000</BOIL_SIZE>\n" +
                " <BOIL_TIME>90.0000000</BOIL_TIME>\n" +
                " <EFFICIENCY>72.0000000</EFFICIENCY>\n" +
                " <HOPS>\n" +
                "<HOP>\n" +
                " <NAME>Citra</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <ORIGIN>U.S.</ORIGIN>\n" +
                " <ALPHA>12.0000000</ALPHA>\n" +
                " <AMOUNT>0.0210000</AMOUNT>\n" +
                " <USE>First Wort</USE>\n" +
                " <TIME>90.0000000</TIME>\n" +
                " <NOTES>Special aroma hops released in 2007.  Imparts high alpha/oil content but low cohumulone.\n" +
                "Aroma: Adds interesting citrus and tropical fruit character to the beer.  \n" +
                "Substitutes: Unknown</NOTES>\n" +
                " <TYPE>Bittering</TYPE>\n" +
                " <FORM>Pellet</FORM>\n" +
                " <BETA>4.0000000</BETA>\n" +
                " <HSI>25.0000000</HSI>\n" +
                " <DISPLAY_AMOUNT>21.00 g</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 g</INVENTORY>\n" +
                " <DISPLAY_TIME>90.0 min</DISPLAY_TIME>\n" +
                "</HOP>\n" +
                "<HOP>\n" +
                " <NAME>Citra</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <ORIGIN>U.S.</ORIGIN>\n" +
                " <ALPHA>12.0000000</ALPHA>\n" +
                " <AMOUNT>0.0280000</AMOUNT>\n" +
                " <USE>Boil</USE>\n" +
                " <TIME>15.0000000</TIME>\n" +
                " <NOTES>Special aroma hops released in 2007.  Imparts high alpha/oil content but low cohumulone.\n" +
                "Aroma: Adds interesting citrus and tropical fruit character to the beer.  \n" +
                "Substitutes: Unknown</NOTES>\n" +
                " <TYPE>Bittering</TYPE>\n" +
                " <FORM>Pellet</FORM>\n" +
                " <BETA>4.0000000</BETA>\n" +
                " <HSI>25.0000000</HSI>\n" +
                " <DISPLAY_AMOUNT>28.00 g</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 g</INVENTORY>\n" +
                " <DISPLAY_TIME>15.0 min</DISPLAY_TIME>\n" +
                "</HOP>\n" +
                "<HOP>\n" +
                " <NAME>Citra</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <ORIGIN>U.S.</ORIGIN>\n" +
                " <ALPHA>12.0000000</ALPHA>\n" +
                " <AMOUNT>0.0280000</AMOUNT>\n" +
                " <USE>Boil</USE>\n" +
                " <TIME>10.0000000</TIME>\n" +
                " <NOTES>Special aroma hops released in 2007.  Imparts high alpha/oil content but low cohumulone.\n" +
                "Aroma: Adds interesting citrus and tropical fruit character to the beer.  \n" +
                "Substitutes: Unknown</NOTES>\n" +
                " <TYPE>Bittering</TYPE>\n" +
                " <FORM>Pellet</FORM>\n" +
                " <BETA>4.0000000</BETA>\n" +
                " <HSI>25.0000000</HSI>\n" +
                " <DISPLAY_AMOUNT>28.00 g</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 g</INVENTORY>\n" +
                " <DISPLAY_TIME>10.0 min</DISPLAY_TIME>\n" +
                "</HOP>\n" +
                "<HOP>\n" +
                " <NAME>Citra</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <ORIGIN>U.S.</ORIGIN>\n" +
                " <ALPHA>12.0000000</ALPHA>\n" +
                " <AMOUNT>0.0280000</AMOUNT>\n" +
                " <USE>Boil</USE>\n" +
                " <TIME>5.0000000</TIME>\n" +
                " <NOTES>Special aroma hops released in 2007.  Imparts high alpha/oil content but low cohumulone.\n" +
                "Aroma: Adds interesting citrus and tropical fruit character to the beer.  \n" +
                "Substitutes: Unknown</NOTES>\n" +
                " <TYPE>Bittering</TYPE>\n" +
                " <FORM>Pellet</FORM>\n" +
                " <BETA>4.0000000</BETA>\n" +
                " <HSI>25.0000000</HSI>\n" +
                " <DISPLAY_AMOUNT>28.00 g</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 g</INVENTORY>\n" +
                " <DISPLAY_TIME>5.0 min</DISPLAY_TIME>\n" +
                "</HOP>\n" +
                "<HOP>\n" +
                " <NAME>Citra</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <ORIGIN>U.S.</ORIGIN>\n" +
                " <ALPHA>12.0000000</ALPHA>\n" +
                " <AMOUNT>0.0280000</AMOUNT>\n" +
                " <USE>Boil</USE>\n" +
                " <TIME>1.0000000</TIME>\n" +
                " <NOTES>Special aroma hops released in 2007.  Imparts high alpha/oil content but low cohumulone.\n" +
                "Aroma: Adds interesting citrus and tropical fruit character to the beer.  \n" +
                "Substitutes: Unknown</NOTES>\n" +
                " <TYPE>Bittering</TYPE>\n" +
                " <FORM>Pellet</FORM>\n" +
                " <BETA>4.0000000</BETA>\n" +
                " <HSI>25.0000000</HSI>\n" +
                " <DISPLAY_AMOUNT>28.00 g</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 g</INVENTORY>\n" +
                " <DISPLAY_TIME>1.0 min</DISPLAY_TIME>\n" +
                "</HOP>\n" +
                "<HOP>\n" +
                " <NAME>Citra</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <ORIGIN>U.S.</ORIGIN>\n" +
                " <ALPHA>12.0000000</ALPHA>\n" +
                " <AMOUNT>0.0450000</AMOUNT>\n" +
                " <USE>Dry Hop</USE>\n" +
                " <TIME>10080.0000000</TIME>\n" +
                " <NOTES>Special aroma hops released in 2007.  Imparts high alpha/oil content but low cohumulone.\n" +
                "Aroma: Adds interesting citrus and tropical fruit character to the beer.  \n" +
                "Substitutes: Unknown</NOTES>\n" +
                " <TYPE>Bittering</TYPE>\n" +
                " <FORM>Pellet</FORM>\n" +
                " <BETA>4.0000000</BETA>\n" +
                " <HSI>25.0000000</HSI>\n" +
                " <DISPLAY_AMOUNT>45.00 g</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 g</INVENTORY>\n" +
                " <DISPLAY_TIME>10080.0 min</DISPLAY_TIME>\n" +
                "</HOP>\n" +
                "</HOPS>\n" +
                "\n" +
                " <FERMENTABLES>\n" +
                "<FERMENTABLE>\n" +
                " <NAME>Pale Malt, Maris Otter</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>Grain</TYPE>\n" +
                " <AMOUNT>4.3799972</AMOUNT>\n" +
                " <YIELD>82.5000000</YIELD>\n" +
                " <COLOR>5.9100000</COLOR>\n" +
                " <ADD_AFTER_BOIL>FALSE</ADD_AFTER_BOIL>\n" +
                " <ORIGIN>United Kingdom</ORIGIN>\n" +
                " <SUPPLIER>Maris Otter</SUPPLIER>\n" +
                " <NOTES>Premium base malt from the UK.  Popular for many English styles of beer including ales, pale ales and bitters.</NOTES>\n" +
                " <COARSE_FINE_DIFF>1.5000000</COARSE_FINE_DIFF>\n" +
                " <MOISTURE>3.0000000</MOISTURE>\n" +
                " <DIASTATIC_POWER>120.0000000</DIASTATIC_POWER>\n" +
                " <PROTEIN>11.7000000</PROTEIN>\n" +
                " <MAX_IN_BATCH>100.0000000</MAX_IN_BATCH>\n" +
                " <RECOMMEND_MASH>TRUE</RECOMMEND_MASH>\n" +
                " <IBU_GAL_PER_LB>0.0000000</IBU_GAL_PER_LB>\n" +
                " <DISPLAY_AMOUNT>4.38 kg</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 kg</INVENTORY>\n" +
                " <POTENTIAL>1.0379500</POTENTIAL>\n" +
                " <DISPLAY_COLOR>5.9 EBC</DISPLAY_COLOR>\n" +
                " <EXTRACT_SUBSTITUTE>Pale Liquid Extract</EXTRACT_SUBSTITUTE>\n" +
                "</FERMENTABLE>\n" +
                "<FERMENTABLE>\n" +
                " <NAME>Munich I (Weyermann)</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>Grain</TYPE>\n" +
                " <AMOUNT>0.6899996</AMOUNT>\n" +
                " <YIELD>82.2300000</YIELD>\n" +
                " <COLOR>13.9870000</COLOR>\n" +
                " <ADD_AFTER_BOIL>FALSE</ADD_AFTER_BOIL>\n" +
                " <ORIGIN>Germany</ORIGIN>\n" +
                " <SUPPLIER>Weyermann</SUPPLIER>\n" +
                " <NOTES>Light Munich malt.  May be used as a base for many German beer styles.  Fest beers, bocks, ales. Enhances malty flavour and aroma</NOTES>\n" +
                " <COARSE_FINE_DIFF>1.5000000</COARSE_FINE_DIFF>\n" +
                " <MOISTURE>3.3000000</MOISTURE>\n" +
                " <DIASTATIC_POWER>50.0000000</DIASTATIC_POWER>\n" +
                " <PROTEIN>10.6000000</PROTEIN>\n" +
                " <MAX_IN_BATCH>100.0000000</MAX_IN_BATCH>\n" +
                " <RECOMMEND_MASH>TRUE</RECOMMEND_MASH>\n" +
                " <IBU_GAL_PER_LB>0.0000000</IBU_GAL_PER_LB>\n" +
                " <DISPLAY_AMOUNT>0.69 kg</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 kg</INVENTORY>\n" +
                " <POTENTIAL>1.0378258</POTENTIAL>\n" +
                " <DISPLAY_COLOR>14.0 EBC</DISPLAY_COLOR>\n" +
                " <EXTRACT_SUBSTITUTE>Amber Liquid Extract</EXTRACT_SUBSTITUTE>\n" +
                "</FERMENTABLE>\n" +
                "<FERMENTABLE>\n" +
                " <NAME>Caramalt</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>Grain</TYPE>\n" +
                " <AMOUNT>0.2287579</AMOUNT>\n" +
                " <YIELD>73.8300000</YIELD>\n" +
                " <COLOR>23.6400000</COLOR>\n" +
                " <ADD_AFTER_BOIL>FALSE</ADD_AFTER_BOIL>\n" +
                " <ORIGIN>UK</ORIGIN>\n" +
                " <SUPPLIER>Muntons</SUPPLIER>\n" +
                " <NOTES></NOTES>\n" +
                " <COARSE_FINE_DIFF>0.0000000</COARSE_FINE_DIFF>\n" +
                " <MOISTURE>7.0000000</MOISTURE>\n" +
                " <DIASTATIC_POWER>0.0000000</DIASTATIC_POWER>\n" +
                " <PROTEIN>12.5000000</PROTEIN>\n" +
                " <MAX_IN_BATCH>0.0000000</MAX_IN_BATCH>\n" +
                " <RECOMMEND_MASH>TRUE</RECOMMEND_MASH>\n" +
                " <IBU_GAL_PER_LB>0.0000000</IBU_GAL_PER_LB>\n" +
                " <DISPLAY_AMOUNT>0.23 kg</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 kg</INVENTORY>\n" +
                " <POTENTIAL>1.0339618</POTENTIAL>\n" +
                " <DISPLAY_COLOR>23.6 EBC</DISPLAY_COLOR>\n" +
                " <EXTRACT_SUBSTITUTE></EXTRACT_SUBSTITUTE>\n" +
                "</FERMENTABLE>\n" +
                "<FERMENTABLE>\n" +
                " <NAME>Melanoidin Malt</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>Grain</TYPE>\n" +
                " <AMOUNT>0.2287579</AMOUNT>\n" +
                " <YIELD>75.0000000</YIELD>\n" +
                " <COLOR>52.2050000</COLOR>\n" +
                " <ADD_AFTER_BOIL>FALSE</ADD_AFTER_BOIL>\n" +
                " <ORIGIN>Germany</ORIGIN>\n" +
                " <SUPPLIER>Best Malz</SUPPLIER>\n" +
                " <NOTES>BEST Melanoidin Malt contains a particularly large amount of dextrins. It gives the beer a powerful taste and a slightly reddish colour. The full-bodied nature of the beer is enhanced.\n" +
                "          It is produced according to a special kilning process. The malt is characterised by the fact that it can be processed extremely well.</NOTES>\n" +
                " <COARSE_FINE_DIFF>1.5000000</COARSE_FINE_DIFF>\n" +
                " <MOISTURE>5.5000000</MOISTURE>\n" +
                " <DIASTATIC_POWER>0.0000000</DIASTATIC_POWER>\n" +
                " <PROTEIN>11.0000000</PROTEIN>\n" +
                " <MAX_IN_BATCH>0.0000000</MAX_IN_BATCH>\n" +
                " <RECOMMEND_MASH>TRUE</RECOMMEND_MASH>\n" +
                " <IBU_GAL_PER_LB>0.0000000</IBU_GAL_PER_LB>\n" +
                " <DISPLAY_AMOUNT>0.23 kg</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 kg</INVENTORY>\n" +
                " <POTENTIAL>1.0345000</POTENTIAL>\n" +
                " <DISPLAY_COLOR>52.2 EBC</DISPLAY_COLOR>\n" +
                " <EXTRACT_SUBSTITUTE></EXTRACT_SUBSTITUTE>\n" +
                "</FERMENTABLE>\n" +
                "<FERMENTABLE>\n" +
                " <NAME>Extra Light Dry Extract</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>Dry Extract</TYPE>\n" +
                " <AMOUNT>0.0000000</AMOUNT>\n" +
                " <YIELD>95.0000000</YIELD>\n" +
                " <COLOR>5.9100000</COLOR>\n" +
                " <ADD_AFTER_BOIL>FALSE</ADD_AFTER_BOIL>\n" +
                " <ORIGIN>US</ORIGIN>\n" +
                " <SUPPLIER></SUPPLIER>\n" +
                " <NOTES>Very light color dry malt extract for general purpose use.\n" +
                "Use as base or to increase gravity of any ale or lager.</NOTES>\n" +
                " <COARSE_FINE_DIFF>1.5000000</COARSE_FINE_DIFF>\n" +
                " <MOISTURE>4.0000000</MOISTURE>\n" +
                " <DIASTATIC_POWER>120.0000000</DIASTATIC_POWER>\n" +
                " <PROTEIN>11.7000000</PROTEIN>\n" +
                " <MAX_IN_BATCH>100.0000000</MAX_IN_BATCH>\n" +
                " <RECOMMEND_MASH>FALSE</RECOMMEND_MASH>\n" +
                " <IBU_GAL_PER_LB>0.0000000</IBU_GAL_PER_LB>\n" +
                " <DISPLAY_AMOUNT>0.00 kg</DISPLAY_AMOUNT>\n" +
                " <INVENTORY>0.00 kg</INVENTORY>\n" +
                " <POTENTIAL>1.0437000</POTENTIAL>\n" +
                " <DISPLAY_COLOR>5.9 EBC</DISPLAY_COLOR>\n" +
                " <EXTRACT_SUBSTITUTE></EXTRACT_SUBSTITUTE>\n" +
                "</FERMENTABLE>\n" +
                "</FERMENTABLES>\n" +
                "\n" +
                " \n" +
                " <YEASTS>\n" +
                "<YEAST>\n" +
                " <NAME>SafAle English Ale</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>Ale</TYPE>\n" +
                " <FORM>Dry</FORM>\n" +
                " <AMOUNT>0.0236590</AMOUNT>\n" +
                " <AMOUNT_IS_WEIGHT>FALSE</AMOUNT_IS_WEIGHT>\n" +
                " <LABORATORY>DCL/Fermentis</LABORATORY>\n" +
                " <PRODUCT_ID>S-04</PRODUCT_ID>\n" +
                " <MIN_TEMPERATURE>15.0000000</MIN_TEMPERATURE>\n" +
                " <MAX_TEMPERATURE>24.0000000</MAX_TEMPERATURE>\n" +
                " <FLOCCULATION>Medium</FLOCCULATION>\n" +
                " <ATTENUATION>73.0000000</ATTENUATION>\n" +
                " <NOTES>Fast starting, fast fermenting yeast.  Quick attenuation helps to produce a clean, crisp, clear ale.  Can be used in a wide range of ales.</NOTES>\n" +
                " <BEST_FOR>Great general purpose ale yeast.</BEST_FOR>\n" +
                " <MAX_REUSE>5</MAX_REUSE>\n" +
                " <TIMES_CULTURED>0</TIMES_CULTURED>\n" +
                " <ADD_TO_SECONDARY>FALSE</ADD_TO_SECONDARY>\n" +
                " <DISPLAY_AMOUNT>23.66 ml</DISPLAY_AMOUNT>\n" +
                " <DISP_MIN_TEMP>15.0 C</DISP_MIN_TEMP>\n" +
                " <DISP_MAX_TEMP>24.0 C</DISP_MAX_TEMP>\n" +
                " <INVENTORY>0.0 pkg</INVENTORY>\n" +
                " <CULTURE_DATE>14 Jun 2003</CULTURE_DATE>\n" +
                "</YEAST>\n" +
                "</YEASTS>\n" +
                "\n" +
                " \n" +
                " <STYLE>\n" +
                " <NAME>American Pale Ale</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <CATEGORY>American Ale</CATEGORY>\n" +
                " <CATEGORY_NUMBER>10</CATEGORY_NUMBER>\n" +
                " <STYLE_LETTER>A</STYLE_LETTER>\n" +
                " <STYLE_GUIDE>BJCP</STYLE_GUIDE>\n" +
                " <TYPE>Ale</TYPE>\n" +
                " <OG_MIN>1.0450000</OG_MIN>\n" +
                " <OG_MAX>1.0600000</OG_MAX>\n" +
                " <FG_MIN>1.0100000</FG_MIN>\n" +
                " <FG_MAX>1.0150000</FG_MAX>\n" +
                " <IBU_MIN>30.0000000</IBU_MIN>\n" +
                " <IBU_MAX>45.0000000</IBU_MAX>\n" +
                " <COLOR_MIN>9.8500000</COLOR_MIN>\n" +
                " <COLOR_MAX>27.5800000</COLOR_MAX>\n" +
                " <CARB_MIN>2.2000000</CARB_MIN>\n" +
                " <CARB_MAX>2.8000000</CARB_MAX>\n" +
                " <ABV_MAX>6.0000000</ABV_MAX>\n" +
                " <ABV_MIN>4.5000000</ABV_MIN>\n" +
                " <NOTES>There is some overlap in color between American pale ale and American amber ale.  The American pale ale will generally be cleaner, have a less caramelly malt profile, less body, and often more finishing hops.</NOTES>\n" +
                " <PROFILE>Usually a moderate to high hop flavor, often showing a citrusy American hop character (although other hop varieties may be used).  Low to moderately high clean malt character supports the hop presentation, and may optionally show small amounts of specialty malt character (bready, toasty, biscuity).  The balance is typically towards the late hops and bitterness, but the malt presence can be substantial.  Caramel flavors are usually restrained or absent.  Fruity esters can be moderate to none.  Moderate to high hop bitterness with a medium to dry finish.  Hop flavor and bitterness often lingers into the finish.  No diacetyl. Dry hopping (if used) may add grassy notes, although this character should not be excessive.</PROFILE>\n" +
                " <INGREDIENTS>Pale ale malt, typically American two-row.  American hops, often but not always ones with a citrusy character.  American ale yeast.  Water can vary in sulfate content, but carbonate content should be relatively low.  Specialty grains may add character and complexity, but generally make up a relatively small portion of the grist.  Grains that add malt flavor and richness, light sweetness, and toasty or bready notes are often used (along with late hops) to differentiate brands.</INGREDIENTS>\n" +
                " <EXAMPLES>Sierra Nevada Pale Ale, Stone Pale Ale, Great Lakes Burning River Pale Ale, Bear Republic XP Pale Ale, Anderson Valley Poleeko Gold Pale Ale, Deschutes Mirror Pond, Full Sail Pale Ale, Three Floyds X-Tra Pale Ale, Firestone Pale Ale, Left Hand Brewing Jackman&#39;s Pale Ale</EXAMPLES>\n" +
                " <DISPLAY_OG_MIN>1.045 SG</DISPLAY_OG_MIN>\n" +
                " <DISPLAY_OG_MAX>1.060 SG</DISPLAY_OG_MAX>\n" +
                " <DISPLAY_FG_MIN>1.010 SG</DISPLAY_FG_MIN>\n" +
                " <DISPLAY_FG_MAX>1.015 SG</DISPLAY_FG_MAX>\n" +
                " <DISPLAY_COLOR_MIN>9.8 EBC</DISPLAY_COLOR_MIN>\n" +
                " <DISPLAY_COLOR_MAX>27.6 EBC</DISPLAY_COLOR_MAX>\n" +
                " <OG_RANGE>1.045-1.060 SG</OG_RANGE>\n" +
                " <FG_RANGE>1.010-1.015 SG</FG_RANGE>\n" +
                " <IBU_RANGE>30.0-45.0 IBUs</IBU_RANGE>\n" +
                " <CARB_RANGE>2.20-2.80 Vols</CARB_RANGE>\n" +
                " <COLOR_RANGE>9.8-27.6 EBC</COLOR_RANGE>\n" +
                " <ABV_RANGE>4.50-6.00 %</ABV_RANGE>\n" +
                "</STYLE>\n" +
                "\n" +
                " <EQUIPMENT>\n" +
                " <NAME>Braumeister - 20 Litre (90 mins)</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <BOIL_SIZE>27.1875000</BOIL_SIZE>\n" +
                " <BATCH_SIZE>21.0000000</BATCH_SIZE>\n" +
                " <TUN_VOLUME>40.0000000</TUN_VOLUME>\n" +
                " <TUN_WEIGHT>4.5359200</TUN_WEIGHT>\n" +
                " <TUN_SPECIFIC_HEAT>0.1200000</TUN_SPECIFIC_HEAT>\n" +
                " <TOP_UP_WATER>0.0000000</TOP_UP_WATER>\n" +
                " <TRUB_CHILLER_LOSS>1.5000000</TRUB_CHILLER_LOSS>\n" +
                " <EVAP_RATE>9.1954023</EVAP_RATE>\n" +
                " <BOIL_TIME>90.0000000</BOIL_TIME>\n" +
                " <CALC_BOIL_VOLUME>TRUE</CALC_BOIL_VOLUME>\n" +
                " <LAUTER_DEADSPACE>0.0000000</LAUTER_DEADSPACE>\n" +
                " <TOP_UP_KETTLE>0.0000000</TOP_UP_KETTLE>\n" +
                " <HOP_UTILIZATION>100.0000000</HOP_UTILIZATION>\n" +
                " <COOLING_LOSS_PCT>4.0000000</COOLING_LOSS_PCT>\n" +
                " <NOTES>For the Braumeister 20l brewing system.  Recommend using any of the &#34;BIAB&#34; or Brew-in-a-bag mash profiles in recipes with this equipment profile.</NOTES>\n" +
                " <DISPLAY_BOIL_SIZE>27.19 l</DISPLAY_BOIL_SIZE>\n" +
                " <DISPLAY_BATCH_SIZE>21.00 l</DISPLAY_BATCH_SIZE>\n" +
                " <DISPLAY_TUN_VOLUME>40.00 l</DISPLAY_TUN_VOLUME>\n" +
                " <DISPLAY_TUN_WEIGHT>4.54 kg</DISPLAY_TUN_WEIGHT>\n" +
                " <DISPLAY_TOP_UP_WATER>0.00 l</DISPLAY_TOP_UP_WATER>\n" +
                " <DISPLAY_TRUB_CHILLER_LOSS>1.50 l</DISPLAY_TRUB_CHILLER_LOSS>\n" +
                " <DISPLAY_LAUTER_DEADSPACE>0.00 l</DISPLAY_LAUTER_DEADSPACE>\n" +
                " <DISPLAY_TOP_UP_KETTLE>0.00 l</DISPLAY_TOP_UP_KETTLE>\n" +
                "</EQUIPMENT>\n" +
                "\n" +
                " <MASH>\n" +
                " <NAME>Temperature Mash, 1 Step, Medium Body</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <GRAIN_TEMP>22.2222222</GRAIN_TEMP>\n" +
                " <TUN_TEMP>22.2222222</TUN_TEMP>\n" +
                " <SPARGE_TEMP>75.5555556</SPARGE_TEMP>\n" +
                " <PH>5.4000000</PH>\n" +
                " <TUN_WEIGHT>160.0000000</TUN_WEIGHT>\n" +
                " <TUN_SPECIFIC_HEAT>0.1200000</TUN_SPECIFIC_HEAT>\n" +
                " <EQUIP_ADJUST>FALSE</EQUIP_ADJUST>\n" +
                " <NOTES>Temperature mash for use when mashing in a brew pot over a heat source such as the stove.  Use heat to maintain desired temperature during the mash.</NOTES>\n" +
                " <DISPLAY_GRAIN_TEMP>22.2 C</DISPLAY_GRAIN_TEMP>\n" +
                " <DISPLAY_TUN_TEMP>22.2 C</DISPLAY_TUN_TEMP>\n" +
                " <DISPLAY_SPARGE_TEMP>75.6 C</DISPLAY_SPARGE_TEMP>\n" +
                " <DISPLAY_TUN_WEIGHT>4.54 kg</DISPLAY_TUN_WEIGHT>\n" +
                "<MASH_STEPS>\n" +
                "<MASH_STEP>\n" +
                " <NAME>Saccharification</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>Infusion</TYPE>\n" +
                " <INFUSE_AMOUNT>25.0009552</INFUSE_AMOUNT>\n" +
                " <STEP_TIME>60.0000000</STEP_TIME>\n" +
                " <STEP_TEMP>66.0000000</STEP_TEMP>\n" +
                " <RAMP_TIME>15.0000000</RAMP_TIME>\n" +
                " <END_TEMP>66.0000000</END_TEMP>\n" +
                " <DESCRIPTION>Add 25.00 l of water at 69.7 C</DESCRIPTION>\n" +
                " <WATER_GRAIN_RATIO>4.523 l/kg</WATER_GRAIN_RATIO>\n" +
                " <DECOCTION_AMT>0.00 l</DECOCTION_AMT>\n" +
                " <INFUSE_TEMP>69.7 C</INFUSE_TEMP>\n" +
                " <DISPLAY_STEP_TEMP>66.0 C</DISPLAY_STEP_TEMP>\n" +
                " <DISPLAY_INFUSE_AMT>25.00 l</DISPLAY_INFUSE_AMT>\n" +
                "</MASH_STEP>\n" +
                "<MASH_STEP>\n" +
                " <NAME>Mash Out</NAME>\n" +
                " <VERSION>1</VERSION>\n" +
                " <TYPE>Temperature</TYPE>\n" +
                " <INFUSE_AMOUNT>0.0000000</INFUSE_AMOUNT>\n" +
                " <STEP_TIME>10.0000000</STEP_TIME>\n" +
                " <STEP_TEMP>75.5555556</STEP_TEMP>\n" +
                " <RAMP_TIME>10.0000000</RAMP_TIME>\n" +
                " <END_TEMP>75.5555556</END_TEMP>\n" +
                " <DESCRIPTION>Heat to 75.6 C over 10 min</DESCRIPTION>\n" +
                " <WATER_GRAIN_RATIO>4.523 l/kg</WATER_GRAIN_RATIO>\n" +
                " <DECOCTION_AMT>0.00 l</DECOCTION_AMT>\n" +
                " <INFUSE_TEMP>83.3 C</INFUSE_TEMP>\n" +
                " <DISPLAY_STEP_TEMP>75.6 C</DISPLAY_STEP_TEMP>\n" +
                " <DISPLAY_INFUSE_AMT>0.00 l</DISPLAY_INFUSE_AMT>\n" +
                "</MASH_STEP>\n" +
                "</MASH_STEPS>\n" +
                "\n" +
                "</MASH>\n" +
                "\n" +
                " <OG>1.0550000</OG>\n" +
                " <FG>1.0140000</FG>\n" +
                " <CARBONATION>2.5000000</CARBONATION>\n" +
                " <FERMENTATION_STAGES>2</FERMENTATION_STAGES>\n" +
                " <PRIMARY_AGE>7.0000000</PRIMARY_AGE>\n" +
                " <PRIMARY_TEMP>19.4444444</PRIMARY_TEMP>\n" +
                " <SECONDARY_AGE>7.0000000</SECONDARY_AGE>\n" +
                " <SECONDARY_TEMP>19.4444444</SECONDARY_TEMP>\n" +
                " <TERTIARY_AGE>7.0000000</TERTIARY_AGE>\n" +
                " <AGE>7.0000000</AGE>\n" +
                " <AGE_TEMP>7.2222222</AGE_TEMP>\n" +
                " <CARBONATION_USED>Keg with 14.89 PSI</CARBONATION_USED>\n" +
                " <FORCED_CARBONATION>FALSE</FORCED_CARBONATION>\n" +
                " <PRIMING_SUGAR_NAME>Forced Carbonation</PRIMING_SUGAR_NAME>\n" +
                " <PRIMING_SUGAR_EQUIV>1.0000000</PRIMING_SUGAR_EQUIV>\n" +
                " <KEG_PRIMING_FACTOR>0.5000000</KEG_PRIMING_FACTOR>\n" +
                " <CARBONATION_TEMP>7.2222222</CARBONATION_TEMP>\n" +
                " <DISPLAY_CARB_TEMP>7.2 C</DISPLAY_CARB_TEMP>\n" +
                " <DATE>09 Mar 2012</DATE>\n" +
                " <EST_OG>1.060 SG</EST_OG>\n" +
                " <EST_FG>1.014 SG</EST_FG>\n" +
                " <EST_COLOR>14.2 EBC</EST_COLOR>\n" +
                " <IBU>79.8 IBUs</IBU>\n" +
                " <IBU_METHOD>Rager</IBU_METHOD>\n" +
                " <EST_ABV>6.0 %</EST_ABV>\n" +
                " <ABV>5.4 %</ABV>\n" +
                " <ACTUAL_EFFICIENCY>60.0 %</ACTUAL_EFFICIENCY>\n" +
                " <CALORIES>519.2 kcal/l</CALORIES>\n" +
                " <DISPLAY_BATCH_SIZE>21.00 l</DISPLAY_BATCH_SIZE>\n" +
                " <DISPLAY_BOIL_SIZE>27.19 l</DISPLAY_BOIL_SIZE>\n" +
                " <DISPLAY_OG>1.055 SG</DISPLAY_OG>\n" +
                " <DISPLAY_FG>1.014 SG</DISPLAY_FG>\n" +
                " <DISPLAY_PRIMARY_TEMP>19.4 C</DISPLAY_PRIMARY_TEMP>\n" +
                " <DISPLAY_SECONDARY_TEMP>19.4 C</DISPLAY_SECONDARY_TEMP>\n" +
                " <DISPLAY_TERTIARY_TEMP>18.3 C</DISPLAY_TERTIARY_TEMP>\n" +
                " <DISPLAY_AGE_TEMP>7.2 C</DISPLAY_AGE_TEMP>\n" +
                "</RECIPE>\n" +
                "</RECIPES>\n");

        return new ByteArrayInputStream(recipe.getBytes());

    }
}

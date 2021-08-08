package brews;

import brews.util.transformer.beerxml.model.*;

import java.util.Arrays;

public class MockImportedRecipe {

    public static ImportedRecipe getImportedRecipe() {

        ImportedRecipe importedRecipe = new ImportedRecipe();

        importedRecipe.setName("Sandman - Zombie Dust Clone");
        importedRecipe.setVersion(1);
        importedRecipe.setType("All Grain");
        importedRecipe.setBrewer("Eric Knee");
        importedRecipe.setAssistantBrewer("");
        importedRecipe.setBatchSize(21.00);
        importedRecipe.setBoilSize(27.1875000);
        importedRecipe.setBoilTime(90.0000000);
        importedRecipe.setEfficency(72.0000000);
        importedRecipe.setOriginalGravity(1.0550000);
        importedRecipe.setFinalGravity(1.0140000);
        importedRecipe.setCarbonation(2.5000000);
        importedRecipe.setFermentationStages(2);
        importedRecipe.setPrimaryAge(7);
        importedRecipe.setPrimaryTemp(19);
        importedRecipe.setSecondaryAge(7);
        importedRecipe.setSecondaryTemp(19);
        importedRecipe.setTertiaryAge(7);
        importedRecipe.setAge(7);
        importedRecipe.setAgeTemp(7);
        importedRecipe.setCarbonationUsed("Keg with 14.89 PSI");
        importedRecipe.setDate("09 Mar 2012");
        importedRecipe.setEstimatedOriginalGravity("1.060 SG");
        importedRecipe.setEstimatedFinalGravity("1.014 SG");
        importedRecipe.setEstimatedColour("14.2 EBC");
        importedRecipe.setIbu("79.8 IBUs");
        importedRecipe.setIbuMethod("Rager");
        importedRecipe.setEstimatedAbv("6.0 %");
        importedRecipe.setEstimatedAbv("5.4 %");
        importedRecipe.setActualEfficency("60.0 %");
        importedRecipe.setCalories("519.2 kcal/l");
        importedRecipe.setDisplayBatchSize("21.00 l");
        importedRecipe.setDisplayBoilSize("27.19 l");
        importedRecipe.setDisplayOriginalGravity("1.055 SG");
        importedRecipe.setDisplayFinalGravity("1.014 SG");
        importedRecipe.setDisplayPrimaryTemp("19.4 C");
        importedRecipe.setDisplaySecondaryTemp("19.4 C");
        importedRecipe.setDisplayTertiaryTemp("18.3 C");
        importedRecipe.setDisplayAgeTemp("7.2 C");

        ImportedFermentable importedFermentable = new ImportedFermentable();
        importedFermentable.setName("Pale Malt, Maris Otter");
        importedFermentable.setVersion(1);
        importedFermentable.setType("Grain");
        importedFermentable.setAmount(4.3799972);
        importedFermentable.setYield(82);
        importedFermentable.setColour(5.9100000);
        importedFermentable.setAddAfterBoil("FALSE");
        importedFermentable.setOrigin("United Kingdom");
        importedFermentable.setSupplier("Maris Otter");
        importedFermentable.setNote("Premium base malt from the UK.  Popular for many English styles of beer including ales, pale ales and bitters");
        importedFermentable.setCoarseFineDiff(1.5000000);
        importedFermentable.setMoisture(3);
        importedFermentable.setDiastaticPower(120);
        importedFermentable.setProtein(11.7000000);
        importedFermentable.setMaxInBatch(100);
        importedFermentable.setRecommendMash("TRUE");
        importedFermentable.setIbuGalPerLB(0);
        importedFermentable.setDisplayAmount("4.38 kg");
        importedFermentable.setInventory("0.00 kg");
        importedFermentable.setPotential(1.0379500);
        importedFermentable.setDisplayColour("5.9 EBC");

        ImportedHop importedHop = new ImportedHop();
        importedHop.setName("Citra");
        importedHop.setVersion(1);
        importedHop.setOrigin("U.S.");
        importedHop.setAlpha(12.0000000);
        importedHop.setAmount(0.0280000);
        importedHop.setUse("Boil");
        importedHop.setTime(1);
        importedHop.setNotes("Special aroma hops released in 2007.  Imparts high alpha/oil content but low cohumulone.\n" +
                "Aroma: Adds interesting citrus and tropical fruit character to the beer.  \n" +
                "Substitutes: Unknown</NOTES>\n");
        importedHop.setType("Bittering");
        importedHop.setForm("Pellet");
        importedHop.setBeta(4.0000000);
        importedHop.setHsi(25);
        importedHop.setDisplayAmount("28.00 g");
        importedHop.setInventory("0.00 g");
        importedHop.setDisplayTime("1.0 min");

        ImportedYeast importedYeast = new ImportedYeast();
        importedYeast.setName("SafAle English Ale");
        importedYeast.setVersion("1");
        importedYeast.setType("Ale");
        importedYeast.setForm("Dry");
        importedYeast.setAmount(0.0236590);
        importedYeast.setAmountIsWeight("FALSE");
        importedYeast.setLaboratory("Fermentis");
        importedYeast.setProductId("S-04");
        importedYeast.setMinTemperature(15);
        importedYeast.setMaxTemperature(24);
        importedYeast.setFlocculation("Medium");
        importedYeast.setAttenuation(73);
        importedYeast.setNotes("Fast starting, fast fermenting yeast.  Quick attenuation helps to produce a clean, crisp, clear ale.  Can be used in a wide range of ales.");
        importedYeast.setBestFor("Great general purpose ale yeast.");
        importedYeast.setMaxReuse(5);
        importedYeast.setTimesCultures(0);
        importedYeast.setAddToSecondary("FALSE");
        importedYeast.setDisplayAmount("23.66 ml");
        importedYeast.setDisplayMinTemp("15.0 C");
        importedYeast.setDisplayMaxTemp("24.0 C");
        importedYeast.setInventory("0.0 pkg");
        importedYeast.setCultureDate("14 Jun 2003");

        ImportedEquipment importedEquipment = new ImportedEquipment();
        importedEquipment.setName("Braumeister - 20 Litre (90 mins)");
        importedEquipment.setVersion(1);
        importedEquipment.setBoilSize(27.1875);
        importedEquipment.setBatchSize(21.00);
        importedEquipment.setTunVolume(40.0);
        importedEquipment.setTunWeight(4.5359);
        importedEquipment.setTunSpecificHeat(0.12);
        importedEquipment.setTopUpWater(0);
        importedEquipment.setTrubChillerLoss(1.5);
        importedEquipment.setEvaparationRate(9);
        importedEquipment.setBoilTime(90);
        importedEquipment.setCalculatedBoilVolume("TRUE");
        importedEquipment.setLauterDeadspace(0.0);
        importedEquipment.setTopUpKettle(0);
        importedEquipment.setHopUtilization(100);
        importedEquipment.setNotes("For the Braumeister 20l brewing system.  Recommend using any of the &#34;BIAB&#34; or Brew-in-a-bag mash profiles in recipes with this equipment profile");
        importedEquipment.setDisplayBoilSize("27.19 l");
        importedEquipment.setDisplayBatchSize("21.00 l");
        importedEquipment.setDisplayTunVolume("40.00 l");
        importedEquipment.setDisplayTunWeight("4.54 kg");
        importedEquipment.setDisplayTopUpWater("0.00 l");
        importedEquipment.setDisplayTrubChillerLoss("1.50 l");
        importedEquipment.setDisplayLauterDeadspace("0.00 l");
        importedEquipment.setDisplayTopUpKettle("0.00 l");

        ImportedMashStep importedMashStep = new ImportedMashStep();
        importedMashStep.setName("Mash Out");
        importedMashStep.setVersion("1");
        importedMashStep.setDecoctionAmount("24");
        importedMashStep.setStepTime(75);
        importedMashStep.setDescription("Heat to 75.6 C over 10 min");
        importedMashStep.setDisplayStepTemp("75.6 C");
        importedMashStep.setDisplayInfuseAmount("0.00 l");
        importedMashStep.setEndTemp(76);
        importedMashStep.setInfuseAmount(0.0000);
        importedMashStep.setRampTime(10);
        importedMashStep.setInfuseTemp("83.4 C");
        importedMashStep.setStepTemp(75);
        importedMashStep.setType("Temperature");
        importedMashStep.setWaterGrainRatio(4.523);

        ImportedMash importedMash = new ImportedMash();
        importedMash.setName("Temperature Mash, 1Step, Medium Body");
        importedMash.setDisplayGrainTemp("22.2 C");
        importedMash.setDisplaySpargeTemp("75.6 C");
        importedMash.setDisplayTunTemp(22);
        importedMash.setDisplayTunWeight("4.54 kg");
        importedMash.setEquipmentAdjust("FALSE");
        importedMash.setGrainTemp(22.2);
        importedMash.setImportedMashSteps(Arrays.asList(importedMashStep));
        importedMash.setNotes("Temperature mash for use when mashing in a brew pot over a heat source such as the stove.  Use heat to maintain desired temperature during the mash.");
        importedMash.setPh(5.4);
        importedMash.setSpargeTemp(75.5555);
        importedMash.setTunSpecificHeat(0.12);
        importedMash.setTunTemp(22.3);
        importedMash.setTunWeight(4.55);

        ImportedMisc importedMisc = new ImportedMisc();
        importedMisc.setType("");
        importedMisc.setAmount(.5);
        importedMisc.setAmountIsWeight("FALSE");
        importedMisc.setBatchSize("21");
        importedMisc.setDisplayAmount("1/2 g");
        importedMisc.setDisplayTime("5 Min");
        importedMisc.setAmount(.5);
        importedMisc.setInventory("0");
        importedMisc.setName("Cacoa nibs");
        importedMisc.setNotes("Adds a chocolaty flavour");
        importedMisc.setTime(5);
        importedMisc.setUse("Boil");
        importedMisc.setUsedFor("Flavor");
        importedMisc.setVersion(1);

        importedRecipe.setImportedFermentables(Arrays.asList(importedFermentable));
        importedRecipe.setImportedHops(Arrays.asList(importedHop));
        importedRecipe.setImportedYeasts(Arrays.asList(importedYeast));
        importedRecipe.setImportedEquipment(importedEquipment);
        importedRecipe.setImportedMash(importedMash);
        importedRecipe.setImportedMiscs(Arrays.asList(importedMisc));
        importedRecipe.setImportedStyle(null);
        importedRecipe.setImportedWaters(null);

        return importedRecipe;
    }

}

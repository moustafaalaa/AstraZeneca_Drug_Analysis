import com.astrazeneca.metadata.DrugsLabelMetadata.{DrugsIngredientsPerYear, _}
import com.astrazeneca.metadata.JsonRepoMetadata.AppConfigRules
import com.astrazeneca.utils.Constants.appConfig
import com.astrazeneca.utils.JsonExtractor.getJsonParsed

object TestingUtils {

  val appConfigRules: AppConfigRules = getJsonParsed[AppConfigRules](appConfig)

  val averageIngredientsPerYear: Seq[DrugsIngredientsPerYear] = {
    Seq(
      DrugsIngredientsPerYear(
        "2016",
        "WLP40 Asclepias Vincetoxicum, Echinacea (Angustifolia), Hypothalamus (Suis), Brain (Suis), Hepar Suis, Kidney (Suis), Methylcobalamin, Pancreas Suis, Stomach (Suis), Aacg-A, Aacg-B, Calcarea Carbonica, Gambogia, Gelsemium Sempervirens, Graphites, Nux Vomica, Phytolacca Decandra, 7-Keto Dhea (Dehydroepiandrosterone), Adenosinum Triphosphoricum Dinatrum, Glucagon, Insulinum, Sarcolacticum Acidum, Proteus (Vulgaris) CYNANCHUM VINCETOXICUM ROOT CYNANCHUM VINCETOXICUM ROOT ECHINACEA ANGUSTIFOLIA ECHINACEA ANGUSTIFOLIA SUS SCROFA HYPOTHALAMUS SUS SCROFA HYPOTHALAMUS PORK BRAIN PORK BRAIN PORK LIVER PORK LIVER PORK KIDNEY PORK KIDNEY METHYLCOBALAMIN METHYLCOBALAMIN SUS SCROFA PANCREAS SUS SCROFA PANCREAS SUS SCROFA STOMACH SUS SCROFA STOMACH APC-356433 APC-356433 APC-356434 APC-356434 OYSTER SHELL CALCIUM CARBONATE, CRUDE OYSTER SHELL CALCIUM CARBONATE, CRUDE GAMBOGE GAMBOGE GELSEMIUM SEMPERVIRENS ROOT GELSEMIUM SEMPERVIRENS ROOT GRAPHITE GRAPHITE STRYCHNOS NUX-VOMICA SEED STRYCHNOS NUX-VOMICA SEED PHYTOLACCA AMERICANA ROOT PHYTOLACCA AMERICANA ROOT PRASTERONE PRASTERONE ADENOSINE TRIPHOSPHATE DISODIUM ADENOSINE TRIPHOSPHATE GLUCAGON GLUCAGON INSULIN PORK INSULIN PORK LACTIC ACID, L- LACTIC ACID, L- PROTEUS VULGARIS PROTEUS VULGARIS WATER ALCOHOL",
        6.0
      ),
      DrugsIngredientsPerYear(
        "2017",
        "Acetaminophen Acetaminophen ACETAMINOPHEN ACETAMINOPHEN HYPROMELLOSES POLYETHYLENE GLYCOLS POVIDONE STARCH, CORN SODIUM STARCH GLYCOLATE TYPE A POTATO STEARIC ACID GPI;A5 Biconvex",
        10.0
      ),
      DrugsIngredientsPerYear(
        "2018",
        "Ilex aquifolium ILEX AQUIFOLIUM FRUITING TOP ALCOHOL WATER ILEX AQUIFOLIUM FRUITING TOP ILEX AQUIFOLIUM FRUITING TOP white,Pressure Pain Cold PE ACETAMINOPHEN, DEXTROMETHORPHAN HYDROBROMIDE, GUAIFENESIN, and PHENYLEPHRINE HYDROCHLORIDE SILICON DIOXIDE CROSCARMELLOSE SODIUM CROSPOVIDONE FD&C YELLOW NO. 6 MAGNESIUM STEARATE MALTODEXTRIN CELLULOSE, MICROCRYSTALLINE POLYETHYLENE GLYCOL, UNSPECIFIED POLYVINYL ALCOHOL POVIDONE STARCH, CORN STEARIC ACID TALC TITANIUM DIOXIDE ACETAMINOPHEN ACETAMINOPHEN DEXTROMETHORPHAN HYDROBROMIDE DEXTROMETHORPHAN GUAIFENESIN GUAIFENESIN PHENYLEPHRINE HYDROCHLORIDE PHENYLEPHRINE AAA;1134",
        9.0
      ),
      DrugsIngredientsPerYear(
        "2015",
        "Secret Brazil Clear Rainforest Mist Aluminum Zirconium Octachlorohydrex Gly Aluminum Zirconium Octachlorohydrex Gly Aluminum Zirconium Octachlorohydrex Gly WATER CYCLOMETHICONE 5 PROPYLENE GLYCOL DIMETHICONE TRISILOXANE CALCIUM CHLORIDE PEG/PPG-18/18 DIMETHICONE",
        28.0
      )
    )
  }

  val averageIngredientsPerYearPerDeliver: Seq[DrugsIngredientsPerYearDelivery] = {
    Seq(
      DrugsIngredientsPerYearDelivery("2015", "TOPICAL", 28.0),
      DrugsIngredientsPerYearDelivery("2017", "ORAL", 10.0),
      DrugsIngredientsPerYearDelivery("2016", "VAGINAL", 6.0),
      DrugsIngredientsPerYearDelivery("2018", "UnKnownRoute", 9.0)
    )
  }

  val expectedDrugDetails: Seq[DrugDetails] = {
    Seq(
      DrugDetails(
        "2016",
        List(
          ("WLP40 Asclepias Vincetoxicum", 3),
          (" Echinacea (Angustifolia)", 3),
          (" Hypothalamus (Suis)", 3),
          (" Brain (Suis)", 3),
          (" Hepar Suis", 3),
          (" Kidney (Suis)", 3),
          (" Methylcobalamin", 2),
          (" Pancreas Suis", 3),
          (" Stomach (Suis)", 3),
          (" Aacg-A", 2),
          (" Aacg-B", 2),
          (" Calcarea Carbonica", 3),
          (" Gambogia", 2),
          (" Gelsemium Sempervirens", 3),
          (" Graphites", 2),
          (" Nux Vomica", 3),
          (" Phytolacca Decandra", 3),
          (" 7-Keto Dhea (Dehydroepiandrosterone)", 4),
          (" Adenosinum Triphosphoricum Dinatrum", 4),
          (" Glucagon", 2),
          (" Insulinum", 2),
          (" Sarcolacticum Acidum", 3),
          (
            " Proteus (Vulgaris) CYNANCHUM VINCETOXICUM ROOT CYNANCHUM VINCETOXICUM ROOT ECHINACEA ANGUSTIFOLIA ECHINACEA ANGUSTIFOLIA SUS SCROFA HYPOTHALAMUS SUS SCROFA HYPOTHALAMUS PORK BRAIN PORK BRAIN PORK LIVER PORK LIVER PORK KIDNEY PORK KIDNEY METHYLCOBALAMIN METHYLCOBALAMIN SUS SCROFA PANCREAS SUS SCROFA PANCREAS SUS SCROFA STOMACH SUS SCROFA STOMACH APC-356433 APC-356433 APC-356434 APC-356434 OYSTER SHELL CALCIUM CARBONATE",
            53),
          (" CRUDE OYSTER SHELL CALCIUM CARBONATE", 6),
          (
            " CRUDE GAMBOGE GAMBOGE GELSEMIUM SEMPERVIRENS ROOT GELSEMIUM SEMPERVIRENS ROOT GRAPHITE GRAPHITE STRYCHNOS NUX-VOMICA SEED STRYCHNOS NUX-VOMICA SEED PHYTOLACCA AMERICANA ROOT PHYTOLACCA AMERICANA ROOT PRASTERONE PRASTERONE ADENOSINE TRIPHOSPHATE DISODIUM ADENOSINE TRIPHOSPHATE GLUCAGON GLUCAGON INSULIN PORK INSULIN PORK LACTIC ACID",
            39),
          (" L- LACTIC ACID", 4),
          (" L- PROTEUS VULGARIS PROTEUS VULGARIS WATER ALCOHOL", 8)
        ),
        "VAGINAL"
      ),
      DrugDetails(
        "2015",
        List(
          (
            "Secret Brazil Clear Rainforest Mist Aluminum Zirconium Octachlorohydrex Gly Aluminum Zirconium Octachlorohydrex Gly Aluminum Zirconium Octachlorohydrex Gly WATER CYCLOMETHICONE 5 PROPYLENE GLYCOL DIMETHICONE TRISILOXANE CALCIUM CHLORIDE PEG/PPG-18/18 DIMETHICONE",
            28)),
        "TOPICAL"
      ),
      DrugDetails(
        "2018",
        List(
          (
            "Ilex aquifolium ILEX AQUIFOLIUM FRUITING TOP ALCOHOL WATER ILEX AQUIFOLIUM FRUITING TOP ILEX AQUIFOLIUM FRUITING TOP white",
            17)
        ),
        "UnKnownRoute"
      ),
      DrugDetails(
        "2018",
        List(
          ("Pressure Pain Cold PE ACETAMINOPHEN", 5),
          (" DEXTROMETHORPHAN HYDROBROMIDE", 3),
          (" GUAIFENESIN", 2),
          (
            " and PHENYLEPHRINE HYDROCHLORIDE SILICON DIOXIDE CROSCARMELLOSE SODIUM CROSPOVIDONE FD&C YELLOW NO. 6 MAGNESIUM STEARATE MALTODEXTRIN CELLULOSE",
            17),
          (" MICROCRYSTALLINE POLYETHYLENE GLYCOL", 4),
          (" UNSPECIFIED POLYVINYL ALCOHOL POVIDONE STARCH", 6),
          (
            " CORN STEARIC ACID TALC TITANIUM DIOXIDE ACETAMINOPHEN ACETAMINOPHEN DEXTROMETHORPHAN HYDROBROMIDE DEXTROMETHORPHAN GUAIFENESIN GUAIFENESIN PHENYLEPHRINE HYDROCHLORIDE PHENYLEPHRINE AAA;1134",
            18)
        ),
        "UnKnownRoute"
      ),
      DrugDetails(
        "2017",
        List(
          (
            "Acetaminophen Acetaminophen ACETAMINOPHEN ACETAMINOPHEN HYPROMELLOSES POLYETHYLENE GLYCOLS POVIDONE STARCH",
            9),
          (" CORN SODIUM STARCH GLYCOLATE TYPE A POTATO STEARIC ACID GPI;A5 Biconvex", 12)
        ),
        "ORAL"
      )
    )
  }

}

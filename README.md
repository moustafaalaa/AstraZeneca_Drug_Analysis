# AstraZeneca_Drug_Analysis

Application configuration available under ```/data/config/AppConfig.json```

```
{
  "fdaConfigURL": "https://api.fda.gov/download.json",
  "fdaConfigFilePath": "\\data\\fda_data\\fda_config\\download.json",
  "fdaZippedPath": "\\data\\fda_data\\zipped\\",
  "zipFileExtension": "zip",
  "fdaUnZippedPath": "\\data\\fda_data\\unzipped\\",
  "fdaDrugLabelDataExtension": "*.json",
  "readFromLocal": false,
  "filesLimit": 1,
  "startingYear": "2009",
  "unKnownRouteName": "UnKnownRoute"
}
```

```
/** A AppConfigRules case class represent the options for the application running
    *
    * @param fdaConfigURL: String Url for the fda meta data
    * @param fdaConfigFilePath: String destination of file path for the fda meta data
    * @param fdaZippedPath: String destination of zipped folder path
    * @param zipFileExtension: String zip file extension
    * @param fdaUnZippedPath: String destination of unzipped folder path
    * @param fdaDrugLabelDataExtension: String extension of the source file
    * @param readFromLocal: Boolean option to read from local if the data is available
    * @param filesLimit: Int option to run based on limited number of files
    * @param startingYear: String starting year for analysis.
    * @param unKnownRouteName: String in string to replace the route name in case the route is null
    */
```

## Part A

Using the data from the OpenFDA API (documentation at [https://open.fda.gov/apis/drug/label/](https://open.fda.gov/apis/drug/label/)) to determine the average number of ingredients (`spl_product_data_elements`) contained in AstraZeneca medicines *per year*.

1. Choose a method to gather the data
2. Transform the data as you see fit
3. Visualize and explore the results

The output should look similar to:

    year   drug_names     avg_number_of_ingredients
    2018   drugA,drugB    21
    ... 

## Part B

Repeat the same analysis, calculate the average number of ingredients *per year and per delivery route* for all manufacturers. 

The output should look similar to:

    year   route      avg_number_of_ingredients
    2018   oral       123
    2018   injection  213
    2017   ...        ...
    ...
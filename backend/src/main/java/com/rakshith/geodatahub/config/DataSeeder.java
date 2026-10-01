package com.rakshith.geodatahub.config;

import com.rakshith.geodatahub.entity.Dataset;
import com.rakshith.geodatahub.repository.DatasetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final DatasetRepository repository;

    public DataSeeder(DatasetRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        Dataset water = new Dataset();
        water.setSlug("hyderabad-water-stress");
        water.setTitle("Hyderabad Water Stress Index");
        water.setDescription("Composite water-stress index across 186 Hyderabad sections, built by merging five civic datasets: tanker deliveries, connections, billing, groundwater and water bodies.");
        water.setCategory("Urban Infrastructure");
        water.setDateAdded(LocalDate.now());
        water.setRepoUrl("https://github.com/Rakshith-Raj08/Hyderabad-Water-Stress-Forecasting-Early-Warning-System");
        water.setMapUrl("/maps/hyderabad-water-stress.html");

        water.setIntroText(
            "Which parts of Hyderabad are most at risk of water shortages, and is the problem getting worse? " +
            "This project combines several open datasets into one section-level view of the city, scores each area's water stress, maps the results, and forecasts tanker demand."
        );

        water.setMethodology(
            "Five datasets (water tankers, connections, billing, groundwater, and water bodies) were merged to cover 185 Hyderabad water-supply sections from January 2022 to February 2024. Most of the effort went into cleaning:\n" +
            "- Section names were standardized across datasets, and sub-zones were merged into their parent sections.\n" +
            "- Non-residential entries such as reservoirs, treatment plants, and control rooms were removed.\n" +
            "- About 29 corrupted billing values were nulled out rather than guessed.\n" +
            "- Sections were geocoded, and failed lookups were fixed manually.\n" +
            "- Each section was matched to its nearest groundwater station and to the water bodies within 3 km.\n\n" +
            "A composite water stress score was then built from five indicators: tanker dependency, groundwater depletion, billing gap, unmet connection demand, and water body scarcity. The scoring went through three iterations (min-max, z-score, then clipped z-score) before the map gave a sensible picture. The result was validated with Moran's I and LISA spatial analysis, 121 years of rainfall data were tested for a trend, and a trend-plus-seasonality model was used to forecast tanker demand."
        );

        water.setFindingsJson(
            "[" +
            "{\"title\":\"West Hyderabad is a real hotspot\"," +
            "\"text\":\"Areas like KPHB, Nizampet, Kondapur, and Gachibowli form a statistically significant high-stress cluster (Moran's I = 0.313, p = 0.001), driven mainly by tanker dependency. The interactive map shows all 185 water-supply sections, colored on a gradient by their water stress score \u2014 sections with higher scores sit at the stressed end of the color scale, so the West Hyderabad cluster stands out at a glance.\"," +
            "\"imageUrl\":null}," +
            "{\"title\":\"Rainfall is not the cause\"," +
            "\"text\":\"Rainfall from 1901 to 2021 shows a slight increase, not a decline, so the stress looks more like demand growth outpacing infrastructure in fast-growing areas. This chart plots annual rainfall with a trend line, showing a slight rise over the century with large year-to-year swings, which is why rainfall is ruled out as the main driver.\"," +
            "\"imageUrl\":\"/charts/hyderabad-water-stress-rainfall.png\"}," +
            "{\"title\":\"Tanker demand is rising\"," +
            "\"text\":\"Citywide bookings grew by about 1,149 per month, with a clear peak from April to June (R\u00b2 = 0.93). The chart shows monthly tanker bookings from 2022 to early 2024, with the fitted trend and a projection into mid-2024. With only about two years of data behind it, the projection shows direction rather than exact numbers.\"," +
            "\"imageUrl\":\"/charts/hyderabad-water-stress-forecast.png\"}" +
            "]"
        );

        water.setDownloadUrl("/data/hyderabad-water-stress-processed.csv");
        // water.setRawDownloadUrl("/data/hyderabad-water-stress-raw.zip");
        water.setSourceLinksJson(
            "[" +
            "{\"name\":\"Tanker deliveries\",\"url\":\"https://data.opencity.in/dataset/hyderabad-water-supply-through-tankers-data\"}," +
            "{\"name\":\"Water connections\",\"url\":\"https://data.opencity.in/dataset/hyderabad-hmwssb-water-connections-data\"}," +
            "{\"name\":\"Billing and collection data\",\"url\":\"https://data.opencity.in/dataset/hyderabad-hwssb-billing-and-collection-data\"}," +
            "{\"name\":\"Groundwater level telemetry (Telangana)\",\"url\":\"https://nwdp.nwic.gov.in/dataset/ground-water-level-telemetry-daily-telangana-gw/resource/a125aa1f-c813-4216-a313-4616740b2c27\"}," +
            "{\"name\":\"Water bodies (Hyderabad and Telangana)\",\"url\":\"https://data.opencity.in/dataset/hyderabad-and-telangana-water-bodies\"}" +
            "]"
        );
        seedIfMissing(water);

        Dataset women = new Dataset();
        women.setSlug("women-empowerment-archetypes");
        women.setTitle("Women's Empowerment Archetypes in India");
        women.setDescription("Three empowerment archetypes identified from NFHS-5 survey data on 60,480 women using PCA and K-Means, mapped at state and district level.");
        women.setCategory("Demographics & Survey");
        women.setDateAdded(LocalDate.now());
        women.setRepoUrl("https://github.com/Rakshith-Raj08/Women-Empowerment");
        women.setMapUrl("/maps/women-empowerment.html");

        women.setIntroText(
            "Empowerment is usually reduced to a single score. This project asks whether Indian women fall into distinct types instead. " +
            "Using NFHS-5 survey data for 60,480 women, it groups women by economic participation and say in household decisions, then maps how household autonomy varies across states and districts."
        );

        women.setMethodology(
            "The survey file has over 700,000 respondents and 500+ columns, so the work began by selecting 12 relevant variables and checking each against the dataset's own codebook. That check caught a wrong variable code before it caused problems. Most of the data appeared blank at first, and the cause turned out to be the survey design rather than errors:\n" +
            "- The employment and finance questions were only asked of a random 15% of women, so the analysis was limited to that group and their matching survey weight was used.\n" +
            "- One question was skipped for women with no earnings. Instead of dropping those women, \"no personal earnings\" was kept as its own category.\n\n" +
            "Decision-making answers were converted into a 0 to 2 autonomy scale. PCA showed that empowerment has several independent dimensions, and K-Means clustering (with the number of groups chosen by silhouette score) produced three archetypes. Results were then mapped at state, urban/rural, and district level. This included matching 707 districts to a boundary file, fixing duplicate district names (seven names repeat across states), renamed districts, and post-2011 splits.\n\n" +
            "A visual check of the map then exposed a flaw in the method. Archetype percentages always sum to 100%, so a state with low employment automatically shows a larger \"homemaker\" share. Archetypes are therefore reported only as a national result, and a separate continuous autonomy score is used for the geographic comparison."
        );

        women.setFindingsJson(
            "[" +
            "{\"title\":\"Three archetypes describe Indian women's empowerment\"," +
            "\"text\":\"Weighted to the national level, 59.0% are 'Homemaker with Household Voice' (not working, but with real say in household decisions), 25.3% are 'Economically Active & Autonomous', and 15.7% are 'Fully Constrained'. The Fully Constrained figure is probably a floor rather than a ceiling, since the survey requires a private interview, and the most controlling households are less likely to allow one.\"," +
            "\"imageUrl\":\"/charts/women-empowerment-clusters.png\"}," +
            "{\"title\":\"Education and age at marriage barely separate the groups\"," +
            "\"text\":\"Average education and age at marriage were nearly identical across all three archetypes. What separates them is economic participation and decision-making power \u2014 two different dimensions rather than one scale.\"," +
            "\"imageUrl\":null}," +
            "{\"title\":\"Geography does not follow the usual assumptions\"," +
            "\"text\":\"Karnataka, Andhra Pradesh, and Telangana had some of the highest Fully Constrained shares (20 to 22%), while Nagaland and Mizoram had the lowest. In several states, including Telangana, Maharashtra, and Tamil Nadu, rural women were more economically active than urban women. Bihar and Uttar Pradesh scored at or above several southern states on the autonomy score, and only Tamil Nadu and Kerala clearly ranked higher. The interactive map colors each district by its household autonomy score using rank-based bins, since the actual scores span a narrow range (0.65 to 1.19). About 3% of districts could not be matched to the boundary file and appear grey.\"," +
            "\"imageUrl\":null}" +
            "]"
        );

        women.setDownloadNote("Individual-level download restricted \u2014 NFHS-5 microdata terms do not allow redistribution. Aggregated results are shown on the map above; for the underlying data, register directly with the DHS Program (source link below).");

        women.setLimitations(
            "The measure covers only self-reported say over a few household decisions, not overall development, and \"joint\" decisions can mean either real partnership or token consultation. Small states like Ladakh and Chandigarh have few respondents, so their numbers are less reliable."
        );

        women.setSourceLinksJson(
            "[{\"name\":\"NFHS-5 (National Family Health Survey)\",\"url\":\"https://dhsprogram.com/data/dataset_admin/login_main.cfm\"}]"
        );
        seedIfMissing(women);
    }

    private void seedIfMissing(Dataset d) {
        if (repository.findBySlug(d.getSlug()).isEmpty()) {
            repository.save(d);
        }
    }
}
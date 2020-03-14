package com.melihcanclk.coronavirustracker.services;

import com.melihcanclk.coronavirustracker.models.LoadModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusService {
    private static String VIRUS_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private List<LoadModel> allModels = new ArrayList<>();
    private int minus = -1;

    @PostConstruct
    @Scheduled(cron = "0 1 1 * * ?", zone = "Europe/Istanbul")
    public void fetchData() throws IOException, InterruptedException {
        List<LoadModel> newModels = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBody = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBody);
        for (CSVRecord record : records) {
            // If informations are empty, use statistics of one day before
            if(record.get(record.size() - 1).equals(""))
                minus = -2;
            LoadModel loadModel = new LoadModel();
            loadModel.setState(record.get("Province/State"));
            loadModel.setCountry(record.get("Country/Region"));
            loadModel.setLastRecord(record.get(record.size() + minus));
            int last = Integer.parseInt(record.get(record.size() + minus));
            int prev = Integer.parseInt(record.get(record.size() + minus - 1));
            loadModel.setDiffFromPrevDay(last - prev);
            newModels.add(loadModel);
        }
        this.allModels = newModels;

    }

    public List<LoadModel> getAllModels() {
        return allModels;
    }
}

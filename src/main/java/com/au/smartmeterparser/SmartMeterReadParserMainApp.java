package com.au.smartmeterparser;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.au.smartmeter.domain.MeterRead;
import com.au.smartmeterparser.config.SmartMeterReadConfig;
import com.au.smartmeterparser.service.SimpleNemParserServiceImpl;
import com.au.smartmeterparser.service.util.CommonAppUtils;

/**
 * Main Application entry point to execute the SmartMeterReadParser
 * 
 * @author deepalipimparkar
 *
 */
@SpringBootApplication
@ComponentScan
public class SmartMeterReadParserMainApp implements CommandLineRunner {
	@Autowired
	SmartMeterReadConfig config;
	@Autowired
	SimpleNemParserServiceImpl simpleNemParserService;

	/**
	 * Launch SmartMeterReadParser app
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SmartMeterReadParserMainApp.class, args);
	}

	/**
	 * Method to parse sample meter read data upon start of the application
	 */
	private void parseSampleMeterReadData () {
		File simpleNemFile = new File("test.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(simpleNemFile);
		if ( CommonAppUtils.isNullOrEmpty(meterReads)) {
			System.out.println("No meter read available.");
		} else {
			meterReads.stream().forEach( mr-> {
				System.out.println(" NMI : " + mr.getNmi() + " EnergyUnit : " + mr.getEnergyUnit()  );
				  mr.getVolumes().entrySet().stream()
			      .forEach(e -> 
	    			System.out.println( " Date :"  + e.getKey() + " Quality:" +  e.getValue().getQuality() + " Volume:" + e.getValue().getVolume()));
			});
		}
		
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("------ This application parse meter read data from smart meters -----");
		parseSampleMeterReadData();
	}
}

package com.au.smartmeterparser.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.au.smartmeter.domain.EnergyUnit;
import com.au.smartmeter.domain.MeterRead;
import com.au.smartmeter.domain.MeterVolume;
import com.au.smartmeter.domain.Quality;
import com.au.smartmeterparser.config.SmartMeterReadConfig;
import com.au.smartmeterparser.exception.InvalidRowException;
import com.au.smartmeterparser.service.util.CommonAppUtils;

 

/**
 * This class parse the meter reading block and it's details i.e. meter volume, size for a specific date
 * 
 * @author deepalipimparkar
 *
 */
@Service
public class SimpleNemParserServiceImpl implements SimpleNemParserService {
	@Autowired
	SmartMeterReadConfig config;
	@Autowired
	SimpleNemFileRowValidatorServiceImpl validatorService;


	/**
	 * This method parses the smart meter read data from csv file
	 * 
	 * @return List<MeterRead>
	 */
	@Override
	public List<MeterRead> parseSimpleNem(File simpleNemFile) {
		List<MeterRead> meterReads = null;
		try {
			if (CommonAppUtils.isNull(simpleNemFile)) {
				throw new InvalidRowException("File is empty!");
			}
			
			meterReads = processFile(simpleNemFile);
		} catch (InvalidRowException | IOException e) {
			e.printStackTrace();
		}
		return meterReads;
	}
	
	/**
	 * Process each row , validate it and parse meter read data
	 * @param file
	 * @return List<MeterRead>
	 * @throws InvalidRowException
	 * @throws IOException
	 */
	protected List<MeterRead> processFile(File file) throws InvalidRowException, IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;
		List<MeterRead> listOfMeterRead = new ArrayList<MeterRead>();

		try {
			inputStream = new FileInputStream(file);
			sc = new Scanner(inputStream, "UTF-8");

			// Validate first row
			if (validatorService.isFirstRowValid(sc.nextLine())) {
				MeterRead meterRead = null;
				while (sc.hasNextLine()) {
					String line = sc.nextLine();

					// validate the row for start read block i.e. starting with 200 and NMI length is 10
					if (validatorService.isRowMeterReadStartBlock(line)) {
						if (validatorService.isValidStartMeterReadBlock(line)) {
							if (null != meterRead) {
								listOfMeterRead.add(meterRead);
							}
							meterRead = readBlock.apply(line);
						}
					// validate the row for meter read details block i.e. starting with 300	
					} else if (validatorService.isRowMeterReadDetailsBlock(line)) {
						if (validatorService.isValidMeterReadDetailBlock(line)) {
							MeterReadingInfo info = parseMeterReadInfo(line);

							if (null != meterRead) {
								meterRead.appendVolume(info.getDate(), info.getMeterVolume());
							}
						}
					// validate the row for meter read ending block
					} else if (validatorService.isRowMeterReadEndBlock(line) && !sc.hasNextLine()) {
						System.out.println(" End of the file");
					} else {
						throw new InvalidRowException("Invalid row !") ;
					}
				}
				
				if (null != meterRead) {
					listOfMeterRead.add(meterRead);
				}
			} else {
				throw new InvalidRowException("First row is missing.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
		return listOfMeterRead;
	}
	 
	/**
	 * Set meter read volume and quality for a specific date
	 * @param line
	 * @return
	 */
	private MeterReadingInfo parseMeterReadInfo(String line) {
		String[] p = line.split(",");// a CSV has comma separated lines
		MeterReadingInfo meterReadInfo = new MeterReadingInfo();

		LocalDate localDate = LocalDate.parse(p[1], DateTimeFormatter.BASIC_ISO_DATE);

		meterReadInfo.setDate(localDate)
					.setMeterVolume(new MeterVolume(new BigDecimal(p[2]), Quality.valueOf(p[3])));
		return meterReadInfo;
	}
	
	/**
	 * POJO class which holds meter reading details 
	 * @author deepalipimparkar
	 *
	 */
	private class MeterReadingInfo {
		LocalDate date;
		MeterVolume meterVolume;

		protected LocalDate getDate() {
			return date;
		}

		protected MeterReadingInfo setDate(LocalDate date) {
			this.date = date;
			return this;

		}

		protected MeterVolume getMeterVolume() {
			return meterVolume;
		}

		protected MeterReadingInfo setMeterVolume(MeterVolume meterVolume) {
			this.meterVolume = meterVolume;
			return this;
		}
	}
	
	/**
	 * Java function to read starting meter read block
	 */
	private Function<String, MeterRead> readBlock = (line) -> {
		String[] columns = line.split(",");
		MeterRead meterRead = new MeterRead(columns[1], EnergyUnit.valueOf(columns[2]));
		return meterRead;
	};
}

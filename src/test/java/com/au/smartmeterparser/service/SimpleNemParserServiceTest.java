package com.au.smartmeterparser.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.au.smartmeter.domain.MeterRead;

/**
 * Testing of the service class SimpleNemParserService
 * 
 * @author deepalipimparkar
 *
 */
@SpringBootTest
public class SimpleNemParserServiceTest {
	@Autowired
	SimpleNemParserServiceImpl simpleNemParserService;

	@Test
	public void testParseSimpleNemWithNullFile() {
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(null);
	    assertEquals(null, meterReads);
	}
	
	@Test
	public void testParseSimpleNemWithMissingFirstRow() {
		File inputFile = new File("simple-nem-missing-first-row.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
		assertEquals(null, meterReads);
	}
	
	@Test
	public void testParseSimpleNemWithMissingMeterReadStartRowHavingDetailsRows() {
		File inputFile = new File("simple-nem-missing-start-row.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
	    assertEquals(0, meterReads.size());
	}
	
	@Test
	public void testParseSimpleNemWithMissingStartAndDetailsRows() {
		File inputFile = new File("simple-nem-missing-start-and-details-row.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
	    assertEquals(0, meterReads.size());
	}
	
	@ParameterizedTest(name = "Run {index}: inputFile={0}")
	@MethodSource({ "testParseSimpleNemWithCorrectData"})
	public void testParseSimpleNem(File inputFile )  {
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
		System.out.println("testParseSimpleNemWithCorrectData() total meter read blocks" + meterReads.size()) ;
		assertEquals(2, meterReads.size());
 	}

	static Stream<Arguments> testParseSimpleNemWithCorrectData() throws Throwable {
		File inputFile = new File("simple-nem-correct-data.csv");
		return Stream.of(Arguments.of(inputFile));
	}

	@Test
	public void testParseSimpleNemWithIncorrectNMI() {
		File inputFile = new File("simple-nem-incorrect-nmi.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
	    assertEquals(null, meterReads);
	}
	
	@Test
	public void testParseSimpleNemWithIncorrectEnergyUnit() {
		File inputFile = new File("simple-nem-incorrect-energy-unit.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
	    assertEquals(null, meterReads);
	}
	
	@Test
	public void testParseSimpleNemWithIncorrectDate() {
		File inputFile = new File("simple-nem-incorrect-date.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
	    assertEquals(null, meterReads);
	}
	
	@Test
	public void testParseSimpleNemWithIncorrectVolume() {
		File inputFile = new File("simple-nem-incorrect-volume.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
	    assertEquals(null, meterReads);
	}
	
	@Test
	public void testParseSimpleNemWithIncorrectQuality() {
		File inputFile = new File("simple-nem-incorrect-quality.csv");
		List<MeterRead> meterReads = simpleNemParserService.parseSimpleNem(inputFile);
	    assertEquals(null, meterReads);
	}
}

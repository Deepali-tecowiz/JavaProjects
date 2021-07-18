package com.au.smartmeterparser.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.au.smartmeterparser.exception.InvalidRowException;

/**
 * Testing of the service class SimpleNemParserService
 * 
 * @author deepalipimparkar
 *
 */
@SpringBootTest
public class SimpleNemFileRowValidatorServiceTest {
	@Autowired
	SimpleNemFileRowValidatorServiceImpl simpleNemFileRowValidatorService;

	@Test
	public void testIsRowEmptyPositive() throws InvalidRowException {
		String row = "100";
		boolean result = simpleNemFileRowValidatorService.isRowEmpty(row);
		assertEquals(false,result);
	}
	 
	
	@Test
	public void testIsRowEmptyNegative() {
		String row = "";
	    Throwable exception = assertThrows(InvalidRowException.class,() -> simpleNemFileRowValidatorService.isRowEmpty(row));
	    	assertEquals("Row is empty.", exception.getMessage());
	}
	
	@Test
	public void testIsFirstRowValid_Positive() throws InvalidRowException {
		String row = "100";
		boolean result = simpleNemFileRowValidatorService.isFirstRowValid(row);
		assertEquals(true,result);
	}
	 
	
	@Test
	public void testIsFirstRowValid_Negative() throws InvalidRowException {
		String row = "200";
		boolean result = simpleNemFileRowValidatorService.isFirstRowValid(row);
		assertEquals(false,result);
	}
	
	@Test
	public void testIsRowMeterReadStartBlock_Positive() throws InvalidRowException {
		String row = "200,6123456789,KWH";
		boolean result = simpleNemFileRowValidatorService.isRowMeterReadStartBlock(row);
		assertEquals(true,result);
	}
	 
	
	@Test
	public void testIsRowMeterReadStartBlock_Negative() throws InvalidRowException {
		String row = "400,6123456789,KWH";
		boolean result = simpleNemFileRowValidatorService.isRowMeterReadStartBlock(row);
		assertEquals(false,result);
	}
	 
	@Test
	public void testIsRowMeterReadDetailsBlock_Positive() throws InvalidRowException {
		String row = "300,20161221,23.43,E";
		boolean result = simpleNemFileRowValidatorService.isRowMeterReadDetailsBlock(row);
		assertEquals(true,result);
	}
	 
	
	@Test
	public void testIsRowMeterReadDetailsBlock_Negative() throws InvalidRowException {
		String row = "400,20161221,23.43,E";
		boolean result = simpleNemFileRowValidatorService.isRowMeterReadDetailsBlock(row);
		assertEquals(false,result);
	}
	
	
	@Test
	public void testIsValidStartMeterReadBlock_Positive() throws InvalidRowException {
		String row = "200,6123456789,KWH";
		boolean result = simpleNemFileRowValidatorService.isValidStartMeterReadBlock(row);
		assertEquals(true,result);
	}
	 
	
	@Test
	public void testIsValidStartMeterReadBlock_Negative() throws InvalidRowException {
		String row = "200,612345678911,KWH";
 	    assertThrows(InvalidRowException.class,() -> simpleNemFileRowValidatorService.isValidStartMeterReadBlock(row));
	}
	
	@Test
	public void testIsValidMeterReadDetailBlock_Positive() throws InvalidRowException {
		String row = "300,20161221,23.43,E";
		boolean result = simpleNemFileRowValidatorService.isValidMeterReadDetailBlock(row);
		assertEquals(true,result);
	}
	 
	
	@Test
	public void testIsValidMeterReadDetailBlock_Negative() throws InvalidRowException {
		String row = "300,220161221,23.43,E";
 	    assertThrows(InvalidRowException.class,() -> simpleNemFileRowValidatorService.isValidMeterReadDetailBlock(row));
	}
}

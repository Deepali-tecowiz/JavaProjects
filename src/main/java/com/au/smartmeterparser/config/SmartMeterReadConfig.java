package com.au.smartmeterparser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * List out the application specific properties
 * 
 * @author deepalipimparkar
 *
 */
@Component
public class SmartMeterReadConfig {
	@Value("${smartmeterread.first.read.block}")
	public String RECORD_TYPE_100;
	
	@Value("${smartmeterread.start.read.block}")
	public String RECORD_TYPE_200;
	
	@Value("${smartmeterread.detail.read.block}")
	public String RECORD_TYPE_300;
	
	@Value("${smartmeterread.last.read.block}")
	public String RECORD_TYPE_900;
}

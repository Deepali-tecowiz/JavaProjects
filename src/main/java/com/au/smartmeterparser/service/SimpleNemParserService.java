package com.au.smartmeterparser.service;

 /**
 * Interface to parse meter reading block and it's details
 */

import java.io.File;
import java.util.List;

import com.au.smartmeter.domain.MeterRead;

public interface SimpleNemParserService {

  /**
   * Parses Simple NEM12 file.
   * 
   * @param simpleNem12File file in Simple NEM12 format
   * @return List of <code>MeterRead</code> that represents the data in the given file.
   */
	List<MeterRead> parseSimpleNem(File simpleNem12File);

}

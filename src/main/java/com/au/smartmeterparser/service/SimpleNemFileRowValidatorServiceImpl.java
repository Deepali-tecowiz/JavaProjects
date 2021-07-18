package com.au.smartmeterparser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.au.smartmeter.domain.EnergyUnit;
import com.au.smartmeter.domain.Quality;
import com.au.smartmeterparser.config.SmartMeterReadConfig;
import com.au.smartmeterparser.exception.InvalidRowException;
import com.au.smartmeterparser.service.util.CommonAppUtils;

/**
 * This class validates the simple nem file row for it's starting , read and
 * ending block and it's values
 * 
 * @author deepalipimparkar
 *
 */
@Service
public class SimpleNemFileRowValidatorServiceImpl {

	@Autowired
	SmartMeterReadConfig config;

	private static String COMMA = ",";

	public boolean isRowEmpty(String row) throws InvalidRowException {
		if (CommonAppUtils.isStringNullOrEmpty(row)) {
			throw new InvalidRowException("Row is empty.");
		} else {
			return false;
		}
	}

	public boolean isFirstRowValid(String row) throws InvalidRowException {
		if (!isRowEmpty(row)) {
			String[] columnVal = row.split(COMMA);
			if (!columnVal[0].trim().equalsIgnoreCase(config.RECORD_TYPE_100)) {
				return false;
			}
		} else {
			throw new InvalidRowException("The first row is empty");
		}
		return true;
	}

	public boolean isValidStartMeterReadBlock(String row) throws InvalidRowException {
		String[] columnVal = row.split(COMMA);
		if (columnVal.length == 3 && isNMILengthValid(columnVal[1]) && isEngeryUnitValid(columnVal[2])) {
			return true;
		} else {
			throw new InvalidRowException("Invalid row. One of the column value is incorrect:" + row);
		}
	}

	public boolean isNMILengthValid(String NMIValue) {
		if (NMIValue.length() == 10) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isEngeryUnitValid(String engeryUnit) {
		if (engeryUnit.equalsIgnoreCase(EnergyUnit.KWH.name())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRowMeterReadDetailsBlock(String row) throws InvalidRowException {
		if (!isRowEmpty(row)) {
			String[] columnVal = row.split(COMMA);
			if (columnVal[0].trim().equalsIgnoreCase(config.RECORD_TYPE_300)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean isValidMeterReadDetailBlock(String row) throws InvalidRowException {
		String[] columnVal = row.split(COMMA);
		if (columnVal.length == 4 && isMeterReadDateValid(columnVal[1]) && isMeterReadVolumeValid(columnVal[2])
				&& isMeterReadQualityValid(columnVal[3])) {
			return true;
		} else {
			throw new InvalidRowException("Invalid row. One of the column value is incorrect:" + row);
		}
	}

	public boolean isRowMeterReadStartBlock(String row) throws InvalidRowException {
		if (!isRowEmpty(row)) {
			String[] columnVal = row.split(COMMA);
			if (!(columnVal[0].trim()).equalsIgnoreCase(config.RECORD_TYPE_200)) {
				return false;
			}
		} else {
			throw new InvalidRowException("The row is empty");
		}
		return true;

	}

	public boolean isMeterReadDateValid(String meterReadDate) {
		if (meterReadDate.length() == 8) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMeterReadQualityValid(String meterReadQuality) {
		if (meterReadQuality.equals(Quality.A.name()) || meterReadQuality.equals(Quality.E.name())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMeterReadVolumeValid(String volume) {
		if (CommonAppUtils.isNumber(volume)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRowMeterReadEndBlock(String row) throws InvalidRowException {
		String[] columnVal = row.split(COMMA);
		if (!isRowEmpty(row) && columnVal[0].equalsIgnoreCase(config.RECORD_TYPE_900.toString())) {
			return true;
		} else {
			return false;
		}
	}

}

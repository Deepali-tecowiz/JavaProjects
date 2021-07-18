# SimpleNem12Parser

## Task

NEM12 is a file format used to represent meter read data from smart meters.


Refer to comments for following files.
* `EnergyUnit.java`
* `MeterRead.java`
* `MeterVolume.java`
* `Quality.java`
* `SimpleNem12Parser.java`
* `TestHarness.java`

### SimpleNem12.csv format specifications
* You can assume, for this exercise, that no quotes or extraneous commas appear in the CSV data.
* First item on every line is the RecordType
* RecordType 100 must be the first line in the file
* RecordType 900 must be the last line in the file
* RecordType 200 represents the start of a meter read block.  This record has the following subsequent items (after RecordType).
You can assume each file does not contain more than one RecordType 200 with the same NMI.
  * NMI ("National Metering Identifier") - String ID representing the meter on the site, modelled in `MeterRead.nmi`.  Value should always be 10 chars long.
  * EnergyUnit - Energy unit of the meter reads, modelled in `EnergyUnit` enum type and `MeterRead.energyUnit`.
* RecordType 300 represents the volume of the meter read for a particular date.  This record has the following subsequent items (after RecordType).
  * Date - In the format yyyyMMdd (e.g. 20170102 is 2nd Jan, 2017).  Modelled in `MeterRead.volumes` map key.
  * Volume - Signed number value.  Modelled in `MeterVolume.volume`.
  * Quality - Represents quality of the meter read, whether it's Actual (A) or Estimate (E).  Value should always be A or E.  Modelled in `MeterVolume.quality`

Your tasks:
1. Write a list of any questions you would have to clarify these requirements.
2. Create a new class (e.g. SimpleNem12ParserImpl) that implements interface `SimpleNem12Parser` and associated test harness.

-----
How to run this application ?
In IDE - Import this project as maven project into the workspace of any IDE. Run SmartMeterReadParserMainApp.java as java application. This would run positive scenario where it processes input file and prints the result.
Run Main Application - Execute SmartMeterReadParserMainApp.java as a java application
Junit Test cases - You can exceute the test cases written for service implementation using Junit Runner. Make sure that JUnitRunner 5 is available in IDE.
Junit test cases are available at path src/test/java

On terminal - Go to terminal at the project directory where POM is places. Install packages using mvn clean install. 
Once this is done, target folder will have jar file created with the name smartmeterparser-0.0.1-snapshot.jar
Run this jar file to see the input test file being parsed and final output is displayed on the terminal


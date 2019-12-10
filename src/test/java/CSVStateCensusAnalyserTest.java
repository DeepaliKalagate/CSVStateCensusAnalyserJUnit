import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CSVStateCensusAnalyserTest
{
    private static final String STATE_CENSUS_DATA_CSV_FILE_PATH="/home/admin1/Desktop/CSVStateCensusAnalyser/src/main/resources/StateCensusData.csv";
    private static final String SortByDensityPerSqKm ="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/SortByDensityPerSqKm.json";
    private static final String SortByAreaInSqKm="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/SortByAreaInSqKm.json";
    private static final String SortState_JSON_FILE_PATH="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/SortByState.json";
    private static final String SortByPopulation="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/SortByPopulation.json";
    StateCensusAnalyser stateCensusAnalyser=new StateCensusAnalyser();

    @Test
    public void giveStateCensusData_CheckRecords_ShouldReturnCountNumber() throws StateException
    {
        int result = stateCensusAnalyser.givenStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Assert.assertEquals(29,result);
    }

    @Test
    public void giveStateCodeFileNameWrong_CheckName_ShouldThrowException()
    {
        try
        {
            int result = stateCensusAnalyser.givenStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(29,result);
        } catch (StateException e)
        {
            System.out.println("Exception : "+e.getMessage());
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_FILE,e.type);
        }

    }

    @Test
    public void giveStateCodeFilePathWrong_CheckExtension_ShouldThrowException()
    {
        try
        {
            int result = stateCensusAnalyser.givenStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(29,result);
        }
        catch (StateException e)
        {
            System.out.println("Exception :"+e.getMessage());
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_FILE,e.type);
        }
    }

    @Test
    public void givenStateCensusFile_IncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            int result = stateCensusAnalyser.givenStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(29,result);
        }
        catch (StateException e)
        {
            System.out.println("Exception :"+e.getMessage());
            Assert.assertEquals(StateException.ExceptionType.INVALID_DELIMITER,e.type);
        }
    }

    @Test
    public void giveStateCodeFile_CheckHeader_ShouldThrowException()
    {
        try
        {
            int result = stateCensusAnalyser.givenStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(37,result);
        }
        catch (StateException e)
        {
            System.out.println("Exception :"+e.getMessage());
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_HEADER,e.type);
        }
    }

    @Test
    public void giveStateCensusData_SortByStateName_ShouldWriteIntoJsonFile()
    {
        try
        {
            boolean result = stateCensusAnalyser.genericSortMethod(
                    STATE_CENSUS_DATA_CSV_FILE_PATH,
                    "State",SortState_JSON_FILE_PATH);
            Assert.assertEquals(29,result);
        } catch (IOException | StateException e)
        {
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_FILE,e);
        }
    }

    @Test
    public void giveStateCensusData_SortByStatePopulation_ShouldWriteIntoJsonFile()
    {
        try
        {
            boolean result = stateCensusAnalyser.genericSortMethod(STATE_CENSUS_DATA_CSV_FILE_PATH,
                    "Population",SortByPopulation  );
            Assert.assertEquals(29,result);
        } catch (IOException | StateException e)
        {
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_FILE,e);
        }
    }


    @Test
    public void sortCSVFile_UsingAreaInSqKmOfState_AlphabeticalOrder()
    {
        try
        {
            boolean result=stateCensusAnalyser.genericSortMethod(STATE_CENSUS_DATA_CSV_FILE_PATH,
                    "AreaInSqKm", SortByAreaInSqKm);
            Assert.assertEquals(true,result);

        }
        catch (StateException | IOException e)
        {
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_FILE, e);
        }
    }

    @Test
    public void sortCSVFile_UsingDensityPerSqKmOfState_WhenIncorrectFileType_TrowException()
    {
        try
        {
            boolean result=stateCensusAnalyser.genericSortMethod(
                    STATE_CENSUS_DATA_CSV_FILE_PATH,
                    "DensityPerSqKm", SortByDensityPerSqKm);
            Assert.assertEquals(true, result);
        }
        catch (StateException | IOException e)
        {
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_FILE,e);
        }
    }
}

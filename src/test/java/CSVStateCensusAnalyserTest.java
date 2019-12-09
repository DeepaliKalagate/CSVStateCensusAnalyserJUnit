import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CSVStateCensusAnalyserTest
{
    private static final String STATE_CENSUS_DATA_CSV_FILE_PATH="/home/admin1/Desktop/CSVStateCensusAnalyser/src/main/resources/StateCensusData.csv";
    StateCensusAnalyser stateCensusAnalyser=new StateCensusAnalyser();

    @Test
    public void giveStateCensusData_CheckRecords_ShouldReturnCountNumber() throws StateException
    {
        int result = stateCensusAnalyser.giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Assert.assertEquals(29,result);
    }

    @Test
    public void giveStateCodeFileNameWrong_CheckName_ShouldThrowException()
    {
        try
        {
            int result = stateCensusAnalyser.giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
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
            int result = stateCensusAnalyser.giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(29,result);
        }
        catch (StateException e)
        {
            System.out.println("Exception :"+e.getMessage());
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_FILE,e.type);
        }
    }

    @Test
    public void givenStatecensusFile_IncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            int result = stateCensusAnalyser.giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
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
            int result = stateCensusAnalyser.giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(37,result);

        }
        catch (StateException e)
        {
            System.out.println("Exception :"+e.getMessage());
            Assert.assertEquals(StateException.ExceptionType.NO_SUCH_HEADER,e.type);
        }
    }

    @Test
    public void giveStateCensusData_SortByStateName_ShouldWriteIntoJsonFile() throws StateException, IOException
    {
        int result = stateCensusAnalyser.sortCSVFile(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Assert.assertEquals(29,result);
    }

    @Test
    public void giveStateCensusData_SortByStatePopulation_ShouldWriteIntoJsonFile() throws IOException, StateException
    {
        int result = stateCensusAnalyser.sortByPopulation(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Assert.assertEquals(29,result);
    }

    @Test
    public void giveStateCensusData_SortByStateDensity_ShouldWriteIntoJsonFile() throws IOException, StateException
    {
        int result = stateCensusAnalyser.sortByDensity(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Assert.assertEquals(29,result);
    }
}

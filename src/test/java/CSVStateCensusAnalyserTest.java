import org.junit.Assert;
import org.junit.Test;

public class CSVStateCensusAnalyserTest
{
    private static final String STATE_CENSUS_DATA_CSV_FILE_PATH="/home/admin1/Desktop/CSVStateCensusAnalyser/src/main/resources/StateCensusData.csv";
    StateCensusAnalyser stateCensusAnalyser=new StateCensusAnalyser();

    @Test
    public void giveStateCensusData_CheckRecords_ShouldReturnCountNumber()
    {
        int result = stateCensusAnalyser.giveStateCensusData(STATE_CENSUS_DATA_CSV_FILE_PATH);
        Assert.assertEquals(29,result);
    }
}

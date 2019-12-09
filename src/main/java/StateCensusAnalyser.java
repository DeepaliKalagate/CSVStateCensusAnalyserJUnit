import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StateCensusAnalyser
{
    List<CSVStatesCensus> csvStateCensuses = new ArrayList<>();

    public int giveStateCensusData(String STATE_CENSUS_DATA_CSV_FILE_PATH) throws StateException {
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(STATE_CENSUS_DATA_CSV_FILE_PATH));
            CsvToBean<CSVStatesCensus> cavToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVStatesCensus.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<CSVStatesCensus> csvUserIterator = cavToBean.iterator();
            while (csvUserIterator.hasNext())
            {
                CSVStatesCensus csvStatesCensus = csvUserIterator.next();
                csvStateCensuses.add(csvStatesCensus);
                if (csvStatesCensus.getState()==null || csvStatesCensus.getPopulation()==0 || csvStatesCensus.getAreaInSqKm()==0 || csvStatesCensus.getDensityPerSqKm()==0)
                {
                    throw new StateException(StateException.ExceptionType.NO_SUCH_HEADER, "No Such Header in File");
                }
                count++;
            }
        }
        catch (RuntimeException e)
        {
            throw new StateException(StateException.ExceptionType.INVALID_DELIMITER,"Exception is Occured because of incorrect Delimiter or Header",e);
        }
        catch (NoSuchFileException e)
        {
            throw new StateException(StateException.ExceptionType.NO_SUCH_FILE,"Please Check File Extention! Give Correct Extension",e);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return count;
    }
}

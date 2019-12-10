
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import sun.security.timestamp.TSRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class StateCensusAnalyser
{
    private static final String STATE_CENSUS_DATA_CSV_FILE_PATH="/home/admin1/Desktop/CSVStateCensusAnalyserJUnit/src/main/resources/StateCensusData.csv";
    List<CSVStatesCensus> csvStateCensuses = new ArrayList<>();

    public int givenStateCensusData(String STATE_CENSUS_DATA_CSV_FILE_PATH) throws StateException
    {
        int count = 0;
        try
        {
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
                if (csvStatesCensus.getState()==null || csvStatesCensus.getPopulation()==null || csvStatesCensus.getAreaInSqKm()==null || csvStatesCensus.getDensityPerSqKm()==null)
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

    public void writeInJSONFile(List<CSVStatesCensus> list,String filePath) throws IOException
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        FileWriter writer = new FileWriter(filePath);
        writer.write(json);
        writer.close();
    }

    public boolean genericSortMethod(String csvFile,String fieldName, String SortByBasedOnField) throws IOException, StateException
    {
        givenStateCensusData(csvFile);
        Collections.sort(csvStateCensuses, new Comparator<CSVStatesCensus>()
        {
            public int compare(CSVStatesCensus csvStatesCensus1, CSVStatesCensus csvStatesCensus2)
            {
                try
                {
                    Field fieldType = CSVStatesCensus.class.getDeclaredField(fieldName);
                    fieldType.setAccessible(true);
                    Comparable stateCensusFieldValue1 = (Comparable) fieldType.get(csvStatesCensus1);
                    Comparable stateCensusFieldValue2 = (Comparable) fieldType.get(csvStatesCensus2);
                    return stateCensusFieldValue1.compareTo(stateCensusFieldValue2);
                }
                finally
                {
                    return 0;
                }
            }
        });
        writeInJSONFile(csvStateCensuses, SortByBasedOnField);
        return true;
    }
}
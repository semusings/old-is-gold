package bhuwanupadhyay;

import com.intuit.karate.cucumber.CucumberRunner;
import com.intuit.karate.cucumber.KarateStats;
import cucumber.api.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@CucumberOptions(tags = {"~@ignore"}) // important: do not use @RunWith(Karate.class) !
public class CucumberReportTestParallel {

    @Test
    public void testParallel() {
        String karateOutputPath = "target/surefire-reports";
        KarateStats stats = CucumberRunner.parallel(getClass(), 5, karateOutputPath);
        generateReport(karateOutputPath);
        assertEquals("there are scenario failures", 0, stats.getFailCount());
    }

    private void generateReport(String karateOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.stream().map(File::getAbsolutePath).forEach(jsonPaths::add);
        ReportBuilder builder = new ReportBuilder(jsonPaths, new Configuration(new File("target"), "Order Manager"));
        builder.generateReports();
    }

}
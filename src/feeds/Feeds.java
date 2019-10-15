package feeds;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author MSLEIBI
 */
public class Feeds {

    /**
     * @param args the command line arguments
     */
    public static void log(Object message) {
        System.out.println(message) ;
    }

    public static int calcCPU(long cpuStartTime, long elapsedStartTime, int cpuCount) {
        long end = System.nanoTime();
        long totalAvailCPUTime = cpuCount * (end - elapsedStartTime);
        long totalUsedCPUTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() - cpuStartTime;
        // log("Total CPU Time:" + totalUsedCPUTime + " ns.");
        // log("Total Avail CPU Time:" + totalAvailCPUTime + " ns.");
        float per = ((float) totalUsedCPUTime * 100) / (float) totalAvailCPUTime;
        log(per);
        return (int) per;
    }

    public static void main(String[] args) {
        int mb = 1024 * 1024;
        int gb = 1024 * 1024 * 1024;

        // Timer timer = new Timer();
        // TimerTask tasknew = new TimerTask() {
        //    @Override
        //     public void run() {
        @SuppressWarnings("restriction")
        com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();

        @SuppressWarnings("restriction")
        long physicalMemorySize = os.getTotalPhysicalMemorySize();

        @SuppressWarnings("restriction")
        long freeMemorySize = os.getFreePhysicalMemorySize();

        long usedMemorySize = physicalMemorySize - freeMemorySize;
        double memory = usedMemorySize / gb;
        long rest = Math.round(usedMemorySize % gb);
        String memoryEx = String.valueOf(memory).substring(0, 1) + "." + String.valueOf(rest).substring(0, 2);

        /* PHYSICAL MEMORY USAGE */
        System.out.println("\n**** Sizes in Giga Bytes ****\n");
        //System.out.println("Total Physical Memory: " + physicalMemorySize / gb + "," + (physicalMemorySize % gb) + " GB");
        //System.out.println("Used Physical Memory: " + String.valueOf(memory).substring(0,4) + " GB \n");
        System.out.println("Used Physical Memory: " + memoryEx + " GB \n");
        //System.out.println("Free Physical Memory: " + freeMemorySize / gb + "," + (freeMemorySize % gb) + " GB");

        try {
            String url = "https://kosmos-int.ov.otto.de/dispatcher-feedserver/v1/changes/atom/0";

            try (XmlReader reader = new XmlReader(new URL(url))) {

                SyndFeed feed = new SyndFeedInput().build(reader);

                System.out.println("*********** " + feed.getTitle() + " ************************");

                System.out.println("Feeds size: " + feed.getEntries().size());
                System.out.println("Done");

                System.out.println("***********************************");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //  };
    //timer.schedule(tasknew, 3000, 3000);
    //}
}

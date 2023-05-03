package fiit.oop.oop_ticket_ordering_system.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class DatesUtils {
    // Singleton instance
    private static final DatesUtils instance = new DatesUtils();

    // Private constructor to prevent instantiation from outside
    private DatesUtils() {
    }

    // Public static method to get the singleton instance
    public static DatesUtils getInstance() {
        return instance;
    }

    /**
     * Generates the next N dates that belong to the specified day of the week.
     *
     * @param n         The number of dates to generate
     * @param dayOfWeek The day of the week to generate dates for
     * @return A list of N dates that belong to the specified day of the week
     */
    public List<LocalDate> getNextNDatesForDayOfWeek(int n, DayOfWeek dayOfWeek) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        // Keep adding dates until we have N dates
        while (dates.size() < n) {
            // Get the next or current occurrence of the specified day of the week
            currentDate = currentDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
            dates.add(currentDate);
            currentDate = currentDate.plusWeeks(1);
        }

        return dates;
    }
}

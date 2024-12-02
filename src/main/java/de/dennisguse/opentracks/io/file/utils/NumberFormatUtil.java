import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatUtils {
    public static final NumberFormat ALTITUDE_FORMAT;
    public static final NumberFormat COORDINATE_FORMAT;
    public static final NumberFormat SPEED_FORMAT;
    public static final NumberFormat DISTANCE_FORMAT;
    public static final NumberFormat HEARTRATE_FORMAT;
    public static final NumberFormat CADENCE_FORMAT;
    public static final NumberFormat POWER_FORMAT;

    static {
        ALTITUDE_FORMAT = NumberFormat.getInstance(Locale.US);
        ALTITUDE_FORMAT.setMaximumFractionDigits(1);
        ALTITUDE_FORMAT.setGroupingUsed(false);

        COORDINATE_FORMAT = NumberFormat.getInstance(Locale.US);
        COORDINATE_FORMAT.setMaximumFractionDigits(6);
        COORDINATE_FORMAT.setMaximumIntegerDigits(3);
        COORDINATE_FORMAT.setGroupingUsed(false);

        SPEED_FORMAT = NumberFormat.getInstance(Locale.US);
        SPEED_FORMAT.setMaximumFractionDigits(2);
        SPEED_FORMAT.setGroupingUsed(false);

        DISTANCE_FORMAT = NumberFormat.getInstance(Locale.US);
        HEARTRATE_FORMAT = NumberFormat.getInstance(Locale.US);
        CADENCE_FORMAT = NumberFormat.getInstance(Locale.US);
        POWER_FORMAT = NumberFormat.getInstance(Locale.US);
    }
}

package halo.com.moneytracker.evenbus.events;

import java.util.Date;
        import java.util.List;

        import halo.com.moneytracker.models.Exchange;
        import lombok.Data;
        import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Created by HoVanLy on 8/2/2016.
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class OttoExchanges {
    private List<Exchange> exchanges;
    private Date createdDate;
    private int year;
    private int month;
}

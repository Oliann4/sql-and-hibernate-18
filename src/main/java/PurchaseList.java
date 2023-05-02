import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "purchaselist")
public class PurchaseList {

    @EmbeddedId
    private PurchaseListKey id;

    private Integer price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public PurchaseList() {
    }
}

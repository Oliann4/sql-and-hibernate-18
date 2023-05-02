import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @EmbeddedId
    private SubscriptionKey id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",
            insertable = false, updatable = false, nullable = false,
            foreignKey = @ForeignKey(name = "student")
    )
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",
            insertable = false, updatable = false, nullable = false,
            foreignKey = @ForeignKey(name = "course")
    )
    private Course course;

    @Column(name = "subscription_date", nullable = false)
    private Date subscriptionDate;

    public Subscription() {}
}

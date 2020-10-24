package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;


import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@SequenceGenerator(name = "SEQ_DONATION", sequenceName = "SEQUENCE_DONATION", initialValue = 1, allocationSize = 1)
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DONATION")
    private Integer id;

    @Column
    private String donorNickname;

    @Column
    private String projectName;

    @Column
    private BigDecimal amount;

    @Column
    private String comment;

    @Column
    private LocalDate date;

    public Donation() {}

    public Donation(String donorNickname, String projectName, BigDecimal amount, String comment, LocalDate date) {
        this.donorNickname = donorNickname;
        this.projectName = projectName;
        this.amount = amount;
        this.comment = comment;
        this.date = date;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getDonorNickname() {
        return donorNickname;
    }

    public void setDonorNickname(String donorNickname) {
        this.donorNickname = donorNickname;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int pointsFromLastDonationOnSameMonth() {
        return 500;
    }

    public int calculatePoints(Donor donor, Project project) {
        int currentDonationPoints = 0;
        Optional<Donation> donorUserLastDonation = donor.lastDonation();

        if (amount.compareTo(new BigDecimal(1000)) > 0) {
            currentDonationPoints = amount.intValue();
        }
        if (project.locationPopulation() < 2000) {
            currentDonationPoints = amount.intValue() * 2;
        }
        LocalDate lastDate = donorUserLastDonation.map(Donation::getDate).orElse(null);
        if (lastDate != null && (LocalDate.now().getMonthValue() == lastDate.getMonthValue()) && (LocalDate.now().getYear() == lastDate.getYear())) {
            currentDonationPoints += pointsFromLastDonationOnSameMonth();
        }
        return currentDonationPoints;
    }

}

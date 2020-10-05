package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@SequenceGenerator(name = "SEQ_DONATION", sequenceName = "SEQUENCE_DONATION", initialValue = 1, allocationSize = 1)
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DONATION")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donorUser_id")
    private DonorUser donorUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column
    private BigDecimal amount;

    @Column
    private String comment;

    @Column
    private LocalDate date;

    public Donation() {}

    public Donation(DonorUser donorUser, Project project, BigDecimal amount, String comment, LocalDate date) {
        this.donorUser = donorUser;
        this.project = project;
        this.amount = amount;
        this.comment = comment;
        this.date = date;

    }

    public DonorUser getDonorUser() {
        return donorUser;
    }

    public void setDonorUser(DonorUser donorUser) {
        this.donorUser = donorUser;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public int calculatePoints(DonorUser donorUser, Project project) {
        int currentDonationPoints = 0;
        Optional<Donation> donorUserLastDonation = donorUser.getLastDonation();

        if (amount.compareTo(new BigDecimal(1000)) > 0) {
            currentDonationPoints = amount.intValue();
        }
        if (project.getLocationPopulation() < 2000) {
            currentDonationPoints = amount.intValue() * 2;
        }
        LocalDate lastDate = donorUserLastDonation.map(Donation::getDate).orElse(null);
        if (lastDate != null && (LocalDate.now().getMonthValue() == lastDate.getMonthValue()) && (LocalDate.now().getYear() == lastDate.getYear())) {
            currentDonationPoints += pointsFromLastDonationOnSameMonth();
        }
        return currentDonationPoints;
    }

}

package com.orion.DigiWallet.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

//TODO: 3.3
// Define this class as a JPA entity mapped to "rewards_point" table
// ADD @table annotation to specify the table name as "rewards_point"
@Entity
@Table(name="rewards_point")
public class RewardsPoint {

    //TODO: 3.3.1
    // Define id as primary key with auto-generation strategy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //TODO: 3.3.2
    // Define points field to store the number of reward points
    @Column(name = "reward_points")
    private int rewardPoints;

    //TODO: 3.3.3
    // Define wallet field to establish many-to-one relationship with Wallet entity
    // Use @JoinColumn with name "wallet_id" and make it NOT NULL
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    //TODO: 3.3.4
    // Define updatedAt field to store the timestamp of last update
    // This value should be automatically updated whenever points are modified
    private LocalDateTime updatedAt;

    //TODO: 3.3.5
    // Generate getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    @PreUpdate
    public void preUpdate() {
        // Automatically update the timestamp whenever points are modified
        this.updatedAt = LocalDateTime.now();
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

//    @PreUpdate
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = LocalDateTime.now();
    }


    //TODO: 3.3.6
    // THIS TABLE IS NOT THERE IN MYSQL SO WHEN YOU RUN IT JPA WILL CREATE IT AUTOMATICALLY
    // GO TO MYSQL AND VERIFY IF THE TABLE IS CREATED OR NOT
    // ALSO CHECK FIELD NAMES AND TYPES
    // WITH CONSTRAINTS

}

package com.green.yogizogi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "myStoreImageSeq", sequenceName = "storeimg_seq", allocationSize = 1)
public class StoreImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myStoreImageSeq")
    private Long inum;
    private String uuid;
    private String imgName;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;
}

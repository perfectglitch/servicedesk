package com.example.energia.servicedesk.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private Date created;

    @Min(1)
    @Max(3)
    private Integer status;

    @Min(1)
    @Max(5)
    private Integer priority;

    private String summary;
    private String description;
    private String email;

}

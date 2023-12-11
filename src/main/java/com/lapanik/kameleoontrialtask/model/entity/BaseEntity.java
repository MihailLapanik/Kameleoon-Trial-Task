package com.lapanik.kameleoontrialtask.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(
            name = "created",
            title = "Created Date",
            description = "When the record was originally created (ISO8601/UTC)",
            example = "2023-11-08T13:52:42Z",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Date createDate;

    @LastModifiedDate
    @JsonProperty(access = READ_ONLY)
    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "update_at")
    @Schema(
            name = "updated",
            title = "Modified Date",
            description = "When the record was last modified (ISO8601/UTC)",
            example = "2023-12-08T14:37:05Z",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Date updateDate;
}

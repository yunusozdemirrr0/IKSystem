package com.example.iksystem.entity;
import com.example.iksystem.enums.model.constant.AttachmentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


import java.util.UUID;

@Entity
@Table(name = "PROTOCOL_ATTACHMENTS")
@Data


public class ProtocolAttachmentEntity {
    @Id
    private UUID id;
    private String filePathUrl;
    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;
    private Boolean showPersonel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private ProtocolsEntity protocol;


}

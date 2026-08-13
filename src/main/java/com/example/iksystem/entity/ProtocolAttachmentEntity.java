package com.example.iksystem.entity;
import com.example.iksystem.enums.model.constant.AttachmentType;
import jakarta.persistence.*;
import lombok.Data;


import java.util.UUID;
/**
 * Bu sınıf Protocol Attachment Entity nesnesini temsil eder.
 */

@Entity
@Table(name = "PROTOCOL_ATTACHMENTS")
@Data


public class ProtocolAttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "file_path_url")
    private String filePathUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private AttachmentType attachmentType;
    @Column(name = "show_personel")
    private Boolean showPersonel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private ProtocolsEntity protocol;


}

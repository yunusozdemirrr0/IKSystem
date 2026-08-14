package com.example.iksystem.entity;

import com.example.iksystem.enums.model.constant.AttachmentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * Bu sınıf Protocol Attachment Entity nesnesini temsil eder.
 */
@Entity
@Table(name = "protocol_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProtocolAttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path_url", nullable = false)
    private String filePathUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private AttachmentType attachmentType;

    @Builder.Default
    @Column(name = "show_personel", nullable = false)
    private Boolean showPersonel = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private ProtocolsEntity protocol;
}